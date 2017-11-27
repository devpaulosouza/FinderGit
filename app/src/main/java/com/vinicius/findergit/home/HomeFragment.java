package com.vinicius.findergit.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinicius.findergit.R;
import com.vinicius.findergit.data.dao.RepositoryDao;
import com.vinicius.findergit.data.model.RealmRepository;

import io.realm.RealmResults;

public class HomeFragment extends Fragment implements MainActivity.RepositorySearchListener {
    RecyclerView rvSearch;
    SearchAdapter searchAdapter;
    RealmResults<RealmRepository> realmRepositories;
    RepositoryDao repositoryDao;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        rvSearch = view.findViewById(R.id.rv_search);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rvSearch.setLayoutManager(llm);

        repositoryDao = new RepositoryDao();

        realmRepositories = repositoryDao.getAll();

        searchAdapter = new SearchAdapter(view.getContext(), realmRepositories);

        rvSearch.setAdapter(searchAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSearchResult() {

    }

    public void notifyAdapterChanged() {
        realmRepositories = repositoryDao.getAll();
        searchAdapter.notifyDataSetChanged();
    }
}
