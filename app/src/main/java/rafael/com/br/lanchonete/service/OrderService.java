package rafael.com.br.lanchonete.service;

import java.util.List;

import rafael.com.br.lanchonete.api.response.OrderResponseVO;
import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public interface OrderService {

    void getOrders(BaseRequestCallback<List<Order>, RuntimeException> callback);
    void createOrder(Order order, BaseRequestCallback<Order, RuntimeException> callback);

}
