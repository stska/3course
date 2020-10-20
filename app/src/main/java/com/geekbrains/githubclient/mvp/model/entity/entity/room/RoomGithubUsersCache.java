package com.geekbrains.githubclient.mvp.model.entity.entity.room;

import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RoomGithubUsersCache implements IRoomGithubUsersCache {
    @Override
    public Single<List<GithubUser>> getUsersFromApi(IDataSource api, Database db) {
        return api.getUsers().flatMap((users) -> Single.fromCallable(() -> {
            List<RoomGithubUser> roomGithubUsers = new ArrayList<>();

            for (GithubUser user : users) {
                RoomGithubUser roomUser = new RoomGithubUser(user.getId(),
                        user.getLogin(),
                        user.getAvatarUrl(),
                        user.getReposUrl());

                roomGithubUsers.add(roomUser);


            }

            db.userDao().insert(roomGithubUsers);
            return users;
        }));
    }

    @Override
    public Single<List<GithubUser>> getUsersFromDb(Database db) {
        return Single.fromCallable(() -> {
            List<RoomGithubUser> roomGithubUsers = db.userDao().getAll();

            List<GithubUser> users = new ArrayList<>();

            for (RoomGithubUser roomGithubUser : roomGithubUsers) {
                GithubUser githubUser = new GithubUser(roomGithubUser.getId(),
                        roomGithubUser.getLogin(),
                        roomGithubUser.getAvatarUrl(),
                        roomGithubUser.getReposUrl());

                users.add(githubUser);
            }

            return users;
        });
    }
}
