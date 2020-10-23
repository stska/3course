package com.geekbrains.githubclient.mvp.di.module;

import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.network.INetworkStatus;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersForks;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersRepo;
import com.geekbrains.githubclient.mvp.model.entity.repo.retrofit.RetrofitGithubUsersForks;
import com.geekbrains.githubclient.mvp.model.entity.repo.retrofit.RetrofitGithubUsersRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {
    @Singleton
    @Provides
    public IGithubUsersRepo usersRepo(IDataSource api, INetworkStatus status, Database db) {
        return new RetrofitGithubUsersRepo(api, status,db);
    }

    @Singleton
    @Provides
    public IGithubUsersForks userRepositoriesRepo(IDataSource api, INetworkStatus status,Database db) {
        return new RetrofitGithubUsersForks(api, status,db);
    }
}
