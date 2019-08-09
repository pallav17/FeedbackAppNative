package com.pallav.feedbacknative.Util;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;


/**
 * Created by abc on 27-Feb-16.
 */
public class Services {

    public interface webserviceAsync  {
        void getResponse(String url, JSONObject result, Object status,
                         webcallid callId);
    }
    public static enum webcallid {
        GET_EMP
    }
    boolean IshowProgress = true;
    ProgressDialog process = null;
    webserviceAsync listener;
    Context context;

    public <T> Services(Context context, T contxt) {
        if (contxt != null)
            this.listener = (webserviceAsync) contxt;
        else if (context != null)
            this.listener = (webserviceAsync) context;
        this.context = context;
        process = new ProgressDialog(this.context);

        process.setCanceledOnTouchOutside(false);
        process.setCancelable(false);
    }

    public void isShowProgress(boolean IshowProgress) {
        this.IshowProgress = IshowProgress;
    }
/**this method for post ok*/
    public void sendRequest(final String Url,
                            final Map<String, String> params,
                            JSONObject jsonObject,
                            final Map<String, String> header,
                            final webcallid callId) {


        RequestQueue requestQueue;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            HttpStack stack = null;
            try {
                stack = new HurlStack(null, new TLSSocketFactory());
            } catch (KeyManagementException e) {
                e.printStackTrace();
                stack = new HurlStack();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                stack = new HurlStack();
            }
            requestQueue = Volley.newRequestQueue(context, stack);
        } else {

        }*/
        requestQueue = Volley.newRequestQueue(context);
        if (IshowProgress)
            process.show();

        Response.Listener Relistener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (process != null && process.isShowing())
                    process.dismiss();
                if (listener != null)
                    listener.getResponse(Url, response, null, callId);
            }
        };

        Response.ErrorListener Erlistener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (process != null && process.isShowing())
                    process.dismiss();
                if (listener != null)
                    listener.getResponse(Url, null, null, callId);
            }
        };

        JsonObjectRequest jsonObjReq;
        if(header!=null) {
            jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    Url, jsonObject,
                    Relistener, Erlistener) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    return header;
                }

            };
        }else{
            jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    Url, jsonObject,
                    Relistener, Erlistener) {

            };
        }

        requestQueue.add(jsonObjReq);


    }

    /*this is for fragment*/
    /*HomeFragment fragment;
    public <T> Services(HomeFragment fragment, Context context) {
        if (context != null)
            this.listener = (webserviceAsync) fragment;
        this.fragment = fragment;
        this.context = context;
        process = new ProgressDialog(this.context);

        process.setCanceledOnTouchOutside(false);
        process.setCancelable(false);
    }*/
    public void sendRequestFromFragment(final String Url, final Map<String, String> params, JSONObject jsonObject, final Map<String, String> header,
                            final webcallid callId) {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        if (IshowProgress)
            process.show();

        Response.Listener Relistener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (process != null && process.isShowing())
                    process.dismiss();
                if (listener != null)
                    listener.getResponse(Url, response, null, callId);
            }
        };

        Response.ErrorListener Erlistener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (process != null && process.isShowing())
                    process.dismiss();
                if (listener != null)
                    listener.getResponse(Url, null, null, callId);
            }
        };

        JsonObjectRequest jsonObjReq;
        if(header!=null) {
            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Url, jsonObject,
                    Relistener, Erlistener) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    return header;
                }

            };
        }else{
            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Url, jsonObject,
                    Relistener, Erlistener) {

            };
        }

        requestQueue.add(jsonObjReq);
    }

/*this is for get*/
    public void Getcallback(final String Url, final Map<String, String> header, final webcallid callId) {
        if (IshowProgress)
            process.show();

        RequestQueue requestQueue;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            HttpStack stack = null;
            try {
                stack = new HurlStack(null, new TLSSocketFactory());
            } catch (KeyManagementException e) {
                e.printStackTrace();
                stack = new HurlStack();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                stack = new HurlStack();
            }
            requestQueue = Volley.newRequestQueue(context, stack);
        } else {
            requestQueue = Volley.newRequestQueue(context);
        }*/
        requestQueue = Volley.newRequestQueue(context);
        if (IshowProgress)
            process.show();

        Response.Listener Relistener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (process != null && process.isShowing())
                    process.dismiss();
                if (listener != null)
                    listener.getResponse(Url, response, null, callId);
            }
        };

        Response.ErrorListener Erlistener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (process != null && process.isShowing())
                    process.dismiss();
                if (listener != null)
                    listener.getResponse(Url, null, null, callId);
            }
        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Url, null,
                Relistener, Erlistener) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return header;
            }
        };


        requestQueue.add(jsonObjReq);


    }
}
