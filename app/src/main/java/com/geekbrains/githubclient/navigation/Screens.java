package com.geekbrains.githubclient.navigation;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.ui.fragment.GitUserProfileFragment;
import com.geekbrains.githubclient.ui.fragment.UsersFragment;

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
        private final GithubUser githubUser;

        public GitUserFragmentPage(GithubUser githubUser){
            this.githubUser = githubUser;
            //this.loginName = loginName;
        }
        @Override
        public Fragment getFragment() {
            return GitUserProfileFragment.getNewInstance(githubUser);
            //return GitUserProfileFragment.getNewInstance(loginName);
        }
    }

}
