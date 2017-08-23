package rafael.com.br.lanchonete.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rafael.com.br.lanchonete.R;
import rafael.com.br.lanchonete.component.DaggerMainViewComponent;
import rafael.com.br.lanchonete.module.MainModule;
import rafael.com.br.lanchonete.presenter.MainPresenterImpl;
import rafael.com.br.lanchonete.view.BaseFragment;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.OrderListView;

import static android.support.design.widget.BottomNavigationView.*;
import static dagger.android.AndroidInjection.inject;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    @Inject
    LunchListView lunchs;

    @Inject
    OrderListView orders;

    @Inject
    PromoListView promos;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configureColorOfIconsAtNavbar();

        navigation.setOnNavigationItemSelectedListener(getNavigationListener());
        navigation.setSelectedItemId(R.id.bar_lunch_list);
    }

    public OnNavigationItemSelectedListener getNavigationListener(){
        return new OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                configureColorOfIconsAtNavbar();

                switch (item.getItemId()){
                    case R.id.bar_lunch_list:
                        onClickListOfLunchsOption();
                        item.setIcon(getLunchIcon(android.R.color.white));
                        break;
                    case R.id.bar_order_list:
                        onClickListOfOrdersOption();
                        item.setIcon(getOrderIcon(android.R.color.white));
                        break;
                    case R.id.bar_promo_list:
                        onClickListOfPromosOption();
                        item.setIcon(getPromoIcon(android.R.color.white));
                        break;
                }

                return true;
            }

        };
    }

    @Override
    public void onClickListOfLunchsOption() {
        presenter.showListOfLunchs();
    }

    @Override
    public void onClickListOfPromosOption() {
        presenter.showListOfPromos();
    }

    @Override
    public void onClickListOfOrdersOption() {
        presenter.showListOfOrders();
    }

    @Override
    public void showListOfLunchsFragment() {
        show((Fragment) lunchs);
    }

    @Override
    public void showListOfPromosFragment() {
        show((Fragment) promos);
    }

    @Override
    public void showListOfOrdersFragment() {
        show((Fragment) orders);
    }

    public void show(Fragment fragment){
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main_container, fragment);
        tx.commit();
    }

    @Override
    public void inject() {
        DaggerMainViewComponent.builder()
                .applicationComponent(getApp().getAppComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);
    }

    public void configureColorOfIconsAtNavbar(){
        navigation.getMenu()
                .findItem(R.id.bar_lunch_list)
                .setIcon(getLunchIcon(android.R.color.darker_gray));
        navigation.getMenu()
                .findItem(R.id.bar_order_list)
                .setIcon(getOrderIcon(android.R.color.darker_gray));
        navigation.getMenu()
                .findItem(R.id.bar_promo_list)
                .setIcon(getPromoIcon(android.R.color.darker_gray));
    }

    public IconDrawable getOrderIcon(int color){
        return new IconDrawable(this, FontAwesomeIcons.fa_shopping_cart)
                .actionBarSize()
                .colorRes(color);
    }

    public IconDrawable getLunchIcon(int color){
        return new IconDrawable(this, FontAwesomeIcons.fa_list)
                .actionBarSize()
                .colorRes(color);
    }

    public IconDrawable getPromoIcon(int color){
        return new IconDrawable(this, FontAwesomeIcons.fa_smile_o)
                .actionBarSize()
                .colorRes(color);
    }

}
