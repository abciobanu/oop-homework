package pojo;

import java.util.Objects;

public class TranslationUnit {
    private Word word;
    private boolean isSingular;
    private int formIndex;

    public TranslationUnit(Word word, boolean isSingular, int formIndex) {
        this.word = word;
        this.isSingular = isSingular;
        this.formIndex = formIndex;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public boolean isSingular() {
        return isSingular;
    }

    public void setSingular(boolean singular) {
        isSingular = singular;
    }

    public int getFormIndex() {
        return formIndex;
    }

    public void setFormIndex(int formIndex) {
        this.formIndex = formIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationUnit that = (TranslationUnit) o;
        return isSingular == that.isSingular && formIndex == that.formIndex && word.equals(that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, isSingular, formIndex);
    }

    @Override
    public String toString() {
        return "TranslationUnit{" +
                "word=" + word +
                ", isSingular=" + isSingular +
                ", formIndex=" + formIndex +
                '}';
    }
}
