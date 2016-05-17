/**
 *
 */
package com.github.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.application.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyTask {

    private Context mContext;
    private boolean isShowProgress = false;
    private ServiceCallBacks mCallBacks;
    private int mCallerType;
    private ProgressDialog mProgressDialog;
    private String TAG = "GIT_TASK";

    public VolleyTask(Context mContext, ServiceCallBacks mCallBacks,
                      int mCallerType) {
        super();
        this.mContext = mContext;
        this.mCallBacks = mCallBacks;
        this.mCallerType = mCallerType;
    }


    /**
     * @return the isShowProgress
     */
    public boolean isShowProgress() {
        return isShowProgress;
    }

    /**
     * @param isShowProgress the isShowProgress to set
     */
    public void setShowProgress(boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
        if (isShowProgress && mProgressDialog == null) {
            //mProgressDialog =  new ProgressDialog(mContext, R.style.Theme_MyDialog);
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setInverseBackgroundForced(true);
            mProgressDialog.setMessage("Please wait...");
            //mProgressDialog.setIndeterminate(true);
            //mProgressDialog = new ProgressDialog(mContext);

            mProgressDialog.setCancelable(false);
        }
    }


    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }


    public void setProgressDialog(ProgressDialog mProgressDialog) {
        this.mProgressDialog = mProgressDialog;
    }


    private void showProgressDialog() {
        // TODO Auto-generated method stub
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }

    }

    private void cancelProgressDialog() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
        }
    }

    /**
     * to call a service with post parameters (Json Array)
     */
    public void postData(String url) {

        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        cancelProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                cancelProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, "GIT_TASK");
    }

    /**
     * to call a service with post parameters (Json object)
     */
    public void getData(String url, JSONObject jsonObject) {
        showProgressDialog();
System.out.println("pppppppppppp"+jsonObject.toString());
        //replace the method type(request method) as per the requirement (POST/GET)
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        mCallBacks.onRequestComplete(response.toString(), mCallerType);

                        cancelProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mCallBacks.onError(error.toString(), mCallerType);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("VOLLEY EXCEPTION ", "Time out error");
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    Log.i("VOLLEY EXCEPTION ", "Server error");

                } else if (error instanceof NetworkError) {
                    Log.i("VOLLEY EXCEPTION ", "Network error");
                } else if (error instanceof ParseError) {
                    Log.i("VOLLEY EXCEPTION ", "Parse error");
                } else {
                    Log.i("VOLLEY EXCEPTION ", "GENERIC error");
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                cancelProgressDialog();
            }
        }) {
            //add the extra header here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "GIT_TASK");
    }

    /**
     * to call a service without post parameters
     */


    public void getData(String url) {
        showProgressDialog();
        //replace the method type(request method) as per the requirement (POST/GET)
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("GET DATA URL ", response.toString());
                        mCallBacks.onRequestComplete(response.toString(), mCallerType);

                        cancelProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("VOLLEY EXCEPTION ", "Time out error");
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    Log.i("VOLLEY EXCEPTION ", "Server error");

                } else if (error instanceof NetworkError) {
                    Log.i("VOLLEY EXCEPTION ", "Network error");
                } else if (error instanceof ParseError) {
                    Log.i("VOLLEY EXCEPTION ", "Parse error");
                } else {
                    Log.i("VOLLEY EXCEPTION ", "GENERIC error");
                }
                VolleyLog.d("PPPPPPP ", "Error: " + error.getMessage());
                cancelProgressDialog();


            }
        }) {

            //add the extra header here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json ");
                
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "GIT_TASK");
    }

    public void dataGET(String url) {
        showProgressDialog();
        //replace the method type(request method) as per the requirement (POST/GET)
        System.out.println("Url");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mCallBacks.onRequestComplete(response.toString(), mCallerType);
                        cancelProgressDialog();                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("VOLLEY EXCEPTION ", "Time out error");
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    Log.i("VOLLEY EXCEPTION ", "Server error");

                } else if (error instanceof NetworkError) {
                    Log.i("VOLLEY EXCEPTION ", "Network error");
                } else if (error instanceof ParseError) {
                    Log.i("VOLLEY EXCEPTION ", "Parse error");
                } else {
                    Log.i("VOLLEY EXCEPTION ", "GENERIC error");
                }
                VolleyLog.d("PPPPPPP ", "Error: " + error.getMessage());
                cancelProgressDialog();            }
        });
       /* StringRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("GET DATA URL ", response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {



            }
        }) {

            //add the extra header here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };*/
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, "GIT_TASK");
    }



}

