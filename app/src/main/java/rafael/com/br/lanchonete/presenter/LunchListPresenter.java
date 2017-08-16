package rafael.com.br.lanchonete.presenter;

import java.util.List;

import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public interface LunchListPresenter {

    void getListOfLunchs(OnRequestListOfLunchsFinished callback);

    interface OnRequestListOfLunchsFinished {

        void onSuccess(List<Lunch> lunchs);
        void onError(Exception e);

    }

}
