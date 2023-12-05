package org.example;

import java.util.ArrayList;
import java.util.Random;

/**
 * AbstractHashMap is an extension of the abstract base class for the ADT Map
 * for implementation based on hashing
 * <p>
 * Based on Goodrich, Tamassia & Goldwasser
 *
 * @author Francois Major
 * @version %I%, %G%
 * @since 1.0
 */

public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
    protected int n = 0; // number of entries in the map
    protected int capacity; // size of the table
    private int prime; // prime factor
    private long scale, shift; // shift and scale factors

    public AbstractHashMap(int cap, int p) {
        this.prime = p;
        this.capacity = cap;
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
        this.createTable();
    }

    public AbstractHashMap(int cap) {
        this(cap, 109345121);
    }

    public AbstractHashMap() {
        this(11);
    }

    // public methods
    public int size() {
        return n;
    }

    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }

    public boolean containsKey(K key) {
        return bucketGet(hashValue(key), key) != null;
    }

    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }

    public V put(K key, V value) {
        V retV = bucketPut(hashValue(key), key, value);
        if (n > capacity * 0.75) // keep load factor <= 0.75
            resize(2 * capacity - 1); // (or find a nearby prime)
        return retV;
    }

    // developer's utilities
    int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    private void resize(int newCap) {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (Entry<K, V> e : this.entrySet())
            buffer.add(e);
        this.capacity = newCap;
        this.createTable(); // based on updated capacity
        this.n = 0; // wil be recomputed while reinserting entries
        for (Entry<K, V> e : buffer)
            put(e.getKey(), e.getValue());
    }

    // protected abstract methods to be implemented by subclasses
    protected abstract void createTable();

    protected abstract V bucketGet(int h, K k);

    protected abstract V bucketPut(int h, K k, V v);

    protected abstract V bucketRemove(int h, K k);
}
