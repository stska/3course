package com.geekbrains.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface UserProfileView extends MvpView {
   void loadProfile();
}
