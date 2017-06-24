package com.kxy.general.common.storage.bridge;

import com.kxy.general.common.storage.DataConflictCallback;
import com.kxy.general.common.storage.DataMissCallback;
import com.kxy.general.common.storage.KvStorage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * an agent type of kv storage, who makes the storage thread safe.
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public abstract class AbstractSyncKvStorageAgent implements KvStorage {
    /**
     * bridge to a real storage implement.
     */
    @Setter
    @Getter
    private KvStorage implement;

    /**
     * @see KvStorage#consistency()
     */
    @Override
    public Boolean consistency() {
        return implement.consistency();
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> T query(String key) {
        try {
            readLock();
            return implement.query(key);
        } finally {
            readUnlock();
        }

    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> T query(String key, T miss) {
        try {
            readLock();
            return implement.query(key, miss);
        } finally {
            readUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys) {
        try {
            readLock();
            return implement.query(keys);
        } finally {
            readUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> Map<String, T> query(List<String> keys,
            DataMissCallback<T> callback) {
        try {
            readLock();
            return implement.query(keys, callback);
        } finally {
            readUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> Map<String, T> queryAll() {
        try {
            readLock();
            return implement.queryAll();
        } finally {
            readUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> void write(String key, T value) {
        try {
            writeLock();
            implement.write(key, value);
        } finally {
            writeUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> void write(String key, T value,
            DataConflictCallback<T> callback) {
        try {
            writeLock();
            implement.write(key, value, callback);
        } finally {
            writeUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> void write(Map<String, T> values) {
        try {
            writeLock();
            implement.write(values);
        } finally {
            writeUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public <T extends Serializable> void write(Map<String, T> values,
            DataConflictCallback<T> callback) {
        try {
            writeLock();
            implement.write(values, callback);
        } finally {
            writeUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public void remove(String key) {
        try {
            writeLock();
            implement.remove(key);
        } finally {
            writeUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public void remove(List<String> keys) {
        try {
            writeLock();
            implement.remove(keys);
        } finally {
            writeUnlock();
        }
    }

    /**
     * @see KvStorage
     */
    @Override
    public void removeAll() {
        try {
            writeLock();
            implement.removeAll();
        } finally {
            writeUnlock();
        }
    }

    /**
     * I want to read lock. but what it really is depends on implements.
     */
    public abstract void readLock();

    /**
     * I want to write lock. but what it really is depends on implements.
     */
    public abstract void writeLock();

    /**
     * I want to read unlock. but what it really is depends on implements.
     * it should match with {@code readLock}
     */
    public abstract void readUnlock();

    /**
     * I want to write unlock. but what it really is depends on implements.
     * it should match with {@code writeLock}
     */
    public abstract void writeUnlock();

}
