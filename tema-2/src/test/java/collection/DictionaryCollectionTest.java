package collection;

import exception.InputDirectoryException;
import exception.InputFileNameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.Definition;
import pojo.Word;
import reader.DictionaryCollectionReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

class DictionaryCollectionTest {
    @Test
    void addWord_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            Word word = new Word("maison", null, null, null, null, null);

            Assertions.assertTrue(dictionaryCollection.addWord(word, "fr"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addWord_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            Word word = new Word("câine", null, null, null, null, null);

            Assertions.assertFalse(dictionaryCollection.addWord(word, "ro"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeWord_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertTrue(dictionaryCollection.removeWord("chat", "fr"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeWord_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertFalse(dictionaryCollection.removeWord("maison", "fr"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addDefinitionForWord_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            Definition definition = new Definition("Un dictionar", "definitions", 2000, null);

            Assertions.assertTrue(dictionaryCollection.addDefinitionForWord("merge", "ro", definition));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addDefinitionForWord_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            Definition definition = new Definition(
                    "Larousse",
                    "synonyms",
                    2000,
                    Arrays.asList("absorber", "becqueter", "bouffer", "boulotter", "briffer", "ingurgiter")
            );

            Assertions.assertFalse(dictionaryCollection.addDefinitionForWord("manger", "fr", definition));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeDefinition_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertTrue(dictionaryCollection.removeDefinition("jeu", "fr", "Laroussesynonyms2000"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeDefinition_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertFalse(dictionaryCollection.removeDefinition("jeu", "fr", "Laroussesynonyms2020"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void translateWord_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertEquals("chat", dictionaryCollection.translateWord("pisică", "ro", "fr"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void translateWord_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertEquals("câine", dictionaryCollection.translateWord("câine", "ro", "fr"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void translateSentence_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertEquals(
                    "pisici mănâncă",
                    dictionaryCollection.translateSentence("Chats mangent.", "fr", "ro")
            );
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void translateSentence_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertEquals(
                    "De ce hibernează urșii",
                    dictionaryCollection.translateSentence("De ce hibernează urșii?", "ro", "fr")
            );
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void translateSentences_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            List<String> expectedSentences = Arrays.asList("chat mange", "minet mange", "greffier mange");

            Assertions.assertEquals(
                    expectedSentences,
                    dictionaryCollection.translateSentences("Pisică mănâncă.", "ro", "fr")
            );
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void translateSentences_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            List<String> expectedSentences = List.of("Sunt student integralist bursier");

            Assertions.assertEquals(
                    expectedSentences,
                    dictionaryCollection.translateSentences("Sunt student integralist bursier.", "ro", "fr")
            );
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDefinitionsForWord_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            List<Definition> expectedDefinitions = Arrays.asList(
                    new Definition(
                            "Dicționar de sinonime",
                            "synonyms",
                            1998,
                            Arrays.asList("a păpa", "a consuma", "a (se) ospăta")
                    ),
                    new Definition(
                            "Dicționarul explicativ al limbii române (ediția a II-a revăzută și adăugită)",
                            "definitions",
                            2009,
                            Arrays.asList(
                                    "A mesteca un aliment în gură și a-l înghiți; a folosi în alimentație, a consuma",
                                    "A rupe prada în bucăți, a sfâșia (și a devora)",
                                    "A face să dispară; a consuma, a nimici, a distruge"
                            )
                    )
            );

            Assertions.assertEquals(expectedDefinitions, dictionaryCollection.getDefinitionsForWord("mânca", "ro"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDefinitionsForWord_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");

            Assertions.assertNull(dictionaryCollection.getDefinitionsForWord("pahar", "ro"));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    void exportDictionary_success() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            dictionaryCollection.exportDictionary("ro");

            Assertions.assertEquals(-1L, Files.mismatch(Path.of("ro_dict.json"), Path.of("ref/ro_dict.json")));
        } catch (InputDirectoryException | InputFileNameException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void exportDictionary_fail() {
        try {
            DictionaryCollection dictionaryCollection = DictionaryCollectionReader.readAllFromDirectory("init");
            dictionaryCollection.exportDictionary("de");

            Assertions.assertFalse(Files.exists(Path.of("de_dict.json")));
        } catch (InputDirectoryException | InputFileNameException e) {
            e.printStackTrace();
        }
    }
}