package rafael.com.br.lanchonete.service;

import java.util.List;

import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

public interface LunchService {

    void getListOfLunchs(final OnRequestListOfLunchsFinished callback);

    public interface OnRequestListOfLunchsFinished {

        void onStart();
        void onEnd();

        void onSuccess(List<Lunch> lunchs);
        void onError(Exception e);

    }


}
