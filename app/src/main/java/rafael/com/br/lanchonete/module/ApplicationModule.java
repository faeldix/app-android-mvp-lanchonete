package rafael.com.br.lanchonete.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rafael.com.br.lanchonete.api.API;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    }

}