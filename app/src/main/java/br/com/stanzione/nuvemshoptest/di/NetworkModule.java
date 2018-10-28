package br.com.stanzione.nuvemshoptest.di;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.stanzione.nuvemshoptest.api.CatApi;
import br.com.stanzione.nuvemshoptest.api.DogApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    @Singleton
    @Named("catApi")
    public String providesCatBaseUrl() {
        return "https://api.thecatapi.com/v1/";
    }

    @Provides
    @Singleton
    @Named("dogApi")
    public String providesDogBaseUrl() {
        return "https://api.thedogapi.com/v1/";
    }

    @Provides
    @Singleton
    @Named("catRetrofit")
    public Retrofit providesCatRetrofit(OkHttpClient client, @Named("catApi") String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("dogRetrofit")
    public Retrofit providesDogRetrofit(OkHttpClient client, @Named("dogApi") String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public CatApi providesCatApi(@Named("catRetrofit") Retrofit retrofit) {
        return retrofit.create(CatApi.class);
    }

    @Provides
    @Singleton
    public DogApi providesDogApi(@Named("dogRetrofit")Retrofit retrofit) {
        return retrofit.create(DogApi.class);
    }

}
