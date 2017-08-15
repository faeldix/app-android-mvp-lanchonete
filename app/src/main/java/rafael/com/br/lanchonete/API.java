package rafael.com.br.lanchonete;

import org.json.JSONArray;

import java.util.List;

import rafael.com.br.lanchonete.model.Combo;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Order;
import rafael.com.br.lanchonete.model.Promo;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by logonrm on 14/08/2017.
 */

public interface API {

    @GET("lanche")
    List<Combo> getCombos();

    @GET("ingrediente/de/{lanche}")
    List<Ingredient> getIngredientsOf(@Path("lanche") Integer lanche);

    @GET("lanche/{lanche}")
    Combo getComboInfo(@Path("lanche") Integer lanche);

    @PUT("pedido/{lanche}")
    void createOrder(@Path("lanche") Integer lanche);

    @PUT("pedido/{lanche}")
    void createCustomOrder(@Path("lanche") Integer lanche, @Field("extras") JSONArray extras);

    @GET("promocao")
    List<Promo> getPromos();

    @GET("pedido")
    List<Order> getOrders();

    @GET("ingrediente")
    List<Ingredient> getListOfIngredients();

}
