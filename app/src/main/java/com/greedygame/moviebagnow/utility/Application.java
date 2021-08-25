package com.greedygame.moviebagnow.utility;


import com.greedygame.moviebagnow.Retrofit.NetworkClient;
import com.greedygame.moviebagnow.Retrofit.NetworkService;

public class Application extends android.app.Application {

    private static Application mInstance;
    private NetworkService mService;

    public static synchronized Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mService = NetworkClient.getClient().create(NetworkService.class);
    }

    public NetworkService getNetworkService() {
        return mService;
    }

}
