package br.com.stanzione.nuvemshoptest.doglist;

import java.util.List;

import br.com.stanzione.nuvemshoptest.BasePresenter;
import br.com.stanzione.nuvemshoptest.BaseView;
import br.com.stanzione.nuvemshoptest.data.Dog;
import io.reactivex.Observable;

public interface DogListContract {

    interface View extends BaseView {
        void showDogList(List<Dog> dogList);
        void showDogBreed(String breed);
    }

    interface Presenter extends BasePresenter<View> {
        void getDogList();
        void getDogBreed(Dog dog);
    }

    interface Repository {
        Observable<List<Dog>> fetchDogList();
    }

}
