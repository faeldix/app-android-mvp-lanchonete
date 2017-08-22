package rafael.com.br.lanchonete.service;

import java.util.List;

import rafael.com.br.lanchonete.model.Promo;

/**
 * Created by rafael-iteris on 18/08/17.
 */

public interface PromoService {

    void getListOfPromos(BaseRequestCallback<List<Promo>, RuntimeException> callback);

}
