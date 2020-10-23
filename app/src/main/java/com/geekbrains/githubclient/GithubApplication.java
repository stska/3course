package com.geekbrains.githubclient;

import android.app.Application;
import android.content.Context;

import com.geekbrains.githubclient.mvp.di.AppComponent;
import com.geekbrains.githubclient.mvp.di.DaggerAppComponent;
import com.geekbrains.githubclient.mvp.di.module.AppModule;
import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class GithubApplication extends Application {
    public static GithubApplication INSTANCE;
    public static final boolean DEBUG = true;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static Context getAppContext() {
        return INSTANCE;
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }


}
