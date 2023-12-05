package org.example;

import java.util.List;

public class TextMap<K extends String, V extends List<String>> extends ProbeHashMap<K, V> {

    public TextMap() {
        super();
    }

    public TextMap(int cap) {
        super(cap);
    }

    public TextMap(int cap, int p) {
        super(cap, p);
    }

    // Method to find the next word in the list associated with a given key
    public String findNextWord(K key, int index) {
        V list = this.get(key);
        if (list == null || index < 0 || index + 1 >= list.size()) {
            return null; // Return null if key not found, index is out of range, or there is no next word
        }
        return list.get(index + 1);
    }
}
