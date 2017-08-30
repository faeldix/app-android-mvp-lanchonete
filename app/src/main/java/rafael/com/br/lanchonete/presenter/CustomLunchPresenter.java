package rafael.com.br.lanchonete.presenter;

import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafael-iteris on 23/08/17.
 */

public interface CustomLunchPresenter {

    void getLunchInfo(Integer id);
    void getListOfIngredients();

    void finalizeOrder();

}
