package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.GithubApplication;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersForks;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersRepo;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.UserItemView;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.navigation.Screens;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<UsersView> {
    private static final String TAG = UsersPresenter.class.getSimpleName();
    private String logName;
    private static final boolean VERBOSE = true;


    @Inject
    IGithubUsersRepo usersRepo;

    @Inject
    IGithubUsersForks iGithubUsersForks;

    @Inject
    Router router;
    @Inject
    Scheduler scheduler;

   public UsersPresenter() {
        GithubApplication.INSTANCE.getAppComponent().inject(this);
    }

    class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();

        @Override
        public void onItemClick(UserItemView view) {

            if (VERBOSE) {
                String user = mUsers.get(view.getPos()).getLogin();
                Log.v(TAG, " onItemClick " + user);
            }
            mUsers.get(view.getPos());
             GithubUser user =  mUsers.get(view.getPos());
            router.navigateTo(new Screens.GitUserFragmentPage(user));

        }

        @Override
        public void bindView(UserItemView view) {
            GithubUser user = mUsers.get(view.getPos());
            //           setData(view, user);

            view.setLogin(user.getLogin());
            view.loadAvatar(user.getAvatarUrl());
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

        usersRepo.getUsers().observeOn(scheduler).subscribe(repos -> {
            mUserListPresenter.mUsers.clear();
            mUserListPresenter.mUsers.addAll(repos);
            getViewState().updateList();
        }, (e) -> {
            Log.w(TAG, "Error" + e.getMessage());
        });
        getViewState().updateList();

    }


    public boolean backPressed() {
        router.exit();
        return true;
    }

    private void userList() {

    }

}
