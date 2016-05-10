package af.afpromotionalcard.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.util.ActivityController;

import af.afpromotionalcard.BuildConfig;
import af.afpromotionalcard.R;
import af.afpromotionalcard.activity.PromotionCardActivity;
import af.afpromotionalcard.activity.PromotionsActivity;
import af.afpromotionalcard.entity.PromotionCard;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.JELLY_BEAN)
public class PromotionsRecyclerViewAdaptorTest {

    private ViewGroup promotionRecyclerView;
    private ActivityController controller;
    private Activity activity;
    private PromotionsRecyclerViewAdaptor adaptor;
    private PromotionCard[] promotions;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(PromotionsActivity.class);
        activity = (PromotionsActivity) controller.get();
        controller.create();
        promotionRecyclerView = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.promotion_card_view, null);

        buildPromotionsArray();

        adaptor = new PromotionsRecyclerViewAdaptor(activity.getApplicationContext(), promotions);
    }

    @Test
    public void bindingViewHolderSetsCorrectValuesFromPromotionsJsonObject() {
        PromotionsRecyclerViewAdaptor.PromotionViewHolder holder = new PromotionsRecyclerViewAdaptor.PromotionViewHolder(promotionRecyclerView);

        adaptor.onBindViewHolder(holder, 0);

        assertThat(String.valueOf(holder.title.getText()), is("promotion-title-1"));
        assertThat(holder.image, notNullValue());
    }

    @Test
    public void onGetItemCountPromotionsJsonObjectSizeGetReturned() {
        assertThat(adaptor.getItemCount(), is(promotions.length));

    }

    private void buildPromotionsArray() {
        PromotionCard.Button button1 = new PromotionCard.Button();
        button1.setTarget("target-1");
        button1.setTitle("title-1");
        PromotionCard card1 = new PromotionCard();
        card1.setButton(button1);
        card1.setTitle("promotion-title-1");
        card1.setDescription("description-1");
        card1.setFooter("footer-1");
        card1.setImage("image-1");

        PromotionCard.Button button2 = new PromotionCard.Button();
        button2.setTitle("title-2");
        button2.setTarget("target-2");
        PromotionCard card2 = new PromotionCard();
        card2.setButton(button2);
        card2.setTitle("promotion-title-2");
        card2.setDescription("description-2");
        card2.setFooter("footer-2");
        card2.setImage("image-2");
        promotions = new PromotionCard[] { card1, card2 };
    }

    @Test
    public void onClickOfPromotionViewHolderStartsPromotionCardActivity() {
        PromotionsRecyclerViewAdaptor.PromotionViewHolder holder = new PromotionsRecyclerViewAdaptor.PromotionViewHolder(promotionRecyclerView);

        holder.onClick(promotionRecyclerView);

        ShadowApplication shadowContext = Shadows.shadowOf(RuntimeEnvironment.application);
        Intent intent = shadowContext.getNextStartedActivity();
        assertThat(intent.getComponent().getClassName(), is(PromotionCardActivity.class.getName()));
    }

    @Test
    public void onClickOfPromotionViewHolderPassesThePositionClickedToPromotionCardActivity() {
        PromotionsRecyclerViewAdaptor.PromotionViewHolder holder = new PromotionsRecyclerViewAdaptor.PromotionViewHolder(promotionRecyclerView);

        holder.onClick(promotionRecyclerView);

        ShadowApplication shadowContext = Shadows.shadowOf(RuntimeEnvironment.application);
        Intent intent = shadowContext.getNextStartedActivity();
        assertThat((int) intent.getExtras().get("position"), is(holder.getAdapterPosition()));
    }

}