package rafael.com.br.lanchonete.adapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rafael.com.br.lanchonete.BuildConfig;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.model.Promo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by rafael-iteris on 21/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PromoListAdapterTest {

    @Test
    public void theSizeOfAdapterMustBeTheSameSizeOfList(){
        List<Promo> mockList = mock(List.class);
        when(mockList.size()).thenReturn(15);

        PromoListAdapter adapter = new PromoListAdapter(mockList);
        Assert.assertEquals(mockList.size(), adapter.getItemCount());
    }

}
