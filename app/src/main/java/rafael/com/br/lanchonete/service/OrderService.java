package rafael.com.br.lanchonete.service;

import java.util.List;

import rafael.com.br.lanchonete.api.response.OrderResponseVO;
import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public interface OrderService {

    void getOrders(GetOrdersCallback callback);
    void createOrder(Order order, CreateOrderCallback callback);

    public interface GetOrdersCallback extends BaseRequestCallback {

        void onSuccess(List<Order> orders);
        void onError(Throwable err);

    }

    public interface CreateOrderCallback extends BaseRequestCallback {

        void onSuccess(OrderResponseVO created);
        void onError(Throwable err);

    }

}
