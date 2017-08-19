package rafael.com.br.lanchonete.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.adapter.PromoListAdapter;
import rafael.com.br.lanchonete.model.Promo;
import rafael.com.br.lanchonete.presenter.PromoListPresenter;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public class FragmentPromoListView extends BaseFragment implements PromoListView {

    @BindView(R.id.list)
    RecyclerView recycler;

    private PromoListPresenter presenter;
    private Picasso picasso;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflated = inflater.inflate(R.layout.recycler, container, false);
        ButterKnife.bind(this, inflated);

        return inflated;
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
        new AlertDialog.Builder(getActivity())
                .setTitle("Atencao")
                .setMessage(message)
                .setNeutralButton("OK", null)
                .show();
    }

    @Override
    public void showListOfLunch(List<Promo> list) {
        PromoListAdapter adapter = new PromoListAdapter(list, picasso, getContext());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public PromoListPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(PromoListPresenter presenter) {
        this.presenter = presenter;
        presenter.setView(this);
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public void setPicasso(Picasso picasso) {
        this.picasso = picasso;
    }

}