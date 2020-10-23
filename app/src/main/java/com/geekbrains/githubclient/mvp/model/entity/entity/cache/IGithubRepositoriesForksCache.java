package com.geekbrains.githubclient.mvp.model.entity.entity.cache;

import com.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IGithubRepositoriesForksCache {
    Single<List<GithubRepository>> getUserRepos(GithubUser user);
    Completable putUserRepos(GithubUser user, List<GithubRepository> repositories);
}
