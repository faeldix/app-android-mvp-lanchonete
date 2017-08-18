package rafael.com.br.lanchonete.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import rafael.com.br.lanchonete.BuildConfig;
import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.presenter.LunchListPresenter;
import rafael.com.br.lanchonete.view.LunchListView;

import static org.mockito.Mockito.*;

/**
 * Created by rafael-iteris on 18/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LunchListAdapterTest {

    @Mock
    private LunchListPresenter mockPresenter;

    @Mock
    private Picasso mockPicasso;

    @Mock
    private LunchListView mockView;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        Application app = RuntimeEnvironment.application;

        when(mockPresenter.getView()).thenReturn(mockView);
        when(mockView.getContext()).thenReturn(app);
    }

    @Test
    public void theSizeOfAdapterMustBeTheSameSizeOfList(){
        List<Lunch> mockList = mock(List.class);
        when(mockList.size()).thenReturn(15);

        LunchListAdapter adapter = new LunchListAdapter(mockPresenter, mockPicasso, mockList);

        Assert.assertEquals(mockList.size(), adapter.getItemCount());
    }

}
