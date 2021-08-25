package com.greedygame.moviebagnow.central;

import androidx.lifecycle.MutableLiveData;

import com.greedygame.moviebagnow.models.Resource;
import com.greedygame.moviebagnow.models.ReviewResponse;
import com.greedygame.moviebagnow.utility.Application;
import com.greedygame.moviebagnow.utility.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnReviews {

    public MutableLiveData<Resource<ReviewResponse>> getReview(String movieId) {
        final MutableLiveData<Resource<ReviewResponse>> ReviewResponse = new MutableLiveData<>();

        Call<ReviewResponse> call  = Application.getInstance().getNetworkService()
                .getReview( movieId, Constants.API_KEY);

        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                ReviewResponse body = response.body();
                if (body != null) {
                    ReviewResponse.setValue(Resource.success(body));
                } else {
                    ReviewResponse.setValue(Resource.<ReviewResponse>error("No Data", null));
                }
            }
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                ReviewResponse.setValue(Resource.<ReviewResponse>error(t.getMessage(),null));
            }
        });
        return ReviewResponse;
    }

}
