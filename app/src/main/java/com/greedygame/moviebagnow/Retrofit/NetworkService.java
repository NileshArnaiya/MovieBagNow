package com.greedygame.moviebagnow.Retrofit;



import com.greedygame.moviebagnow.models.CastResponse;
import com.greedygame.moviebagnow.models.MovieModel;
import com.greedygame.moviebagnow.models.MovieResponse;
import com.greedygame.moviebagnow.models.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @GET("3/movie/now_playing")
    Call<MovieResponse> getNowPlayingMovie(@Query("api_key") String apiKey);

    @GET("3/movie/popular")
    Call<MovieResponse> getPopularMovie(@Query("api_key") String apiKey);

    @GET("3/movie/{movie_id}")
    Call<MovieModel> getMovieDetails(@Path("movie_id") String movie_id, @Query("api_key") String apiKey);

    @GET("3/movie/{movie_id}/similar")
    Call<MovieResponse> getSimilarMovie(@Path("movie_id") String movie_id, @Query("api_key") String apiKey);

    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewResponse> getReview(@Path("movie_id") String movie_id, @Query("api_key") String apiKey);

    @GET("3/movie/{movie_id}/credits")
    Call<CastResponse> getCredits(@Path("movie_id") String movie_id, @Query("api_key") String apiKey);

}