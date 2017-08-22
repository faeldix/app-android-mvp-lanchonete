package rafael.com.br.lanchonete.presenter;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.view.LunchListView;

/**
 * Created by rafael-iteris on 18/08/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class LunchListPresenterImplTest {

    @Mock
    private LunchListView mockView;

    @Spy
    private LunchListPresenterImpl presenter;

    @Before
    public void init(){
        presenter.setView(mockView);
    }

    @Test
    public void whenServiceStartsTheRequestTheViewMustShowProgress(){
        presenter.getOnRequestListOfLunchsFinishedCallback().onStart();
        Mockito.verify(mockView).onShowLoading();
    }

    @Test
    public void whenServiceEndTheRequestTheViewMustRemoveTheProgress(){
        presenter.getOnRequestListOfLunchsFinishedCallback().onEnd();
        Mockito.verify(mockView).onDismissLoading();
    }

    @Test
    public void whenServiceReturnWithSuccessTheViewMustShowTheListOfItens(){
        presenter.getOnRequestListOfLunchsFinishedCallback().onSuccess(Mockito.anyList());
        Mockito.verify(mockView).showListOfLunch(Mockito.anyList());
    }

    @Test
    public void whenServiceReturnWithErrorTheViewMustShowAnErrorMessage(){
        presenter.getOnRequestListOfLunchsFinishedCallback().onErro(Mockito.any(RuntimeException.class));
        Mockito.verify(mockView).onShowErrorMessage(Mockito.anyString());
    }

    @Test
    public void whenAnItemIsSelectedTheViewMustShowOptions(){
        presenter.onSelectAnLunchOfList(Mockito.any(Lunch.class));
        Mockito.verify(mockView).showOptionsOfLunch(Mockito.any(Lunch.class));
    }


}
