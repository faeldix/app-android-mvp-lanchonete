package rafael.com.br.lanchonete.module;

import dagger.Component;
import rafael.com.br.lanchonete.view.MainActivity;
import rafael.com.br.lanchonete.component.ApplicationComponent;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.OrderListView;
import rafael.com.br.lanchonete.view.PromoListView;

/**
 * Created by rafael-iteris on 18/08/17.
 */


@Component(modules = {LunchModule.class, OrderModule.class, PromoModule.class }, dependencies = { ApplicationComponent.class })
public interface MainViewComponent {

    LunchListView provideLunchView();
    OrderListView provideOrderView();
    PromoListView providePromoView();

    public void inject(MainActivity activity);

}
