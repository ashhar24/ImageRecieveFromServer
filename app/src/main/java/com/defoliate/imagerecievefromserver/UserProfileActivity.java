package com.defoliate.imagerecievefromserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/*
 * Created by defoliate on 13-12-2015.
 */

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.imageView);
        imageView.setImageUrl("http://www.mtv.com/news/wp-content/uploads/splash/2012/05/fan_bingbing.jpg", imageLoader);

        checkfriend("Naruto");
        findViewById(R.id.baccept).setOnClickListener(this);
        findViewById(R.id.breject).setOnClickListener(this);
    }

    private void checkfriend (String currentuser)
    {
        StringRequest strreq = new StringRequest(
                Request.Method.POST,
                "url",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse (String response)
                    {
                        if(true)
                        {
                            findViewById(R.id.baccept).setVisibility(View.GONE);
                            findViewById(R.id.breject).setVisibility(View.GONE);
                        }

                        else
                        {
                            findViewById(R.id.baccept).setVisibility(View.VISIBLE);
                            findViewById(R.id.breject).setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse (VolleyError error)
                    {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {
                return super.getParams();
            }
        };
        MyApplication.getInstance().addToRequestQueue(strreq);
    }

    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {
            case R.id.baccept:
                request(1);
                break;

            case R.id.breject:
                request(0);
                break;
        }

    }

    private void request (int flag)
    {
        StringRequest strreq = new StringRequest(
                Request.Method.POST,
                "url",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse (String response)
                    {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse (VolleyError error)
                    {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {
                return super.getParams();
            }
        };
        MyApplication.getInstance().addToRequestQueue(strreq);
    }
}
