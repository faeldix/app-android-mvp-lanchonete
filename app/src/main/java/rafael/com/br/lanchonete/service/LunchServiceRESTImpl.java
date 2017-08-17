package rafael.com.br.lanchonete.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

public class LunchServiceRESTImpl implements LunchService {

    private API api;

    public LunchServiceRESTImpl(){} /* for testing purposes */

    public LunchServiceRESTImpl(API api) {
        this.api = api;
    }

    @Override
    public void getListOfLunchs(final OnRequestListOfLunchsFinished callback) {

        zip().onErrorResumeNext(new Function<Throwable, ObservableSource<? extends LunchServiceResponse>>() {

            @Override
            public ObservableSource<? extends LunchServiceResponse> apply(@NonNull Throwable throwable) throws Exception {
                callback.onError(new RuntimeException(throwable));
                callback.onEnd();

                return Observable.empty();
            }

        }).subscribe(new Consumer<LunchServiceResponse>() {

            @Override
            public void accept(LunchServiceResponse response) throws Exception {
                List<Lunch> result = new ArrayList<Lunch>();

                List<IngredientResponseVO> ingredients = response.getIngredients();
                Map<Integer, Ingredient> hash = new HashMap<Integer, Ingredient>();

                for (IngredientResponseVO vo : ingredients)
                    hash.put(vo.id, new Ingredient(vo.id, vo.name, new BigDecimal(vo.price.toString()), vo.image));

                for(InfoLunchResponseVO vo: response.getLunch()){
                    Lunch lunch = new Lunch();
                    lunch.setId(vo.id);
                    lunch.setImage(vo.image);
                    lunch.setName(vo.name);

                    for(Integer id : vo.ingredients){
                        Ingredient ingredient = hash.get(id);
                        lunch.addIngredient(ingredient);
                    }

                    result.add(lunch);
                }

                callback.onSuccess(result);
                callback.onEnd();
            }

        });

        callback.onStart();
    }

    public void setApi(API api) {
        this.api = api;
    }

    public API getApi() {
        return api;
    }

    private Observable<LunchServiceResponse> zip(){
        return Observable.zip(getRequestOfListOfLunchs(), getRequestOfListOfIngredients(), new BiFunction<List<InfoLunchResponseVO>, List<IngredientResponseVO>, LunchServiceResponse>() {

            @Override
            public LunchServiceResponse apply(@NonNull List<InfoLunchResponseVO> infoLunchResponseVOs, @NonNull List<IngredientResponseVO> ingredientResponseVOs) throws Exception {
                return new LunchServiceResponse(infoLunchResponseVOs, ingredientResponseVOs);
            }

        });
    }

    private Observable<List<InfoLunchResponseVO>> getRequestOfListOfLunchs(){
        return api.getLunchs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<IngredientResponseVO>> getRequestOfListOfIngredients(){
        return api.getListOfIngredients()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public class LunchServiceResponse {

        private List<InfoLunchResponseVO> lunch;
        private List<IngredientResponseVO> ingredients;

        public LunchServiceResponse(List<InfoLunchResponseVO> lunch, List<IngredientResponseVO> ingredients) {
            this.lunch = lunch;
            this.ingredients = ingredients;
        }

        public List<InfoLunchResponseVO> getLunch() {
            return lunch;
        }

        public List<IngredientResponseVO> getIngredients() {
            return ingredients;
        }

    }

}
