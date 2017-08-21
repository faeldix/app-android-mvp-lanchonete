package rafael.com.br.lanchonete.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rafael-iteris on 14/08/2017.
 */

public class Lunch {

    private Integer id;
    private String name;
    private String image;

    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Lunch() {
    }

    public Lunch(Integer id, String name, String image, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.image = image;

        this.ingredients.addAll(ingredients);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice(){
        BigDecimal price = BigDecimal.ZERO;

        for (Ingredient ingredient : ingredients){
            price = price.add(ingredient.getPrice());
        }

        return price;
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public String getIngredientListDescription(){
        StringBuilder builder = new StringBuilder();

        for (Ingredient ingredient : ingredients){
            builder.append(ingredient.getName());
            builder.append(", ");
        }

        int last = builder.lastIndexOf(",");
        return builder.substring(0, last);
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient){
        ingredients.remove(ingredient);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lunch lunch = (Lunch) o;

        return id != null ? id.equals(lunch.id) : lunch.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
