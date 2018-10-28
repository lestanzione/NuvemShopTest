package br.com.stanzione.nuvemshoptest.di;

import javax.inject.Singleton;

import br.com.stanzione.nuvemshoptest.catlist.CatListFragment;
import br.com.stanzione.nuvemshoptest.catlist.CatListModule;
import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                CatListModule.class
        }
)
public interface ApplicationComponent {
    void inject(CatListFragment fragment);
}
