package rafael.com.br.lanchonete.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;

import static io.reactivex.Observable.*;

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
        callback.onStart();

        zip().onErrorResumeNext(error(callback)).subscribe(success(callback));
    }

    private Consumer<LunchServiceResponse> success(final OnRequestListOfLunchsFinished callback){
        return new Consumer<LunchServiceResponse>() {

            @Override
            public void accept(LunchServiceResponse response) throws Exception {

                final HashMap<Integer, Ingredient> hash = fromIterable(response.getIngredients()).collectInto(new HashMap<Integer, Ingredient>(), new BiConsumer<HashMap<Integer, Ingredient>, IngredientResponseVO>() {

                    @Override
                    public void accept(HashMap<Integer, Ingredient> map, IngredientResponseVO vo) throws Exception {
                        map.put(vo.id, new Ingredient(vo.id, vo.name, new BigDecimal(vo.price.toString()), vo.image));
                    }

                }).blockingGet();

                List<Lunch> result = fromIterable(response.getLunch()).map(new Function<InfoLunchResponseVO, Lunch>() {

                    @Override
                    public Lunch apply(@NonNull InfoLunchResponseVO vo) throws Exception {
                        final Lunch lunch = new Lunch(vo.id, vo.name, vo.image, Collections.<Ingredient>emptyList());

                        fromIterable(vo.ingredients).blockingForEach(new Consumer<Integer>() {

                            @Override
                            public void accept(Integer id) throws Exception {
                                Ingredient ingredient = hash.get(id);
                                lunch.addIngredient(ingredient);
                            }

                        });

                        return lunch;
                    }

                }).toList().blockingGet();

                callback.onSuccess(result);
                callback.onEnd();
            }

        };
    }

    private Function<Throwable, ObservableSource<? extends LunchServiceResponse>> error(final OnRequestListOfLunchsFinished callback){
        return new Function<Throwable, ObservableSource<? extends LunchServiceResponse>>() {

            @Override
            public ObservableSource<? extends LunchServiceResponse> apply(@NonNull Throwable throwable) throws Exception {
                callback.onError(new RuntimeException(throwable));
                callback.onEnd();

                return empty();
            }

        };
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

    public void setApi(API api) {
        this.api = api;
    }

    public API getApi() {
        return api;
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
