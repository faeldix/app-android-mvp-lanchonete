package rafael.com.br.lanchonete.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import rafael.com.br.lanchonete.service.PromoService;
import rafael.com.br.lanchonete.view.PromoListView;

/**
 * Created by rafael-iteris on 21/08/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class PromoListPresenterTest {

    @Mock
    private PromoListView mockView;

    @Mock
    private PromoService mockService;

    @Spy
    private PromoListPresenterImpl presenter;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalStateException.class)
    public void whenViewIsNotSetTheMethodGetListOfPromoMustThrowAnException(){
        presenter.getListOfPromo();
    }

    @Test
    public void whenMethodOnStartOfCallbackIsCalledTheViewMustShowProgress(){
        presenter.setView(mockView);

        presenter.getCallback().onStart();
        Mockito.verify(mockView).onShowLoading();
    }

    @Test
    public void whenMethodOnEndOfCallbackIsCalledTheViewMustRemoveTheProgress(){
        presenter.setView(mockView);

        presenter.getCallback().onEnd();
        Mockito.verify(mockView).onDismissLoading();
    }

    @Test
    public void whenMethodOnSuccessIsCalledTheViewMustShowTheListOfItens(){
        presenter.setView(mockView);

        presenter.getCallback().onSuccess(Mockito.anyList());
        Mockito.verify(mockView).showListOfPromos(Mockito.anyList());
    }

    @Test
    public void whenMethodOnErrorIsCalledTheViewMustShowAnErrorMessage(){
        presenter.setView(mockView);

        presenter.getCallback().onErro(Mockito.any(RuntimeException.class));
        Mockito.verify(mockView).onShowErrorMessage(Mockito.anyString());
    }

}
