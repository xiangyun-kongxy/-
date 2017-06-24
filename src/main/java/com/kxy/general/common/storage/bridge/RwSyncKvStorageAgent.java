package com.kxy.general.common.storage.bridge;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * thread safe kv storage agent with read-write lock.
 * it is suitable when count(read) >>> count(write).
 *
 * Created by xiangyun.kong on 6/21/17.
 */
public class RwSyncKvStorageAgent extends AbstractSyncKvStorageAgent {

    /**
     * lock object.
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

    /**
     * @see AbstractSyncKvStorageAgent#readLock()
     */
    @Override
    public void readLock() {
        lock.readLock().lock();
    }

    /**
     * @see AbstractSyncKvStorageAgent#writeLock()
     */
    @Override
    public void writeLock() {
        lock.writeLock().lock();
    }

    /**
     * @see AbstractSyncKvStorageAgent#readUnlock()
     */
    @Override
    public void readUnlock() {
        lock.readLock().unlock();
    }

    /**
     * @see AbstractSyncKvStorageAgent#writeUnlock()
     */
    @Override
    public void writeUnlock() {
        lock.writeLock().unlock();
    }
}
