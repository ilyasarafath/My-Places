package com.example.myplaces;

import com.android.volley.VolleyError;

public interface ProviderInterface {
    void onResponse(String response);
    void onErrorResponse(VolleyError error);
}
