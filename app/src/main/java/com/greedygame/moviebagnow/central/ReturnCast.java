package com.greedygame.moviebagnow.central;

import androidx.lifecycle.MutableLiveData;

import com.greedygame.moviebagnow.models.CastResponse;
import com.greedygame.moviebagnow.models.Resource;
import com.greedygame.moviebagnow.utility.Application;
import com.greedygame.moviebagnow.utility.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnCast {

    public MutableLiveData<Resource<CastResponse>> getCast(String movieId) {
        final MutableLiveData<Resource<CastResponse>> CastResponse = new MutableLiveData<>();

        Call<CastResponse> call  = Application.getInstance().getNetworkService()
                .getCredits( movieId, Constants.API_KEY);

        call.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                CastResponse body = response.body();
                if (body != null) {
                    CastResponse.setValue(Resource.success(body));
                } else {
                    CastResponse.setValue(Resource.<CastResponse>error("No Data", null));
                }
            }
            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                CastResponse.setValue(Resource.<CastResponse>error(t.getMessage(),null));
            }
        });
        return CastResponse;
    }

}
