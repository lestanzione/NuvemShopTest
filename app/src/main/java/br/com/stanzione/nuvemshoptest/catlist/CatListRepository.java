package br.com.stanzione.nuvemshoptest.catlist;

import java.util.List;

import br.com.stanzione.nuvemshoptest.api.CatApi;
import br.com.stanzione.nuvemshoptest.data.Cat;
import io.reactivex.Observable;

public class CatListRepository implements CatListContract.Repository {

    private CatApi catApi;

    public CatListRepository(CatApi catApi){
        this.catApi = catApi;
    }

    @Override
    public Observable<List<Cat>> fetchCatList() {
        return null;
    }
}
