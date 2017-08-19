package rafael.com.br.lanchonete.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public class FragmentOrderListView extends BaseFragment implements OrderListView {

    @BindView(R.id.list)
    RecyclerView recycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflated = inflater.inflate(R.layout.recycler, container, false);
        ButterKnife.bind(this, inflated);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onShowErrorMessage(String message) {

    }

    @Override
    public void showListOfOrder(List<Order> list) {

    }

    @Override
    public void showOptionsOfOrder(Order order) {

    }

}
