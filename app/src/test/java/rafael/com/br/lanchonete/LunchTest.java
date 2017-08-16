package rafael.com.br.lanchonete;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchTest {

    @Test
    public void priceMustBeASumOfIngridientsPrice(){
        List<Ingredient> ingredients = Arrays.asList(DataSource.carne3x);
        Lunch lunch = new Lunch(0, "", "", ingredients);

        BigDecimal expected = DataSource.PRECO_CARNE
                .multiply(new BigDecimal(lunch.getIngredients().size()));

        Assert.assertEquals(expected, lunch.getPrice());
    }

}
