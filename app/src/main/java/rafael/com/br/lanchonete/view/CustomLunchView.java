package rafael.com.br.lanchonete.view;

import java.math.BigDecimal;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 23/08/17.
 */

public interface CustomLunchView extends BaseView {

    public void showMessageOfError(String err);
    public void showMessageOfSuccessOfOrder(String succ);

    public void showInfoLunch(Lunch lunch);
    public void showListOfIngredients(List<Ingredient> ingredients);
    public void showTotalPrice(BigDecimal total);

}
