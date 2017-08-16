package rafael.com.br.lanchonete.service;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

public class LunchServiceRESTImpl implements LunchService {

    private API api;

    public LunchServiceRESTImpl(API api) {
        this.api = api;
    }

    /* TODO paralelizar requisicao */

    @Override
    public void getListOfLunchs(final OnRequestListOfLunchsFinished callback){
        callback.onStart();

        api.getListOfIngredients().enqueue(new Callback<List<IngredientResponseVO>>() {

            @Override
            public void onResponse(Call<List<IngredientResponseVO>> call, Response<List<IngredientResponseVO>> response) {

                if(!response.isSuccessful()){
                    callback.onError(new RuntimeException("Não foi possivel concluir a solicitacao."));
                    callback.onEnd();

                    return;
                }

                Map<Integer, IngredientResponseVO> hash = new HashMap<Integer, IngredientResponseVO>();

                for(IngredientResponseVO vo : response.body()){
                    hash.put(vo.id, vo);
                }

                getList(hash, callback);
            }

            @Override
            public void onFailure(Call<List<IngredientResponseVO>> call, Throwable t) {
                callback.onError(new RuntimeException(t));
                callback.onEnd();
            }

        });

    }

    private void getList(final Map<Integer, IngredientResponseVO> hash, final OnRequestListOfLunchsFinished callback){

        api.getLunchs().enqueue(new Callback<List<InfoLunchResponseVO>>() {

            @Override
            public void onResponse(Call<List<InfoLunchResponseVO>> call, Response<List<InfoLunchResponseVO>> response) {

                if(!response.isSuccessful()){
                    callback.onError(new RuntimeException("Não foi possivel concluir a solicitacao."));
                    callback.onEnd();

                    return;
                }

                List<Lunch> models = new ArrayList<Lunch>();

                for(InfoLunchResponseVO vo : response.body()){
                    Lunch lunch = new Lunch();

                    lunch.setId(vo.id);
                    lunch.setImage(vo.image);
                    lunch.setName(vo.name);

                    for(Integer id : vo.ingredients){
                        IngredientResponseVO ingredient = hash.get(id);
                        lunch.addIngredient(new Ingredient(ingredient.id, ingredient.name, new BigDecimal(ingredient.price.toString()), ingredient.image));
                    }

                    models.add(lunch);
                }

                callback.onSuccess(models);
                callback.onEnd();
            }

            @Override
            public void onFailure(Call<List<InfoLunchResponseVO>> call, Throwable t) {
                callback.onError(new RuntimeException(t));
                callback.onEnd();
            }

        });

    }

}
