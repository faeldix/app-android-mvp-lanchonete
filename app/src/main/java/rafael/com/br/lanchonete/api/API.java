package rafael.com.br.lanchonete.api;

import java.util.List;

import io.reactivex.Observable;
import rafael.com.br.lanchonete.api.request.AddOrderRequestVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.OrderResponseVO;
import rafael.com.br.lanchonete.api.response.PromoResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by rafael-iteris on 14/08/2017.
 */

public interface API {

    @GET("lanche")
    Observable<List<InfoLunchResponseVO>> getLunchs();

    @GET("ingrediente/de/{lanche}")
    Call<List<IngredientResponseVO>> getIngredientsOfLunch(@Path("lanche") Integer lanche);

    @GET("lanche/{lanche}")
    Call<InfoLunchResponseVO> getInfoOfLunch(@Path("lanche") Integer lanche);

    @PUT("pedido/{lanche}")
    Call<OrderResponseVO> createOrder(@Path("lanche") Integer lanche, @Body AddOrderRequestVO request);

    @GET("promocao")
    Call<List<PromoResponseVO>> getPromos();

    @GET("pedido")
    Call<List<OrderResponseVO>> getOrders();

    @GET("ingrediente")
    Observable<List<IngredientResponseVO>> getListOfIngredients();

}
