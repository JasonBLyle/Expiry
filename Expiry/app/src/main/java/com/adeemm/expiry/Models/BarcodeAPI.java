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


/**
 * This is the class that calls the openfoodfacts api with the barcode from the capture class
 * to find out what item was scanned.
 */
public class BarcodeAPI {
    private final String baseURL = "https://world.openfoodfacts.org/api/v0/product/";
    private final Activity instance;
    private final RequestQueue rq;

    public BarcodeAPI(Activity obj) {
        this.rq = Volley.newRequestQueue(obj);
        this.instance = obj;
    }

    /**
     * Pre:UPC is the string with the UPC of the item
     * @param UPC is the barcode scanned in
     * @return the url for the product scanned
     */
    public Uri getURL(String UPC) {
        return Uri.parse(baseURL + UPC + ".json");
    }

    /**
     * Pre: url is the url for the product we are looking up
     * Post: the response is recorded, and the given callback is called
     */
    public void getData(Uri url, Consumer<JSONObject> callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.accept(response);
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
