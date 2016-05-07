package af.afpromotionalcard.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import af.afpromotionalcard.R;
import af.afpromotionalcard.activity.PromotionCardActiviy;
import af.afpromotionalcard.entity.PromotionCards;

public class PromotionsRecyclerViewAdaptor extends RecyclerView.Adapter<PromotionsRecyclerViewAdaptor.PromotionViewHolder> {
    List<PromotionCards> promotions;
    Context context;

    public PromotionsRecyclerViewAdaptor(Context context, List<PromotionCards> promotions) {
        this.promotions = promotions;
        this.context = context;
    }

    @Override
    public PromotionsRecyclerViewAdaptor.PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_card_view, parent, false);
        return new PromotionViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(PromotionsRecyclerViewAdaptor.PromotionViewHolder holder, int position) {
        holder.title.setText(promotions.get(position).getTitle());
        Picasso.with(context).load(promotions.get(position).getImage()).resize(120, 60).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView image;

        public PromotionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.promo_title);
            image = (ImageView) itemView.findViewById(R.id.promo_img);
        }

        @Override
        public void onClick(View v) {
            Intent promotionCardIntent = new Intent(v.getContext(), PromotionCardActiviy.class);
            v.getContext().startActivity(promotionCardIntent);
        }
    }
}
