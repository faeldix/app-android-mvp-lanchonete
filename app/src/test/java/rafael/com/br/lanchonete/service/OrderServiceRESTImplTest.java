package rafael.com.br.lanchonete.service;

import junit.framework.Assert;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import rafael.com.br.lanchonete.BuildConfig;
import rafael.com.br.lanchonete.RxJavaJUnitRule;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.request.AddOrderRequestVO;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.api.response.OrderResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;

import static java.util.Arrays.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rafael-iteris on 22/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class OrderServiceRESTImplTest {

    @Rule
    public RxJavaJUnitRule rule = new RxJavaJUnitRule();

    @Mock
    private API mockApi;

    @Spy
    private OrderServiceRESTImpl mockImplementation;

    @Mock
    private BaseRequestCallback<List<Order>, RuntimeException> mockGetListCallback;

    @Mock
    private BaseRequestCallback<Order, RuntimeException> mockCreateCallback;

    @Captor
    private ArgumentCaptor<List<Order>> captorOrders;

    @Captor
    private ArgumentCaptor<Order> captorOrder;

    private Throwable exception = new RuntimeException("ERROR");

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockImplementation.setApi(mockApi);
    }

    @Test
    public void afterRequestOfListOfOrdersTheCallbackMethodOnSuccessMustBeCalled(){
        Observable<List<InfoLunchResponseVO>> lunchs = Observable.just(asList(createInfoOfLunchResponse(1)));
        Observable<List<IngredientResponseVO>> ingredients = Observable.just(asList(createIngredientResponse(1), createIngredientResponse(2)));
        Observable<List<OrderResponseVO>> orders = Observable.just(asList(createOrderResponse(1, 1)));

        when(mockApi.getListOfIngredients()).thenReturn(ingredients);
        when(mockApi.getLunchs()).thenReturn(lunchs);
        when(mockApi.getOrders()).thenReturn(orders);

        mockImplementation.getOrders(mockGetListCallback);

        verify(mockGetListCallback).onStart();
        verify(mockGetListCallback).onSuccess(anyList());
        verify(mockGetListCallback).onEnd();
    }

    @Test
    public void afterRequestOfListOfOrdersWithErrorTheCallbackMethodOnErrorMustBeCalled(){
        Observable<List<InfoLunchResponseVO>> lunchs = Observable.error(exception);
        Observable<List<IngredientResponseVO>> ingredients = Observable.just(asList(createIngredientResponse(1), createIngredientResponse(2)));
        Observable<List<OrderResponseVO>> orders = Observable.just(asList(createOrderResponse(1, 1)));

        when(mockApi.getListOfIngredients()).thenReturn(ingredients);
        when(mockApi.getLunchs()).thenReturn(lunchs);
        when(mockApi.getOrders()).thenReturn(orders);

        mockImplementation.getOrders(mockGetListCallback);

        verify(mockGetListCallback).onStart();
        verify(mockGetListCallback).onErro(any(RuntimeException.class));
        verify(mockGetListCallback).onEnd();
    }

    @Test
    public void afterRequestOfListOfOrdersWithSuccessTheCallbackMethodOnSucessMustBeCalledWithAListOfOrdersWithAllInformations(){
        Observable<List<InfoLunchResponseVO>> lunchs = Observable.just(asList(createInfoOfLunchResponse(1)));
        Observable<List<IngredientResponseVO>> ingredients = Observable.just(asList(createIngredientResponse(1), createIngredientResponse(2)));
        Observable<List<OrderResponseVO>> orders = Observable.just(asList(createOrderResponse(1, 1)));

        when(mockApi.getListOfIngredients()).thenReturn(ingredients);
        when(mockApi.getLunchs()).thenReturn(lunchs);
        when(mockApi.getOrders()).thenReturn(orders);

        mockImplementation.getOrders(mockGetListCallback);

        verify(mockGetListCallback).onStart();
        verify(mockGetListCallback).onSuccess(captorOrders.capture());
        verify(mockGetListCallback).onEnd();

        List<Order> captured = captorOrders.getValue();
        Order result = captured.iterator().next();

        Assert.assertEquals(1, captured.size());
        Assert.assertEquals(1, result.getId().intValue());
        Assert.assertEquals(1, result.getLunch().getId().intValue());

        Assert.assertEquals(2, result.getLunch().getIngredients().size());
        Assert.assertEquals(2, result.getExtras().size());
    }

    @Test
    public void afterRequestOfCreateOrderWithSuccessTheCallbackMethodMustBeCalledWithAOrderWithId(){
        Order order = createOrder();

        AddOrderRequestVO body = createAddOrderRequestVO(order);
        OrderResponseVO responseVO = new OrderResponseVO(123);

        when(mockApi.createOrder(anyInt(), any(AddOrderRequestVO.class)))
                .thenReturn(Observable.just(responseVO));

        mockImplementation.createOrder(order, mockCreateCallback);

        verify(mockCreateCallback).onStart();
        verify(mockCreateCallback).onSuccess(captorOrder.capture());
        verify(mockCreateCallback).onEnd();

        Order result = captorOrder.getValue();

        Assert.assertNotNull(result.getId());
        Assert.assertEquals(123, result.getId().intValue());
    }

    @Test
    public void afterRequestOfCreateOrderWithErrorTheCallbackMethodMustBeCalledWithAnError(){
        Order order = createOrder();
        Observable<OrderResponseVO> error = Observable.error(exception);

        when(mockApi.createOrder(anyInt(), any(AddOrderRequestVO.class))).thenReturn(error);

        mockImplementation.createOrder(order, mockCreateCallback);

        verify(mockCreateCallback).onStart();
        verify(mockCreateCallback).onErro(any(RuntimeException.class));
        verify(mockCreateCallback).onEnd();
    }


    public AddOrderRequestVO createAddOrderRequestVO(Order order){
        AddOrderRequestVO vo = new AddOrderRequestVO();
        vo.itens = new JSONArray();

        for (Ingredient item : order.getExtras()){
            vo.itens.put(item.getId());
        }

        return vo;
    }

    public static Order createOrder(){
        Ingredient ingredient = new Ingredient(1, "Banana", BigDecimal.ONE, "");
        Lunch lunch = new Lunch(1, "Big Mac", "", Arrays.asList(ingredient));
        Order order = new Order(null, lunch, Arrays.asList(ingredient));

        return order;
    }

    public static OrderResponseVO createOrderResponse(Integer id, Integer lunchId){
        OrderResponseVO vo = new OrderResponseVO();
        vo.id = id;
        vo.lunchId = lunchId;
        vo.date = new Date();
        vo.extras = new JSONArray().put(1).put(2);

        return vo;
    }

    public static InfoLunchResponseVO createInfoOfLunchResponse(Integer id){
        InfoLunchResponseVO lunch = new InfoLunchResponseVO(id, "Ingredient", "http", null);
        lunch.ingredients = asList(1, 2);

        return lunch;
    }

    public static IngredientResponseVO createIngredientResponse(Integer id){
        IngredientResponseVO ingredient = mock(IngredientResponseVO.class);
        ingredient.id = id;
        ingredient.price = 1d;

        return ingredient;
    }

}
