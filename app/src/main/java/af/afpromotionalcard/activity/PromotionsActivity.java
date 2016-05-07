package af.afpromotionalcard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import af.afpromotionalcard.R;
import af.afpromotionalcard.adaptor.PromotionsRecyclerViewAdaptor;
import af.afpromotionalcard.entity.JSONResponse;
import af.afpromotionalcard.entity.PromotionCards;
import af.afpromotionalcard.entity.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromotionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PromotionCards> data;
    private PromotionsRecyclerViewAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        initViews();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.promotions_recyler_view_id);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getPromotions()));
                adapter = new PromotionsRecyclerViewAdaptor(getApplicationContext(), data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
