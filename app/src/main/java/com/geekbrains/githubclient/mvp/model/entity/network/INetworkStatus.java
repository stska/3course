package com.geekbrains.githubclient.mvp.model.entity.network;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface INetworkStatus {
    Observable<Boolean> isOnline();
    Single<Boolean> isOnlineSingle();
}
