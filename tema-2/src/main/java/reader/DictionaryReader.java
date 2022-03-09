package reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pojo.Word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;

public class DictionaryReader {
    /**
     * Reads a dictionary represented as a JSON file.
     *
     * @param file The JSON file containing the information in the dictionary.
     * @return a HashMap that contains the information read.
     */
    public static HashMap<String, Word> readDictionary(File file) {
        HashMap<String, Word> wordHashMap = new HashMap<>();

        // deserialization
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Word>>(){}.getType();
            Collection<Word> words = gson.fromJson(bufferedReader, collectionType);

            for (Word word : words) {
                wordHashMap.put(word.getWord(), word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordHashMap;
    }
}
