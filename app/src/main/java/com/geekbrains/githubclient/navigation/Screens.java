package com.geekbrains.githubclient.navigation;

import androidx.fragment.app.Fragment;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.ui.fragment.GitUserProfileFragment;
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
        //private final String loginName;
        //private final GithubUser githubUser;
        private final List<GithubForks> githubForks;

        public GitUserFragmentPage(List<GithubForks> githubForks){
            this.githubForks = githubForks;
            //this.loginName = loginName;
        }
        @Override
        public Fragment getFragment() {
            return GitUserProfileFragment.getNewInstance(githubForks);
            //return GitUserProfileFragment.getNewInstance(loginName);
        }
    }

}
