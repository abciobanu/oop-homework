package collection;

import com.google.gson.Gson;
import pojo.Definition;
import pojo.TranslationUnit;
import pojo.Word;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DictionaryCollection {
    private final HashMap<String, HashMap<String, Word>> collection;

    public DictionaryCollection(HashMap<String, HashMap<String, Word>> collection) {
        this.collection = collection;
    }

    /**
     * Adds a word to the dictionary.
     *
     * @param word     The word to be added.
     * @param language The language for which the word is added.
     * @return true if the word has been added; false if there is no dictionary for the language or the word already
     * exists in the dictionary.
     */
    public boolean addWord(Word word, String language) {
        HashMap<String, Word> dictionary = collection.get(language);
        if (dictionary == null) {
            System.err.println("There is no dictionary for the given language: " + language);
            return false;
        }

        String wordString = word.getWord();

        if (dictionary.containsKey(wordString)) {
            System.err.println("The word \"" + wordString + "\" already exists in the dictionary");
            return false;
        }

        dictionary.put(wordString, word);
        return true;
    }

    /**
     * Removes a word from the dictionary.
     *
     * @param word     The word to be removed.
     * @param language The language for which the word is removed.
     * @return true if the word has been removed; false if there is no dictionary for the language or the word does not
     * exist in the dictionary.
     */
    public boolean removeWord(String word, String language) {
        HashMap<String, Word> dictionary = collection.get(language);
        if (dictionary == null) {
            System.err.println("There is no dictionary for the given language: " + language);
            return false;
        }

        Word removedWord = dictionary.remove(word);

        if (removedWord == null) {
            System.err.println("The word \"" + word + "\" does not exist in the dictionary");
            return false;
        }

        return true;
    }

    // returns a TranslationUnit object that corresponds to the given word
    private TranslationUnit getTranslationUnit(String word, String fromLanguage) {
        HashMap<String, Word> dictionary = collection.get(fromLanguage);
        if (dictionary == null) {
            // there is no dictionary for the given language
            return null;
        }

        // iterates through the dictionary and searches for the word in the given form
        for (Map.Entry<String, Word> wordEntry : dictionary.entrySet()) {
            Word currentWord = wordEntry.getValue();

            if (currentWord.getWord().equals(word)) {
                return new TranslationUnit(currentWord, true, -1);
            }

            List<String> singularForms = currentWord.getSingularForms();
            for (int i = 0; i < singularForms.size(); ++i) {
                if (singularForms.get(i).equals(word)) {
                    return new TranslationUnit(currentWord, true, i);
                }
            }

            List<String> pluralForms = currentWord.getPluralForms();
            for (int i = 0; i < pluralForms.size(); ++i) {
                if (pluralForms.get(i).equals(word)) {
                    return new TranslationUnit(currentWord, false, i);
                }
            }
        }

        // the word in the given form does not exist in the dictionary
        return null;
    }

    /**
     * Adds a new definition for a word.
     *
     * @param word       The word for which the definition is to be added.
     * @param language   The language of the word.
     * @param definition The definition to be added.
     * @return true if the definition has been added; false if the word does not exist in the dictionary or the
     * definition already exists.
     */
    public boolean addDefinitionForWord(String word, String language, Definition definition) {
        TranslationUnit translationUnit = getTranslationUnit(word, language);
        if (translationUnit == null) {
            System.err.println("The word \"" + word + "\" does not exist in the dictionary");
            return false;
        }

        Word queriedWord = translationUnit.getWord();
        List<Definition> definitions = queriedWord.getDefinitions();

        for (Definition existentDefinition : definitions) {
            if (existentDefinition.equals(definition)) {
                System.err.println("The given definition for the word \"" + word + "\" already exists");
                return false;
            }
        }

        definitions.add(definition);
        return true;
    }

    /**
     * Removes a definition for a word.
     *
     * @param word       The word for which the definition is to be removed.
     * @param language   The language of the word.
     * @param dictionary The unique identifier for a dictionary (a string consisting of the dictionary name,
     *                   concatenated with the type and year thereof).
     * @return true if the definition has been removed; false if the word does not exist in the dictionary or there is
     * no definition with the given unique identifier.
     */
    public boolean removeDefinition(String word, String language, String dictionary) {
        TranslationUnit translationUnit = getTranslationUnit(word, language);
        if (translationUnit == null) {
            System.err.println("The word \"" + word + "\" does not exist in the dictionary");
            return false;
        }

        Word queriedWord = translationUnit.getWord();
        List<Definition> definitions = queriedWord.getDefinitions();

        for (Definition existentDefinition : definitions) {
            StringBuilder dictionaryIdentifier = new StringBuilder()
                    .append(existentDefinition.getDictionaryName())
                    .append(existentDefinition.getDictionaryType())
                    .append(existentDefinition.getDictionaryYear());

            if (dictionaryIdentifier.toString().equals(dictionary)) {
                definitions.remove(existentDefinition);
                return true;
            }
        }

        System.err.println("There is no definition with the given unique identifier: " + dictionary);
        return false;
    }

    /**
     * Translates a word from one language to another.
     *
     * @param word         The word to be translated.
     * @param fromLanguage The language from which the word is translated.
     * @param toLanguage   The language in which the word is translated.
     * @return a String that contains the translated word or the given word parameter, if translation was not possible.
     */
    public String translateWord(String word, String fromLanguage, String toLanguage) {
        String lowerCaseWord = word.toLowerCase(Locale.ROOT);
        TranslationUnit sourceTranslationUnit = getTranslationUnit(lowerCaseWord, fromLanguage);
        if (sourceTranslationUnit == null) {
            // the word does not exist in the dictionary, so its original form is returned
            return word;
        }

        HashMap<String, Word> targetDictionary = collection.get(toLanguage);
        if (targetDictionary == null) {
            // there is no dictionary for the given language
            return word;
        }

        /*
         * iterates through the dictionary and searches for the word that has the same english form (word_en), thus
         * obtaining the translation
         */
        String englishForm = sourceTranslationUnit.getWord().getEnglishWord();
        for (Map.Entry<String, Word> wordEntry : targetDictionary.entrySet()) {
            Word currentWord = wordEntry.getValue();

            if (currentWord.getEnglishWord().equals(englishForm)) {
                boolean isSingular = sourceTranslationUnit.isSingular();
                int formIndex = sourceTranslationUnit.getFormIndex();

                List<String> singularForms = currentWord.getSingularForms();
                List<String> pluralForms = currentWord.getPluralForms();

                if (formIndex == -1) {
                    return currentWord.getWord();
                } else if (isSingular && formIndex < singularForms.size()) {
                    return singularForms.get(formIndex);
                } else if (!isSingular && formIndex < pluralForms.size()) {
                    return pluralForms.get(formIndex);
                } else {
                    // no appropriate form available for the translated word
                    return word;
                }
            }
        }

        // no translation available for the word
        return word;
    }

    /**
     * Translates a sentence from one language to another.
     *
     * @param sentence     The sentence to be translated.
     * @param fromLanguage The language from which the sentence is translated.
     * @param toLanguage   The language in which the sentence is translated.
     * @return a String that contains the translated sentence.
     */
    public String translateSentence(String sentence, String fromLanguage, String toLanguage) {
        // splits the sentence into words; anything that is not a letter or number is matched
        String[] words = sentence.split("[^\\p{L}\\p{N}]+");

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            String translatedWord = translateWord(word, fromLanguage, toLanguage);
            stringBuilder.append(translatedWord).append(" ");
        }

        // removes trailing whitespace
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    /**
     * Translates a sentence from one language to another and provides three variants of translation using synonyms of
     * the words in the sentence.
     *
     * @param sentence     The sentence to be translated.
     * @param fromLanguage The language from which the sentence is translated.
     * @param toLanguage   The language in which the sentence is translated.
     * @return an ArrayList of up to three String objects, representing the translation alternatives; if there are no
     * three translation variants, only the possible options will be provided.
     */
    public ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage) {
        ArrayList<String> sentences = new ArrayList<>();

        // the translated form of the original sentence, from which at most two more variants of translation are created
        String basicTranslatedSentence = translateSentence(sentence, fromLanguage, toLanguage);
        sentences.add(basicTranslatedSentence);

        // splits the sentence into words; anything that is not a letter or number is matched
        String[] words = basicTranslatedSentence.split("[^\\p{L}\\p{N}]+");

        for (String word : words) {
            TranslationUnit translationUnit = getTranslationUnit(word, toLanguage);
            if (translationUnit != null) {
                HashSet<String> synonyms = new HashSet<>();

                // gets all synonyms for the current word
                List<Definition> definitions = translationUnit.getWord().getDefinitions();
                for (Definition definition : definitions) {
                    if (definition.getDictionaryType().equals("synonyms")) {
                        synonyms.addAll(definition.getDictionaryText());
                    }
                }

                // uses the synonyms to form new sentences
                for (String synonym : synonyms) {
                    String newTranslatedSentence = basicTranslatedSentence.replace(word, synonym);
                    sentences.add(newTranslatedSentence);

                    // returns the list of sentences, if three variants have been obtained
                    if (sentences.size() == 3) {
                        return sentences;
                    }
                }
            }
        }

        // returns the list of all possible sentences (less than three variants)
        return sentences;
    }

    /**
     * Gets the definitions and synonyms for a word.
     *
     * @param word     The word for which definitions and synonyms are sought.
     * @param language The language to which the word belongs.
     * @return an ArrayList of Definition objects, in ascending order by dictionary year, or null, if the word does not
     * exist in the dictionary.
     */
    public ArrayList<Definition> getDefinitionsForWord(String word, String language) {
        TranslationUnit translationUnit = getTranslationUnit(word, language);
        if (translationUnit == null) {
            System.err.println("The word \"" + word + "\" does not exist in the dictionary");
            return null;
        }

        Word queriedWord = translationUnit.getWord();

        // gets a copy of the definitions of the word, then sorts it and finally returns it
        ArrayList<Definition> definitions = new ArrayList<>(List.copyOf(queriedWord.getDefinitions()));
        definitions.sort(Comparator.comparing(Definition::getDictionaryYear));

        return definitions;
    }

    /**
     * Exports a dictionary to a JSON file. The words are ordered alphabetically, and the definitions are ordered
     * ascending by dictionary year.
     *
     * @param language The language of the dictionary to be exported.
     */
    public void exportDictionary(String language) {
        HashMap<String, Word> dictionary = collection.get(language);
        if (dictionary == null) {
            System.err.println("There is no dictionary for the given language: " + language);
        } else {
            // gets the list of words of the given language and sorts the words alphabetically
            List<Word> words = new ArrayList<>(dictionary.values());
            words.sort(Comparator.comparing(Word::getWord));

            // then sorts the definitions of the words by the year of their dictionaries
            for (Word word : words) {
                word.getDefinitions().sort(Comparator.comparing(Definition::getDictionaryYear));
            }

            // serialization
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(language + "_dict.json"))) {
                Gson gson = new Gson();
                String json = gson.toJson(words);

                bufferedWriter.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
