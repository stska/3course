package com.geekbrains.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface RepositoryView extends MvpView {
    void init();
    void setId(String text);
    void setTitle(String text);
    void setForksCount(String text);
}
