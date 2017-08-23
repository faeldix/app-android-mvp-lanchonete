package rafael.com.br.lanchonete.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rafael.com.br.lanchonete.Constants;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.adapter.IngredientAdapter;
import rafael.com.br.lanchonete.component.CustomLunchComponent;
import rafael.com.br.lanchonete.component.DaggerCustomLunchComponent;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Order;
import rafael.com.br.lanchonete.module.CustomLunchModule;
import rafael.com.br.lanchonete.presenter.CustomLunchPresenter;

import static rafael.com.br.lanchonete.adapter.IngredientAdapter.*;

/**
 * Created by rafael-iteris on 23/08/17.
 */

public class CustomLunchActivity extends BaseActivity implements CustomLunchView {

    @BindView(R.id.txt_lunch_title)
    TextView lunchName;

    @BindView(R.id.txt_lunch_price)
    TextView lunchPrice;

    @BindView(R.id.txt_lunch_ingredients)
    TextView lunchIngredients;

    @BindView(R.id.img_lunch_img)
    ImageView lunchImg;

    @BindView(R.id.list)
    RecyclerView recycler;

    @BindView(R.id.txt_order_total)
    TextView orderPrice;

    @Inject
    CustomLunchPresenter presenter;

    @Inject
    Picasso picasso;

    private IngredientAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_lunch);
        ButterKnife.bind(this);

        int lunchId = getIntent().getExtras().getInt(Constants.BUNDLE_KEY_LUNCH_ID);

        presenter.getListOfIngredients();
        presenter.getLunchInfo(lunchId);
    }

    @Override
    public void showMessageOfError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Atencao")
                .setMessage(message)
                .setNeutralButton("OK", null)
                .show();
    }

    @Override
    public void showInfoLunch(Lunch lunch) {
        lunchName.setText(lunch.getName());
        lunchPrice.setText(lunch.getPrice().toString());
        lunchIngredients.setText(lunch.getIngredientListDescription());
        orderPrice.setText("TOTAL: " +lunch.getPrice().toString());

        picasso.load(lunch.getImage())
                .resize(100, 100)
                .into(lunchImg);

    }

    @Override
    public void showListOfIngredients(List<Ingredient> ingredients) {

        if(recycler.getAdapter() == null){
            adapter = new IngredientAdapter(ingredients, picasso, (OnIngredientModifierListener) presenter);

            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    @Override
    public void showTotalPrice(BigDecimal total) {
        orderPrice.setText("R$ " + total.toString());
    }

    @OnClick(R.id.btn_finalize_order)
    public void finalizeOrder(){

    }

    @Override
    public void inject() {
        DaggerCustomLunchComponent.builder()
                .applicationComponent(getApp().getAppComponent())
                .customLunchModule(new CustomLunchModule(this))
                .build()
                .inject(this);
    }

}
