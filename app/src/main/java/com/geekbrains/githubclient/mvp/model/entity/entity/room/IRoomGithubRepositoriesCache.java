package com.geekbrains.githubclient.mvp.model.entity.entity.room;

import com.geekbrains.githubclient.mvp.model.entity.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IRoomGithubRepositoriesCache {
    Single<List<GithubForks>> getRepoFromDb(Database db, GithubUser githubUser);
    Single<List<GithubForks>> saveReposToDb(String repos_url, IDataSource api, Database d, GithubUser githubUser);
}
