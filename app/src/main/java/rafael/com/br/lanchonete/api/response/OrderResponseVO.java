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
    @SerializedName("extras")
    public List<Integer> extras;
    public Date date;

    public OrderResponseVO() {}

    public OrderResponseVO(Integer id) {
        this.id = id;
    }

    public OrderResponseVO(Integer id, Integer lunchId, List<Integer> extras, Date date) {
        this.id = id;
        this.lunchId = lunchId;
        this.extras = extras;
        this.date = date;
    }

}
