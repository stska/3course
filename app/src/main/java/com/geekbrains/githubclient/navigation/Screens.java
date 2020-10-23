package com.geekbrains.githubclient.navigation;

import androidx.fragment.app.Fragment;

import com.geekbrains.githubclient.mvp.model.entity.GithubRepository;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.ui.fragment.GitUserProfileFragment;
import com.geekbrains.githubclient.ui.fragment.RepositoryForkFragment;
import com.geekbrains.githubclient.ui.fragment.UsersFragment;

import java.util.List;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new UsersFragment();
        }
    }
    public static final class GitUserFragmentPage extends SupportAppScreen {
        private final GithubUser githubUser;
        public GitUserFragmentPage(GithubUser user){
            githubUser = user;

        }
        @Override
        public Fragment getFragment() {
            return GitUserProfileFragment.getNewInstance(githubUser);
        }
    }
    public static class RepositoryScreen extends SupportAppScreen {
       private final  GithubForks repository;
        public RepositoryScreen( GithubForks repo) {
            this.repository = repo;
        }

        @Override
        public Fragment getFragment() {
            return RepositoryForkFragment.newInstance(repository);
        }
    }

}
