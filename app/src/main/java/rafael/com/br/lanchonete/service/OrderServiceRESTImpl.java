package rafael.com.br.lanchonete.service;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.request.AddOrderRequestVO;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.api.response.OrderResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;

import static io.reactivex.Observable.*;
import static io.reactivex.Observable.empty;
import static io.reactivex.Observable.fromIterable;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

// {id, idlunch, extrasIngredients, date}

public class OrderServiceRESTImpl implements OrderService {

    private API api;

    public OrderServiceRESTImpl() {}

    public OrderServiceRESTImpl(API api) {
        this.api = api;
    }

    /* get list of orders structure */

    @Override
    public void getOrders(BaseRequestCallback<List<Order>, RuntimeException> callback) {
        zip().onErrorResumeNext(getListOrdersError(callback)).subscribe(getListOrdersSuccess(callback));

        callback.onStart();
    }

    private Consumer<OrdersZippedResponse> getListOrdersSuccess(final BaseRequestCallback<List<Order>, RuntimeException> callback){
        return new Consumer<OrdersZippedResponse>() {
            @Override
            public void accept(OrdersZippedResponse response) throws Exception {

                /* mapeando ingredients */

                final HashMap<Integer, Ingredient> hashIngredients = fromIterable(response.getIngredientResponseVOs()).collectInto(new HashMap<Integer, Ingredient>(), new BiConsumer<HashMap<Integer, Ingredient>, IngredientResponseVO>() {

                    @Override
                    public void accept(HashMap<Integer, Ingredient> map, IngredientResponseVO vo) throws Exception {
                        map.put(vo.id, new Ingredient(vo.id, vo.name, new BigDecimal(vo.price.toString()), vo.image));
                    }

                }).blockingGet();

                /* mapeando lanches */

                final HashMap<Integer, Lunch> hashLunch = fromIterable(response.getInfoLunchResponseVOs()).collectInto(new HashMap<Integer, Lunch>(), new BiConsumer<HashMap<Integer, Lunch>, InfoLunchResponseVO>() {

                    @Override
                    public void accept(HashMap<Integer, Lunch> map, InfoLunchResponseVO vo) throws Exception {
                        final Lunch lunch = new Lunch(vo.id, vo.name, vo.image, Collections.<Ingredient>emptyList());

                        fromIterable(vo.ingredients).blockingForEach(new Consumer<Integer>() {

                            @Override
                            public void accept(Integer id) throws Exception {
                                Ingredient ingredient = hashIngredients.get(id);
                                lunch.addIngredient(ingredient);
                            }

                        });

                        map.put(vo.id, lunch);
                    }

                }).blockingGet();

                /* mapeando ordens */

                List<Order> result = fromIterable(response.getOrderResponseVOs()).collectInto(new ArrayList<Order>(), new BiConsumer<ArrayList<Order>, OrderResponseVO>() {

                    @Override
                    public void accept(ArrayList<Order> orders, OrderResponseVO orderResponseVO) throws Exception {
                        Lunch lunch = hashLunch.get(orderResponseVO.lunchId);

                        Order order = new Order();
                        order.setLunch(lunch);
                        order.setId(orderResponseVO.id);

                        for (int i = 0; i < orderResponseVO.extras.length(); i++) {
                            int id = orderResponseVO.extras.getInt(i);
                            order.addIngredient(hashIngredients.get(id));
                        }

                        orders.add(order);
                    }

                }).blockingGet();

                callback.onSuccess(result);
                callback.onEnd();
            }
        };
    }

    private Observable<OrdersZippedResponse> zip(){
        return Observable.zip(getListOrdersRequest(), getListOfIngredientsRequest(), getListOfLunchsRequest(), new Function3<List<OrderResponseVO>, List<IngredientResponseVO>, List<InfoLunchResponseVO>, OrdersZippedResponse>() {

            @Override
            public OrdersZippedResponse apply(@NonNull List<OrderResponseVO> orderResponseVOs, @NonNull List<IngredientResponseVO> ingredientResponseVOs, @NonNull List<InfoLunchResponseVO> infoLunchResponseVOs) throws Exception {
                return new OrdersZippedResponse(orderResponseVOs, ingredientResponseVOs, infoLunchResponseVOs);
            }

        });
    }

    private Function<Throwable, ObservableSource<? extends OrdersZippedResponse>> getListOrdersError(final BaseRequestCallback<List<Order>, RuntimeException> callback){
        return new Function<Throwable, ObservableSource<? extends OrdersZippedResponse>>() {
            @Override
            public ObservableSource<? extends OrdersZippedResponse> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Não foi possivel buscar a informação solicitada.", throwable));
                callback.onEnd();

                return empty();
            }
        };
    }

    private Observable<List<OrderResponseVO>> getListOrdersRequest(){
        return api.getOrders()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<InfoLunchResponseVO>> getListOfLunchsRequest(){
        return api.getLunchs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<IngredientResponseVO>> getListOfIngredientsRequest(){
        return api.getListOfIngredients()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class OrdersZippedResponse {

        private List<OrderResponseVO> orderResponseVOs;
        private List<IngredientResponseVO> ingredientResponseVOs;
        private List<InfoLunchResponseVO> infoLunchResponseVOs;

        public OrdersZippedResponse(List<OrderResponseVO> orderResponseVOs, List<IngredientResponseVO> ingredientResponseVOs, List<InfoLunchResponseVO> infoLunchResponseVOs) {
            this.orderResponseVOs = orderResponseVOs;
            this.ingredientResponseVOs = ingredientResponseVOs;
            this.infoLunchResponseVOs = infoLunchResponseVOs;
        }

        public List<OrderResponseVO> getOrderResponseVOs() {
            return orderResponseVOs;
        }

        public List<IngredientResponseVO> getIngredientResponseVOs() {
            return ingredientResponseVOs;
        }

        public List<InfoLunchResponseVO> getInfoLunchResponseVOs() {
            return infoLunchResponseVOs;
        }

    }

    /* create order */

    @Override
    public void createOrder(Order order, BaseRequestCallback<Order, RuntimeException> callback) {
        callback.onStart();

        getCreateOrderRequest(order)
                .onErrorResumeNext(getCreateOrderError(callback))
                .subscribe(getCreateOrderSuccess(order, callback));
    }

    public Observable<OrderResponseVO> getCreateOrderRequest(Order order){
        AddOrderRequestVO vo = new AddOrderRequestVO();
        vo.itens = fromIterable(order.getExtras())
                .collectInto(new JSONArray(), new BiConsumer<JSONArray, Ingredient>() {

            @Override
            public void accept(JSONArray array, Ingredient ingredient) throws Exception {
                array.put(ingredient.getId());
            }

        }).blockingGet();

        return api.createOrder(order.getLunch().getId(), vo)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Consumer<OrderResponseVO> getCreateOrderSuccess(final Order order, final BaseRequestCallback<Order, RuntimeException> callback){
        return new Consumer<OrderResponseVO>() {

            @Override
            public void accept(OrderResponseVO response) throws Exception {
                order.setId(response.id); // adicionando o ID gerado

                callback.onSuccess(order);
                callback.onEnd();
            }

        };
    }

    public Function<Throwable, ObservableSource<? extends OrderResponseVO>> getCreateOrderError(final BaseRequestCallback<Order, RuntimeException> callback){
        return new Function<Throwable, ObservableSource<? extends OrderResponseVO>>() {

            @Override
            public ObservableSource<? extends OrderResponseVO> apply(@NonNull Throwable throwable) throws Exception {
                callback.onErro(new RuntimeException("Não foi foi possivel salvar o pedido.", throwable));
                callback.onEnd();

                return empty();
            }

        };
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

}
