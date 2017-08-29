package rafael.com.br.lanchonete.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import rafael.com.br.lanchonete.RxJavaJUnitRule;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.model.Ingredient;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rafael-iteris on 29/08/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceRESTImplTest {

    @Rule
    public RxJavaJUnitRule rule = new RxJavaJUnitRule();

    @Mock
    private API mockApi;

    @Mock
    private BaseRequestCallback<List<Ingredient>, RuntimeException> callback;

    @Spy
    private IngredientServiceRESTImpl mockImplementation;

    private RuntimeException exception = new RuntimeException("TESTE");

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockImplementation.setApi(mockApi);
    }

    @Test
    public void afterSuccessOfRequestOfListOfIngredientsTheCallbackMethodOnSuccessMustBeCalled() {
        List<IngredientResponseVO> ingredients = Collections.emptyList();
        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));

        mockImplementation.getListOfIngredients(callback);

        verify(callback).onStart();
        verify(callback).onSuccess(anyListOf(Ingredient.class));
        verify(callback).onEnd();
    }

    @Test
    public void afterAnErrorOfRequestOfListOfIngredientsTheCallbackMethodOnErrorMustBeCalled(){
        Observable<List<IngredientResponseVO>> error = Observable.error(exception);

        when(mockApi.getListOfIngredients()).thenReturn(error);

        mockImplementation.getListOfIngredients(callback);

        verify(callback).onStart();
        verify(callback).onErro(any(RuntimeException.class));
        verify(callback).onEnd();
    }

}
