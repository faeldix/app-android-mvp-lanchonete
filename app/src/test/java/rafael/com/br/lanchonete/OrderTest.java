package rafael.com.br.lanchonete;

import android.provider.ContactsContract;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;

import static org.mockito.Mockito.*;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class OrderTest {

    private List<Ingredient> ingredients;

    @Spy
    private Order order;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void priceOfOrderMustBeASumOfLunchIngredients(){
        Order order = new Order(DataSource.comAlface);

        Assert.assertEquals(DataSource.alface.getPrice(), order.getPrice());
    }

    @Test
    public void priceOfOrderMustBeASumOfLunchIngredientsAndExtras(){
        Order order = new Order(DataSource.comAlface);

        order.addIngredient(DataSource.alface);

        BigDecimal expected = DataSource.alface.getPrice().multiply(new BigDecimal(2));

        Assert.assertEquals(expected, order.getPrice());
    }

    @Test
    public void verifyIfThereIsALightDiscountWhenThereIsAlfaceAndNotBacon(){
        Order order = new Order(DataSource.comAlface);

        Assert.assertEquals(true, order.hasALightDiscount());

        order.addIngredient(DataSource.bacon);

        Assert.assertEquals(false, order.hasALightDiscount());
    }

    @Test
    public void verifyIfThereIsAMeatDiscountIfThereIsThreeMeatOrMore(){
        Order order = new Order(DataSource.comAlface);

        order.addIngredient(DataSource.carne);
        order.addIngredient(DataSource.carne);

        Assert.assertEquals(false, order.hasALotOfMeatDiscount());

        order.addIngredient(DataSource.carne);

        Assert.assertEquals(true, order.hasALotOfMeatDiscount());

        order.addIngredient(DataSource.carne);

        Assert.assertEquals(true, order.hasALotOfMeatDiscount());
    }

    @Test
    public void verifyIfThereIsACheaseDiscountIfThereIsThreeCheeseOrMore(){
        Order order = new Order(DataSource.comAlface);

        order.addIngredient(DataSource.queijo);
        order.addIngredient(DataSource.queijo);

        Assert.assertEquals(false, order.hasALotOfCheaseDiscount());

        order.addIngredient(DataSource.queijo);

        Assert.assertEquals(true, order.hasALotOfCheaseDiscount());

        order.addIngredient(DataSource.queijo);

        Assert.assertEquals(true, order.hasALotOfCheaseDiscount());
    }

    @Test
    public void applyADiscountOfTenPercentIfThereIsAlfaceAndNotBacon(){
        Order order = new Order(DataSource.comAlface);

        BigDecimal discount = DataSource.comAlface.getPrice().multiply(new BigDecimal(0.1));
        BigDecimal expected = order.getPrice().subtract(discount);

        Assert.assertEquals(expected, order.getFinalPrice());
    }

    @Test
    public void applyADiscountOfOneFreeMeatForEachThreeMeat(){
        Order order = new Order(DataSource.comBacon);

        order.addIngredient(DataSource.carne);
        order.addIngredient(DataSource.carne);
        order.addIngredient(DataSource.carne);

        BigDecimal expected = DataSource.carne.getPrice().multiply(new BigDecimal(2)).add(DataSource.bacon.getPrice());

        Assert.assertEquals(expected, order.getFinalPrice());
    }

    @Test
    public void applyADiscountOfOneFreeCheeseForEachThreeCheese(){
        Order order = new Order(DataSource.comBacon);

        order.addIngredient(DataSource.queijo);
        order.addIngredient(DataSource.queijo);
        order.addIngredient(DataSource.queijo);

        BigDecimal expected = DataSource.queijo.getPrice().multiply(new BigDecimal(2)).add(DataSource.bacon.getPrice());

        Assert.assertEquals(expected, order.getFinalPrice());
    }



}
