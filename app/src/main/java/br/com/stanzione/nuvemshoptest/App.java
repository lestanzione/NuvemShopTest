package br.com.stanzione.nuvemshoptest;

import android.app.Application;

import br.com.stanzione.nuvemshoptest.catlist.CatListModule;
import br.com.stanzione.nuvemshoptest.di.ApplicationComponent;
import br.com.stanzione.nuvemshoptest.di.DaggerApplicationComponent;
import br.com.stanzione.nuvemshoptest.di.NetworkModule;
import br.com.stanzione.nuvemshoptest.doglist.DogListModule;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .catListModule(new CatListModule())
                .dogListModule(new DogListModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
