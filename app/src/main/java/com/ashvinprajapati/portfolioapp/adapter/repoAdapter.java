package com.ashvinprajapati.portfolioapp.adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashvinprajapati.portfolioapp.Models.repoModel;
import com.ashvinprajapati.portfolioapp.R;

import java.util.List;

public class repoAdapter extends RecyclerView.Adapter<repoAdapter.ViewHolder> {

    static View repoCard;

    private List<repoModel> repoModelList;
    public repoAdapter(List<repoModel> repoList) {
        this.repoModelList = repoList;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<repoModel> newData) {
        repoModelList.clear();
        repoModelList.addAll(newData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, link;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.repoName);
            description = view.findViewById(R.id.repoDescription);
            link = view.findViewById(R.id.repoLink);
            repoCard = view.findViewById(R.id.repoCard);
        }
    }

    @NonNull
    @Override
    public repoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projects, parent, false);
        return new repoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull repoAdapter.ViewHolder holder, int position) {
        repoModel repoModel = repoModelList.get(position);
        holder.name.setText("Name: " + repoModel.getName());
        holder.description.setText("Description: " + repoModel.getDescription());
        holder.link.setText("Link: " + repoModel.getLink());
        holder.link.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repoModel.getLink()));
            v.getContext().startActivity(intent);
        });
        Animation anim = AnimationUtils.loadAnimation(repoAdapter.repoCard.getContext(), R.anim.fade_slide_up);
        repoAdapter.repoCard.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return repoModelList.size();
    }
}
