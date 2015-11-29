package com.defoliate.imagerecievefromserver;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by defoliate on 29-11-2015.
 */
public class CustomImageAdapter extends BaseAdapter
{
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<ImageClass> imageItems;

    public CustomImageAdapter (Activity activity, List<ImageClass> imageItems)
    {
        this.activity = activity;
        this.imageItems = imageItems;
    }

    @Override
    public int getCount ()
    {
        return imageItems.size();
    }

    @Override
    public Object getItem (int position)
    {
        return imageItems.get(position);
    }

    @Override
    public long getItemId (int position)
    {
        return position;
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if(imageLoader == null)
            imageLoader = MyApplication.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        // getting movie data for the row
        ImageClass m = imageItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        title.setText(m.getTitle());

        return convertView;
    }
}
