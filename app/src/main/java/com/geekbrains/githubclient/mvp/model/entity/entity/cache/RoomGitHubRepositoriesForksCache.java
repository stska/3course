package com.geekbrains.githubclient.mvp.model.entity.entity.cache;

import com.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.RoomGithubRepository;
import com.geekbrains.githubclient.mvp.model.entity.entity.room.RoomGithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomGitHubRepositoriesForksCache implements IGithubRepositoriesForksCache  {
    private final Database db;

    public RoomGitHubRepositoriesForksCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<List<GithubRepository>> getUserRepos(GithubUser user) {
        return Single.fromCallable(()-> {

            RoomGithubUser roomUser = db.userDao().findByLogin(user.getLogin());

            if (roomUser == null) {
                throw new RuntimeException("No such user in cache");
            }

            List<RoomGithubRepository> roomGithubRepository = db.repositoryDao().findByUser(roomUser.getId());

            List<GithubRepository> githubRepositoryList = new ArrayList<>();

            for (RoomGithubRepository roomGithubrepository : roomGithubRepository) {
                GithubRepository githubRepository = new GithubRepository(roomGithubrepository.getId(),
                        roomGithubrepository.getName(),
                        roomGithubrepository.getForksCount());

                githubRepositoryList.add(githubRepository);
            }

            return githubRepositoryList;
        });
    }

    @Override
    public Completable putUserRepos(GithubUser user, List<GithubRepository> repositories) {
        return Completable.fromAction(()->{
            RoomGithubUser roomUser = db.userDao().findByLogin(user.getLogin());

            List<RoomGithubRepository> roomGithubRepositories = new ArrayList<>();

            for (GithubRepository repo: repositories) {
                RoomGithubRepository roomRepo = new RoomGithubRepository(repo.getId(), repo.getName(), repo.getForksCount(), roomUser.getId());
                roomGithubRepositories.add(roomRepo);
            }

            db.repositoryDao().insert(roomGithubRepositories);
        }).subscribeOn(Schedulers.io());
    }
}
