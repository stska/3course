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
    // private GithubUserRepo mUsersRepo = new GithubUserRepo();
    private Router mRouter = GithubApplication.INSTANCE.getRouter();

    private final IGithubUsersRepo USERS_REPO;
    private final Router ROUTER;
    private final Scheduler SCHEDULER;
    // private RetrofitGithubUsersForks retrofitGithubUsersForks;
    private final IGithubUsersForks RETROFIT_GITHUB_USERS_REPOSITIRIES;


    public UsersPresenter(Scheduler scheduler, IGithubUsersRepo usersRepo, Router router, IGithubUsersForks retrofitGithubUsersForks) {
        SCHEDULER = scheduler;
        USERS_REPO = usersRepo;
        ROUTER = router;
        RETROFIT_GITHUB_USERS_REPOSITIRIES = retrofitGithubUsersForks;

    }

    class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();
        private List<GithubForks> mForks = new ArrayList<>();

        @Override
        public void onItemClick(UserItemView view) {

            if (VERBOSE) {
                String user = mUsers.get(view.getPos()).getLogin();
                Log.v(TAG, " onItemClick " + user);
            }
            mUsers.get(view.getPos());

            String urlRepos = mUsers.get(view.getPos()).getReposUrl();
            loadForks(urlRepos, mUsers.get(view.getPos()));

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

    private void loadForks(String urlRepos, GithubUser githubUser) {
        RETROFIT_GITHUB_USERS_REPOSITIRIES.getForks(urlRepos, githubUser).observeOn(SCHEDULER).subscribe(f -> {
            Log.v(TAG, "found some forks");
            mUserListPresenter.mForks.clear();
            mUserListPresenter.mForks.addAll(f);
            mRouter.navigateTo(new Screens.GitUserFragmentPage(f));
        }, (e) -> {
            Log.w(TAG, "Error" + e.getMessage());
        });

    }

    private void loadData() {

        USERS_REPO.getUsers().observeOn(SCHEDULER).subscribe(repos -> {
            mUserListPresenter.mUsers.clear();
            mUserListPresenter.mUsers.addAll(repos);
            getViewState().updateList();
        }, (e) -> {
            Log.w(TAG, "Error" + e.getMessage());
        });
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
//Вариант 1
   /*     mUsersRepo.getUsers().concatMap(githubUsers -> Observable.just(githubUsers)).subscribe(
                (s) -> Log.i(TAG, "onNext " + mUserListPresenter.mUsers.addAll(s)),
                (e) -> Log.i(TAG, "onError " + e.getMessage()),
                () -> Log.i(TAG, "onComplete "));  */
//Вариант 2
// mUsersRepo.getUsers().subscribe(getUsersListObserver());