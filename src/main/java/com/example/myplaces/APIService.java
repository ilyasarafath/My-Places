package com.example.myplaces;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class APIService extends  Volley {

    ProviderInterface providerInterface;
    RequestQueue queue;

    public APIService(ProviderInterface providerInterface) {
        this.providerInterface = providerInterface;
        this.queue = queue;
    }

    public  void  getRequest(Context context , String url){
      if(queue == null)
            queue = Volley.newRequestQueue(context);

      StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      try{
                          providerInterface.onResponse(response);
                      }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                  }
              }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
                try{
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(context, "No network available", Toast.LENGTH_SHORT).show();
                    }
                    else
                        providerInterface.onErrorResponse(error);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
          }
      });

// Add the request to the RequestQueue.
      queue.add(stringRequest);


  }


}
