package rafael.com.br.lanchonete.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.Constants;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.adapter.LunchListAdapter;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class FragmentLunchListView extends BaseFragment implements LunchListView {

    @BindView(R.id.list)
    RecyclerView recycler;

    private LunchListPresenter presenter;
    private Picasso picasso;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Lista de Lanches");
        presenter.getListOfLunch();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler, container, false);
        ButterKnife.bind(this, view);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        new AlertDialog.Builder(getActivity())
                .setTitle("Atencao")
                .setMessage(message)
                .setNeutralButton("OK", null)
                .show();
    }

    @Override
    public void showListOfLunch(List<Lunch> list) {

        if(recycler.getAdapter() == null){
            LunchListAdapter adapter = new LunchListAdapter(presenter, picasso, list);
            recycler.setAdapter(adapter);
        }

    }

    @Override
    public void showOptionsOfLunch(final Lunch lunch) {
        new AlertDialog.Builder(getActivity())
                .setTitle(lunch.getName())
                .setMessage(lunch.getIngredientListDescription())
                .setPositiveButton("Fazer Pedido!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onMakeOrder(lunch);
                    }

                })
                .setNegativeButton("Adicionar Ingredientes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToCustomize(lunch);
                    }

                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })
                .show();
    }

    @Override
    public void showSuccessMessageOfOrder() {
        Toast.makeText(getActivity(), "Pedido feito com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToCustomize(Lunch lunch) {
        Bundle extras = new Bundle();
        extras.putInt(Constants.BUNDLE_KEY_LUNCH_ID, lunch.getId());

        Intent intent = new Intent(getContext(), CustomLunchActivity.class);
        intent.putExtras(extras);

        getActivity().startActivity(intent);
    }

    public LunchListPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(LunchListPresenter presenter) {
        this.presenter = presenter;
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public void setPicasso(Picasso picasso) {
        this.picasso = picasso;
    }
}

