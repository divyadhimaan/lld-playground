import java.util.Objects;

public class Cache {
    private final Storage storage;
    private final EvictionEngine evictionEngine;

    public Cache(int capacity)
    {
        this.storage =  new Storage(capacity);
        this.evictionEngine = new EvictionEngine();
    }

    public String getKey(int key)
    {
        String resultValue = storage.getKey(key);
        if(!Objects.equals(resultValue, "null"))
            evictionEngine.KeyAccessed(key);

        return resultValue;
    }
    public void putKey(int key, String value)
    {
        storage.addKey(key, value, evictionEngine);
        evictionEngine.KeyAccessed(key);
    }
}
