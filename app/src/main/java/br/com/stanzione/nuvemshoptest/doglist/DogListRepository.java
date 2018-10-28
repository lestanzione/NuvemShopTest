package br.com.stanzione.nuvemshoptest.doglist;

import java.util.List;

import br.com.stanzione.nuvemshoptest.api.DogApi;
import br.com.stanzione.nuvemshoptest.data.Dog;
import io.reactivex.Observable;

public class DogListRepository implements DogListContract.Repository {

    private DogApi dogApi;

    public DogListRepository(DogApi dogApi){
        this.dogApi = dogApi;
    }

    @Override
    public Observable<List<Dog>> fetchDogList() {
        return dogApi.fetchDogs(DogApi.LIMIT);
    }
}
