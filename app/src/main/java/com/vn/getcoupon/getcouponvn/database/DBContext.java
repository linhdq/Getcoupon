package com.vn.getcoupon.getcouponvn.database;

import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;

import java.util.List;

import io.realm.Realm;

/**
 * Created by linhdq on 5/14/17.
 */

public class DBContext {
    private Realm realm;

    public DBContext() {
        realm = Realm.getDefaultInstance();
    }

    private static DBContext inst;

    public static DBContext getInst() {
        if (inst == null) {
            inst = new DBContext();
        }
        return inst;
    }

    public void addStoreItem(JSONStoreItem model) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public List<JSONStoreItem> getAllStoreItem() {
        realm = Realm.getDefaultInstance();
        return realm.where(JSONStoreItem.class).findAll();
    }

}
