public class CacheDemo {
    public static void main(String[] args) {

        Cache cache = new Cache(3);

        cache.putKey(1, "Value 1");
        cache.putKey(2, "Value 2");
        cache.putKey(3, "Value 3");

        System.out.println(cache.getKey(1)); // Output: Value 1
        System.out.println(cache.getKey(2)); // Output: Value 2

        cache.putKey(4, "Value 4");

        System.out.println(cache.getKey(3)); // Output: null
        System.out.println(cache.getKey(4)); // Output: Value 4

        cache.putKey(2, "Updated Value 2");

        System.out.println(cache.getKey(1)); // Output: Value 1
        System.out.println(cache.getKey(2)); // Output: Updated Value 2
    }
}
