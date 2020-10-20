package com.geekbrains.githubclient.mvp.model.entity.entity.room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (
        foreignKeys = {@ForeignKey(
                entity = RoomGithubUser.class,
                parentColumns = {"id"},
                childColumns = {"userId"},
                onDelete = ForeignKey.CASCADE
        )}
)
public class RoomGithubRepository {
    @PrimaryKey @NonNull
    public String id;
    public String name;
   // public int forksCount;
   public String forksCount;
    public String userId;

    public RoomGithubRepository(){

    }
    public RoomGithubRepository(String id,String name,String forksCount,String userId){
        this.id = id;
        this.name = name;
        this.forksCount = forksCount;
        this.userId = userId;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getForksCount(){
        return forksCount;
    }
    public String getUserId(){
        return userId;
    }
}