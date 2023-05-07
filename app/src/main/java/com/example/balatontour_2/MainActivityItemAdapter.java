package com.example.balatontour_2;

import android.content.Context;
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

import java.util.ArrayList;

public class MainActivityItemAdapter extends RecyclerView.Adapter<MainActivityItemAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MainActivityItem> mEventItemData;
    private int lastPosition = -1;


    public MainActivityItemAdapter(Context mContext, ArrayList<MainActivityItem> mEventItemData) {
        this.mContext = mContext;
        this.mEventItemData = mEventItemData;
    }

    @NonNull
    @Override
    public MainActivityItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainActivityItemAdapter.ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.activity_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityItemAdapter.ViewHolder holder, int position) {
        MainActivityItem currentItem = mEventItemData.get(position);

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
        return mEventItemData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mDateText;
        private TextView mDateHypenText;
        private TextView mDate2Text;

        private TextView mTitleText;
        private ImageView mItemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDateText = itemView.findViewById(R.id.eventDate);
            mDateHypenText = itemView.findViewById(R.id.eventHypen);
            mDate2Text = itemView.findViewById(R.id.eventDate2);
            mTitleText = itemView.findViewById(R.id.eventTitle);
            mItemImage = itemView.findViewById(R.id.eventImage);
        }

        void bindTo(MainActivityItem currentItem){
            mDateText.setText(currentItem.getEventDate());
            mDateHypenText.setText(currentItem.getEventDateHypen());
            mDate2Text.setText(currentItem.getEventDate2());

            mTitleText.setText(currentItem.getEventTitle());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);
        }
    }
}
