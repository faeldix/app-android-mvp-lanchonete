package rafael.com.br.lanchonete.api.request;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class AddOrderRequestVO {

    @SerializedName("extras")
    public JsonArray itens;

}
