package rafael.com.br.lanchonete.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class InfoLunchResponseVO {

    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public String image;
    @SerializedName("ingredients")
    public List<Integer> ingredients = new ArrayList<>();

}
