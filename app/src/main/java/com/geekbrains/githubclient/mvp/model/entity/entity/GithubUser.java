package com.geekbrains.githubclient.mvp.model.entity.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class GithubUser implements Parcelable {
    @Expose
    private String login;
    @Expose
    String id;
    @Expose
    String avatarUrl;
    // @Expose String reposUrl;
    @Expose
    String repos_url;

    public GithubUser(String id, String login, String avatar, String repoUrl) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatar;
        this. repos_url = repoUrl;
    }

    public GithubUser(String login) {
        this.login = login;
    }

    protected GithubUser(Parcel in) {
        login = in.readString();
    }


    public static final Creator<GithubUser> CREATOR = new Creator<GithubUser>() {
        @Override
        public GithubUser createFromParcel(Parcel in) {
            return new GithubUser(in);
        }

        @Override
        public GithubUser[] newArray(int size) {
            return new GithubUser[size];
        }
    };

    /*   public Observable<String> getLogin() {
           return Observable.just(mLogin);
       } */
    public String getLogin() {
        return login;
    }

    public String getReposUrl() {
        return repos_url;
    }

    public String getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
    }
}
