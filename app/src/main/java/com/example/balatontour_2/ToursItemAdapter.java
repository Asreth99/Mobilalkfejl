package com.example.balatontour_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ToursItemAdapter extends RecyclerView.Adapter<ToursItemAdapter.ViewHolder>{
    private static final String LOG_TAG = ToursItemAdapter.class.getName();
    private Context mContext;
    private ArrayList<ToursItem> mToursItemData;
    private int lastPosition = -1;

    public ToursItemAdapter() {
    }

    public ToursItemAdapter(Context mContext, ArrayList<ToursItem> mShopingItemData) {
        this.mContext = mContext;
        this.mToursItemData = mShopingItemData;
    }

    @NonNull
    @Override
    public ToursItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.tours_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToursItemAdapter.ViewHolder holder, int position) {
        // Get current sport.
        ToursItem currentItem = mToursItemData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentItem);


        if(holder.getBindingAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getBindingAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mToursItemData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameText;
        private TextView mDescText;
        private ImageView mItemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameText = itemView.findViewById(R.id.tours_title);
            mDescText = itemView.findViewById(R.id.tours_description);
            mItemImage = itemView.findViewById(R.id.tours_image);

        }

        void bindTo(ToursItem currentItem){
            mNameText.setText(currentItem.getName());
            mDescText.setText(currentItem.getDescription());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);
        }
    }
}
