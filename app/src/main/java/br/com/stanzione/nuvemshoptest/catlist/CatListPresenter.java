package br.com.stanzione.nuvemshoptest.catlist;

public class CatListPresenter implements CatListContract.Presenter {

    private CatListContract.View view;
    private CatListContract.Repository repository;

    public CatListPresenter(CatListContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void getCatList() {

    }

    @Override
    public void attachView(CatListContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {

    }
}
