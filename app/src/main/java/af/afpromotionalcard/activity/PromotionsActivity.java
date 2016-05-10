package af.afpromotionalcard.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import af.afpromotionalcard.R;
import af.afpromotionalcard.adaptor.PromotionsRecyclerViewAdaptor;
import af.afpromotionalcard.entity.JSONResponse;
import af.afpromotionalcard.entity.PromotionCard;
import af.afpromotionalcard.entity.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static af.afpromotionalcard.entity.RequestInterface.JASON_FEED_BASE_URL;

public class PromotionsActivity extends AppCompatActivity {

    public static final String CACHE_FILE_NAME = "cacheFile.srl";

    private RecyclerView recyclerView;
    private PromotionCard[] data;
    private PromotionsRecyclerViewAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        initContentViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        data = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initContentViews();
    }

    private void initContentViews() {
        recyclerView = (RecyclerView) findViewById(R.id.promotions_recyler_view_id);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        if (isNetworkAvailable()) {
            loadJSON();
        } else {
            getCachedData();
        }
    }

    boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JASON_FEED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                try {
                    cacheResposnse(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data = jsonResponse.getPromotions();
                adapter = new PromotionsRecyclerViewAdaptor(getApplicationContext(), data);
                recyclerView.setAdapter(adapter);
            }

            private void cacheResposnse(Response<JSONResponse> response) throws IOException {
                Gson gson = new Gson();
                String jsonString = gson.toJson(response.body().getPromotions());
                ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(getCacheDir(), "/") + CACHE_FILE_NAME));
                out.writeObject(jsonString);
                out.close();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void getCachedData() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(getCacheDir(), "") + "cacheFile.srl"));
            Gson gson = new Gson();
            data = gson.fromJson(String.valueOf(in.readObject()), PromotionCard[].class);
            in.close();
        } catch (IOException e) {
            alertUser();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(data != null) {
            adapter = new PromotionsRecyclerViewAdaptor(getApplicationContext(), data);
            recyclerView.setAdapter(adapter);
        }
    }

    private void alertUser() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("No internet detected");
        alertDialog.setMessage("The Promotion app requires internet to initially load your promotions");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
