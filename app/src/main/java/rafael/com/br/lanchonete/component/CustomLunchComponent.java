package rafael.com.br.lanchonete.component;

import dagger.Component;
import rafael.com.br.lanchonete.module.CustomLunchModule;
import rafael.com.br.lanchonete.module.MainModule;
import rafael.com.br.lanchonete.presenter.CustomLunchPresenter;
import rafael.com.br.lanchonete.view.CustomLunchActivity;

/**
 * Created by rafael-iteris on 23/08/17.
 */
@ActivityScope
@Component(modules = CustomLunchModule.class, dependencies = ApplicationComponent.class)
public interface CustomLunchComponent {

    CustomLunchPresenter provideCustomLunchPresenter();

    public void inject(CustomLunchActivity activity);

}
