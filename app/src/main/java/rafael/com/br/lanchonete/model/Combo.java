package rafael.com.br.lanchonete.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logonrm on 14/08/2017.
 */

public class Combo {

    public Integer id;
    public String name;
    public String image;

    public List<Ingredient> ingredients = new ArrayList<Ingredient>();

}
