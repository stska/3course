package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import com.geekbrains.githubclient.mvp.model.entity.Model;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubRepositoriesRepo;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersForks;
import com.geekbrains.githubclient.mvp.model.entity.repo.IGithubUsersRepo;
import com.geekbrains.githubclient.mvp.model.entity.repo.retrofit.RetrofitGithubUsersRepo;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.IRepoListPresenter;
import com.geekbrains.githubclient.mvp.view.ISetLogNameForUserProf;
import com.geekbrains.githubclient.mvp.view.RepoItemView;
import com.geekbrains.githubclient.mvp.view.UserItemView;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.navigation.Screens;
import com.geekbrains.githubclient.ui.BackButtonListener;
import com.geekbrains.githubclient.ui.fragment.UsersFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Router;

//public class UserFragmentPresenter extends MvpPresenter<UserProfileView> implements BackButtonListener{
public class UserFragmentPresenter extends MvpPresenter<UsersView> implements BackButtonListener {
    private static final String TAG = UsersPresenter.class.getSimpleName();

    @Inject
    IGithubUsersForks iGithubUsersForks;
    @Inject
    Router router;
    @Inject
    Scheduler scheduler;

    private final GithubUser user;

    public UserFragmentPresenter(GithubUser user) {
        GithubApplication.INSTANCE.getAppComponent().inject(this);
       this.user =  user;


    }
    private class UsersListPresenter implements IRepoListPresenter {
       private List<GithubForks> repositories = new ArrayList<>();

        @Override
        public void onItemClick(RepoItemView view) {
            final GithubForks repository = repositories.get(view.getPos());
           router.navigateTo(new Screens.RepositoryScreen(repository));

        }

        @Override
        public void bindView(RepoItemView view) {

            GithubForks githubFork = repositories.get(view.getPos());
           view.setRepoName(githubFork.getForkName());
        }

        @Override
        public int getCount() {

            return repositories.size();
        }
    }


    public boolean backPressed() {
        router.exit();
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
          iGithubUsersForks.getForks(user.getReposUrl(),user).observeOn(scheduler).subscribe(repositories-> {
            usersListPresenter.repositories.clear();
            usersListPresenter.repositories.addAll(repositories);
            getViewState().updateList();
        }, (e) -> {
            Log.w(TAG, "Error" + e.getMessage());
        });

        getViewState().updateList();
    }
}
