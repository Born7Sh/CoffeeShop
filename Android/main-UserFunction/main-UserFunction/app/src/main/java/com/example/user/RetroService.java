package com.example.user;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetroService {
    @GET("/cafe/")
    Call<List<ComCafeData>> requestAllCafe();

    @GET("/cafe/{id}/")
    Call<ComCafeData> requestCafe(@Path("id") int id);

    @FormUrlEncoded
    @POST("/check/")
    Call<Check> provideCheck(
            @Query("format") String json,
            @Field("uid") int uno,
            @Field("nickname") String nickname,
            @Field("start_date") String start_date,
            @Field("start_time") String start_time,
            @Field("end_date") String end_date,
            @Field("end_time") String end_time,
            @Field("sno") int sno,
            @Field("cno") int cno
    );

    @GET("/check/check")
    Call<List<Check>> requestUserCheck(@Query("uid") int uid);

    @GET("/cafe/{id}/")
    Call<ComCafeData> getFavoriteCafe(@Path("id") int id);

    @GET("/favorite/favorite")
    Call<List<ComFavorite>> getFavorite(@Query("uid") int uid);

    @GET("/favorite/favorite")
    Call<List<ComFavorite>> getUserCafeFavorite(@QueryMap Map<String,Integer> option);

    @FormUrlEncoded
    @POST("/favorite/")
    Call<ComFavorite> provideFavorite(
            @Query("format") String json,
            @Field("cno") int cno,
            @Field("uid") int uid
    );
    @DELETE("/favorite/{id}/")
    Call<Void> deleteFavorite(@Path("id") int id);

    @Multipart
    @POST("/review/")
    Call<ResponseBody> provideReviewP(
            @Query("format") String json,
            @Part("uid") int uid,
            @Part("review") String review,
            @Part("star") int star,
            @Part MultipartBody.Part picture,
            @Part ("cno") int cno
            //@Part("picture\"; filename=\"pp.png\" ") RequestBody picture
    );

    @Multipart
    @POST("/review/")
    Call<ResponseBody> provideReviewNP(
        @Query("format") String json,
        @Part("uid") int uid,
        @Part("review") String review,
        @Part("star") int star,
        @Part("cno") int cno
        );

    @GET("/review/review")
    Call<List<Review>> getReview(
            @Query("cno") int cno
    );

    @FormUrlEncoded
    @POST("/booking1/")
    Call<Booking1> provideBooking1(
    @Query("format") String json,
    @Field("cno") int cno,
    @Field("sno") int sno,
    @Field("t0000") boolean t0000,
    @Field("t0030") boolean t0030,
    @Field("t0100") boolean t0100,
    @Field("t0130") boolean t0130,
    @Field("t0200") boolean t0200,
    @Field("t0230") boolean t0230,
    @Field("t0300") boolean t0300,
    @Field("t0330") boolean t0330,
    @Field("t0400") boolean t0400,
    @Field("t0430") boolean t0430,
    @Field("t0500") boolean t0500,
    @Field("t0530") boolean t0530,
    @Field("t0600") boolean t0600,
    @Field("t0630") boolean t0630,
    @Field("t0700") boolean t0700,
    @Field("t0730") boolean t0730,
    @Field("t0800") boolean t0800,
    @Field("t0830") boolean t0830
    );

    @FormUrlEncoded
    @POST("/booking2/")
    Call<Booking2> provideBooking2(
            @Query("format") String json,
            @Field("cno") int cno,
            @Field("sno") int sno,
            @Field("t0900") boolean t0900,
            @Field("t0930") boolean t0930,
            @Field("t1000") boolean t1000,
            @Field("t1030") boolean t1030,
            @Field("t1100") boolean t1100,
            @Field("t1130") boolean t1130,
            @Field("t1200") boolean t1200,
            @Field("t1230") boolean t1230,
            @Field("t1300") boolean t1300,
            @Field("t1330") boolean t1330,
            @Field("t1400") boolean t1400,
            @Field("t1430") boolean t1430,
            @Field("t1500") boolean t1500,
            @Field("t1530") boolean t1530,
            @Field("t1600") boolean t1600,
            @Field("t1630") boolean t1630
    );

    @FormUrlEncoded
    @POST("/booking3/")
    Call<Booking3> provideBooking3(
            @Query("format") String json,
            @Field("cno") int cno,
            @Field("sno") int sno,
            @Field("t1700") boolean t1700,
            @Field("t1730") boolean t1730,
            @Field("t1800") boolean t1800,
            @Field("t1830") boolean t1830,
            @Field("t1900") boolean t1900,
            @Field("t1930") boolean t1930,
            @Field("t2000") boolean t2000,
            @Field("t2030") boolean t2030,
            @Field("t2100") boolean t2100,
            @Field("t2130") boolean t2130,
            @Field("t2200") boolean t2200,
            @Field("t2230") boolean t2230,
            @Field("t2300") boolean t2300,
            @Field("t2330") boolean t2330
    );

    @FormUrlEncoded
    @POST("/time/")
    Call<Time> requestTime(
            @Field("time") String time,
            @Field("uid") String uid
    );


    @GET("/img/img")
    Call<List<CafeImage>> getCafeImages(
            @Query("cno") int cno
    );

    @GET("/booking1/booking1")
    Call<List<Booking1>> getBook1(
            @Query("cno") int cno
    );

    @GET("/booking2/booking2")
    Call<List<Booking2>> getBook2(
            @Query("cno") int cno
    );

    @GET("/booking3/booking3")
    Call<List<Booking3>> getBook3(
            @Query("cno") int cno
    );

    @DELETE("/check/{id}/")
    Call<Void> DeleteCheck(
            @Path("id") int id
    );

    @DELETE("/booking1/{id}/")
    Call<Void> DeleteBook1(
            @Path("id") int id

    );

    @DELETE("/booking2/{id}/")
    Call<Void> DeleteBook2(
            @Path("id") int id
    );

    @DELETE("/booking3/{id}/")
    Call<Void> DeleteBook3(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("/app_login/")
    Call<Login> requestLogin(
            @Field("userid") String userid,
            @Field("userpw") String userpw
    );
}
