package rafael.com.br.lanchonete.presenter;

import rafael.com.br.lanchonete.view.BaseView;
import rafael.com.br.lanchonete.view.LunchListView;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public interface BasePresenter<T extends BaseView> {

    public T getView();
    public void setView(T view);

}
