package rafael.com.br.lanchonete.presenter;

import java.util.List;

import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.view.LunchListView;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public interface LunchListPresenter extends BasePresenter<LunchListView> {

    public void getListOfLunch();

    public void onSelectAnLunchOfList(Lunch item);
    public void onMakeOrder(Lunch lunch);

}
