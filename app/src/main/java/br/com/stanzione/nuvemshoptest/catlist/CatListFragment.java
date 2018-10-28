package br.com.stanzione.nuvemshoptest.catlist;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import br.com.stanzione.nuvemshoptest.App;
import br.com.stanzione.nuvemshoptest.R;
import br.com.stanzione.nuvemshoptest.catlist.adapter.CatListAdapter;
import br.com.stanzione.nuvemshoptest.data.Cat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CatListFragment extends Fragment implements CatListContract.View, CatListAdapter.OnCatSelectedListener {

    @Inject
    CatListContract.Presenter presenter;

    @BindView(R.id.catListRecyclerView)
    RecyclerView catListRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private CatListAdapter adapter;
    private List<Cat> catList;

    private Boolean isStarted = false;
    private Boolean isVisible = false;


    public CatListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cat_list, container, false);
        setupUi(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        setupInjector(context);
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible) {
            presenter.getCatList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            presenter.getCatList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    private void setupUi(View view){
        ButterKnife.bind(this, view);

        adapter = new CatListAdapter(getContext(), this);
        catListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        catListRecyclerView.setAdapter(adapter);

    }

    private void setupInjector(Context context){
        ((App) (context.getApplicationContext()))
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showGeneralError() {
        Snackbar.make(catListRecyclerView, "General error", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(catListRecyclerView, "Network error", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showCatList(List<Cat> catList) {
        this.catList = catList;
        adapter.setItems(catList);
    }

    @Override
    public void onCatImageSelected(int position) {
        Cat selectedCat = catList.get(position);

        if(null != selectedCat.getBreedList() && !selectedCat.getBreedList().isEmpty()) {
            Snackbar.make(catListRecyclerView, selectedCat.getBreedList().get(0).getName(), Snackbar.LENGTH_LONG).show();
        }
    }
}
