package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Word {
    @SerializedName("word")
    private String word;

    @SerializedName("word_en")
    private String englishWord;

    @SerializedName("type")
    private String partOfSpeech;

    @SerializedName("singular")
    private List<String> singularForms;

    @SerializedName("plural")
    private List<String> pluralForms;

    @SerializedName("definitions")
    private List<Definition> definitions;

    public Word(String word,
                String englishWord,
                String partOfSpeech,
                List<String> singularForms,
                List<String> pluralForms,
                List<Definition> definitions) {
        this.word = word;
        this.englishWord = englishWord;
        this.partOfSpeech = partOfSpeech;
        this.singularForms = singularForms;
        this.pluralForms = pluralForms;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<String> getSingularForms() {
        return singularForms;
    }

    public void setSingularForms(List<String> singularForms) {
        this.singularForms = singularForms;
    }

    public List<String> getPluralForms() {
        return pluralForms;
    }

    public void setPluralForms(List<String> pluralForms) {
        this.pluralForms = pluralForms;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return word.equals(word1.word)
                && englishWord.equals(word1.englishWord)
                && partOfSpeech.equals(word1.partOfSpeech)
                && singularForms.equals(word1.singularForms)
                && pluralForms.equals(word1.pluralForms)
                && definitions.equals(word1.definitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, englishWord, partOfSpeech, singularForms, pluralForms, definitions);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", englishWord='" + englishWord + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", singularForms=" + singularForms +
                ", pluralForms=" + pluralForms +
                ", definitions=" + definitions +
                '}';
    }
}
