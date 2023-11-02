package com.application.learnlingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    private TrieNode root;
    private HashMap<Integer,String> map;
    private int wordTotal;
    public Trie() {
        root = new TrieNode();
        wordTotal=0;
        map = new HashMap<>();
    }

    public boolean put(String word) {
        TrieNode head = root;
        String lowerCaseString = word.toLowerCase();
        for (int i = 0; i < lowerCaseString.length(); i++) {
            char ch = lowerCaseString.charAt(i);
            head.addChild(ch);
            head = head.getChild(ch);
        }
        if (head.isEndOfWord()) {
            return false;
        }
        head.setWordID(wordTotal);
        map.put(wordTotal,word);
        wordTotal++;
        return true;
    }

    public boolean contain(String word) {
        String lowerCaseString = word.toLowerCase();
        TrieNode head = root;
        for (int i = 0; i < lowerCaseString.length(); i++) {
            char ch = lowerCaseString.charAt(i);
            if (!head.existNode(ch)) {
                return false;
            }
            head = head.getChild(ch);
        }
        return head.isEndOfWord();
    }

    public List<String> getPrefixStringList(String prefixString) {
        String lowerCaseString = prefixString.toLowerCase();
        TrieNode head = root;
        List<String> result = new ArrayList<>();
        for (int i = 0; i < lowerCaseString.length(); i++) {
            char ch = lowerCaseString.charAt(i);
            if (head.getChild(ch) == null) {
                return result;
            }
            head = head.getChild(ch);
        }
        traversalNode(head, result);
        return result;
    }

    private void traversalNode(TrieNode head, List<String> result) {
        if (head.isEndOfWord()) {
            int index = head.getWordID();
            if (!map.containsKey(index)){
                throw new IllegalArgumentException("The word doesn\'t exist in Trie but in this node have end of word !");
            }
            result.add(map.get(index));
        }
        for (Character character:head.children.keySet())
        {
            char ch = character;
            traversalNode(head.getChild(ch), result);
        }
    }

    public boolean remove(String word) {

        if (!contain(word)) {
            throw new IllegalArgumentException(new StringBuilder().append("Error: The word").append(word).append(" doesn't exist!").toString());
        }
        TrieNode head = root;
        for (int i = 0;i<word.length();i++){
            char ch = word.charAt(i);
            head = head.getChild(ch);
        }
        head.setWordID(-1);
        return true;
    }


    private class TrieNode {
        private HashMap<Character,TrieNode> children;
        private int wordID;


        public TrieNode() {
            children = new HashMap<>();
            wordID = -1;
        }

        public int getWordID(){
            return wordID;
        }
        public boolean isEndOfWord() {
            return wordID >=0;
        }

        public void setWordID(int ID){
            this.wordID = ID;
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
