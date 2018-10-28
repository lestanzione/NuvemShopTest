package br.com.stanzione.nuvemshoptest.doglist;

import javax.inject.Singleton;

import br.com.stanzione.nuvemshoptest.api.DogApi;
import dagger.Module;
import dagger.Provides;

@Module
public class DogListModule {

    @Provides
    @Singleton
    DogListContract.Presenter providesPresenter(DogListContract.Repository repository){
        DogListPresenter presenter = new DogListPresenter(repository);
        return presenter;
    }

    @Provides
    @Singleton
    DogListContract.Repository providesRepository(DogApi dogApi){
        DogListRepository repository = new DogListRepository(dogApi);
        return repository;
    }
}
