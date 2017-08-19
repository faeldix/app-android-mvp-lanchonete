package rafael.com.br.lanchonete.component;

import dagger.Component;
import rafael.com.br.lanchonete.view.MainActivity;
import rafael.com.br.lanchonete.module.LunchModule;
import rafael.com.br.lanchonete.view.LunchListView;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

@ActivityScope
@Component(modules = LunchModule.class, dependencies = { ApplicationComponent.class } )
public interface LunchComponent {

    LunchListView provideLunchListView();

    void inject(MainActivity mainActivity);

}
