package com.geekbrains.githubclient.mvp.model.entity.entity.room;

import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IRoomGithubUsersCache {
    Single<List<GithubUser>> getUsersFromApi(IDataSource api, Database db);
    Single<List<GithubUser>> getUsersFromDb(Database db);
}
