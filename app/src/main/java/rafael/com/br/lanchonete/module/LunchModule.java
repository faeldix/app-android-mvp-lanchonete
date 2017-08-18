package rafael.com.br.lanchonete.module;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.component.ActivityScope;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;
import rafael.com.br.lanchonete.presenter.LunchListPresenterImpl;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.service.LunchServiceRESTImpl;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.LunchListViewImpl;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

@Module
public class LunchModule {

    @Provides @ActivityScope
    public LunchService provideService(API api){
        return new LunchServiceRESTImpl(api);
    }

    @Provides @ActivityScope
    public LunchListView provideLunchListView(LunchListPresenter presenter, Picasso picasso){
        LunchListViewImpl view = new LunchListViewImpl();
        view.setPresenter(presenter);
        view.setPicasso(picasso);

        return view;
    }

    @Provides @ActivityScope
    public LunchListPresenter provideLunchListPresenter(LunchService service){
        return new LunchListPresenterImpl(service);
    }

}
