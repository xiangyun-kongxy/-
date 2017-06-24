package com.kxy.general.common.storage;

/**
 * base interface for all storage class
 *
 * Created by xiangyun.kong on 6/9/17.
 */
public interface Storage {
    /**
     * every implement should show users whether it is consistent.
     *
     * @return true if consistent or false
     */
    Boolean consistency();
}
