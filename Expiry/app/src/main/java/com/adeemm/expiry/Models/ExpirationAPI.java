package com.adeemm.expiry.Models;


import android.app.Activity;
import android.net.Uri;
import android.util.Log;

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
    private final Consumer<Map<String, Uri>> callback;
    private final Activity instance;
    private final RequestQueue rq;

    public ExpirationAPI(Consumer<Map<String, Uri>> call, Activity obj) {
        this.rq = Volley.newRequestQueue(obj);
        this.callback = call;
        this.instance = obj;
    }

    public void getSearchResults(String query) {
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
                                results.put(anchor.text(), Uri.parse(anchor.attr("href")));
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

    public void getExpiration(Uri uri) {
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document doc = Jsoup.parse(response);
                    Elements nodes = doc.select(".food-storage-container > .food-inside");
                    String expiration = nodes.get(0).select(".food-storage-right > .red-image > span").text();
                    Log.e("Expiration:", expiration);
                    // TODO: callback
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
