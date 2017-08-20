package rafael.com.br.lanchonete.module;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import rafael.com.br.lanchonete.component.ActivityScope;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;
import rafael.com.br.lanchonete.presenter.LunchListPresenterImpl;
import rafael.com.br.lanchonete.presenter.MainPresenterImpl;
import rafael.com.br.lanchonete.presenter.OrderListPresenter;
import rafael.com.br.lanchonete.presenter.OrderListPresenterImpl;
import rafael.com.br.lanchonete.presenter.PromoListPresenter;
import rafael.com.br.lanchonete.presenter.PromoListPresenterImpl;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.service.OrderService;
import rafael.com.br.lanchonete.service.PromoService;
import rafael.com.br.lanchonete.view.FragmentLunchListView;
import rafael.com.br.lanchonete.view.FragmentOrderListView;
import rafael.com.br.lanchonete.view.FragmentPromoListView;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.MainPresenter;
import rafael.com.br.lanchonete.view.MainView;
import rafael.com.br.lanchonete.view.OrderListView;
import rafael.com.br.lanchonete.view.PromoListView;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

@Module
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    /* main */

    @ActivityScope @Provides
    public MainPresenter provideMainPresenter(){
        return new MainPresenterImpl(view);
    }

    /* lunch */

    @Provides @ActivityScope
    public LunchListView provideLunchListView(LunchListPresenter presenter, Picasso picasso){
        FragmentLunchListView view = new FragmentLunchListView();
        view.setPresenter(presenter);
        view.setPicasso(picasso);

        presenter.setView(view);
        return view;
    }

    @Provides @ActivityScope
    public LunchListPresenter provideLunchListPresenter(LunchService service){
        return new LunchListPresenterImpl(service);
    }

    /* promo */

    @Provides @ActivityScope
    public PromoListView providePromoListView(PromoListPresenter presenter){
        FragmentPromoListView view = new FragmentPromoListView();
        view.setPresenter(presenter);

        presenter.setView(view);
        return view;
    }

    @Provides @ActivityScope
    public PromoListPresenter providePromoListPresenter(PromoService service){
        return new PromoListPresenterImpl(service);
    }

    /* orders */

    @Provides @ActivityScope
    public OrderListView provideOrderListView(OrderListPresenter presenter){
        FragmentOrderListView view = new FragmentOrderListView();
        view.setPresenter(presenter);

        presenter.setView(view);

        return view;
    }

    @Provides @ActivityScope
    public OrderListPresenter provideOrderListPresenter(OrderService service){
        return new OrderListPresenterImpl(service);
    }

}
