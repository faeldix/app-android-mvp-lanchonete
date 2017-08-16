package rafael.com.br.lanchonete;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class DataSource {

    public static BigDecimal PRECO_ALFACE = new BigDecimal(3.0d);
    public static BigDecimal PRECO_BACON = new BigDecimal(2.0d);
    public static BigDecimal PRECO_QUEIJO = new BigDecimal(3.0d);
    public static BigDecimal PRECO_CARNE = new BigDecimal(4.0d);

    public static Ingredient alface = new Ingredient(0, "Alface", PRECO_ALFACE, "");
    public static Ingredient bacon = new Ingredient(0, "Bacon", PRECO_BACON, "");
    public static Ingredient queijo = new Ingredient(0, "Queijo", PRECO_QUEIJO, "");
    public static Ingredient carne = new Ingredient(0, "Carne", PRECO_CARNE, "");

    public static Ingredient[] carne3x = times(3, carne);
    public static Ingredient[] queijo3x = times(3, queijo);

    public static Lunch comQueijo = new Lunch(0, "", "", Arrays.asList(queijo));
    public static Lunch comCarne = new Lunch(0, "", "", Arrays.asList(carne));
    public static Lunch comAlface = new Lunch(0, "", "", Arrays.asList(alface));
    public static Lunch comBacon = new Lunch(0, "", "", Arrays.asList(bacon));

    public static Ingredient[] times(int times, Ingredient ingredient){
        Ingredient[] ingredients = new Ingredient[times];

        for (int i = 0; i < times; i++) {
            ingredients[i] = ingredient;
        }

        return ingredients;
    }

}
