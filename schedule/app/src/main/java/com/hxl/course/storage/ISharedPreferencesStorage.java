package com.hxl.course.storage;

import java.util.List;

public interface ISharedPreferencesStorage<K,V> {
     String setValue(K key, V value);
     V getValue(K key,V defValue);
     default List<V> getList(){return null;}
}
