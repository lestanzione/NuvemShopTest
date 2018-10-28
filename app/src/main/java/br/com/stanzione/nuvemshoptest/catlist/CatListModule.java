package br.com.stanzione.nuvemshoptest.catlist;

import javax.inject.Singleton;

import br.com.stanzione.nuvemshoptest.api.CatApi;
import dagger.Module;
import dagger.Provides;

@Module
public class CatListModule {

    @Provides
    @Singleton
    CatListContract.Presenter providesPresenter(CatListContract.Repository repository){
        CatListPresenter presenter = new CatListPresenter(repository);
        return presenter;
    }

    @Provides
    @Singleton
    CatListContract.Repository providesRepository(CatApi catApi){
        CatListRepository repository = new CatListRepository(catApi);
        return repository;
    }

}
