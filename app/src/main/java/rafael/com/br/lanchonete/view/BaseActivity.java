package rafael.com.br.lanchonete.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rafael.com.br.lanchonete.App;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public App getApp(){
        return (App) getApplication();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inject();
    }

    public abstract void inject();

}
