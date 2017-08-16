package rafael.com.br.lanchonete;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DaggerApplication;
import rafael.com.br.lanchonete.component.ApplicationComponent;
import rafael.com.br.lanchonete.component.DaggerLunchComponent;
import rafael.com.br.lanchonete.component.LunchComponent;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.LunchListViewImpl;

import static dagger.android.AndroidInjection.inject;

//TODO passar essa classe para MVP

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    LinearLayout container;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    @Inject
    LunchListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App app = (App) getApplication();
        DaggerLunchComponent.builder().applicationComponent(app.getApp()).build().inject(this);

        show((Fragment) view);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()){
                    case R.id.bar_lunch_list:
                        show((Fragment) view);
                        break;
                    case R.id.bar_order_list:
                    case R.id.bar_promo_list:
                        break;
                }

                tx.commit();

                return true;
            }

        });
    }

    public void show(Fragment fragment){
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main_container, fragment);
        tx.commit();
    }

}
