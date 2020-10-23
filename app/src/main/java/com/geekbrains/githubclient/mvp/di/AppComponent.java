package com.geekbrains.githubclient.mvp.di;

import com.geekbrains.githubclient.mvp.di.module.ApiModule;
import com.geekbrains.githubclient.mvp.di.module.AppModule;
import com.geekbrains.githubclient.mvp.di.module.CacheModule;
import com.geekbrains.githubclient.mvp.di.module.CiceroneModule;
import com.geekbrains.githubclient.mvp.di.module.RepoModule;
import com.geekbrains.githubclient.mvp.presenter.MainPresenter;
import com.geekbrains.githubclient.mvp.presenter.RepositoryForkPresenter;
import com.geekbrains.githubclient.mvp.presenter.UserFragmentPresenter;
import com.geekbrains.githubclient.mvp.presenter.UsersPresenter;
import com.geekbrains.githubclient.ui.MainActivity;
import com.geekbrains.githubclient.ui.fragment.RepositoryForkFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApiModule.class,
                AppModule.class,
                CacheModule.class,
                CiceroneModule.class,
                RepoModule.class
        }
)
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
    void inject(UsersPresenter usersPresenter);
    void inject(UserFragmentPresenter userFragmentPresenter);
    void inject(RepositoryForkPresenter repositoryForkPresenter);

}