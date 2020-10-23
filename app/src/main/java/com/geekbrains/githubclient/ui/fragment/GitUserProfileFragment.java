package com.geekbrains.githubclient.ui.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.model.entity.entity.GithubForks;

import com.geekbrains.githubclient.mvp.model.entity.entity.GithubUser;
import com.geekbrains.githubclient.mvp.presenter.UserFragmentPresenter;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.ui.BackButtonListener;
import com.geekbrains.githubclient.ui.adapter.RepoAdapter;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class GitUserProfileFragment extends MvpAppCompatFragment implements UsersView, BackButtonListener {
    private static final String GIT_USER = "git_user";
    private RecyclerView.LayoutManager mLayoutManager;
    private View mView;
    private RepoAdapter repoAdapter;
    private RecyclerView mRecyclerView;

    @InjectPresenter
    UserFragmentPresenter mPresenter;
    @Inject
    Router router;

    @ProvidePresenter
    UserFragmentPresenter provideForwardPresenter() {
        assert getArguments() != null;
        final GithubUser user = getArguments().getParcelable(GIT_USER);
       // return new UserFragmentPresenter(getArguments().getParcelableArrayList(GIT_USER), AndroidSchedulers.mainThread());
        return new UserFragmentPresenter(user);
    }

  //  public static GitUserProfileFragment getNewInstance(List<GithubForks> mForks) {
  public static GitUserProfileFragment getNewInstance(GithubUser user) {
       // ArrayList<GithubForks> tmp = new ArrayList<>(mForks);
        GitUserProfileFragment fragment = new GitUserProfileFragment();
        Bundle arguments = new Bundle();
       // arguments.putParcelableArrayList(GIT_USER, tmp);
      arguments.putParcelable(GIT_USER,  user);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.user_fragment_layout, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_users);

        return mView;
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }


    @Override
    public void init() {
        mLayoutManager = new LinearLayoutManager(mView.getContext());
        repoAdapter = new RepoAdapter(mPresenter.getPresenter());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(repoAdapter);
    }

    @Override
    public void updateList() {
        repoAdapter.notifyDataSetChanged();
    }
}
