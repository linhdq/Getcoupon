package com.vn.getcoupon.getcouponvn.database;

import android.util.Log;

import com.vn.getcoupon.getcouponvn.model.FollowListModel;
import com.vn.getcoupon.getcouponvn.model.json_model.JSONStoreItem;

import java.util.ArrayList;
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

    public void addFollowStore(FollowListModel model) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }

    public void deleteFollowModel(String id) {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(FollowListModel.class).equalTo("id", id).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }

    public List<String> getAllFollowItem() {
        realm = Realm.getDefaultInstance();
        List<String> list = new ArrayList<>();
        for (FollowListModel model : realm.where(FollowListModel.class).findAll()) {
            list.add(model.getId());
        }
        return list;
    }

    public List<FollowListModel> getAllFollowModel() {
        realm = Realm.getDefaultInstance();
        return realm.where(FollowListModel.class).findAll();
    }
}
