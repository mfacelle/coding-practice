package mfacelle.coding.practice.hashtable.arrays;

/** dumb main method to test chained hashtable - not using groovy/spock or junit (though probably should) */
public class TestArrayHashTable
{
    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable<>();

        System.out.println("Empty table: ");
        System.out.println(table);

        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3);
        table.put("key4", 4);
        table.put("key5", 5);


        System.out.println("After some inserts");
        System.out.println(table);
        System.out.println("numElements:" + table.getNumElements() + ", size:" + table.getSize() + ", alpha:" + table.getAlpha() + "\n");

        table.put("key55", 6);
        table.put("key777", 7);

        System.out.println("After some inserts");
        System.out.println(table);
        System.out.println("numElements:" + table.getNumElements() + ", size:" + table.getSize() + ", alpha:" + table.getAlpha() + "\n");

        table.put("key8", 8);
        table.put("key9", 9);
        table.put("key10", 10);
        table.put("key16", 11);

        System.out.println("After some inserts");
        System.out.println(table);
        System.out.println("numElements:" + table.getNumElements() + ", size:" + table.getSize() + ", alpha:" + table.getAlpha() + "\n");

        System.out.println("contains key \"key1\": " + table.containsKey("key1"));
        System.out.println("get key \"key1\": " + table.get("key1"));
        System.out.println("contains key \"key9\": " + table.containsKey("key9"));
        System.out.println("get key \"key9\": " + table.get("key9"));
        System.out.println("contains key \"key1234\": " + table.containsKey("key1234"));
        System.out.println("get key \"key1234\": " + table.get("key1234"));
        System.out.println();

        table.put("key10", 100);

        System.out.println("after updating key \"key10\"");
        System.out.println(table);
        System.out.println("numElements:" + table.getNumElements() + ", size:" + table.getSize() + ", alpha:" + table.getAlpha() + "\n");

        table.delete("key3");
        table.delete("key55");

        System.out.println("after deleting \"key3\" and \"key55\"");
        System.out.println(table);
        System.out.println("numElements:" + table.getNumElements() + ", size:" + table.getSize() + ", alpha:" + table.getAlpha() + "\n");
    }
}
