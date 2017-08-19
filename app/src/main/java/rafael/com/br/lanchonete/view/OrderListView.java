package rafael.com.br.lanchonete.view;

import java.util.List;

import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafael-iteris on 18/08/17.
 */

public interface OrderListView extends BaseView {

    void onShowErrorMessage(String message);

    void showListOfOrder(List<Order> list);
    void showOptionsOfOrder(Order order);

}
