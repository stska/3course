package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.Model;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersForks;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersRepo;
import com.geekbrains.githubclient.mvp.model.entity.repo.retrofit.RetrofitGithubUsersRepo;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.IRepoListPresenter;
import com.geekbrains.githubclient.mvp.view.ISetLogNameForUserProf;
import com.geekbrains.githubclient.mvp.view.RepoItemView;
import com.geekbrains.githubclient.mvp.view.UserItemView;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.ui.BackButtonListener;
import com.geekbrains.githubclient.ui.fragment.UsersFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

//public class UserFragmentPresenter extends MvpPresenter<UserProfileView> implements BackButtonListener{
public class UserFragmentPresenter extends MvpPresenter<UsersView> implements BackButtonListener {
    private static final String TAG = UsersPresenter.class.getSimpleName();
    private final String GREETING = "Hello, user: ";
    private GithubUser githubUser;
    private GithubForks githubForks;
   // private List<GithubForks> githubForks;
   // private final Router ROUTER;
    private ArrayList<GithubForks> githubForksTEST;

    private final Scheduler SCHEDULER;
    private Router mRouter = GithubApplication.INSTANCE.getRouter();
    // public UserFragmentPresenter(String logName) {
    public UserFragmentPresenter(ArrayList<GithubForks> githubForks,Scheduler scheduler) {

        githubForksTEST = githubForks;
        SCHEDULER = scheduler;

    }
    private class UsersListPresenter implements IRepoListPresenter {

        @Override
        public void onItemClick(RepoItemView view) {

        }

        @Override
        public void bindView(RepoItemView view) {
           GithubForks githubFork = githubForksTEST.get(view.getPos());
           view.setRepoName(githubFork.getForkName());
        }

        @Override
        public int getCount() {
            return githubForksTEST.size();
        }
    }


    public boolean backPressed() {
        mRouter.exit();
        return true;
    }
    private UserFragmentPresenter.UsersListPresenter usersListPresenter = new UserFragmentPresenter.UsersListPresenter();


    public IRepoListPresenter getPresenter() {
        return usersListPresenter;
    }
    final Observer<String> stringObserver = new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Log.i(TAG, "onSubscribe");
        }

        @Override
        public void onNext(@NonNull String s) {
            Log.i(TAG, "onNext " + s);
            getViewState();
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
        getViewState().init();
        loadData();
    }

    private void loadData() {
        //TODO Подскажите, какой из представленных вариантов лучше, если никакой, то какой тогда :D



        //Вариант 1
   /*     mUsersRepo.getUsers().concatMap(githubUsers -> Observable.just(githubUsers)).subscribe(
                (s) -> Log.i(TAG, "onNext " + mUserListPresenter.mUsers.addAll(s)),
                (e) -> Log.i(TAG, "onError " + e.getMessage()),
                () -> Log.i(TAG, "onComplete "));  */
        //Вариант 2
        // mUsersRepo.getUsers().subscribe(getUsersListObserver());

        getViewState().updateList();
    }
}
