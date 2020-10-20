package com.geekbrains.githubclient.mvp.model.entity.repo.retrofit;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.IRoomGithubUsersCache;

import com.geekbrains.githubclient.mvp.model.entity.entity.room.RoomGithubUsersCache;
import com.geekbrains.githubclient.mvp.model.entity.network.INetworkStatus;

import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {
    final IDataSource api;
    final INetworkStatus networkStatus;
    final Database db;
    final IRoomGithubUsersCache cache;

    public RetrofitGithubUsersRepo(IDataSource api, INetworkStatus status, Database db) {
        this.api = api;
        this.networkStatus = status;
        this.db = db;
        this.cache = new RoomGithubUsersCache();
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        // return api.getUsers().subscribeOn(Schedulers.io());

        return networkStatus.isOnlineSingle().flatMap((isOline) -> {
            if (isOline) {
                return cache.getUsersFromApi(api, db);

            } else {
                return cache.getUsersFromDb(db);
            }
        }).subscribeOn(Schedulers.io());

    }

}
