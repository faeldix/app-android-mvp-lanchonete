package rafael.com.br.lanchonete.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rafael-iteris on 14/08/2017.
 */

public class Order {

    public static final Double LIGHT_DISCOUNT = 0.1d;
    public static final int NUMBER_OF_MEAT_DISCOUNT = 3;
    public static final int NUMBER_OF_CHEEASE_DISCOUNT = 3;

    private Lunch lunch;
    private List<Ingredient> extras = new ArrayList<Ingredient>();

    public Order() {}

    public Order(Lunch lunch) {
        this.lunch = lunch;
    }

    public Order(Lunch lunch, List<Ingredient> extras) {
        this.lunch = lunch;
        this.extras.addAll(extras);
    }

    public Lunch getLunch() {
        return lunch;
    }

    public void setLunch(Lunch lunch) {
        this.lunch = lunch;
    }

    public void addIngredient(Ingredient ingredient){
        extras.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient){
        extras.remove(ingredient);
    }

    public List<Ingredient> getExtras() {
        return Collections.unmodifiableList(extras);
    }

    public BigDecimal getPrice(){
        BigDecimal normal = lunch.getPrice();
        BigDecimal custom = BigDecimal.ZERO;

        for(Ingredient ingredient : extras){
            custom = custom.add(ingredient.getPrice());
        }

        return normal.add(custom);
    }

    public BigDecimal getFinalPrice(){
        BigDecimal price = getPrice();

        if(hasALightDiscount()){
            BigDecimal discount = price.multiply(new BigDecimal(LIGHT_DISCOUNT));
            price = price.subtract(discount); // 10%
        }

        if(hasALotOfCheaseDiscount()){
            BigDecimal priceOf = getPriceOf("Queijo");
            int number = getNumberOf("Queijo");
            int free = number / NUMBER_OF_CHEEASE_DISCOUNT;

            BigDecimal discount = priceOf
                    .multiply(new BigDecimal(free));
            price = price.subtract(discount);
        }

        if(hasALotOfMeatDiscount()){
            BigDecimal priceOf = getPriceOf("Carne");
            int number = getNumberOf("Carne");
            int free = number / NUMBER_OF_MEAT_DISCOUNT;

            BigDecimal discount = priceOf
                    .multiply(new BigDecimal(free));
            price = price.subtract(discount);
        }

        return price;
    }

    public boolean hasALightDiscount(){
        boolean hasAlface = getNumberOf("Alface") > 0;
        boolean hasBacon = getNumberOf("Bacon") == 0;

        return hasAlface && hasBacon;
    }

    public boolean hasALotOfMeatDiscount() {
        return getNumberOf("Carne") >= 3;
    }

    public boolean hasALotOfCheaseDiscount() {
        return getNumberOf("Queijo") >= 3;
    }

    private BigDecimal getPriceOf(String type){
        BigDecimal price = BigDecimal.ZERO;

        for (Ingredient ingredient : lunch.getIngredients()){
            if(ingredient.getName().contains(type)) price = ingredient.getPrice();
        }

        for (Ingredient ingredient : extras){
            if(ingredient.getName().contains(type)) price = ingredient.getPrice();
        }

        return price;
    }

    private int getNumberOf(String type){
        int number = 0;

        for (Ingredient ingredient : lunch.getIngredients()){
            if(ingredient.getName().contains(type)) number++;
        }

        for (Ingredient ingredient : extras){
            if(ingredient.getName().contains(type)) number++;
        }

        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return lunch != null ? lunch.equals(order.lunch) : order.lunch == null;
    }

    @Override
    public int hashCode() {
        return lunch != null ? lunch.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Order{" +
                "lunch=" + lunch +
                ", extras=" + extras +
                '}';
    }

}