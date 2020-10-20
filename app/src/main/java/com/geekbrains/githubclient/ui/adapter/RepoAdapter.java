package com.geekbrains.githubclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.IRepoListPresenter;
import com.geekbrains.githubclient.mvp.view.RepoItemView;
import com.geekbrains.githubclient.mvp.view.UserItemView;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
private IRepoListPresenter mPresenter;

public RepoAdapter(IRepoListPresenter presenter) {
        mPresenter = presenter;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View userView = inflater.inflate(R.layout.repo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);

        return viewHolder;
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onItemClick(holder);
            }
        });

        mPresenter.bindView(holder);
    }

    @Override
public int getItemCount() {
        return mPresenter.getCount();
        }

public static class ViewHolder extends RecyclerView.ViewHolder implements RepoItemView {
    TextView textView;
    private int position;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.repo_name);
    }

    @Override
    public int getPos() {
        return position;
    }

    @Override
    public void setRepoName(String text) {
        textView.setText(text);
    }

}

}
