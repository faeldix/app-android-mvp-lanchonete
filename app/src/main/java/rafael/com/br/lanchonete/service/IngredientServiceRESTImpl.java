package rafael.com.br.lanchonete.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;

import static io.reactivex.Observable.empty;

/**
 * Created by rafael-iteris on 21/08/17.
 */

public class IngredientServiceRESTImpl implements IngredientService {

    private API api;

    public IngredientServiceRESTImpl(API api) {
        this.api = api;
    }

    @Override
    public void getListOfIngredients(BaseRequestCallback<List<Ingredient>, RuntimeException> callback) {
        request()
                .onErrorResumeNext(getCallbackOfError(callback))
                .subscribe(getCallbackOfSuccess(callback));
    }

    private Observable<List<IngredientResponseVO>> request(){
        return api.getListOfIngredients()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Consumer<List<IngredientResponseVO>> getCallbackOfSuccess(final BaseRequestCallback<List<Ingredient>, RuntimeException> callback){
        return new Consumer<List<IngredientResponseVO>>() {
            @Override
            public void accept(List<IngredientResponseVO> ingredients) throws Exception {
                List<Ingredient> result = new ArrayList<>();

                for(IngredientResponseVO vo : ingredients){
                    Ingredient item = new Ingredient(vo.id, vo.name, new BigDecimal(vo.price.toString()), vo.image);
                    result.add(item);
                }

                callback.onSuccess(result);
                callback.onEnd();
            }
        };
    }

    private Function<Throwable, ObservableSource<? extends List<IngredientResponseVO>>> getCallbackOfError(final BaseRequestCallback<List<Ingredient>, RuntimeException> callback){
        return new Function<Throwable, ObservableSource<? extends List<IngredientResponseVO>>>() {
            @Override
            public ObservableSource<? extends List<IngredientResponseVO>> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Não foi possivel buscar a informação solicitada.", throwable));
                callback.onEnd();

                return empty();
            }
        };
    }

}
