package rafael.com.br.lanchonete.adapter;

import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rafael.com.br.lanchonete.BuildConfig;
import rafael.com.br.lanchonete.model.Ingredient;
import rafael.com.br.lanchonete.model.Lunch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rafael.com.br.lanchonete.adapter.IngredientAdapter.*;

/**
 * Created by rafael-iteris on 29/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class IngredientAdapterTest {

    @Mock
    private Picasso mockPicasso;

    @Mock
    private OnIngredientModifierListener listener;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void theSizeOfAdapterMustBeTheSameSizeOfList(){
        List<Ingredient> mockList = mock(List.class);
        when(mockList.size()).thenReturn(15);

        IngredientAdapter adapter = new IngredientAdapter(mockList, mockPicasso, listener);
        Assert.assertEquals(mockList.size(), adapter.getItemCount());

    }

}
