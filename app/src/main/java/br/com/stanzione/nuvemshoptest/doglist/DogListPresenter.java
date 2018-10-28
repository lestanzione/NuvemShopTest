package br.com.stanzione.nuvemshoptest.doglist;

import java.util.List;

import br.com.stanzione.nuvemshoptest.data.Dog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
                repository.fetchDogList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Dog>>() {
                                    @Override
                                    public void accept(List<Dog> dogs) throws Exception {
                                        System.out.println(dogs);
                                        System.out.println(dogs.size());
                                        view.showDogList(dogs);
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
    public void attachView(DogListContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}
