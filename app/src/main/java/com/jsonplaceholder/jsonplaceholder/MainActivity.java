package com.jsonplaceholder.jsonplaceholder;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listview;
    private List<PostResponse> postDatalist;
    private List<UserResponse> userDatalist;
    private PostAdapter adapter;
    private UserService userService;
    private PostService postService;

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.RV);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        postService = retrofit.create(PostService.class);

        final Call<List<UserResponse>> userCall = userService.getUserResponse();
        Call<List<PostResponse>> postCall = postService.getPostResponse();



        postCall.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if(response.code()==200){
                    postDatalist=response.body();
                    userCall.enqueue(new Callback<List<UserResponse>>() {
                        @Override
                        public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                            if(response.code()==200){
                                userDatalist=response.body();
                                adapter = new PostAdapter(MainActivity.this,postDatalist,userDatalist);
                                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                                llm.setOrientation(LinearLayoutManager.VERTICAL);
                                listview.setLayoutManager(llm);
                                listview.setAdapter(adapter);
                            }

                        }

                        @Override
                        public void onFailure(Call<List<UserResponse>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {

            }
        });

    }
}
