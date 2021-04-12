package com.example.foodinventorydemo;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.foodinventorydemo.model.ProductUnitData;
import com.example.foodinventorydemo.utils.ResourceResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookupCodeService {
    private final String TAG = "PRODUCTLOOKUP";

    private String ACCESS_TOKEN_1 = "ea99924fbe34418fbcad9003f9718df3";
    private String ACCESS_TOKEN_2 = "f14aeb3d11dc46b58b52c50e13a04ac3";
    private String ACCESS_TOKEN = ACCESS_TOKEN_1;

    public void fetchProductData(String c, final ResourceResponseHandler<ProductUnitData> handler) {
        final String code = c;

        String reqUrl = "https://api.apigenius.io/products/lookup?upc="+code;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, reqUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i(TAG, response.toString());

                try {
                    if (response.getInt("status") == 200)
                        handler.handleRes(createProductDataFromResponse(response));
                    else if (response.getInt("status") == 404) {
                        throw new NotFoundException(code);
                    }
                    else if (response.has("message"))
                        throw new Exception(response.getString("message").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.handleError(e);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse res = error.networkResponse;
                String resJson = "";
                try {
                    resJson = new String(res.data, HttpHeaderParser.parseCharset(res.headers));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Log.e(TAG,""+error);
                Log.e(TAG,resJson);

                if (res.statusCode == 403) {
                    Log.e(TAG,"Token failed: "+ACCESS_TOKEN);
                    if (ACCESS_TOKEN == ACCESS_TOKEN_1) {
                        ACCESS_TOKEN = ACCESS_TOKEN_2;
                        fetchProductData(code,handler);
                    }
                    else handler.handleError(new Exception("Can not fetch data at this time!"));
                }

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ApiGenius_API_Key", ACCESS_TOKEN);
                return params;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    protected ProductUnitData createProductDataFromResponse(JSONObject res) {
        String code = stringFromJSON(res, "identifier");
        JSONObject data = objectFromJSON(res,"items");
        String name = stringFromJSON(data, "title");
        String brand = stringFromJSON(data, "brand");
        String category = stringFromJSON(data, "category");
        String description = stringFromJSON(data, "description");
        String weight = stringFromJSON(data, "weight");

        JSONArray imagesJson = arrayFromJSON(data, "images");
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < imagesJson.length(); i++) {
            try {
                imageUrls.add((String)imagesJson.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return new ProductUnitData(code, name, brand, description, null, category, weight, imageUrls, 1);
    }

    private String stringFromJSON(JSONObject data, String key) {
        try {
            return data.has(key) ? data.getString(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject objectFromJSON(JSONObject data, String key) {
        try {
            return data.has(key) ? data.getJSONObject(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray arrayFromJSON(JSONObject data, String key) {
        try {
            return data.has(key) ? data.getJSONArray(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class NotFoundException extends Exception {
        public final String code;
        public NotFoundException(String code) {
            super("Could not locate product: "+code);
            this.code = code;
        }
    }

}
