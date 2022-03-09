package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Definition {
    @SerializedName("dict")
    private String dictionaryName;

    @SerializedName("dictType")
    private String dictionaryType;

    @SerializedName("year")
    private int dictionaryYear;

    @SerializedName("text")
    private List<String> dictionaryText;

    public Definition(String dictionaryName,
                      String dictionaryType,
                      int dictionaryYear,
                      List<String> dictionaryText) {
        this.dictionaryName = dictionaryName;
        this.dictionaryType = dictionaryType;
        this.dictionaryYear = dictionaryYear;
        this.dictionaryText = dictionaryText;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    public int getDictionaryYear() {
        return dictionaryYear;
    }

    public void setDictionaryYear(int dictionaryYear) {
        this.dictionaryYear = dictionaryYear;
    }

    public List<String> getDictionaryText() {
        return dictionaryText;
    }

    public void setDictionaryText(List<String> dictionaryText) {
        this.dictionaryText = dictionaryText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return dictionaryYear == that.dictionaryYear
                && dictionaryName.equals(that.dictionaryName)
                && dictionaryType.equals(that.dictionaryType)
                && dictionaryText.equals(that.dictionaryText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionaryName, dictionaryType, dictionaryYear, dictionaryText);
    }

    @Override
    public String toString() {
        return "Definition{" +
                "dictionaryName='" + dictionaryName + '\'' +
                ", dictionaryType='" + dictionaryType + '\'' +
                ", dictionaryYear=" + dictionaryYear +
                ", dictionaryText=" + dictionaryText +
                '}';
    }
}
