package br.com.stanzione.nuvemshoptest.di;

import javax.inject.Singleton;

import br.com.stanzione.nuvemshoptest.catlist.CatListFragment;
import br.com.stanzione.nuvemshoptest.catlist.CatListModule;
import br.com.stanzione.nuvemshoptest.doglist.DogListFragment;
import br.com.stanzione.nuvemshoptest.doglist.DogListModule;
import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                CatListModule.class,
                DogListModule.class
        }
)
public interface ApplicationComponent {
    void inject(CatListFragment fragment);
    void inject(DogListFragment fragment);
}
