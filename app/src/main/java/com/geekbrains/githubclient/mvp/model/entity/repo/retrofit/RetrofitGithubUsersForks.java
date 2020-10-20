package com.geekbrains.githubclient.mvp.model.entity.repo.retrofit;

import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.IRoomGithubRepositoriesCache;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.RoomGithubRepositoriesCache;
import com.geekbrains.githubclient.mvp.model.entity.network.INetworkStatus;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersForks;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUsersForks implements IGithubUsersForks {
    private IDataSource mApi;
    private final INetworkStatus mNetworkStatus;
    private final Database mDb;
    private final IRoomGithubRepositoriesCache cache;


    public RetrofitGithubUsersForks(IDataSource api, INetworkStatus status, Database db) {
        mApi = api;
        mNetworkStatus = status;
        mDb = db;
        this.cache = new RoomGithubRepositoriesCache();
    }

    @Override
    public Single<List<GithubForks>> getForks(String repos_url, GithubUser githubUser) {
        // return api.getForks(repos_url).subscribeOn(Schedulers.io());
        return mNetworkStatus.isOnlineSingle().flatMap(isOnline -> {
            if (isOnline) {
                return cache.saveReposToDb(repos_url, mApi, mDb, githubUser);

            } else {
                return cache.getRepoFromDb(mDb, githubUser);

            }
        }).subscribeOn(Schedulers.io());

    }
}
