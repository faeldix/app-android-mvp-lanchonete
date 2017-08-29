package rafael.com.br.lanchonete.view;

import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rafael.com.br.lanchonete.BuildConfig;
import rafael.com.br.lanchonete.R;

import static org.mockito.Mockito.*;

/**
 * Created by rafael-iteris on 28/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void whenMainActivityStartsTheOptionSelectedOnBottomBarMustBeTheLunchList(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Assert.assertEquals(activity.navigation.getSelectedItemId(), R.id.bar_lunch_list);
    }

    @Test
    public void whenMainActivityStartsTheFragmentWithListOfLunchsMustBePresented(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        Assert.assertEquals(((Fragment) activity.lunchs).isVisible(), true);
        Assert.assertEquals(((Fragment) activity.promos).isVisible(), false);
        Assert.assertEquals(((Fragment) activity.orders).isVisible(), false);
    }

    @Test
    public void whenSelectTheListOfLunchOptionTheListOfLunchsMustBePresented(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.navigation.setSelectedItemId(R.id.bar_order_list);
        activity.navigation.setSelectedItemId(R.id.bar_lunch_list);

        Assert.assertEquals(((Fragment) activity.lunchs).isVisible(), true);
        Assert.assertEquals(((Fragment) activity.promos).isVisible(), false);
        Assert.assertEquals(((Fragment) activity.orders).isVisible(), false);
    }

    @Test
    public void whenSelectTheListOfPromosOptionTheListOfPromosMustBePresented(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.navigation.setSelectedItemId(R.id.bar_promo_list);

        Assert.assertEquals(((Fragment) activity.lunchs).isVisible(), false);
        Assert.assertEquals(((Fragment) activity.promos).isVisible(), true);
        Assert.assertEquals(((Fragment) activity.orders).isVisible(), false);
    }

    @Test
    public void whenSelectTheListOfOrdersOptionTheListOfOrdersMustBePresented(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.navigation.setSelectedItemId(R.id.bar_order_list);

        Assert.assertEquals(((Fragment) activity.lunchs).isVisible(), false);
        Assert.assertEquals(((Fragment) activity.promos).isVisible(), false);
        Assert.assertEquals(((Fragment) activity.orders).isVisible(), true);
    }

    @Test
    public void whenSelectTheListOfLunchOptionAtBottomBarTheViewMethodOnClickListOfLunchsOptionMustBeCalled(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity = spy(activity);

        activity.navigation.setSelectedItemId(R.id.bar_lunch_list);

        MenuItem item = mock(MenuItem.class);
        when(item.getItemId()).thenReturn(R.id.bar_lunch_list);

        activity.getNavigationListener().onNavigationItemSelected(item);
        verify(activity).onClickListOfLunchsOption();
    }

    @Test
    public void whenSelectTheListOfPromoOptionAtBottomBarTheViewMethodOnClickListOfPromoOptionMustBeCalled(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity = spy(activity);

        MenuItem item = mock(MenuItem.class);
        when(item.getItemId()).thenReturn(R.id.bar_promo_list);

        activity.getNavigationListener().onNavigationItemSelected(item);
        verify(activity).onClickListOfPromosOption();
    }

    @Test
    public void whenClickOnListOfOrdersOptionTheMethodOnClickListOfOrdersOptionMustBeCalled(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity = spy(activity);

        MenuItem item = mock(MenuItem.class);
        when(item.getItemId()).thenReturn(R.id.bar_order_list);

        activity.getNavigationListener().onNavigationItemSelected(item);
        verify(activity).onClickListOfOrdersOption();
    }

}
