package rafael.com.br.lanchonete.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.adapter.LunchListAdapter;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class LunchListViewImpl extends DefaultFragment implements LunchListView {

    @BindView(R.id.list)
    RecyclerView recycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lunch_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onShowLoading() {
        startProgress();
    }

    @Override
    public void onDismissLoading() {
        stopProgress();
    }

    @Override
    public void onShowErrorMessage(String message) {

    }

    @Override
    public void showListOfLunch(LunchListAdapter adapter) {
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClickOnLunchItem(Lunch lunch) {

    }

    @Override
    public void onDefaultLunchSelected(Lunch lunch) {
        // TODO ir para tela de pedido
    }

    @Override
    public void onCustomLunchSelected(Lunch lunch) {
        // TODO ir para tela de customização de lanche
    }
}
