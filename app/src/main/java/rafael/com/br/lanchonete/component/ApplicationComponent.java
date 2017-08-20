package rafael.com.br.lanchonete.component;

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import rafael.com.br.lanchonete.App;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.module.ApplicationModule;
import rafael.com.br.lanchonete.module.HTTPModule;
import rafael.com.br.lanchonete.module.ServiceModule;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.service.OrderService;
import rafael.com.br.lanchonete.service.PromoService;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

@Singleton
@Component(modules = { ApplicationModule.class, HTTPModule.class, ServiceModule.class })
public interface ApplicationComponent {

    Context provideContext();
    Gson provideGson();
    Picasso providePicasso();

    API provideAPI();

    LunchService provideServiceLunch();
    OrderService provideOrderService();
    PromoService providePromoService();

    void inject(App application);

}