package rafael.com.br.lanchonete.api.response;

import java.util.ArrayList;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class InfoLunchResponseVO {

    public Integer id;
    public String name;
    public String image;
    public List<Ingredient> ingredients;

}
