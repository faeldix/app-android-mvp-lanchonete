package rafael.com.br.lanchonete;

import android.provider.ContactsContract;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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

    @Spy
    private Order order;

    @Spy
    private Lunch mockLunch;

    @Spy
    private Ingredient mockIngredient;

    private BigDecimal price = BigDecimal.ONE;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        when(mockIngredient.getPrice()).thenReturn(price);
    }

    @Test
    public void priceOfOrderMustBeASumOfLunchIngredients(){
        when(mockLunch.getPrice()).thenReturn(price);
        order.setLunch(mockLunch);

        Assert.assertEquals(price, order.getPrice());
    }

    @Test
    public void priceOfOrderMustBeASumOfLunchIngredientsAndExtras(){
        order.setLunch(mockLunch);
        order.addIngredient(mockIngredient);

        BigDecimal lunchPrice = mockLunch.getPrice();
        BigDecimal extraPrice = mockIngredient.getPrice();
        BigDecimal expected = lunchPrice.add(extraPrice);

        Assert.assertEquals(expected, order.getPrice());
    }

    @Test
    public void verifyIfThereIsALightDiscountWhenThereIsAlfaceAndNotBacon(){
        /* teste so com alface */

        when(mockIngredient.getName())
                .thenReturn("alface");

        mockLunch.addIngredient(mockIngredient);
        order.setLunch(mockLunch);

        Assert.assertEquals(true, order.hasALightDiscount());

        /* teste com alface e bacon */

        Ingredient mockBacon = prepareMockForTypeOfIngredient("bacon");

        order.addIngredient(mockBacon);

        Assert.assertEquals(false, order.hasALightDiscount());
    }

    @Test
    public void verifyIfThereIsAMeatDiscountWhenThreeMeatOrMoreExist(){
        order.setLunch(mockLunch);

        when(mockIngredient.getName())
                .thenReturn("carne");

        order.addIngredient(mockIngredient);
        order.addIngredient(mockIngredient);

        Assert.assertEquals(false, order.hasALotOfMeatDiscount());

        order.addIngredient(mockIngredient);

        Assert.assertEquals(true, order.hasALotOfMeatDiscount());
    }

    @Test
    public void verifyIfThereIsACheeseDiscountWhenThreeCheeseOrMoreExist(){
        order.setLunch(mockLunch);

        Ingredient mockQueijo = prepareMockForTypeOfIngredient("queijo");

        order.addIngredient(mockQueijo);
        order.addIngredient(mockQueijo);

        Assert.assertEquals(false, order.hasALotOfCheaseDiscount());

        order.addIngredient(mockQueijo);

        Assert.assertEquals(true, order.hasALotOfCheaseDiscount());
    }

    @Test
    public void applyADiscountOfTenPercentIfThereIsAlfaceAndNotBacon(){
        when(mockIngredient.getName()).thenReturn("alface");

        order.setLunch(mockLunch);
        order.addIngredient(mockIngredient);

        BigDecimal discount = price.multiply(new BigDecimal("0.1"));
        BigDecimal expected = order.getPrice().subtract(discount);

        Assert.assertEquals(expected, order.getFinalPrice());
    }

    @Test
    public void applyADiscountOfOneFreeMeatForEachThreeMeat(){
        order.setLunch(mockLunch);

        when(mockIngredient.getName())
                .thenReturn("carne");

        order.addIngredient(mockIngredient);
        order.addIngredient(mockIngredient);
        order.addIngredient(mockIngredient);

        BigDecimal expected = mockIngredient.getPrice().multiply(new BigDecimal("2"));
        Assert.assertEquals(expected, order.getFinalPrice());
    }

    @Test
    public void applyADiscountOfOneFreeCheeseForEachThreeCheese(){
        order.setLunch(mockLunch);

        when(mockIngredient.getName())
                .thenReturn("queijo");

        order.addIngredient(mockIngredient);
        order.addIngredient(mockIngredient);
        order.addIngredient(mockIngredient);

        BigDecimal expected = mockIngredient.getPrice().multiply(new BigDecimal("2"));
        Assert.assertEquals(expected, order.getFinalPrice());
    }

    @Test
    public void applyAllDiscountsAtTheSameTimeIfNeeded(){
        order.setLunch(mockLunch);

        Ingredient mockAlface = prepareMockForTypeOfIngredient("alface");
        Ingredient mockCarne = prepareMockForTypeOfIngredient("carne");
        Ingredient mockQueijo = prepareMockForTypeOfIngredient("queijo");

        order.addIngredient(mockAlface);

        order.addIngredient(mockCarne);
        order.addIngredient(mockCarne);
        order.addIngredient(mockCarne);

        order.addIngredient(mockQueijo);
        order.addIngredient(mockQueijo);
        order.addIngredient(mockQueijo);

        BigDecimal light = order.getPrice().multiply(new BigDecimal("0.1"));
        BigDecimal lotOfMeat = mockCarne.getPrice();
        BigDecimal lotOfCheese = mockQueijo.getPrice();

        BigDecimal totalOfDiscount = light.add(lotOfCheese).add(lotOfMeat);
        BigDecimal expected = order.getPrice().subtract(totalOfDiscount);

        Assert.assertEquals(expected, order.getFinalPrice());
    }

    private Ingredient prepareMockForTypeOfIngredient(String type){
        Ingredient spy = mock(Ingredient.class);
        when(spy.getName()).thenReturn(type).getMock();
        when(spy.getPrice()).thenReturn(price);

        return spy;
    }

}
