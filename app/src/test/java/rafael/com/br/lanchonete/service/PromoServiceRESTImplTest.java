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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import rafael.com.br.lanchonete.RxJavaJUnitRule;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.api.response.PromoResponseVO;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Promo;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static rafael.com.br.lanchonete.service.PromoService.*;

/**
 * Created by rafael-iteris on 21/08/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class PromoServiceRESTImplTest {

    @Rule
    public RxJavaJUnitRule rule = new RxJavaJUnitRule();

    @Mock
    private API mockApi;

    @Mock
    private PromoServiceResponseCallback mockCallback;

    @Spy
    private PromoServiceRESTImpl mockService;

    @Captor
    private ArgumentCaptor<List<Promo>> captor;

    private Throwable exception = new RuntimeException("ERROR");

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        mockService.setApi(mockApi);
    }

    @Test
    public void afterSuccessOfRequestTheCallbackMethodOnSuccessMustBeCalledWithListOfPromos(){
        List<PromoResponseVO> result = Arrays.asList(new PromoResponseVO(), new PromoResponseVO());

        when(mockApi.getPromos()).thenReturn(Observable.just(result));

        mockService.getListOfPromos(mockCallback);

        verify(mockCallback).onStart();
        verify(mockCallback).onSucces(captor.capture());
        verify(mockCallback).onEnd();

        Assert.assertEquals(result.size(), captor.getValue().size());
    }

    @Test
    public void afterAnErrorOfRequestTheCallbackMethodOnErrorMustBeCalled(){
        Observable<List<PromoResponseVO>> error = Observable.error(exception);
        when(mockApi.getPromos()).thenReturn(error);

        mockService.getListOfPromos(mockCallback);

        verify(mockCallback).onStart();
        verify(mockCallback).onError(any(RuntimeException.class));
        verify(mockCallback).onEnd();
    }

}
