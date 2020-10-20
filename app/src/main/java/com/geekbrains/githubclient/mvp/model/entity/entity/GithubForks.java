package com.geekbrains.githubclient.mvp.model.entity.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class GithubForks implements Parcelable {
    @Expose String name;
    @Expose String id;
    @Expose String forks;
    @Expose String userId;


   public GithubForks(String name,String id,String fork,String userId){
       this.name = name;
       this.id = id;
       this.forks = fork;
       this.userId = userId;
   }

    protected GithubForks(Parcel in) {
        name = in.readString();
    }

    public static final Creator<GithubForks> CREATOR = new Creator<GithubForks>() {
        @Override
        public GithubForks createFromParcel(Parcel in) {
            return new GithubForks(in);
        }

        @Override
        public GithubForks[] newArray(int size) {
            return new GithubForks[size];
        }
    };

    public String getForkName() {
        return name;
    }
    public String getId(){
        return id;
    }
    public String getFork(){
        return forks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
