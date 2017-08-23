package rafael.com.br.lanchonete.module;

import dagger.Module;
import dagger.Provides;
import rafael.com.br.lanchonete.component.ActivityScope;
import rafael.com.br.lanchonete.presenter.CustomLunchPresenter;
import rafael.com.br.lanchonete.presenter.CustomLunchPresenterImpl;
import rafael.com.br.lanchonete.service.IngredientService;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.view.CustomLunchView;
import rafael.com.br.lanchonete.view.MainView;

/**
 * Created by rafael-iteris on 23/08/17.
 */

@Module
public class CustomLunchModule {

    private final CustomLunchView view;

    public CustomLunchModule(CustomLunchView view) {
        this.view = view;
    }

    @Provides @ActivityScope
    public CustomLunchPresenter providePresenter(LunchService lunchService, IngredientService ingredientService){
        CustomLunchPresenterImpl presenter = new CustomLunchPresenterImpl(lunchService, ingredientService);
        presenter.setView(view);

        return presenter;
    }

}
