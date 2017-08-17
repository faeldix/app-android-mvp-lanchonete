package rafael.com.br.lanchonete.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.InfoLunchResponseVO;
import rafael.com.br.lanchonete.api.response.IngredientResponseVO;
import rafael.com.br.lanchonete.model.Lunch;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rafael.com.br.lanchonete.service.LunchService.*;

/**
 * Created by rafael-iteris on 17/08/17.
 */

public class LunchServiceRESTImplTest {

    @Mock
    private API mockApi;

    @Spy
    private LunchServiceRESTImpl impl;

    private Throwable exception = new RuntimeException("ERROR");

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        impl.setApi(mockApi);
    }

    @Test
    public void teste(){
        List<IngredientResponseVO> ingredients = Collections.emptyList();
        List<InfoLunchResponseVO> lunchs = Collections.emptyList();

        when(mockApi.getListOfIngredients()).thenReturn(Observable.just(ingredients));
        when(mockApi.getLunchs()).thenReturn(Observable.just(lunchs));

        OnRequestListOfLunchsFinished callback = mock(OnRequestListOfLunchsFinished.class);

        impl.getListOfLunchs(callback);

        verify(callback).onStart();
        verify(callback).onSuccess(anyList());
        verify(callback).onEnd();
    }

    @After
    public void end(){}

}
