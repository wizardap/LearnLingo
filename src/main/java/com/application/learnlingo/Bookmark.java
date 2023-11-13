package com.application.learnlingo;

import java.util.List;

public interface Bookmark {
    void importData();

    List<String> exportData();

    void add(String word);

    void remove(String word);
    void clear();
}
