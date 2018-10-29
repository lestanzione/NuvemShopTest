package br.com.stanzione.nuvemshoptest.catlist;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.stanzione.nuvemshoptest.data.Breed;
import br.com.stanzione.nuvemshoptest.data.Cat;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatListPresenterTest {

    private CatListContract.View mockView;
    private CatListContract.Repository mockRepository;
    private CatListPresenter presenter;

    @BeforeClass
    public static void setupRxSchedulers() {

        Scheduler immediate = new Scheduler() {

            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Before
    public void setup() {

        mockView = mock(CatListContract.View.class);
        mockRepository = mock(CatListContract.Repository.class);

        presenter = new CatListPresenter(mockRepository);
        presenter.attachView(mockView);
    }

    @Test
    public void withNetworkShouldShowCatList(){

        when(mockRepository.fetchCatList()).thenReturn(Observable.just(new ArrayList<>()));

        presenter.getCatList();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showCatList(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showCatBreed(anyString());
        verify(mockRepository, times(1)).fetchCatList();

    }

    @Test
    public void withNoNetworkShouldShowNetworkError(){

        when(mockRepository.fetchCatList()).thenReturn(Observable.error(new IOException()));

        presenter.getCatList();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, never()).showCatList(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, times(1)).showNetworkError();
        verify(mockView, never()).showCatBreed(anyString());
        verify(mockRepository, times(1)).fetchCatList();

    }

    @Test
    public void withGeneralErrorShouldShowGeneralError(){

        when(mockRepository.fetchCatList()).thenReturn(Observable.error(new Throwable()));

        presenter.getCatList();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, never()).showCatList(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showCatBreed(anyString());
        verify(mockRepository, times(1)).fetchCatList();

    }

    @Test
    public void withCatBreedShouldShowMessage(){

        Cat cat = new Cat();
        Breed breed = new Breed();
        List<Breed> breedList = new ArrayList<>();

        breed.setName("Breed");
        breedList.add(breed);
        cat.setBreedList(breedList);

        presenter.getCatBreed(cat);

        verify(mockView, never()).setProgressBarVisible(true);
        verify(mockView, never()).showCatList(any(List.class));
        verify(mockView, never()).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, times(1)).showCatBreed(breed.getName());
        verify(mockRepository, never()).fetchCatList();

    }

    @Test
    public void withEmptyBreedListShouldDoNothing(){

        Cat cat = new Cat();
        List<Breed> breedList = new ArrayList<>();

        cat.setBreedList(breedList);

        presenter.getCatBreed(cat);

        verify(mockView, never()).setProgressBarVisible(true);
        verify(mockView, never()).showCatList(any(List.class));
        verify(mockView, never()).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showCatBreed(anyString());
        verify(mockRepository, never()).fetchCatList();

    }

    @Test
    public void withNoBreedListShouldDoNothing(){

        Cat cat = new Cat();

        presenter.getCatBreed(cat);

        verify(mockView, never()).setProgressBarVisible(true);
        verify(mockView, never()).showCatList(any(List.class));
        verify(mockView, never()).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showCatBreed(anyString());
        verify(mockRepository, never()).fetchCatList();

    }

}