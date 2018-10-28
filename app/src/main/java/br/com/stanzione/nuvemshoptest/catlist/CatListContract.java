package br.com.stanzione.nuvemshoptest.catlist;

import java.util.List;

import br.com.stanzione.nuvemshoptest.BasePresenter;
import br.com.stanzione.nuvemshoptest.BaseView;
import br.com.stanzione.nuvemshoptest.data.Cat;
import io.reactivex.Observable;

public interface CatListContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{
        void getCatList();
    }

    interface Repository {
        Observable<List<Cat>> fetchCatList();
    }

}
