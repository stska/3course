package com.geekbrains.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class GithubRepository implements Parcelable {
    @Expose
    String id;
    @Expose String name;
    @Expose String forksCount;

    public GithubRepository(String id, String name, String forksCount) {
        this.id = id;
        this.name = name;
        this.forksCount = forksCount;
    }


    protected GithubRepository(Parcel in) {
        id = in.readString();
        name = in.readString();
        forksCount = in.readString();
    }

    public static final Creator<GithubRepository> CREATOR = new Creator<GithubRepository>() {
        @Override
        public GithubRepository createFromParcel(Parcel in) {
            return new GithubRepository(in);
        }

        @Override
        public GithubRepository[] newArray(int size) {
            return new GithubRepository[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForksCount() {
        return forksCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(forksCount);
    }

}