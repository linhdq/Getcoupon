package com.vn.getcoupon.getcouponvn;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by linhdq on 5/14/17.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
