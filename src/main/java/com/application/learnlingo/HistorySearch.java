package com.application.learnlingo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HistorySearch {
    private List<String> historyList;
    private String historyPath;

    public HistorySearch() {
        historyList = new ArrayList<>();
    }

    public HistorySearch(String path) {
        historyList = new ArrayList<>();
        historyPath = path;
        loadHistory();
    }

    public void loadHistory() {
        File historyFile = new File(historyPath);
        if (!historyFile.exists()) {
            try {
                if (!historyFile.createNewFile()) {
                    System.out.println(new StringBuilder().append("Couldn't create file ")
                            .append(historyPath));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileReader fr = null;
        try {
            fr = new FileReader(historyPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't read data from ")
                    .append(historyPath)
                    .toString());
        }
        assert fr != null;
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while (true) {
            try {
                if ((line = br.readLine()) == null) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(new StringBuilder().append("Error: Can't read data from ")
                        .append(historyPath));
            }
            historyList.add(line);
        }
        try {
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't close ")
                    .append(historyPath));

        }
    }

    public void insertToHistory(String word) {
        try {
            if (historyList.contains(word)) {
                historyList.removeIf(e -> e.equals(word));
            }
            historyList.add(0, word);
            FileWriter fw = new FileWriter(historyPath);
            BufferedWriter bf = new BufferedWriter(fw);
            for (String target : historyList) {
                bf.write(target);
                bf.newLine();
            }
            bf.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(new StringBuilder().append("Error: Can't insert word to file ")
                    .append(historyPath));
        }
    }

    public List<String> exportHistoryList() {
        return historyList;
    }

    public void clearHistory() {

        try {
            FileWriter fw = new FileWriter(historyPath);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write("");
            bf.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        historyList.clear();

    }

    public String getHistoryIndex(int index){
        if (historyList.isEmpty() || index>historyList.size()){
            throw new IllegalArgumentException("Error: The index is out of bound of history list!");
        }
        return historyList.get(index);
    }

}
