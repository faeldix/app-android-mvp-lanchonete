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
import rafael.com.br.lanchonete.model.Order;
import rafael.com.br.lanchonete.service.LunchService;
import rafael.com.br.lanchonete.view.LunchListView;

import static org.mockito.Mockito.*;

/**
 * Created by rafael-iteris on 18/08/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class LunchListPresenterImplTest {

    @Mock
    private LunchListView mockView;

    @Spy
    private LunchListPresenterImpl presenter;

    @Test(expected = IllegalStateException.class)
    public void whenViewIsNotSetTheMethodGetListOfPromoMustThrowAnException(){
        presenter.getListOfLunch();
    }

    @Test
    public void whenServiceStartTheRequestOfAnOrderTheViewMustShowProgress(){
        presenter.setView(mockView);

        presenter.getOnRequestOrderFinishedCallback().onStart();
        verify(mockView).onShowLoading();
    }

    @Test
    public void whenServiceEndTheRequestOfAnOrderTheViewMustRemoveTheProgress(){
        presenter.setView(mockView);

        presenter.getOnRequestOrderFinishedCallback().onEnd();
        verify(mockView).onDismissLoading();
    }

    @Test
    public void whenServiceEndTheRequestOfAnOrderWithSuccessTheViewMustShowAnSuccessMessage(){
        presenter.setView(mockView);

        presenter.getOnRequestOrderFinishedCallback().onSuccess(mock(Order.class));
        verify(mockView).showSuccessMessageOfOrder();
    }

    @Test
    public void whenServiceEndTheRequestOfAnOrderWithErrorTheViewMustShowAnErrorMessage(){
        presenter.setView(mockView);

        presenter.getOnRequestOrderFinishedCallback().onErro(any(RuntimeException.class));
        verify(mockView).onShowErrorMessage(anyString());
    }

    @Test
    public void whenServiceStartsTheRequestOfListOfLunchsTheViewMustShowProgress(){
        presenter.setView(mockView);

        presenter.getOnRequestListOfLunchsFinishedCallback().onStart();
        verify(mockView).onShowLoading();
    }

    @Test
    public void whenServiceEndTheRequestOfListOfLunchsTheViewMustRemoveTheProgress(){
        presenter.setView(mockView);

        presenter.getOnRequestListOfLunchsFinishedCallback().onEnd();
        verify(mockView).onDismissLoading();
    }

    @Test
    public void whenServiceEndTheRequestOfListOfLunchsWithSuccessTheViewMustShowTheListOfItens(){
        presenter.setView(mockView);

        presenter.getOnRequestListOfLunchsFinishedCallback().onSuccess(anyList());
        verify(mockView).showListOfLunch(anyList());
    }

    @Test
    public void whenServiceEndTheRequestOfListOfLunchsReturnWithErrorTheViewMustShowAnErrorMessage(){
        presenter.setView(mockView);

        presenter.getOnRequestListOfLunchsFinishedCallback().onErro(any(RuntimeException.class));
        verify(mockView).onShowErrorMessage(anyString());
    }

    @Test
    public void whenAnItemIsSelectedTheViewMustShowOptions(){
        presenter.setView(mockView);

        presenter.onSelectAnLunchOfList(any(Lunch.class));
        verify(mockView).showOptionsOfLunch(any(Lunch.class));
    }


}
