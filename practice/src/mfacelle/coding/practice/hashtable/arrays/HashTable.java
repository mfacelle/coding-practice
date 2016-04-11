package mfacelle.coding.practice.hashtable.arrays;

/**
 * A HashTable implementation that uses only arrays - utilizes open addressing / linear probing
 * Implements containsKey, get, put, delete, toString
 */
public class HashTable<K,V>
{
    /** default initial size - also the size table is increased by when load factor becomes too large */
    private static final int DEFAULT_SIZE = 10;
    /** max load factor before resizing table */
    private static final float MAX_ALPHA = 0.70f;

    private K[] keys;
    private V[] values;
    private int numElements;
    private int size;
    private float alpha;    // "load factor" = numElements / size

    // ---

    public HashTable() {
        this(DEFAULT_SIZE);
    }

    public HashTable(int mSize) {
        size = mSize;
        numElements = 0;
        // initialize arrays
        keys = (K[]) new Object[mSize];
        values = (V[]) new Object[mSize];
        for (int i = 0; i < mSize; i++) {
            keys[i] = null;
            values[i] = null;
        }
        alpha = computeAlpha();
    }

    // ---

    /**
     *  Puts the key,value pair. Uses linear probing if there is a conflict.
     *  If the key already exists, value is returned and overwritten with new value
     */
    public V put(K key, V value) {
        int hashedKey = hash(key);
        System.out.println("[DEBUG] put key: " + key + ", hashed key: " + hashedKey);

        // due to put(), there is guaranteed to be at least one null slot
        //  therefore, this cannot be an infinite loop
        while (keys[hashedKey] != null) {
            if (keys[hashedKey].equals(key)) {
                V oldValue = values[hashedKey];
                values[hashedKey] = value;
                return oldValue;
            }
            hashedKey = nextIndex(hashedKey);
        }

        // key not found - current index is an empty space
        keys[hashedKey] = key;
        values[hashedKey] = value;
        numElements++;

        // recompute alpha and increase table size if too big
        alpha = computeAlpha();
        if (alpha >= MAX_ALPHA) {
            increaseTableSize();
        }

        return null;
    }

    // ---

    /**
     *  Gets the value associated with the specified key.  Returns null if not found
     */
    public V get(K key) {
        int hashedKey = hash(key);

        while (keys[hashedKey] != null) {
            if (keys[hashedKey].equals(key)) {
                return values[hashedKey];
            }
            hashedKey = nextIndex(hashedKey);
        }
        // null space hit: key not found
        return null;
    }

    // ---

    /**
     *  Returns true if the key is contained in this hashtable
     */
    public boolean containsKey(K key) {
        int hashedKey = hash(key);

        while (keys[hashedKey] != null) {
            if (keys[hashedKey].equals(key)) {
                return true;
            }
            hashedKey = nextIndex(hashedKey);
        }
        // null space hit: key not found
        return false;
    }

    // ---

    /**
     *  Deletes the key,value pair from the table. Returns the value if key is found; null if not found
     */
    public V delete(K key) {
        int hashedKey = hash(key);
        System.out.println("[DEBUG] delete key: " + key + ", hashed key: " + hashedKey);


        // find the key in the table
        while (keys[hashedKey] != null) {
            if (keys[hashedKey].equals(key)) {
                // key found - remove it; re-insert all following elements (due to linear probing)
                V foundValue = values[hashedKey];
                System.out.println("[DEBUG] key found in delete(): " + keys[hashedKey]);
                // remove the element
                keys[hashedKey] = null;
                values[hashedKey] = null;
                numElements--;
                alpha = computeAlpha();
                // go to next element
                hashedKey = nextIndex(hashedKey);
                // remove and re-insert any consecutive elements
                // because of linear probing and null-checks, if these aren't reinserted,
                //  they might not be found during the next get() or containsKey()
                while (keys[hashedKey] != null) {
                    K reinsertKey = keys[hashedKey];
                    V reinsertValue = values[hashedKey];
                    keys[hashedKey] = null;
                    values[hashedKey] = null;
                    numElements--;
                    put(reinsertKey, reinsertValue);
                    hashedKey = nextIndex(hashedKey);
                }
                return foundValue;
            }
            hashedKey = nextIndex(hashedKey);
        }

        // null space reached - key not found
        return null;
    }

    // ---

    public int getSize()    { return size; }
    public int getNumElements() { return numElements; } // for testing
    public float getAlpha() { return alpha; }   // for testing

    // ---

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append("[").append(i).append("]  ");
            sb.append(keys[i]).append(" : ").append(values[i]).append("\n");
        }
        return sb.toString();
    }

    // ---

    /** Hashes the key to select which bucket it falls in */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % size;
    }

    // -

    /** gets the next index - moves circular on [0,size) */
    private int nextIndex(int currentIndex) {
        return (currentIndex +1 ) % size;
    }

    private float computeAlpha() {
        return (float)numElements / (float)size;
    }

    // -

    /** increases the size of the table by DEFAULT_SIZE */
    private void increaseTableSize() {
        int newSize = size + DEFAULT_SIZE;
        K[] oldKeys = keys;
        V[] oldValues = values;
        K[] newKeys = (K[]) new Object[newSize];
        V[] newValues = (V[]) new Object[newSize];

        // put everything into new arrays (hash will change due to size increase)
        for (int i = 0; i < size; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }

        size = newSize;
        keys = newKeys;
        values = newValues;
        alpha = computeAlpha();
    }
}
