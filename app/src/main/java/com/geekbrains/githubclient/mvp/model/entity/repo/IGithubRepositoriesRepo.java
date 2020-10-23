package com.geekbrains.githubclient.mvp.model.entity.repo;

import com.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IGithubRepositoriesRepo {
    Single<List<GithubRepository>> getRepositories(GithubUser user);
}