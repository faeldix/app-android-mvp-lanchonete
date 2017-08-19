package rafael.com.br.lanchonete.service;

import org.json.JSONArray;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.request.AddOrderRequestVO;
import rafael.com.br.lanchonete.api.response.OrderResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public class OrderServiceRESTImpl implements OrderService {

    private API api;

    public OrderServiceRESTImpl(API api) {
        this.api = api;
    }

    /* get list of orders structure */

    @Override
    public void getOrders(GetOrdersCallback callback) {
        getListOrdersRequest()
                .onErrorResumeNext(getListOrdersError(callback))
                .doOnSubscribe(getListOrdersSuccess(callback));

        callback.onStart();
    }

    private Observable<List<OrderResponseVO>> getListOrdersRequest(){
        return api.getOrders().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Function<Throwable, ObservableSource<? extends List<OrderResponseVO>>> getListOrdersError(GetOrdersCallback callback){
        return new Function<Throwable, ObservableSource<? extends List<OrderResponseVO>>>() {
            @Override
            public ObservableSource<? extends List<OrderResponseVO>> apply(@NonNull Throwable throwable) throws Exception {
                return null;
            }
        };
    }

    private Consumer<Disposable> getListOrdersSuccess(GetOrdersCallback callback){
        return new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

            }
        };
    }

    /* create order */

    @Override
    public void createOrder(Order order, CreateOrderCallback callback) {
        getCreateOrderRequest(order)
                .onErrorResumeNext(getCreateOrderError(callback))
                .subscribe(getCreateOrderSuccess(callback));
    }

    public Observable<OrderResponseVO> getCreateOrderRequest(Order order){
        AddOrderRequestVO vo = new AddOrderRequestVO();
        vo.itens = Observable.fromIterable(order.getExtras())
                .collectInto(new JSONArray(), new BiConsumer<JSONArray, Ingredient>() {

            @Override
            public void accept(JSONArray array, Ingredient ingredient) throws Exception {
                array.put(ingredient.getId());
            }

        }).blockingGet();

        return api.createOrder(order.getLunch().getId(), vo).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Consumer<OrderResponseVO> getCreateOrderSuccess(CreateOrderCallback callback){
        return new Consumer<OrderResponseVO>() {

            @Override
            public void accept(OrderResponseVO orderResponseVO) throws Exception {

            }

        };
    }

    public Function<Throwable, ObservableSource<? extends OrderResponseVO>> getCreateOrderError(CreateOrderCallback callback){
        return new Function<Throwable, ObservableSource<? extends OrderResponseVO>>() {

            @Override
            public ObservableSource<? extends OrderResponseVO> apply(@NonNull Throwable throwable) throws Exception {
                return null;
            }

        };
    }

    public CreateOrderCallback getCreateOrderCallback(GetOrdersCallback callback){
         return new CreateOrderCallback() {

             @Override
             public void onSuccess(OrderResponseVO created) {

             }

             @Override
             public void onError(Throwable err) {

             }

             @Override
             public void onStart() {

             }

             @Override
             public void onEnd() {

             }

         };
    }

}
