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

    private Observable<LunchListServiceResponse> zipListOfLunchs(){
        return Observable.zip(getRequestOfListOfLunchs(), getRequestOfListOfIngredients(), new BiFunction<List<InfoLunchResponseVO>, List<IngredientResponseVO>, LunchListServiceResponse>() {

            @Override
            public LunchListServiceResponse apply(@NonNull List<InfoLunchResponseVO> infoLunchResponseVOs, @NonNull List<IngredientResponseVO> ingredientResponseVOs) throws Exception {
                return new LunchListServiceResponse(infoLunchResponseVOs, ingredientResponseVOs);
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

    private Consumer<LunchListServiceResponse> successListOfLunchs(final BaseRequestCallback callback){
        return new Consumer<LunchListServiceResponse>() {

            @Override
            public void accept(LunchListServiceResponse response) throws Exception {

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

    private Function<Throwable, ObservableSource<? extends LunchListServiceResponse>> errorListOfLunchs(final BaseRequestCallback<List<Lunch>, RuntimeException> callback){
        return new Function<Throwable, ObservableSource<? extends LunchListServiceResponse>>() {

            @Override
            public ObservableSource<? extends LunchListServiceResponse> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Não foi possivel buscar a informação solicitada.", throwable));
                callback.onEnd();

                return empty();
            }

        };
    }

    public class LunchListServiceResponse {

        private List<InfoLunchResponseVO> lunch;
        private List<IngredientResponseVO> ingredients;

        public LunchListServiceResponse(List<InfoLunchResponseVO> lunch, List<IngredientResponseVO> ingredients) {
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
        zipInfoLunch(id).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends InfoOfLunchResponse>>() {

            @Override
            public ObservableSource<? extends InfoOfLunchResponse> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Não foi possivel buscar a informação solicitada.", throwable));
                callback.onEnd();

                return empty();
            }

        }).subscribe(new Consumer<InfoOfLunchResponse>() {

            @Override
            public void accept(InfoOfLunchResponse response) throws Exception {

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

        });
    }

    public Observable<InfoOfLunchResponse> zipInfoLunch(Integer id){
        return zip(getInfoOfLunchRequest(id), getRequestOfListOfIngredients(), new BiFunction<InfoLunchResponseVO, List<IngredientResponseVO>, InfoOfLunchResponse>() {

            @Override
            public InfoOfLunchResponse apply(@NonNull InfoLunchResponseVO infoLunchResponseVO, @NonNull List<IngredientResponseVO> ingredientResponseVOs) throws Exception {
                return new InfoOfLunchResponse(infoLunchResponseVO, ingredientResponseVOs);
            }

        });
    }

    public Observable<InfoLunchResponseVO> getInfoOfLunchRequest(Integer lunchId){
        return api.getInfoOfLunch(lunchId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void setApi(API api) {
        this.api = api;
    }

    public API getApi() {
        return api;
    }

    public static class InfoOfLunchResponse {

        private InfoLunchResponseVO lunch;
        private List<IngredientResponseVO> ingredients;

        public InfoOfLunchResponse(InfoLunchResponseVO lunch, List<IngredientResponseVO> ingredients) {
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

}
