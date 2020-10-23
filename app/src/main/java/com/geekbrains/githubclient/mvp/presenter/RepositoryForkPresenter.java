package com.geekbrains.githubclient.mvp.presenter;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.view.RepositoryView;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class RepositoryForkPresenter extends MvpPresenter<RepositoryView> {

   private final GithubForks githubRepository;
    @Inject
    Router router;

   public RepositoryForkPresenter( GithubForks githubRepository) {
        this.githubRepository = githubRepository;
        GithubApplication.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();

        String id = githubRepository.getId();
        String title = githubRepository.getForkName();
        String forks = githubRepository.getFork();

        getViewState().setId(id != null ? id : "");
        getViewState().setTitle(title != null ? title : "");
        getViewState().setForksCount(String.valueOf(forks));

    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}
