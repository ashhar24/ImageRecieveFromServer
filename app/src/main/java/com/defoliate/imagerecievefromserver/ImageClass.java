package com.defoliate.imagerecievefromserver;

/*
 * Created by defoliate on 29-11-2015.
 */
public class ImageClass
{

    private String title, thumbnailUrl;

    public ImageClass (String name, String thumbnailUrl)
    {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public ImageClass ()
    {
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String name)
    {
        this.title = name;
    }

    public String getThumbnailUrl ()
    {
        return thumbnailUrl;
    }

    public void setThumbnailUrl (String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
    }
}
