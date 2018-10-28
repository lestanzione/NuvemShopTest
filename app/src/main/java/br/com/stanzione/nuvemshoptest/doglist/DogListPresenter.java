package br.com.stanzione.nuvemshoptest.doglist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.stanzione.nuvemshoptest.data.Cat;
import br.com.stanzione.nuvemshoptest.data.Dog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Func2;

public class DogListPresenter implements DogListContract.Presenter {

    private DogListContract.View view;
    private DogListContract.Repository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DogListPresenter(DogListContract.Repository repository){
        this.repository = repository;
    }

    @Override
    public void getDogList() {

        view.setProgressBarVisible(true);

        compositeDisposable.add(
                Observable.zip(repository.fetchDogList(), repository.fetchDogList(),
                        new BiFunction<List<Dog>, List<Dog>, List<Dog>>() {
                            @Override
                            public List<Dog> apply(List<Dog> dogs, List<Dog> dogs2) throws Exception {
                                List<Dog> finalDogList = new ArrayList<>();
                                finalDogList.addAll(dogs);
                                finalDogList.addAll(dogs2);
                                return finalDogList;
                            }
                        }
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Dog>>() {
                                    @Override
                                    public void accept(List<Dog> dogs) throws Exception {
                                        view.showDogList(dogs);
                                        view.setProgressBarVisible(false);
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        view.setProgressBarVisible(false);
                                        if(throwable instanceof IOException){
                                            view.showNetworkError();
                                        }
                                        else{
                                            view.showGeneralError();
                                        }
                                    }
                                }
                        )
        );

    }

    @Override
    public void attachView(DogListContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}
