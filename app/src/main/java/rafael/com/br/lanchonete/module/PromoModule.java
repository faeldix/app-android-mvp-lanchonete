package rafael.com.br.lanchonete.module;

import android.support.v4.app.Fragment;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.component.ActivityScope;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;
import rafael.com.br.lanchonete.presenter.PromoListPresenter;
import rafael.com.br.lanchonete.presenter.PromoListPresenterImpl;
import rafael.com.br.lanchonete.service.PromoService;
import rafael.com.br.lanchonete.service.PromoServiceRESTImpl;
import rafael.com.br.lanchonete.view.FragmentPromoListView;
import rafael.com.br.lanchonete.view.PromoListView;

/**
 * Created by rafael-iteris on 18/08/17.
 */

@Module
public class PromoModule {

    @Provides @ActivityScope
    public PromoService provideService(API api){
        return new PromoServiceRESTImpl(api);
    }

    @Provides @ActivityScope
    public PromoListView provideView(PromoListPresenter presenter, Picasso picasso){
        FragmentPromoListView view = new FragmentPromoListView();
        view.setPicasso(picasso);
        view.setPresenter(presenter);

        return view;
    }

    @Provides @ActivityScope
    public PromoListPresenter providePresenter(PromoService service){
        return new PromoListPresenterImpl(service);
    }

}