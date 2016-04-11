package mfacelle.coding.practice.hashtable.chained;


/** dumb main method to test chained hashtable - not using groovy/spock or junit (though probably should) */
public class TestChainedHashTable
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
        table.put("key6", 6);
        table.put("key7", 7);
        table.put("key8", 8);
        table.put("key9", 9);
        table.put("key10", 10);

        System.out.println("After some inserts");
        System.out.println(table);
        System.out.println("size: " + table.getSize());

        System.out.println("contains key \"key1\": " + table.containsKey("key1"));
        System.out.println("get key \"key1\": " + table.get("key1"));
        System.out.println("contains key \"key9\": " + table.containsKey("key9"));
        System.out.println("get key \"key9\": " + table.get("key9"));
        System.out.println("contains key \"key1234\": " + table.containsKey("key1234"));
        System.out.println("get key \"key1234\": " + table.get("key1234"));

        table.put("key10", 100);

        System.out.println("after updating key \"key10\"");
        System.out.println(table);
        System.out.println("size: " + table.getSize());

        table.delete("key2");
        table.delete("key4");
        table.delete("key5");

        System.out.println("after some deletes");
        System.out.println(table);
        System.out.println("size: " + table.getSize());
    }
}
