package br.com.stanzione.nuvemshoptest.doglist;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.stanzione.nuvemshoptest.data.Breed;
import br.com.stanzione.nuvemshoptest.data.Dog;
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

public class DogListPresenterTest {

    private DogListContract.View mockView;
    private DogListContract.Repository mockRepository;
    private DogListPresenter presenter;

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

        mockView = mock(DogListContract.View.class);
        mockRepository = mock(DogListContract.Repository.class);

        presenter = new DogListPresenter(mockRepository);
        presenter.attachView(mockView);
    }

    @Test
    public void withNetworkShouldShowDogList(){

        when(mockRepository.fetchDogList()).thenReturn(Observable.just(new ArrayList<>()));

        presenter.getDogList();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showDogList(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showDogBreed(anyString());
        verify(mockRepository, times(2)).fetchDogList();

    }

    @Test
    public void withNoNetworkShouldShowNetworkError(){

        when(mockRepository.fetchDogList()).thenReturn(Observable.error(new IOException()));

        presenter.getDogList();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, never()).showDogList(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, times(1)).showNetworkError();
        verify(mockView, never()).showDogBreed(anyString());
        verify(mockRepository, times(2)).fetchDogList();

    }

    @Test
    public void withGeneralErrorShouldShowGeneralError(){

        when(mockRepository.fetchDogList()).thenReturn(Observable.error(new Throwable()));

        presenter.getDogList();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, never()).showDogList(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showDogBreed(anyString());
        verify(mockRepository, times(2)).fetchDogList();

    }

    @Test
    public void withDogBreedShouldShowMessage(){

        Dog dog = new Dog();
        Breed breed = new Breed();
        List<Breed> breedList = new ArrayList<>();

        breed.setName("Breed");
        breedList.add(breed);
        dog.setBreedList(breedList);

        presenter.getDogBreed(dog);

        verify(mockView, never()).setProgressBarVisible(true);
        verify(mockView, never()).showDogList(any(List.class));
        verify(mockView, never()).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, times(1)).showDogBreed(breed.getName());
        verify(mockRepository, never()).fetchDogList();

    }

    @Test
    public void withEmptyBreedListShouldDoNothing(){

        Dog dog = new Dog();
        List<Breed> breedList = new ArrayList<>();

        dog.setBreedList(breedList);

        presenter.getDogBreed(dog);

        verify(mockView, never()).setProgressBarVisible(true);
        verify(mockView, never()).showDogList(any(List.class));
        verify(mockView, never()).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showDogBreed(anyString());
        verify(mockRepository, never()).fetchDogList();

    }

    @Test
    public void withNoBreedListShouldDoNothing(){

        Dog dog = new Dog();

        presenter.getDogBreed(dog);

        verify(mockView, never()).setProgressBarVisible(true);
        verify(mockView, never()).showDogList(any(List.class));
        verify(mockView, never()).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockView, never()).showDogBreed(anyString());
        verify(mockRepository, never()).fetchDogList();

    }

}