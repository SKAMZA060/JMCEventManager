package com.jmceventmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;
import com.jmceventmanager.Models.ModelEvent;
import com.jmceventmanager.R;

import java.util.List;

public class EventGridAdapter extends ArrayAdapter<ModelEvent> {
    private final Context context;
    private int lastPosition = -1;

    public EventGridAdapter(Context context, List<ModelEvent> events) {
        super(context, R.layout.grid_item_event, events);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelEvent event = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grid_item_event, parent, false);

            viewHolder.cardView = convertView.findViewById(R.id.eventCard);
            viewHolder.tvEventName = convertView.findViewById(R.id.tvEventName);
            viewHolder.tvEventDateTime = convertView.findViewById(R.id.tvEventDateTime);
            viewHolder.tvVenue = convertView.findViewById(R.id.tvVenue);
            viewHolder.tvParticipants = convertView.findViewById(R.id.tvParticipants);
            viewHolder.tvEventStatus = convertView.findViewById(R.id.tvEventStatus);
            viewHolder.progressBar = convertView.findViewById(R.id.progressBar);
            viewHolder.tvCategory = convertView.findViewById(R.id.tvCategory);
            viewHolder.ivCategoryIcon = convertView.findViewById(R.id.ivCategoryIcon); // Add this

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set data
        viewHolder.tvEventName.setText(event.getEventName());
        viewHolder.tvEventDateTime.setText(event.getEventDateTime());
        viewHolder.tvVenue.setText(event.getVenue());
        viewHolder.tvParticipants.setText(
                String.format("%d/%d", event.getCurrentRegistrations(), event.getCapacity())
        );
        viewHolder.tvEventStatus.setText(event.getEventStatus());
        viewHolder.tvCategory.setText(event.getEventCategory());

        // Set progress bar
        int progress = event.getCapacity() > 0 ?
                (int) ((double) event.getCurrentRegistrations() / event.getCapacity() * 100) : 0;
        viewHolder.progressBar.setProgress(progress);

        // Set status color
        int statusColor = getStatusColor(event.getEventStatus());
        viewHolder.tvEventStatus.setTextColor(statusColor);

        // Set category icon
        setCategoryIcon(viewHolder.ivCategoryIcon, event.getEventCategory());

        // Apply animation
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.grid_item_enter : R.anim.grid_item_enter);
        convertView.startAnimation(animation);
        lastPosition = position;

        return convertView;
    }

    // Add this method to set category icons
    private void setCategoryIcon(ImageView imageView, String category) {
        switch (category) {
            case "Cultural":
                imageView.setImageResource(R.drawable.ic_category_cultural);
                imageView.setBackgroundColor(context.getColor(R.color.cultural));
                break;
            case "Sports":
                imageView.setImageResource(R.drawable.ic_category_sports);
                imageView.setBackgroundColor(context.getColor(R.color.sports));
                break;
            case "Educational":
                imageView.setImageResource(R.drawable.ic_category_educational);
                imageView.setBackgroundColor(context.getColor(R.color.educational));
                break;
            case "Community Service":
                imageView.setImageResource(R.drawable.ic_category_community);
                imageView.setBackgroundColor(context.getColor(R.color.community_service));
                break;
            default:
                imageView.setImageResource(R.drawable.ic_event);
                imageView.setBackgroundColor(context.getColor(R.color.primary));
        }

        // Make the background circular
        imageView.setBackgroundResource(R.drawable.circle_background);
    }

    private int getStatusColor(String status) {
        switch (status) {
            case "Active":
                return context.getColor(R.color.success);
            case "Full":
                return context.getColor(R.color.warning);
            case "Cancelled":
                return context.getColor(R.color.error);
            case "Completed":
                return context.getColor(R.color.secondary);
            default:
                return context.getColor(R.color.secondary);
        }
    }

    private static class ViewHolder {
        MaterialCardView cardView;
        TextView tvEventName;
        TextView tvEventDateTime;
        TextView tvVenue;
        TextView tvParticipants;
        TextView tvEventStatus;
        TextView tvCategory;
        ProgressBar progressBar;
        ImageView ivCategoryIcon; // Add this
    }
}