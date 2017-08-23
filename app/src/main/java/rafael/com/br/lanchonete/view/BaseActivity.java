package rafael.com.br.lanchonete.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        inject();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return super.onSupportNavigateUp();
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

    public synchronized void startProgress(){
        if(!manager.getDialog().isShowing()) manager.show();
    }

    public synchronized void stopProgress(){
        if(manager.getDialog().isShowing()) manager.dismiss();
    }

    public abstract void inject();

}
