package rafael.com.br.lanchonete.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    public void getListOfLunchs(final BaseRequestCallback<List<Lunch>, RuntimeException> callback) {
        callback.onStart();

        zipListOfLunchs().onErrorResumeNext(errorListOfLunchs(callback)).subscribe(successListOfLunchs(callback));
    }

    private Observable<LunchListServiceZippedResponse> zipListOfLunchs(){
        return Observable.zip(getRequestOfListOfLunchs(), getRequestOfListOfIngredients(), new BiFunction<List<InfoLunchResponseVO>, List<IngredientResponseVO>, LunchListServiceZippedResponse>() {

            @Override
            public LunchListServiceZippedResponse apply(@NonNull List<InfoLunchResponseVO> infoLunchResponseVOs, @NonNull List<IngredientResponseVO> ingredientResponseVOs) throws Exception {
                return new LunchListServiceZippedResponse(infoLunchResponseVOs, ingredientResponseVOs);
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

    private Consumer<LunchListServiceZippedResponse> successListOfLunchs(final BaseRequestCallback callback){
        return new Consumer<LunchListServiceZippedResponse>() {

            @Override
            public void accept(LunchListServiceZippedResponse response) throws Exception {

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

    private Function<Throwable, ObservableSource<? extends LunchListServiceZippedResponse>> errorListOfLunchs(final BaseRequestCallback<List<Lunch>, RuntimeException> callback){
        return new Function<Throwable, ObservableSource<? extends LunchListServiceZippedResponse>>() {

            @Override
            public ObservableSource<? extends LunchListServiceZippedResponse> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Não foi possivel buscar a informação solicitada.", throwable));
                callback.onEnd();

                return empty();
            }

        };
    }

    private class LunchListServiceZippedResponse {

        private List<InfoLunchResponseVO> lunch;
        private List<IngredientResponseVO> ingredients;

        public LunchListServiceZippedResponse(List<InfoLunchResponseVO> lunch, List<IngredientResponseVO> ingredients) {
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

    /* get info of lunch */

    @Override
    public void getInfoOfLunch(Integer id, final BaseRequestCallback<Lunch, RuntimeException> callback) {
        zipInfoLunch(id).onErrorResumeNext(getCallbackofErrorInfoLunch(callback)).subscribe(getCallbackOfSuccessInfoLunch(callback));
    }

    public Observable<InfoOfLunchZippedResponse> zipInfoLunch(Integer id){
        return zip(getInfoOfLunchRequest(id), getRequestOfListOfIngredients(), new BiFunction<InfoLunchResponseVO, List<IngredientResponseVO>, InfoOfLunchZippedResponse>() {

            @Override
            public InfoOfLunchZippedResponse apply(@NonNull InfoLunchResponseVO infoLunchResponseVO, @NonNull List<IngredientResponseVO> ingredientResponseVOs) throws Exception {
                return new InfoOfLunchZippedResponse(infoLunchResponseVO, ingredientResponseVOs);
            }

        });
    }

    public Observable<InfoLunchResponseVO> getInfoOfLunchRequest(Integer lunchId){
        return api.getInfoOfLunch(lunchId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Consumer<InfoOfLunchZippedResponse> getCallbackOfSuccessInfoLunch(final BaseRequestCallback<Lunch, RuntimeException> callback){
        return new Consumer<InfoOfLunchZippedResponse>() {

            @Override
            public void accept(InfoOfLunchZippedResponse response) throws Exception {

                final HashMap<Integer, Ingredient> hash = fromIterable(response.getIngredients()).collectInto(new HashMap<Integer, Ingredient>(), new BiConsumer<HashMap<Integer, Ingredient>, IngredientResponseVO>() {

                    @Override
                    public void accept(HashMap<Integer, Ingredient> map, IngredientResponseVO vo) throws Exception {
                        map.put(vo.id, new Ingredient(vo.id, vo.name, new BigDecimal(vo.price.toString()), vo.image));
                    }

                }).blockingGet();

                InfoLunchResponseVO vo = response.getLunch();

                Lunch lunch = new Lunch(vo.id, vo.name, vo.image, Collections.<Ingredient>emptyList());

                for(Integer id : vo.ingredients){
                    Ingredient ingredient = hash.get(id);
                    lunch.addIngredient(ingredient);
                }

                callback.onSuccess(lunch);
                callback.onEnd();
            }

        };
    }

    public Function<Throwable, ObservableSource<? extends InfoOfLunchZippedResponse>> getCallbackofErrorInfoLunch(final BaseRequestCallback<Lunch, RuntimeException> callback){
        return new Function<Throwable, ObservableSource<? extends InfoOfLunchZippedResponse>>() {

            @Override
            public ObservableSource<? extends InfoOfLunchZippedResponse> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Não foi possivel buscar a informação solicitada.", throwable));
                callback.onEnd();

                return empty();
            }

        };
    }

    private static class InfoOfLunchZippedResponse {

        private InfoLunchResponseVO lunch;
        private List<IngredientResponseVO> ingredients;

        public InfoOfLunchZippedResponse(InfoLunchResponseVO lunch, List<IngredientResponseVO> ingredients) {
            this.lunch = lunch;
            this.ingredients = ingredients;
        }

        public InfoLunchResponseVO getLunch() {
            return lunch;
        }

        public List<IngredientResponseVO> getIngredients() {
            return ingredients;
        }

    }

    public void setApi(API api) {
        this.api = api;
    }

    public API getApi() {
        return api;
    }

}
