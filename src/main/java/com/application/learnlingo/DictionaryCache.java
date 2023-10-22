package com.application.learnlingo;

import java.util.HashMap;

public class DictionaryCache extends GeneralController {
    private HashMap<String, Word> cache = new HashMap<>();

    public Word getWordInformation(String wordName) {
        Word information = cache.get(wordName);
        if (information == null){
            information = getWordFromDatabase(wordName);
            cache.put(wordName,information);
        }
        return information;
    }
    public Word getWordFromDatabase(String wordName){
        if (isUKFlagVisible)
            return evDict.getWordInformation(wordName);
        return veDict.getWordInformation(wordName);
    }
}
