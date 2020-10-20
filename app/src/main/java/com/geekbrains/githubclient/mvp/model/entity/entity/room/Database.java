package com.geekbrains.githubclient.mvp.model.entity.entity.room;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.entity.dao.RepositoryDao;
import com.geekbrains.githubclient.mvp.model.entity.entity.dao.UserDao;

@androidx.room.Database(entities = {RoomGithubUser.class, RoomGithubRepository.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static final String DB_NAME = "database.db";

    private static volatile Database INSTANCE;

    public abstract UserDao userDao();
    public abstract RepositoryDao repositoryDao();

    public static Database getInstance() {
        Database localRef = INSTANCE;

        if (localRef == null) {
            synchronized (Database.class) {
                INSTANCE = localRef;

                if (localRef == null) {
                    INSTANCE = localRef = Room.databaseBuilder(GithubApplication.getAppContext(), Database.class, DB_NAME)
                            .build();
                }
            }
        }

        return localRef;
    }



}