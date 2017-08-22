package rafael.com.br.lanchonete.presenter;

import java.util.List;

import rafael.com.br.lanchonete.model.Promo;
import rafael.com.br.lanchonete.service.BaseRequestCallback;
import rafael.com.br.lanchonete.service.PromoService;
import rafael.com.br.lanchonete.view.PromoListView;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public class PromoListPresenterImpl implements PromoListPresenter {

    private PromoService service;
    private PromoListView view;

    public PromoListPresenterImpl() {}

    public PromoListPresenterImpl(PromoService service) {
        this.service = service;
    }

    @Override
    public void getListOfPromo() {
        if (view == null)
            throw new IllegalStateException("A view não foi setada para a classe: " + getClass().getName());

        service.getListOfPromos(getCallback());
    }

    public BaseRequestCallback<List<Promo>,RuntimeException> getCallback() {
        return new BaseRequestCallback<List<Promo>, RuntimeException>() {

            @Override
            public void onSuccess(List<Promo> result) {
                view.showListOfPromos(result);
            }

            @Override
            public void onErro(RuntimeException err) {
                view.onShowErrorMessage("Não foi possivel buscar as promoções");
            }

            @Override
            public void onStart() {
                view.onShowLoading();
            }

            @Override
            public void onEnd() {
                view.onDismissLoading();
            }

        };
    }

    @Override
    public PromoListView getView() {
        return getView();
    }

    @Override
    public void setView(PromoListView view) {
        this.view = view;
    }

}
