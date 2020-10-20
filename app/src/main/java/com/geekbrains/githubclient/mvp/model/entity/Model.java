package com.geekbrains.githubclient.mvp.model.entity;

import com.geekbrains.githubclient.ApiHolder;
import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;

public class Model {
    private static volatile Model INSTANCE;

    private ApiHolder mApiHolder;

    public Model(){
        mApiHolder = new ApiHolder();
    }

    public static Model getInstance() {
        Model localRef = INSTANCE;

        if (localRef == null) {
            synchronized (Model.class) {
                INSTANCE = localRef;

                if (localRef == null) {
                    INSTANCE = localRef = new Model();
                }
            }
        }

        return localRef;
    }

    public synchronized IDataSource getApi() {
        return mApiHolder.getDataSource();
    }
}
