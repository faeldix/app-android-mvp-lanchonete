package rafael.com.br.lanchonete.service;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import rafael.com.br.lanchonete.RxJavaJUnitRule;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.model.Lunch;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rafael.com.br.lanchonete.service.LunchService.*;

/**
 * Created by rafael-iteris on 17/08/17.
 */


@RunWith(MockitoJUnitRunner.class)
public class LunchServiceRESTImplTest {

    @Rule
    public RxJavaJUnitRule rule = new RxJavaJUnitRule();

    @Mock
    private API mockApi;

    @Spy
    private LunchServiceRESTImpl mockImplementation;

    @Mock
    private OnRequestListOfLunchsFinished callback;

    @Captor
    private ArgumentCaptor<List<Lunch>> captor;

    private Throwable exception = new RuntimeException("ERROR");

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        mockImplementation.setApi(mockApi);
    }

    @Test
    public void afterSuccessOfRequestTheCallbackMethodOnSuccessMustBeCalled(){
        List<IngredientResponseVO> ingredients = Collections.emptyList();
        List<InfoLunchResponseVO> lunchs = Collections.emptyList();

        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callback);

        verify(callback).onStart();
        verify(callback).onSuccess(anyList());
        verify(callback).onEnd();
    }

    @Test
    public void afterAnErrorOfRequestTheCallbackMethodOnErrorMustBeCalled(){
        Observable<List<IngredientResponseVO>> error = Observable.error(exception);
        List<InfoLunchResponseVO> lunchs = Collections.emptyList();

        when(mockApi.getListOfIngredients()).thenReturn(error);
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callback);

        verify(callback).onStart();
        verify(callback).onError(any(RuntimeException.class));
        verify(callback).onEnd();
    }

    @Test
    public void afterSuccessOfRequestTheCallbackMethodOnSuccessMustBeCalledWithAListOfLunchs(){
        List<IngredientResponseVO> ingredients = Collections.emptyList();
        List<InfoLunchResponseVO> lunchs = Arrays.asList(spy(InfoLunchResponseVO.class), spy(InfoLunchResponseVO.class));

        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callback);
        verify(callback).onSuccess(captor.capture());

        Assert.assertEquals(lunchs.size(), captor.getValue().size());
    }

    @Test
    public void afterSuccessOfRequestTheCallbackMethodOnSuccessMustBeCalledWithAListOfLunchsAndYourIngredients(){
        IngredientResponseVO i1 = create(1);
        IngredientResponseVO i2 = create(2);

        InfoLunchResponseVO lunch = mock(InfoLunchResponseVO.class);
        lunch.ingredients = Arrays.asList(i1.id, i2.id);

        List<InfoLunchResponseVO> lunchs = Arrays.asList(lunch);
        List<IngredientResponseVO> ingredients = Arrays.asList(i1, i2);

        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callback);
        verify(callback).onSuccess(captor.capture());

        Lunch unique = captor.getValue().iterator().next();
        Assert.assertEquals(ingredients.size(), unique.getIngredients().size());
    }

    public IngredientResponseVO create(Integer id){
        IngredientResponseVO ingredient = mock(IngredientResponseVO.class);
        ingredient.id = id;
        ingredient.price = 1d;

        return ingredient;
    }

}
