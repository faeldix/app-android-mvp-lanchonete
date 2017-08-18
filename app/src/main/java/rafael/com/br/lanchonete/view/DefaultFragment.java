package rafael.com.br.lanchonete.view;

import android.content.Context;
import android.support.v4.app.Fragment;

import rafael.com.br.lanchonete.App;
import rafael.com.br.lanchonete.component.ApplicationComponent;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class DefaultFragment extends Fragment {

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

}
