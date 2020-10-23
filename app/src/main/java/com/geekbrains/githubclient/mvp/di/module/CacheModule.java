package com.geekbrains.githubclient.mvp.di.module;


import androidx.room.Room;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.IRoomGithubRepositoriesCache;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.IRoomGithubUsersCache;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.RoomGithubRepositoriesCache;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.RoomGithubUsersCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Singleton
    @Provides
    Database database() {
        return Room.databaseBuilder(GithubApplication.getAppContext(), Database.class, Database.DB_NAME)
                .build();
    }

    @Singleton
    @Provides
    IRoomGithubUsersCache usersCache(Database db) {
      //  return new RoomGithubUsersCache(db);
        return new RoomGithubUsersCache();
    }

    @Singleton
    @Provides
    IRoomGithubRepositoriesCache userRepositoriesCache(Database db) {
       // return new RoomGithubRepositoriesCache(db);
        return new RoomGithubRepositoriesCache();
    }

}