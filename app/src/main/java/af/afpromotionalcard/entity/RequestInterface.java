package af.afpromotionalcard.entity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("v2/572c2c861300009327e2b8eb")
    Call<JSONResponse> getJSON();
}
