package rafael.com.br.lanchonete.view;

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
import rafael.com.br.lanchonete.component.DaggerLunchComponent;
import rafael.com.br.lanchonete.view.BaseFragment;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.OrderListView;

import static android.support.design.widget.BottomNavigationView.*;
import static dagger.android.AndroidInjection.inject;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_container)
    LinearLayout container;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    @Inject
    LunchListView lunchs;

    @Inject
    OrderListView orders;

    @Inject
    OrderListView promos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(getNavigationListener());
        show((Fragment) lunchs);
    }

    public void show(Fragment fragment){
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main_container, fragment);
        tx.commit();
    }

    public OnNavigationItemSelectedListener getNavigationListener(){
        return new OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bar_lunch_list:
                        show((Fragment) lunchs);
                        break;
                    case R.id.bar_order_list:
                        show((Fragment) orders);
                        break;
                    case R.id.bar_promo_list:
                        show((Fragment) promos);
                        break;
                }

                return true;
            }

        };
    }

    @Override
    public void inject() {
        DaggerLunchComponent.builder()
                .applicationComponent(getApp().getAppComponent()).build()
                .inject(this);
    }
}
