package com.geekbrains.githubclient.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.network.INetworkStatus;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AndroidNetworkStatus implements INetworkStatus {
    private static final String TAG = AndroidNetworkStatus.class.getSimpleName();

    private BehaviorSubject<Boolean> statusObject = BehaviorSubject.create();


    public AndroidNetworkStatus() {
        statusObject.onNext(false);

        ConnectivityManager connectivityManager = (ConnectivityManager) GithubApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest networkRequest = new NetworkRequest.Builder().build();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                statusObject.onNext(true);
                if (GithubApplication.DEBUG) {
                    Log.d(TAG, "onAvailable");
                }
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();

                if (GithubApplication.DEBUG) {
                    Log.d(TAG, "onUnavailable");
                }

                statusObject.onNext(false);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);

                if (GithubApplication.DEBUG) {
                    Log.d(TAG, "onLost");
                }

                statusObject.onNext(false);
            }
        });
    }

    @Override
    public Observable<Boolean> isOnline() {
        return statusObject;
    }

    @Override
    public Single<Boolean> isOnlineSingle() {
        return statusObject.first(false);
    }
}