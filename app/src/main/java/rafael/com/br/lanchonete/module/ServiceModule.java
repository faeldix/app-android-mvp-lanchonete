package rafael.com.br.lanchonete.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.service.LunchServiceRESTImpl;
import rafael.com.br.lanchonete.service.OrderService;
import rafael.com.br.lanchonete.service.OrderServiceRESTImpl;
import rafael.com.br.lanchonete.service.PromoService;
import rafael.com.br.lanchonete.service.PromoServiceRESTImpl;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

@Module
public class ServiceModule {

    @Singleton @Provides
    public LunchService provideServiceLunch(API api){
        return new LunchServiceRESTImpl(api);
    }

    @Singleton @Provides
    public OrderService provideOrderService(API api){
        return new OrderServiceRESTImpl(api);
    }

    @Singleton @Provides
    public PromoService providePromoService(API api){
        return new PromoServiceRESTImpl(api);
    }

}
