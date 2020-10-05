package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepo;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.UserItemView;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<UsersView> {
    private static final String TAG = UsersPresenter.class.getSimpleName();
    private String logName;
    private static final boolean VERBOSE = true;
    private GithubUserRepo mUsersRepo = new GithubUserRepo();
    private Router mRouter = GithubApplication.INSTANCE.getRouter();

    public UsersPresenter() {

    }

    private class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();

        @Override
        public void onItemClick(UserItemView view) {

            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.getPos());
            }
            GithubUser user = mUsers.get(view.getPos());
            mRouter.navigateTo(new Screens.GitUserFragmentPage(user));
        }

        @Override
        public void bindView(UserItemView view) {
            GithubUser user = mUsers.get(view.getPos());
            setData(view, user);
        }

        @Override
        public int getCount() {
            return mUsers.size();
        }

    }

    private UsersPresenter.UsersListPresenter mUserListPresenter = new UsersPresenter.UsersListPresenter();

    public IUserListPresenter getPresenter() {
        return mUserListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();

        loadData();

    }

    private void loadData() {
        //TODO Подскажите, какой из представленных вариантов лучше, если никакой, то какой тогда :D
        //Вариант 1
        mUsersRepo.getUsers().concatMap(new Function<List<GithubUser>, ObservableSource<? extends List<GithubUser>>>() {
            @Override
            public ObservableSource<? extends List<GithubUser>> apply(List<GithubUser> githubUsers) throws Throwable {
                return Observable.just(githubUsers);
            }
        }).subscribe(
                (s) -> Log.i(TAG, "onNext " + mUserListPresenter.mUsers.addAll(s)),
                (e) -> Log.i(TAG, "onError " + e.getMessage()),
                () -> Log.i(TAG, "onComplete "));
        //Вариант 2
        // mUsersRepo.getUsers().subscribe(getUsersListObserver());

        getViewState().updateList();
    }


    public boolean backPressed() {
        mRouter.exit();
        return true;
    }

    private void setData(UserItemView view, GithubUser user) {
        final Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext " + s);
                view.setLogin(s);
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
        user.getLogin().subscribe(stringObserver);
    }

    private Observer<List<GithubUser>> getUsersListObserver() {
        return new Observer<List<GithubUser>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(List<GithubUser> values) {
                mUserListPresenter.mUsers.addAll(values);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        };
    }

    private void userList() {

    }

}
