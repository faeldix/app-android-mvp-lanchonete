package rafael.com.br.lanchonete.view;

import android.content.Context;
import android.support.v4.app.Fragment;

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

    public void startProgress(){
        manager.show();
    }

    public void stopProgress(){
        manager.dismiss();
    }

}
