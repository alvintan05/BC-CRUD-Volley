package com.alvin.crud_volley.util;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    private static final String TAG = "AppController";
    private static AppController instance;
    RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AppController getInstance() {
        return instance;
    }


    // untuk mengembalikan nilai dari data reques
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    // untuk menghandle request
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    // untuk menghandle request tanpa customize parameter
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    // untuk mengcancel request apabila terjadi sesuatu yg tidak diinginkan
    public void cancelAllRequest(Object req) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(req);
        }
    }

}
