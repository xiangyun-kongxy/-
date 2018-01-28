package com.kxy.general.common.datamining.data;

import lombok.Getter;
import lombok.Setter;

/**
 * key value struct.
 * @param <K> type of key
 * @param <V> type of value
 * Created by kongxiangyun on 1/28/18.
 */
@Getter @Setter
public class KeyValue<K, V> {
    /**
     * key.
     */
    private K key;
    /**
     * value.
     */
    private V value;

    /**
     * init construction.
     * @param key key
     * @param value value
     */
    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * default construction.
     */
    public KeyValue() {
    }
}
