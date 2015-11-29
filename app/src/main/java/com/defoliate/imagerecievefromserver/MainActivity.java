package com.defoliate.imagerecievefromserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    private static String TAG = MainActivity.class.getSimpleName();
    public List<String> friendlist = new ArrayList<>();
    //public List<String> imagelinks = new ArrayList<>();
    private List<ImageClass> imageList = new ArrayList<ImageClass>();

    private ListView listView;
    private CustomImageAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomImageAdapter(this, imageList);
        listView.setAdapter(adapter);

        requestfriendlist("Naruto");
    }

    private void requestfriendlist (String profilename)
    {
        String uri = String.format(Config.URL_REQUEST_FRIENDS + "?current_user=%1$s", profilename);
        Log.d(TAG + "uri", uri);
        StringRequest strReq = new StringRequest(
                Request.Method.GET,
                uri,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse (String response)
                    {
                        try
                        {
                            JSONObject responseObj = new JSONObject(response);
                            Log.d(TAG+"friends", response);
                            JSONArray jResult = responseObj.getJSONArray("req_users");
                            Toast.makeText(MainActivity.this, jResult.toString(), Toast.LENGTH_SHORT).show();
                            for(int i = 0; i < jResult.length(); i++)
                            {
                                JSONObject jresponse = jResult.getJSONObject(i);
                                String profile = jresponse.getString("userid");
                                friendlist.add(profile);
                            }
                            for(int i=0 ;i<friendlist.size();i++)
                                requestimagelink(friendlist.get(i));
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
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    private void requestimagelink (final String profilename)
    {
        String uri = String.format(Config.URL_REQUEST_IMAGE + "?userid=%1$s", profilename);
        Toast.makeText(MainActivity.this, uri, Toast.LENGTH_SHORT).show();
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
                            Log.d(TAG, response);
                            JSONArray jResult = responseObj.getJSONArray("photos");
                            for(int i = 0; i < jResult.length(); i++)
                            {
                                JSONObject jresponse = jResult.getJSONObject(i);
                                ImageClass img = new ImageClass();
                                img.setThumbnailUrl(jresponse.getString("name"));
                                img.setTitle(profilename);
                                imageList.add(img);
                                //imagelinks.add(jresponse.getString("name"));
                            }
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
}

