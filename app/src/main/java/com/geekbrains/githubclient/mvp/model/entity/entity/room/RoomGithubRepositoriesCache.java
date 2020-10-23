package com.geekbrains.githubclient.mvp.model.entity.entity.room;

import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RoomGithubRepositoriesCache implements IRoomGithubRepositoriesCache {
    @Override
    public Single<List<GithubForks>> getRepoFromDb(Database db, GithubUser githubUser) {
        return Single.fromCallable(() -> {
            List<RoomGithubRepository> roomGithubRepository = db.repositoryDao().findByUser(githubUser.getId());
            List<GithubForks> repos = new ArrayList<>();
            if (roomGithubRepository.size() > 0) {
                for (RoomGithubRepository roomGithubRep : roomGithubRepository) {
                    GithubForks githubFork = new GithubForks(roomGithubRep.getName(),
                            roomGithubRep.getId(),
                            roomGithubRep.forksCount,
                            roomGithubRep.getUserId());
                    repos.add(githubFork);

                }
            }

            return repos;

        });
    }

    @Override
    public Single<List<GithubForks>> saveReposToDb(String repos_url, IDataSource api, Database d, GithubUser githubUser) {
        return api.getForks(repos_url).flatMap((repos) -> Single.fromCallable(() -> {
            List<RoomGithubRepository> roomGithubRepositories = new ArrayList<>();

            for (GithubForks githubForks : repos) {
                RoomGithubRepository roomGithubRepository = new RoomGithubRepository(githubForks.getId(),
                        githubForks.getForkName(),
                        githubForks.getFork(),
                        githubUser.getId());
                roomGithubRepositories.add(roomGithubRepository);
            }
            d.repositoryDao().insert(roomGithubRepositories);
            return repos;
        }));
    }
}
