package com.geekbrains.githubclient.mvp.model.entity.entity.room;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;

import java.util.List;

public class Cache {
    private List<GithubUser> mGithubUserList;

    public Cache(List<GithubUser> githubUserList){
        mGithubUserList = githubUserList;
    }


}
