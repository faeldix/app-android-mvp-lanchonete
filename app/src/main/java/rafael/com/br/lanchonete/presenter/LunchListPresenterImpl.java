package rafael.com.br.lanchonete.presenter;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import rafael.com.br.lanchonete.adapter.LunchListAdapter;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.service.LunchServiceRESTImpl;
import rafael.com.br.lanchonete.view.LunchListView;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchListPresenterImpl implements LunchListPresenter {

    private LunchService service;
    private LunchListView view;
    private Picasso picasso;

    @Inject
    public LunchListPresenterImpl(LunchService service, Picasso picasso) {
        this.service = service;
        this.picasso = picasso;
    }

    @Override
    public LunchListView getView() {
        return view;
    }

    @Override
    public void setView(LunchListView view) {
        this.view = view;
    }

    @Override
    public void getListOfLunch() {
        service.getListOfLunchs(new LunchService.OnRequestListOfLunchsFinished() {

            @Override
            public void onSuccess(List<Lunch> lunchs) {
                LunchListAdapter adapter = new LunchListAdapter(LunchListPresenterImpl.this, picasso, lunchs);
                view.showListOfLunch(adapter);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();

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

        });
    }

    @Override
    public void onSelectAnLunchOfList(Lunch item) {
        view.showOptionsOfLunch(item);
    }

}
