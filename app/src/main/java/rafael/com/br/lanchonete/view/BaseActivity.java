package rafael.com.br.lanchonete.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rafael.com.br.lanchonete.App;
import rafael.com.br.lanchonete.util.ProgressManager;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressManager manager;

    public App getApp(){
        return (App) getApplication();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new ProgressManager(this);

        inject();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onShowLoading() {
        startProgress();
    }

    @Override
    public void onDismissLoading() {
        stopProgress();
    }

    public void startProgress(){
        manager.show();
    }

    public void stopProgress(){
        manager.dismiss();
    }

    public abstract void inject();

}
