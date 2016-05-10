package af.afpromotionalcard.entity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    public static final String JASON_FEED_BASE_URL = "http://www.mocky.io";
    public static final String JASON_FEED_END_POINT = "v2/572c2c861300009327e2b8eb";

    @GET(JASON_FEED_END_POINT)
    Call<JSONResponse> getJSON();
}
