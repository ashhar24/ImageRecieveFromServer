package com.defoliate.imagerecievefromserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
{
    private static String TAG = MainActivity.class.getSimpleName();
    public String[] imagelinks = new String[100];


    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestimagelink("Ashhar");
        //getimages(imagelinks);
    }

    private void requestimagelink (final String profilename)
    {
        String uri = String.format(Config.URL_REQUEST_IMAGE + "?userid=%1$s", profilename);
        Log.d(TAG, "uri" + uri);
        StringRequest strReq = new StringRequest(
                Request.Method.GET,
                uri,
                new Response.Listener<String>()
                {
                    //response from the server
                    @Override
                    public void onResponse (String response)
                    {
                        try
                        {
                            JSONObject responseObj = new JSONObject(response);
                            ((TextView) findViewById(R.id.tvprofilejson)).setText(response);
                            Log.d(TAG,response);
                            JSONArray jResult = responseObj.getJSONArray("photos");
                            for(int i = 0; i < jResult.length(); i++)
                            {
                                JSONObject jresponse = jResult.getJSONObject(i);
                                imagelinks[i] = jresponse.getString("name");
                            }
                            getimages(imagelinks);
                        }
                        catch(JSONException e)
                        {
                            Log.d(TAG, "error" + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse (VolleyError error)
                    {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    private void getimages (String[] imagelinks)
    {
        for(int i = 0; i < imagelinks.length; i++)
        {
            String url = imagelinks[i];
            //String url = "http://192.168.0.108:8000/media/files/users/Ashhar/IMG_20151128_201501.jpg";
            if (url!=null)
            {
                Log.d(TAG,url);
                ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

                ((NetworkImageView)findViewById(R.id.networkImageView)).setDefaultImageResId(R.drawable.ic_launcher);
                ((NetworkImageView)findViewById(R.id.networkImageView)).setImageUrl(url, imageLoader);
            }

        }
    }
}
