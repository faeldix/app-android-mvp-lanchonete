package rafael.com.br.lanchonete.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private BaseRequestCallback<List<Lunch>, RuntimeException> callbackListOfLunchs;

    @Mock
    private BaseRequestCallback<Lunch, RuntimeException> callbackInfoOfLunch;

    @Captor
    private ArgumentCaptor<List<Lunch>> captorListOfLunchs;

    @Captor
    private ArgumentCaptor<Lunch> captorInfoOfLunch;

    private Throwable exception = new RuntimeException("ERROR");

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        mockImplementation.setApi(mockApi);
    }

    @Test
    public void afterSuccessOfRequestOfListOfLunchsTheCallbackMethodOnSuccessMustBeCalled(){
        List<IngredientResponseVO> ingredients = Collections.emptyList();
        List<InfoLunchResponseVO> lunchs = Collections.emptyList();

        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callbackListOfLunchs);

        verify(callbackListOfLunchs).onStart();
        verify(callbackListOfLunchs).onSuccess(anyList());
        verify(callbackListOfLunchs).onEnd();
    }

    @Test
    public void afterAnErrorOfRequestOfListOfLunchsTheCallbackMethodOnErrorMustBeCalled(){
        Observable<List<IngredientResponseVO>> error = Observable.error(exception);
        List<InfoLunchResponseVO> lunchs = Collections.emptyList();

        when(mockApi.getListOfIngredients()).thenReturn(error);
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callbackListOfLunchs);

        verify(callbackListOfLunchs).onStart();
        verify(callbackListOfLunchs).onErro(any(RuntimeException.class));
        verify(callbackListOfLunchs).onEnd();
    }

    @Test
    public void afterSuccessOfRequestOfListOfLunchsTheCallbackMethodOnSuccessMustBeCalledWithAListOfLunchs(){
        List<IngredientResponseVO> ingredients = Collections.emptyList();
        List<InfoLunchResponseVO> lunchs = Arrays.asList(spy(InfoLunchResponseVO.class), spy(InfoLunchResponseVO.class));

        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callbackListOfLunchs);

        verify(callbackListOfLunchs).onStart();
        verify(callbackListOfLunchs).onSuccess(captorListOfLunchs.capture());
        verify(callbackListOfLunchs).onEnd();

        Assert.assertEquals(lunchs.size(), captorListOfLunchs.getValue().size());
    }

    @Test
    public void afterSuccessOfRequestTheCallbackMethodOnSuccessMustBeCalledWithAListOfLunchsAndYourIngredients(){
        IngredientResponseVO i1 = createIngredient(1);
        IngredientResponseVO i2 = createIngredient(2);

        InfoLunchResponseVO lunch = mock(InfoLunchResponseVO.class);
        lunch.ingredients = Arrays.asList(i1.id, i2.id);

        List<InfoLunchResponseVO> lunchs = Arrays.asList(lunch);
        List<IngredientResponseVO> ingredients = Arrays.asList(i1, i2);

        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        mockImplementation.getListOfLunchs(callbackListOfLunchs);

        verify(callbackListOfLunchs).onStart();
        verify(callbackListOfLunchs).onSuccess(captorListOfLunchs.capture());
        verify(callbackListOfLunchs).onEnd();

        Lunch unique = captorListOfLunchs.getValue().iterator().next();
        Assert.assertEquals(ingredients.size(), unique.getIngredients().size());
    }

    @Test
    public void afterSuccessOfRequestOfInfoOfLunchsTheCallbackMethodOnSuccessMustBeCalled(){
        Observable<InfoLunchResponseVO> info = Observable.just(createInfoOfLunch(1));
        Observable<List<IngredientResponseVO>> ingredients = Observable.just(Arrays.asList(createIngredient(1)));

        when(mockApi.getInfoOfLunch(anyInt()))
                .thenReturn(info);
        when(mockApi.getListOfIngredients())
                .thenReturn(ingredients);

        mockImplementation.getInfoOfLunch(1, callbackInfoOfLunch);

        verify(callbackInfoOfLunch).onStart();
        verify(callbackInfoOfLunch).onSuccess(any(Lunch.class));
        verify(callbackInfoOfLunch).onEnd();
    }

    @Test
    public void afterAnErrorOfRequestOfInfoOfLunchsTheCallbackMethodOnErrorMustBeCalled(){
        Observable<InfoLunchResponseVO> error = Observable.error(exception);
        Observable<List<IngredientResponseVO>> ingredients = Observable.just(Collections.<IngredientResponseVO>emptyList());

        when(mockApi.getInfoOfLunch(anyInt()))
                .thenReturn(error);
        when(mockApi.getListOfIngredients())
                .thenReturn(ingredients);

        mockImplementation.getInfoOfLunch(1, callbackInfoOfLunch);

        verify(callbackInfoOfLunch).onStart();
        verify(callbackInfoOfLunch).onErro(any(RuntimeException.class));
        verify(callbackInfoOfLunch).onEnd();
    }

    @Test
    public void afterSuccessOfRequestOfInfoOfLunchsTheCallbackMethodOnSuccessMustBeCalledWithInfoOfLunch(){
        IngredientResponseVO i1 = createIngredient(1);
        IngredientResponseVO i2 = createIngredient(2);

        InfoLunchResponseVO vo = new InfoLunchResponseVO(1, "X-Tudo", "www.img.com/img.png", Arrays.asList(1, 2));

        when(mockApi.getInfoOfLunch(anyInt()))
                .thenReturn(Observable.just(vo));
        when(mockApi.getListOfIngredients())
                .thenReturn(Observable.just(Arrays.asList(i1, i2)));

        mockImplementation.getInfoOfLunch(1, callbackInfoOfLunch);

        verify(callbackInfoOfLunch).onStart();
        verify(callbackInfoOfLunch).onSuccess(captorInfoOfLunch.capture());
        verify(callbackInfoOfLunch).onEnd();

        Lunch info = captorInfoOfLunch.getValue();

        Assert.assertEquals(vo.id, info.getId());
        Assert.assertEquals(vo.ingredients.size(), info.getIngredients().size());
    }

    public InfoLunchResponseVO createInfoOfLunch(Integer id){
        return new InfoLunchResponseVO(id, "Ingredient", "http", Collections.<Integer>emptyList());
    }

    public IngredientResponseVO createIngredient(Integer id){
        IngredientResponseVO ingredient = mock(IngredientResponseVO.class);
        ingredient.id = id;
        ingredient.price = 1d;

        return ingredient;
    }

}
