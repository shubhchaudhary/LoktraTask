package com.github.net;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ServiceManager {

    private Context mContext;
    private ServiceCallBacks mServiceCallBacks;
    private Activity activity;

    public ServiceManager(Context mContext, ServiceCallBacks callBacks) {
        super();
        this.mContext = mContext;
        this.mServiceCallBacks = callBacks;
    }

    public ServiceManager(Context mContext, ServiceCallBacks callBacks,
                          Activity activity) {
        super();
        this.mContext = mContext;
        this.mServiceCallBacks = callBacks;
        this.activity = activity;
    }

    public void getDetails(String token ) {
        if (!ConnectionChecker.selfLearnig(mContext)) {
            Toast.makeText(mContext, "Internet is not available, Please check",
                    Toast.LENGTH_LONG).show();
            return;
        }


        VolleyTask submitTask = new VolleyTask(mContext, mServiceCallBacks,ServiceCallBacks.GITHUB_CALL);
        submitTask.setShowProgress(true);

        submitTask.dataGET("https://api.github.com/repos/rails/rails/commits?access_token="+token);
    }

}