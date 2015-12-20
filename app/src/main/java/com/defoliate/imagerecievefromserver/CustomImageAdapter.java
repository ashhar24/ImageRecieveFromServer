package com.defoliate.imagerecievefromserver;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/*
 * Created by defoliate on 29-11-2015.
 */

public class CustomImageAdapter extends RecyclerView.Adapter<CustomImageAdapter.PersonViewHolder>
{
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
    private List<ImageClass> imageItems;

    public CustomImageAdapter (List<ImageClass> imageItems)
    {
        this.imageItems = imageItems;
    }

    @Override
    public PersonViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder (PersonViewHolder holder, int position)
    {
        // getting movie data for the row
        if(imageLoader == null)
            imageLoader = MyApplication.getInstance().getImageLoader();
        ImageClass m = imageItems.get(position);

        // thumbnail image
        holder.thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        holder.personAge.setText(m.getTitle());
    }

    @Override
    public long getItemId (int position)
    {
        return position;
    }

    @Override
    public int getItemCount ()
    {
        return imageItems.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView personName, personAge;
        NetworkImageView thumbNail;

        PersonViewHolder (View itemView)
        {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.mcardview);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_age);
            thumbNail = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
