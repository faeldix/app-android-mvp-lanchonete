package rafael.com.br.lanchonete.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import rafael.com.br.lanchonete.App;
import rafael.com.br.lanchonete.component.ApplicationComponent;
import rafael.com.br.lanchonete.util.ProgressManager;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class BaseFragment extends Fragment implements BaseView {

    private ProgressManager manager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        manager = new ProgressManager(getActivity());
    }

    public ApplicationComponent getAppComponent(){
        App app = (App) getActivity().getApplication();
        return app.getAppComponent();
    }

    public void startProgress(){
        manager.show();
    }

    public void stopProgress(){
        manager.dismiss();
    }

    @Override
    public void onShowLoading() {
        startProgress();
    }

    @Override
    public void onDismissLoading() {
        stopProgress();
    }

}
