package rafael.com.br.lanchonete.service;

import java.util.List;

import rafael.com.br.lanchonete.model.Ingredient;

/**
 * Created by rafael-iteris on 21/08/17.
 */

public interface IngredientService {

    void getListOfIngredients(BaseRequestCallback<List<Ingredient>, RuntimeException> callback);

}
