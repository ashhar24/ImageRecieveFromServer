package com.defoliate.imagerecievefromserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static String TAG = MainActivity.class.getSimpleName();
    private List<ImageClass> imageList = new ArrayList<>();

    private RecyclerView recyclerView;
    private CustomImageAdapter adapter;

    private Button bfriendlist, bpendingfriendlist;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new CustomImageAdapter(imageList);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        bfriendlist = (Button) findViewById(R.id.bfriendlist);
        bpendingfriendlist = (Button) findViewById(R.id.bpendingfriendlist);

        bfriendlist.setOnClickListener(this);
        bpendingfriendlist.setOnClickListener(this);
    }

    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {
            case R.id.bfriendlist:
                requestfriendlist("Naruto");
                break;

            case R.id.bpendingfriendlist:
                requestpendinglist("Naruto");
                break;
        }
    }

    private void requestfriendlist (String profilename)
    {
        imageList.clear();
        adapter.notifyDataSetChanged();
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
                            Log.d(TAG + "friends", response);
                            JSONArray jResult = responseObj.getJSONArray("req_users");
                            ((TextView) findViewById(R.id.tvprofilejson)).setText(jResult.toString());
                            for(int i = 0; i < jResult.length(); i++)
                            {
                                JSONObject jresponse = jResult.getJSONObject(i);
                                Toast.makeText(MainActivity.this, jresponse.toString(), Toast.LENGTH_SHORT).show();
                                ImageClass img = new ImageClass();
                                img.setThumbnailUrl(jresponse.getString("url"));
                                img.setTitle(jresponse.getString("uid"));
                                imageList.add(img);
                                adapter.notifyDataSetChanged();
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
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    private void requestpendinglist (String profilename)
    {
        imageList.clear();
        adapter.notifyDataSetChanged();
        String uri = String.format(Config.URL_REQUEST_PENDING_FRIENDS + "?current_user=%1$s", profilename);
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
                            Log.d(TAG + "friends", response);
                            JSONArray jResult = responseObj.getJSONArray("req_users");
                            ((TextView) findViewById(R.id.tvprofilejson)).setText(jResult.toString());
                            for(int i = 0; i < jResult.length(); i++)
                            {
                                JSONObject jresponse = jResult.getJSONObject(i);
                                Toast.makeText(MainActivity.this, jresponse.toString(), Toast.LENGTH_SHORT).show();
                                ImageClass img = new ImageClass();
                                img.setThumbnailUrl(jresponse.getString("url"));
                                img.setTitle(jresponse.getString("uid"));
                                imageList.add(img);
                                adapter.notifyDataSetChanged();
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
        MyApplication.getInstance().addToRequestQueue(strReq);
    }
}