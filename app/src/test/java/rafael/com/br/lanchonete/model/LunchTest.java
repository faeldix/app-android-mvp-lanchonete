package rafael.com.br.lanchonete.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchTest {

    @Mock
    private Ingredient mockIngredient;

    private BigDecimal price = BigDecimal.ONE;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void priceMustBeASumOfIngredientsPrice(){
        Mockito.when(mockIngredient.getPrice()).thenReturn(price);

        Lunch lunch = new Lunch();

        /* with zero ingredients */

        Assert.assertEquals(BigDecimal.ZERO, lunch.getPrice());

        /* add one ingredient */

        lunch.addIngredient(mockIngredient);
        Assert.assertEquals(price, lunch.getPrice());

        /* add two ingredient */

        lunch.addIngredient(mockIngredient);
        Assert.assertEquals(price.multiply(BigDecimal.valueOf(2)), lunch.getPrice());
    }



}
