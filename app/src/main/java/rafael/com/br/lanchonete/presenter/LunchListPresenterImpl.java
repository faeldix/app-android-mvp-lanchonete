package rafael.com.br.lanchonete.presenter;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import rafael.com.br.lanchonete.adapter.LunchListAdapter;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.service.LunchServiceRESTImpl;
import rafael.com.br.lanchonete.view.LunchListView;

import static rafael.com.br.lanchonete.service.LunchService.*;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchListPresenterImpl implements LunchListPresenter {

    private LunchService service;
    private LunchListView view;

    public LunchListPresenterImpl() {}

    @Inject
    public LunchListPresenterImpl(LunchService service) {
        this.service = service;
    }

    @Override
    public void getListOfLunch() {
        if(view == null)
            throw new IllegalStateException("A view não foi setada para a classe: " + getClass().getName());

        service.getListOfLunchs(getOnRequestListOfLunchsFinishedCallback());
    }

    public OnRequestListOfLunchsFinished getOnRequestListOfLunchsFinishedCallback(){
        return new OnRequestListOfLunchsFinished() {

            @Override
            public void onSuccess(List<Lunch> lunchs) {
                view.showListOfLunch(lunchs);
            }

            @Override
            public void onError(Exception e) {
                view.onShowErrorMessage("Desculpe. Não foi possivel carregar a lista de lanches.");
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
    public void onSelectAnLunchOfList(Lunch item) {
        view.showOptionsOfLunch(item);
    }

    @Override
    public LunchListView getView() {
        return view;
    }

    @Override
    public void setView(LunchListView view) {
        this.view = view;
    }

    public void setService(LunchService service) {
        this.service = service;
    }

    public LunchService getService() {
        return service;
    }

}
