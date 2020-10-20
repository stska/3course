package com.geekbrains.githubclient.mvp.model.entity.repo;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IGithubUsersForks {
    Single<List<GithubForks>> getForks( String repos_url,GithubUser githubUser);
}
