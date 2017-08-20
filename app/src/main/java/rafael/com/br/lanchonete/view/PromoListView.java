package rafael.com.br.lanchonete.view;

import java.util.List;

import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Promo;

/**
 * Created by rafael-iteris on 18/08/17.
 */

public interface PromoListView extends BaseView {

    void onShowErrorMessage(String message);
    void showListOfPromos(List<Promo> list);

}
