package br.com.stanzione.nuvemshoptest.doglist;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import br.com.stanzione.nuvemshoptest.App;
import br.com.stanzione.nuvemshoptest.R;
import br.com.stanzione.nuvemshoptest.data.Dog;
import br.com.stanzione.nuvemshoptest.doglist.adapter.DogListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DogListFragment extends Fragment implements DogListContract.View {

    @Inject
    DogListContract.Presenter presenter;

    @BindView(R.id.dogListRecyclerView)
    RecyclerView dogListRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private DogListAdapter adapter;

    private Boolean isStarted = false;
    private Boolean isVisible = false;


    public DogListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dog_list, container, false);
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
            presenter.getDogList();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            presenter.getDogList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    private void setupUi(View view){
        ButterKnife.bind(this, view);

        adapter = new DogListAdapter(getContext());
        dogListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        dogListRecyclerView.setAdapter(adapter);

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
        Snackbar.make(dogListRecyclerView, "General error", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(dogListRecyclerView, "Network error", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDogList(List<Dog> dogList) {
        adapter.setItems(dogList);
    }
}
