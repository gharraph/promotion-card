package af.afpromotionalcard.activity;

import android.os.Build;
import android.os.Bundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import af.afpromotionalcard.BuildConfig;
import af.afpromotionalcard.R;

import static junit.framework.Assert.assertNotNull;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.JELLY_BEAN)
public class PromotionsActivityTest {
    private ActivityController controller;
    private PromotionsActivity activity;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(PromotionsActivity.class);
        activity = (PromotionsActivity) controller.get();
        controller.create().start().resume();
        activity.getSupportFragmentManager().executePendingTransactions();
    }

    @After
    public void tearDown() {
        controller.pause().stop().destroy();
        activity = null;
        controller = null;
    }

    @Test
    public void onCreateSetsTheContectViewWithActivityPromotionRecyclerView() {
        assertNotNull(activity.findViewById(R.id.promotions_recyler_view_id));
    }

    @Test
    public void onCreateInitializesPromotionRecylcerViewWithPromotionsRecyclerViewAdaptor() {

    }

}