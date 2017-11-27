package com.vinicius.findergit.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vinicius.findergit.R;
import com.vinicius.findergit.data.dao.OwnerDao;
import com.vinicius.findergit.data.model.RealmOwner;
import com.vinicius.findergit.data.model.RealmRepository;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by paulo on 26/11/17.
 *
 */

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private Context context;
    private OwnerDao ownerDao;
    private RealmResults<RealmRepository> realmResults;
    SearchAdapter(Context context, RealmResults<RealmRepository> realmResults) {
        this.context = context;
        this.realmResults = realmResults;
        ownerDao = new OwnerDao();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final RealmRepository repository = realmResults.get(position);
        RealmOwner realmOwner;
        String avatarUrl;

        if (repository != null) {
            realmOwner = ownerDao.get(repository.getOwnerName());
            holder.tvNameRepository.setText(repository.getName());
            holder.tvLanguage.setText(repository.getLanguage());
            holder.tvNameOwner.setText(repository.getOwnerName());

            if (realmOwner != null) {
                avatarUrl = realmOwner.getAvatarUrl();

                if (avatarUrl!=null && !avatarUrl.equals("")) {
                    Picasso.with(context)
                            .load(avatarUrl)
                            .resize(60,60)
                            .into(holder.ivRepository);
                }
            }

            holder.rlItemSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Uri uri = Uri.parse(repository.getUrl());
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(uri));
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.error_unknown, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.rl_item_search)
        RelativeLayout rlItemSearch;
        @BindView(R.id.iv_repository)
        ImageView ivRepository;
        @BindView(R.id.tv_name_owner)
        TextView tvNameOwner;
        @BindView(R.id.tv_name_repository)
        TextView tvNameRepository;
        @BindView(R.id.tv_language)
        TextView tvLanguage;
        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

/*
class SearchAdapter extends RealmBasedRecyclerViewAdapter<RealmRepository, SearchAdapter.SearchViewHolder> {
    private Context context;
    OwnerDao ownerDao;
    SearchAdapter(Context context, RealmResults<RealmRepository> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.context = context;
        ownerDao = new OwnerDao();
    }

    @Override
    public SearchViewHolder onCreateRealmViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchViewHolder(inflater.inflate(R.layout.item_search, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(@NonNull final SearchViewHolder searchViewHolder, int i) {
        final RealmRepository repository = realmResults.get(i);
        RealmOwner realmOwner;
        String avatarUrl;

        if (repository != null) {
            realmOwner = ownerDao.get(repository.getOwnerName());
            searchViewHolder.tvNameRepository.setText(repository.getName());
            searchViewHolder.tvLanguage.setText(repository.getLanguage());
            searchViewHolder.tvNameOwner.setText(repository.getOwnerName());

            if (realmOwner != null) {
                avatarUrl = realmOwner.getAvatarUrl();

                if (!avatarUrl.equals("")) {
                    Picasso.with(context)
                            .load(avatarUrl)
                            .resize(60,60)
                            .into(searchViewHolder.ivRepository);
                }
            }

            searchViewHolder.rlItemSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Uri uri = Uri.parse(repository.getUrl());
                        context.startActivity(new Intent().setData(uri));
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.error_unknown, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    class SearchViewHolder extends RealmViewHolder {
        @BindView(R.id.rl_item_search)
        RelativeLayout rlItemSearch;
        @BindView(R.id.iv_repository)
        ImageView ivRepository;
        @BindView(R.id.tv_name_owner)
        TextView tvNameOwner;
        @BindView(R.id.tv_name_repository)
        TextView tvNameRepository;
        @BindView(R.id.tv_language)
        TextView tvLanguage;
        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
*/
