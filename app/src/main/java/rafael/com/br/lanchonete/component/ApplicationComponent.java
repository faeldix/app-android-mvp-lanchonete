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

/**
 * Created by rafaelfreitas on 8/16/17.
 */

@Singleton
@Component(modules = { ApplicationModule.class, HTTPModule.class })
public interface ApplicationComponent {

    public Context provideContext();
    public Gson provideGson();

    public API provideAPI();
    public OkHttpClient provideHttpClient();
    public Picasso providePicasso();

    public void inject(App application);

}