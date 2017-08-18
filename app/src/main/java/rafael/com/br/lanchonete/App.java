package rafael.com.br.lanchonete;

import android.app.Application;

import java.io.IOException;

import rafael.com.br.lanchonete.component.ApplicationComponent;
import rafael.com.br.lanchonete.component.DaggerApplicationComponent;
import rafael.com.br.lanchonete.module.ApplicationModule;
import rafael.com.br.lanchonete.module.HTTPModule;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

public class App extends Application {

    private ApplicationComponent app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return app;
    }

}
