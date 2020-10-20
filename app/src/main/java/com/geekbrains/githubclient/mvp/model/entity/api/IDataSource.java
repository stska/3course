package com.geekbrains.githubclient.mvp.model.entity.api;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IDataSource {
    @GET("/users")
    Single<List<GithubUser>> getUsers();
    @GET("")
    Single<List<GithubForks>> getForks(@Url String url);
}
