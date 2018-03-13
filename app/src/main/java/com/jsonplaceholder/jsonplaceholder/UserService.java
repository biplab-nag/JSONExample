package com.jsonplaceholder.jsonplaceholder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by biplab on 13-Mar-18.
 */

public interface UserService {

    @GET("users")
    Call<List<UserResponse>> getUserResponse();
}
