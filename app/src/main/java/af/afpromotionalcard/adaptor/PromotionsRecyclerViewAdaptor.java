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

import af.afpromotionalcard.R;
import af.afpromotionalcard.activity.PromotionCardActivity;
import af.afpromotionalcard.entity.PromotionCard;

public class PromotionsRecyclerViewAdaptor extends RecyclerView.Adapter<PromotionsRecyclerViewAdaptor.PromotionViewHolder> {
    PromotionCard[] promotions;
    Context context;

    public PromotionsRecyclerViewAdaptor(Context context, PromotionCard[] promotions) {
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
        holder.title.setText(promotions[position].getTitle());
        Picasso.with(context).load(promotions[position].getImage()).fit().placeholder(R.drawable.ic_place_holder).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return promotions.length;
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
            Intent promotionCardIntent = new Intent(v.getContext(), PromotionCardActivity.class);
            promotionCardIntent.putExtra("position", getAdapterPosition());
            v.getContext().startActivity(promotionCardIntent);
        }
    }
}
