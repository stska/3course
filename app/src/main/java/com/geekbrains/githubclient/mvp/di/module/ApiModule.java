package com.geekbrains.githubclient.mvp.di.module;

import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.network.INetworkStatus;
import com.geekbrains.githubclient.ui.AndroidNetworkStatus;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Named("baseUrl")
    @Provides
    String baseUrl() {
        return "https://api.github.com/";
    }

    @Singleton
    @Provides
    IDataSource api(@Named("baseUrl") String baseUrl, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(IDataSource.class);
    }

    @Singleton
    @Provides
    Gson gson() {
        return new GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation().create();
    }

    @Singleton
    @Provides
    INetworkStatus networkStatus() {
        return new AndroidNetworkStatus();
    }
}
