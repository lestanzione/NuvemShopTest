package br.com.stanzione.nuvemshoptest.doglist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.stanzione.nuvemshoptest.data.Dog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
                Observable.zip(repository.fetchDogList(), repository.fetchDogList(), this::concatDogLists)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::onDogListReceived,
                                this::onDogListError
                        )
        );

    }

    @Override
    public void getDogBreed(Dog dog) {
        if(null != dog.getBreedList() && !dog.getBreedList().isEmpty()) {
            view.showDogBreed(dog.getBreedList().get(0).getName());
        }
    }

    @Override
    public void attachView(DogListContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    private List<Dog> concatDogLists(List<Dog> dogs, List<Dog> dogs2) {
        List<Dog> finalDogList = new ArrayList<>();
        finalDogList.addAll(dogs);
        finalDogList.addAll(dogs2);
        return finalDogList;
    }

    private void onDogListReceived(List<Dog> dogs) {
        view.showDogList(dogs);
        view.setProgressBarVisible(false);
    }

    private void onDogListError(Throwable throwable) {
        view.setProgressBarVisible(false);
        if(throwable instanceof IOException){
            view.showNetworkError();
        }
        else{
            view.showGeneralError();
        }
    }

}
