package main.Services;

public interface ICache {
    void put(String key, Object obj);
    Object get(String key);
    void remove(String key);
}
