package rafael.com.br.lanchonete.component;

import dagger.Component;
import rafael.com.br.lanchonete.module.MainModule;
import rafael.com.br.lanchonete.presenter.BasePresenter;
import rafael.com.br.lanchonete.presenter.MainPresenter;
import rafael.com.br.lanchonete.view.MainActivity;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.OrderListView;
import rafael.com.br.lanchonete.view.PromoListView;

/**
 * Created by rafael-iteris on 18/08/17.
 */

@ActivityScope
@Component(modules = MainModule.class, dependencies = ApplicationComponent.class)
public interface MainViewComponent {

    MainPresenter provideMainPresenter();

    LunchListView provideLunchView();
    OrderListView provideOrderView();
    PromoListView providePromoView();

    public void inject(MainActivity activity);

}
