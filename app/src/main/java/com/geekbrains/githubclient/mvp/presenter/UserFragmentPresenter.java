package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.view.ISetLogNameForUserProf;
import com.geekbrains.githubclient.mvp.view.UserProfileView;
import com.geekbrains.githubclient.ui.BackButtonListener;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

//public class UserFragmentPresenter extends MvpPresenter<UserProfileView> implements BackButtonListener{
public class UserFragmentPresenter extends MvpPresenter<ISetLogNameForUserProf> implements BackButtonListener {
    private static final String TAG = UsersPresenter.class.getSimpleName();
    private Router mRouter;
    //private String logName;
    private final String GREETING = "Hello, user: ";
    private GithubUser githubUser;

    // public UserFragmentPresenter(String logName) {
    public UserFragmentPresenter(GithubUser githubUser) {
        mRouter = GithubApplication.INSTANCE.getRouter();

        if (githubUser != null) {
            githubUser.getLogin().subscribe(stringObserver);
        }
    }

    public boolean backPressed() {
        mRouter.exit();
        return true;
    }

    final Observer<String> stringObserver = new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Log.i(TAG, "onSubscribe");
        }

        @Override
        public void onNext(@NonNull String s) {
            Log.i(TAG, "onNext " + s);
            getViewState().setLogUserLogName(GREETING.concat(s));
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.i(TAG, "onError ");
        }

        @Override
        public void onComplete() {
            Log.i(TAG, "onComplete");
        }
    };

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

}
