package rafael.com.br.lanchonete.presenter;

import java.util.ArrayList;
import java.util.List;

import rafael.com.br.lanchonete.adapter.LunchListAdapter;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.model.Lunch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchListPresenterImpl implements LunchListPresenter {

    private API api;
    private LunchListAdapter adapter;

    public LunchListPresenterImpl(API api) {
        this.api = api;
    }

    @Override
    public void getListOfLunchs(final OnRequestListOfLunchsFinished callback) {
        api.getLunchs().enqueue(new Callback<List<InfoLunchResponseVO>>() {

            @Override
            public void onResponse(Call<List<InfoLunchResponseVO>> call, Response<List<InfoLunchResponseVO>> response) {

                if(!response.isSuccessful()){
                    //TODO criar um mecanismo mais eficiente para retorno de erros

                    callback.onError(new RuntimeException("Não foi possivel buscar a informação desejada"));
                    return;
                }

                List<InfoLunchResponseVO> list = response.body();
                List<Lunch> lunchs = new ArrayList<Lunch>();

                for(InfoLunchResponseVO info : list){
                    Lunch lunch = new Lunch(info.id, info.name, info.image, info.ingredients);
                    lunchs.add(lunch);
                }

                callback.onSuccess(lunchs);
            }

            @Override
            public void onFailure(Call<List<InfoLunchResponseVO>> call, Throwable t) {
                callback.onError(new RuntimeException(t));
            }

        });
    }

}
