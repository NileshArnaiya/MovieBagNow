package com.greedygame.moviebagnow.central;

import androidx.lifecycle.MutableLiveData;

import com.greedygame.moviebagnow.models.MovieModel;
import com.greedygame.moviebagnow.models.MovieResponse;
import com.greedygame.moviebagnow.models.Resource;
import com.greedygame.moviebagnow.utility.Application;
import com.greedygame.moviebagnow.utility.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnMovieDetails {

    public MutableLiveData<Resource<MovieModel>> getMovieDetails(String movieId) {
        final MutableLiveData<Resource<MovieModel>> moviewDetails = new MutableLiveData<>();

        Call<MovieModel> call  = Application.getInstance().getNetworkService()
                .getMovieDetails( movieId, Constants.API_KEY);

        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                MovieModel body = response.body();
                if (body != null) {
                    moviewDetails.setValue(Resource.success(body));
                } else {
                    moviewDetails.setValue(Resource.<MovieModel>error("No Data", null));
                }
            }
            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                moviewDetails.setValue(Resource.<MovieModel>error(t.getMessage(),null));
            }
        });
        return moviewDetails;
    }

    public MutableLiveData<Resource<MovieResponse>> getSimilarMovieResult(String movieId) {
        final MutableLiveData<Resource<MovieResponse>> similarMovie = new MutableLiveData<>();

        Call<MovieResponse> call  = Application.getInstance().getNetworkService()
                .getSimilarMovie( movieId, Constants.API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse body = response.body();
                if (body != null) {
                    similarMovie.setValue(Resource.success(body));
                } else {
                    similarMovie.setValue(Resource.<MovieResponse>error("No Data", null));
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                similarMovie.setValue(Resource.<MovieResponse>error(t.getMessage(),null));
            }
        });
        return similarMovie;
    }


}
