package rafael.com.br.lanchonete.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

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

        presenter.showListOfLunchs();
        navigation.setOnNavigationItemSelectedListener(getNavigationListener());
    }

    public OnNavigationItemSelectedListener getNavigationListener(){
        return new OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bar_lunch_list:
                        onClickListOfLunchsOption();
                        break;
                    case R.id.bar_order_list:
                        onClickListOfOrdersOption();
                        break;
                    case R.id.bar_promo_list:
                        onClickListOfPromosOption();
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

}
