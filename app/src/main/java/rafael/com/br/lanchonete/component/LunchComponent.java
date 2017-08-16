package rafael.com.br.lanchonete.component;

import android.support.v4.app.Fragment;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import rafael.com.br.lanchonete.MainActivity;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.module.LunchModule;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.LunchListViewImpl;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

@ActivityScope
@Component(modules = LunchModule.class, dependencies = { ApplicationComponent.class } )
public interface LunchComponent {

    public LunchListView provideLunchListView();

    void inject(MainActivity mainActivity);

}
