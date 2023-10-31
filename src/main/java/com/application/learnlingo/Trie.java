package com.application.learnlingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    static final int ALPHABET_SIZE = 256;
    static final char FIRST_CHARACTER = (char)0;
    private TrieNode root;
    private int index(char ch){
        return (int) ch - FIRST_CHARACTER;
    }

    public Trie() {
        root = new TrieNode();
    }

    public boolean put(String word) {
        TrieNode head = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            head.addChild(ch);
            head = head.getChild(ch);
        }
        if (head.isEndOfWord()) {
            return false;
        }
        head.increaseEndWord(1);
        return true;
    }

    public boolean contain(String word) {
        return count(word)>0;
    }

    public List<String> getPrefixStringList(String prefixString) {
        TrieNode head = root;
        StringBuilder completeWord = new StringBuilder(prefixString);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < prefixString.length(); i++) {
            char ch = prefixString.charAt(i);
            if (head.getChild(ch) == null) {
                return result;
            }
            head = head.getChild(ch);
        }
        traversalNode(head, result, completeWord);
        return result;
    }

    private void traversalNode(TrieNode head, List<String> result, StringBuilder prefix) {
        if (head.isEndOfWord()) result.add(prefix.toString());
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            char ch = (char)i;
            if (head.getChild(ch) != null) {
                prefix.append(ch);
                traversalNode(head.getChild(ch), result, prefix);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }

    public boolean remove(String word) {
        int frequencyOfWord = count(word);
        if (frequencyOfWord==0) {
            throw new IllegalArgumentException(new StringBuilder().append("Error: The word").append(word).append(" doesn't exist!").toString());
        }
        TrieNode head = root;
        for (int i = 0;i<word.length();i++){
            char ch = word.charAt(i);
            head = head.getChild(ch);
        }
        head.increaseEndWord(-1);
        return true;
    }
    public int count(String word){
        TrieNode head = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!head.existNode(ch)) {
                return 0;
            }
            head = head.getChild(ch);
        }
        return head.wordTotal();
    }

    private class TrieNode {
        private HashMap<Character,TrieNode> children;
        private int endWordTotal;


        public TrieNode() {
            children = new HashMap<>();
            endWordTotal = 0;
        }

        public int wordTotal(){
            return endWordTotal;
        }
        public boolean isEndOfWord() {
            return endWordTotal>0;
        }

        public void increaseEndWord(int value) {
            endWordTotal += value;
        }

        public TrieNode getChild(char ch) {
            return children.get(ch);
        }

        public void addChild(char ch) {
            if (children.get(ch) == null)
                children.put(ch,new TrieNode());
        }
        public void removeChild(char ch){
            if (children.get(ch)== null){
                throw new IllegalArgumentException("Error: Doesn't exist node to remove!");
            }
            children.remove(ch);
        }

        public boolean existNode(char ch){
            return (children.get(ch) != null);
        }
    }
}
