package rafael.com.br.lanchonete.service;

import java.util.List;

import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

public interface LunchService {

    void getListOfLunchs(final BaseRequestCallback<List<Lunch>, RuntimeException> callback);
    void getInfoOfLunch(Integer id, final BaseRequestCallback<Lunch, RuntimeException> callback);

}
