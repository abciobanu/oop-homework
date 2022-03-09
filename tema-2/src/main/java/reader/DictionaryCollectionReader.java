package reader;

import collection.DictionaryCollection;
import exception.InputDirectoryException;
import exception.InputFileNameException;
import pojo.Word;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryCollectionReader {
    /**
     * Reads all the dictionaries represented as JSON files from a directory.
     *
     * @param directoryPath A String object that represents the path of the directory from which JSON files are to be
     *                      read.
     * @return a DictionaryCollection object that contains all the information read.
     * @throws InputDirectoryException if the file denoted by the given path is not a directory.
     * @throws InputFileNameException  if a file in the directory does not comply with the required JSON file name
     *                                 format: "lang_dict.json", where "lang" is the language - e.g., "ro", "fr", "de".
     */
    public static DictionaryCollection readAllFromDirectory(String directoryPath)
            throws InputDirectoryException, InputFileNameException {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new InputDirectoryException("Invalid directory path! (" + directoryPath + ")");
        }

        HashMap<String, HashMap<String, Word>> collection = new HashMap<>();

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();

                // the files that matter are the JSON files
                if (fileName.lastIndexOf(".json") != -1) {
                    // file name format: "lang_dict.json", where "lang" is the language (e.g., "ro", "fr", "de")
                    Pattern pattern = Pattern.compile("^[^_]+");
                    Matcher matcher = pattern.matcher(fileName);
                    if (!matcher.find()) {
                        throw new InputFileNameException("Invalid file name! (" + fileName + ")");
                    }

                    String dictionaryLanguage = matcher.group();

                    // all the words in the JSON file are obtained, then introduced into the collection
                    HashMap<String, Word> wordHashMap = DictionaryReader.readDictionary(file);
                    collection.put(dictionaryLanguage, wordHashMap);
                }
            }
        }

        return new DictionaryCollection(collection);
    }
}
