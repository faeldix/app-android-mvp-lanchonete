package rafael.com.br.lanchonete.presenter;

import rafael.com.br.lanchonete.view.MainView;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView view;

    public MainPresenterImpl() {}

    public MainPresenterImpl(MainView view) {
        this.view = view;
    }

    @Override
    public void showListOfLunchs() {
        view.showListOfLunchsFragment();
    }

    @Override
    public void showListOfOrders() {
        view.showListOfOrdersFragment();
    }

    @Override
    public void showListOfPromos() {
        view.showListOfPromosFragment();
    }

    @Override
    public MainView getView() {
        return view;
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }
}
