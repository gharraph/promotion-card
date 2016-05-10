package af.afpromotionalcard.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import af.afpromotionalcard.R;
import af.afpromotionalcard.entity.PromotionCard;

import static af.afpromotionalcard.activity.PromotionsActivity.CACHE_FILE_NAME;

public class PromotionCardActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTextView;
    TextView descriptionTextView;
    TextView footerTextView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_card);
        setupContentViews(getCachedData());
    }

    PromotionCard[] getCachedData() {
        PromotionCard[] data = null;
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(new File(getCacheDir(),"/") + CACHE_FILE_NAME));
            Gson gson = new Gson();
            data = gson.fromJson(String.valueOf(in.readObject()), PromotionCard[].class);
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    void setupContentViews(PromotionCard[] data) {
        imageView = (ImageView) findViewById(R.id.promo_img_large);
        titleTextView = (TextView) findViewById(R.id.promo_title_large);
        descriptionTextView = (TextView) findViewById(R.id.description);
        footerTextView= (TextView) findViewById(R.id.footer);
        button = (Button) findViewById(R.id.shop_now_button);

        PromotionCard promotionCard = data[(int)getIntent().getExtras().get("position")];

        String imgURL = promotionCard.getImage();
        String feedTitle = promotionCard.getTitle();
        String feedDescription = promotionCard.getDescription();
        String feedFooter = promotionCard.getFooter();

        Picasso.with(this).load(imgURL).placeholder(R.drawable.ic_place_holder).fit().into(imageView);
        final PromotionCard.Button feedButton = promotionCard.getButton();

        if(feedTitle != null) {
            titleTextView.setText(feedTitle);
        }

        if (feedDescription != null){
            descriptionTextView.setText(feedDescription);
        }

        if(feedFooter !=  null){
            footerTextView.setText(Html.fromHtml(feedFooter));
        }

        button.setText(feedButton.getTitle());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedButton.getTarget()));
                internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
                internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(internetIntent);
            }
        });
    }
}
