package br.com.stanzione.nuvemshoptest.catlist;

import java.util.List;

import br.com.stanzione.nuvemshoptest.data.Cat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CatListPresenter implements CatListContract.Presenter {

    private CatListContract.View view;
    private CatListContract.Repository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CatListPresenter(CatListContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void getCatList() {

        view.setProgressBarVisible(true);

        compositeDisposable.add(
                repository.fetchCatList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Cat>>() {
                                    @Override
                                    public void accept(List<Cat> cats) throws Exception {
                                        System.out.println(cats);
                                        System.out.println(cats.size());
                                        view.showCatList(cats);
                                        view.setProgressBarVisible(false);
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        throwable.printStackTrace();
                                    }
                                }
                        )
        );

    }

    @Override
    public void attachView(CatListContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}
