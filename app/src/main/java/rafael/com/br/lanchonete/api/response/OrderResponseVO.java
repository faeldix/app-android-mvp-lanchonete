package rafael.com.br.lanchonete.api.response;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.Date;
import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;
import retrofit2.http.Field;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class OrderResponseVO {

    public Integer id;

    @SerializedName("id_sandwich")
    public Integer lunchId;

    public JSONArray extras;
    public Date date;

}
