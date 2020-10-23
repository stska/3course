package com.geekbrains.githubclient.mvp.model.entity.entity.room;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.entity.dao.RepositoryDao;
import com.geekbrains.githubclient.mvp.model.entity.entity.dao.UserDao;


    @androidx.room.Database(entities = {RoomGithubUser.class, RoomGithubRepository.class}, version = 1)
    public abstract class Database extends RoomDatabase {

        public static final String DB_NAME = "database.db";
        public abstract UserDao userDao();
        public abstract RepositoryDao repositoryDao();
    }


