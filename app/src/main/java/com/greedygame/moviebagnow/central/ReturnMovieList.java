package com.greedygame.moviebagnow.central;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.greedygame.moviebagnow.models.MovieResponse;
import com.greedygame.moviebagnow.models.Resource;
import com.greedygame.moviebagnow.utility.Application;
import com.greedygame.moviebagnow.utility.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnMovieList {

    public MutableLiveData<Resource<MovieResponse>> getNowPlayingMovie() {
        final MutableLiveData<Resource<MovieResponse>> movieResult = new MutableLiveData<>();

        Log.d("Checkh", String.valueOf(Application.getInstance()));
        Call<MovieResponse> call  = Application.getInstance().getNetworkService().getNowPlayingMovie(Constants.API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse body = response.body();
                if (body != null) {
                    movieResult.setValue(Resource.success(body));
                } else {
                    movieResult.setValue(Resource.<MovieResponse>error("No Data", null));
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movieResult.setValue(Resource.<MovieResponse>error(t.getMessage(),null));
            }
        });
        return movieResult;
    }

    public MutableLiveData<Resource<MovieResponse>> getPopularMovieResult() {
        final MutableLiveData<Resource<MovieResponse>> moviewResult = new MutableLiveData<>();

        Call<MovieResponse> call  = Application.getInstance().getNetworkService()
                .getPopularMovie( Constants.API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse body = response.body();

                if (body != null) {
                    moviewResult.setValue(Resource.success(body));
                } else {
                    moviewResult.setValue(Resource.<MovieResponse>error("No Data", null));
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                moviewResult.setValue(Resource.<MovieResponse>error(t.getMessage(),null));
            }
        });
        return moviewResult;
    }


}
