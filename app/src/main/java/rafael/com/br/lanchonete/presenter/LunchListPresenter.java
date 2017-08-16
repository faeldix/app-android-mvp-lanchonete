package rafael.com.br.lanchonete.presenter;

import java.util.List;

import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.view.LunchListView;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public interface LunchListPresenter {

    public void getListOfLunch();
    public void onSelectAnLunchOfList(Lunch item);

    public LunchListView getView();
    public void setView(LunchListView view);

}
