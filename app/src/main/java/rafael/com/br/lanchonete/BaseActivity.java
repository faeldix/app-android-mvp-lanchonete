package rafael.com.br.lanchonete;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rafael-iteris on 18/08/17.
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
