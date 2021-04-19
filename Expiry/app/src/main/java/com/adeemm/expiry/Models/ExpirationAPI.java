package com.adeemm.expiry.Models;


import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.adeemm.expiry.Utils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class ExpirationAPI {
    private final String searchURL = "https://stilltasty.com/searchitems/search";
    private final Activity instance;
    private final RequestQueue rq;

    /**
     * This is the constructor for the ExpirationAPI class
     */
    public ExpirationAPI(Activity obj) {
        this.rq = Volley.newRequestQueue(obj);
        this.instance = obj;
    }

    /**
     * Pre:query contains a name.
     * callback is a given function to pass api response data to after the network call is finished
     */
    public void getSearchResults(String query, Consumer<Map<String, Uri>> callback) {
        rq.getCache().clear();

        StringRequest request = new StringRequest
                (Request.Method.POST, searchURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Map<String, Uri> results = new HashMap<>();

                        Document doc = Jsoup.parse(response);
                        Elements categories = doc.select(".search-border > .how-long");

                        for (Element c: categories) {
                            Elements nodes = c.select(".search-list > p");

                            for (Element n : nodes) {
                                Element anchor = n.child(0);
                                results.put(Utils.convertToTitleCase(anchor.text()), Uri.parse(anchor.attr("href")));
                            }
                        }

                        callback.accept(results);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: handle error
                    }
                }
        )

        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("_method", "POST");
                params.put("search", query);
                return params;
            }
        };

        rq.add(request);
    }

    /**
     * this function receives the response from the api and parses it into somthing we can use
     */
    public void getExpiration(Uri uri, Consumer<String> callback) {
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document doc = Jsoup.parse(response);
                    Elements nodes = doc.select(".food-storage-container > .food-inside");
                    String expiration = nodes.get(0).select(".food-storage-right > .red-image > span").text();
                    callback.accept(expiration);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: handle error
                }
            }
        );

        rq.add(request);
    }
}
