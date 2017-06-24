package com.kxy.general.common.storage.bridge;

import java.util.concurrent.locks.ReentrantLock;

/**
 * thread safe kv storage agent with reentrant lock.
 * it is suitable in most scene
 *
 * Created by xiangyun.kong on 6/21/17.
 */
public class SyncKvStorageAgent extends AbstractSyncKvStorageAgent {

    /**
     * lock object.
     */
    private ReentrantLock lock = new ReentrantLock(true);

    /**
     * @see AbstractSyncKvStorageAgent#readLock()
     */
    @Override
    public void readLock() {
        lock.lock();
    }

    /**
     * @see AbstractSyncKvStorageAgent#writeLock()
     */
    @Override
    public void writeLock() {
        lock.lock();
    }

    /**
     * @see AbstractSyncKvStorageAgent#readUnlock()
     */
    @Override
    public void readUnlock() {
        lock.unlock();
    }

    /**
     * @see AbstractSyncKvStorageAgent#writeUnlock()
     */
    @Override
    public void writeUnlock() {
        lock.unlock();
    }
}
