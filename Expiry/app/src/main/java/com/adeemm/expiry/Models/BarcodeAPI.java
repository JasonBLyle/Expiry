package com.adeemm.expiry.Models;

import android.app.Activity;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.function.Consumer;


public class BarcodeAPI {
    private final String baseURL = "https://world.openfoodfacts.org/api/v0/product/";
    private final Consumer<JSONObject> callback;
    private final Activity instance;
    private final RequestQueue rq;

    public BarcodeAPI(Consumer<JSONObject> call, Activity obj) {
        this.rq = Volley.newRequestQueue(obj);
        this.callback = call;
        this.instance = obj;
    }

    public Uri getURL(String UPC) {
        return Uri.parse(baseURL + UPC + ".json");
    }

    public void getData(Uri url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO: Handle response
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                }
        );

        rq.add(jsonObjectRequest);
    }
}
