package com.geekbrains.githubclient.mvp.model.entity.api;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IDataSourceUser {
    @GET("/users")
    Single<List<GithubUser>> getUsers();
    @GET
    Single<List<GithubForks>> getForks(@Url String url);
}
