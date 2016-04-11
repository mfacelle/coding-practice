package mfacelle.coding.practice.hashtable.chained;

import java.lang.reflect.Array;

/**
 * A HashTable implementation that uses chained hasing.
 * Implements containsKey, get, put, delete, toString
 */
public class HashTable<K,V>
{
    private static final int DEFAULT_NUM_BUCKETS = 10;

    public KeyValueNode<K,V>[] buckets;
    int numBuckets;
    int size;

    // ---

    public HashTable() {
        this(DEFAULT_NUM_BUCKETS);
    }
    public HashTable(int pNumBuckets) {
        numBuckets = pNumBuckets;
        size = 0;
        // initialize array to all null buckets
        // generic array... had to look this part up
        buckets = (KeyValueNode<K,V>[]) Array.newInstance(KeyValueNode.class, pNumBuckets);
        for (int i = 0; i < pNumBuckets; i++) {
            buckets[i] = null;
        }
    }

    // ---

    /**
     * Puts the key,value pair into this table.
     * Returns null if key didnt already exist. Returns the previous value associated with the key, if it already exists
     */
    public V put(K key, V value) {
        int hashedKey = hash(key);

        // check if key already exists
        KeyValueNode<K,V> node = buckets[hashedKey];
        while (node != null) {
            // if key is found - update value and return the old value
            if (node.key.equals(key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }

        // if key doesnt exist already:
        // insert the new node at the top of the list (easiest insertion)
        KeyValueNode<K,V> nodeToInsert = new KeyValueNode<K,V>(key, value);
        nodeToInsert.next = buckets[hashedKey];
        if (buckets[hashedKey] != null) {
            buckets[hashedKey].prev = nodeToInsert;
        }
        buckets[hashedKey] = nodeToInsert;
        size++;

        return null;
    }

    // ---

    /**
     * Returns true if the key exists in the table, false if otherwise
     */
    public boolean containsKey(K key) {
        int hashedKey = hash(key);
        KeyValueNode<K,V> node = buckets[hashedKey];
        while (node != null) {
            if (node.key.equals(key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    // ---

    /**
     * Returns the value associated with the key, if it exists. Returns null if not
     */
    public V get(K key) {
        int hashedKey = hash(key);
        KeyValueNode<K,V> node = buckets[hashedKey];
        while (node != null) {
            // if key is found - return the value
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    // ---

    /**
     * Deletes a key,value entry from this table.  Returns the value if found, null if not found
     */
    public V delete(K key) {
        int hashedKey = hash(key);
        KeyValueNode<K,V> node = buckets[hashedKey];
        while (node != null) {
            // if key is found - remove it from the list
            // being double-linked makes this super easy
            if (node.key.equals(key)) {
                if (node.prev != null) {
                    node.prev.next = node.next;
                }
                if (node.next != null) {
                    node.next.prev = node.prev;
                    buckets[hashedKey] = node.next;
                }
                else {
                    buckets[hashedKey] = null;
                }
                node.prev = null;
                node.next = null;
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    // ---

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numBuckets; i++) {
            sb.append("[").append(i).append("]  ");
            sb.append("{ ");
            KeyValueNode<K,V> node = buckets[i];
            while (node != null) {
                sb.append(node).append("  ");
                node = node.next;
            }
            sb.append(" }\n");
        }

        return sb.toString();
    }

    // ---

    public int getSize()    { return size; }

    // ---

    /** Hashes the key to select which bucket it falls in */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % numBuckets;
    }

    // =====

    /** linked list node to be used in each table's bucket.
     * doubly-linked for easier deletion.  takes up more memory, though
     */
    private class KeyValueNode<K,V> {
        protected K key;
        protected V value;
        protected KeyValueNode<K,V> prev;
        protected KeyValueNode<K,V> next;

        public KeyValueNode(K pKey, V pValue) {
            key = pKey;
            value = pValue;
            prev = null;
            next = null;
        }

        public String toString() {
            return "[" + key + ":" + value + "]";
        }
    }
}
