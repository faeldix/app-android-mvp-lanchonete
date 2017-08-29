package rafael.com.br.lanchonete.presenter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.view.CustomLunchView;

import static org.mockito.Mockito.*;

/**
 * Created by rafael-iteris on 29/08/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class CustomLunchPresenterImplTest {

    @Spy
    private CustomLunchPresenterImpl presenter;

    @Mock
    private CustomLunchView mockView;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalStateException.class)
    public void whenViewIsNullTheMethodGetInfoOfLunchMustBeThrowAnException(){
        presenter.getLunchInfo(0);
    }

    @Test(expected = IllegalStateException.class)
    public void whenViewIsNullTheMethodGetIngredientsMustBeThrowAnException(){
        presenter.getListOfIngredients();
    }

    @Test
    public void whenServiceStartsTheRequestOfInfoOfLunchTheViewMustShowProgress(){
        presenter.setView(mockView);
        presenter.getLunchInfoRequestCallback().onStart();

        verify(mockView).onShowLoading();
    }

    @Test
    public void whenServiceEndTheRequestOfInfoOfLunchTheViewMustDismissProgress(){
        presenter.setView(mockView);

        presenter.getLunchInfoRequestCallback().onStart();
        presenter.getLunchInfoRequestCallback().onEnd();

        verify(mockView).onDismissLoading();
    }

    @Test
    public void whenServiceReturnWithSuccessTheViewMustShowTheInfoOfLunch(){
        presenter.setView(mockView);
        presenter.getLunchInfoRequestCallback().onSuccess(any(Lunch.class));

        verify(mockView).showInfoLunch(any(Lunch.class));
    }

    @Test
    public void whenServiceReturnWithAnErrorTheViewMustShowTheErrorMessage(){
        presenter.setView(mockView);
        presenter.getLunchInfoRequestCallback().onErro(any(RuntimeException.class));

        verify(mockView).showMessageOfError(anyString());
    }

    @Test
    public void whenServiceStartTheRequestOfListOfIngredientsTheViewMustShowProgress(){
        presenter.setView(mockView);

        presenter.getListOfIngredientsRequestCallback().onStart();
        verify(mockView).onShowLoading();
    }

    @Test
    public void whenServiceEndTheRequestOfListOfIngredientsTheViewMustShowProgress(){
        presenter.setView(mockView);

        presenter.getListOfIngredientsRequestCallback().onStart();
        presenter.getListOfIngredientsRequestCallback().onEnd();
        verify(mockView).onDismissLoading();
    }

    @Test
    public void whenServiceReturnWithSuccessTheListOfIngredientesTheViewMustShowTheList(){
        presenter.setView(mockView);

        presenter.getListOfIngredientsRequestCallback().onSuccess(anyList());
        verify(mockView).showListOfIngredients(anyList());
    }

    @Test
    public void whenServiceReturnWithAnErrorTheErrorMessageMustBePresented(){
        presenter.setView(mockView);
        presenter.getListOfIngredientsRequestCallback().onErro(any(RuntimeException.class));

        verify(mockView).showMessageOfError(anyString());
    }

    @Test
    public void whenTheRequestOfListOfIngredientsAndInfoOfLunchStartsTheProgressOfRequestMustBeDismissedOnlyAfterTheEndOfAllRequest(){
        presenter.setView(mockView);

        presenter.getListOfIngredientsRequestCallback().onStart();
        presenter.getLunchInfoRequestCallback().onStart();

        Assert.assertEquals(presenter.getCounter().get(), 2);

        presenter.getListOfIngredientsRequestCallback().onEnd();
        presenter.getLunchInfoRequestCallback().onEnd();

        Assert.assertEquals(presenter.getCounter().get(), 0);

        verify(mockView, times(1)).onDismissLoading();
    }

}
