# promotion-card
- this app will run via Android Studio
- clone the repo and run the app.
- the provided url for the JSON feed is not correct. the button object is once and object and the second time is an array, which made parsing json not possible;
- the app is currently running with the followng url : http://www.mocky.io/v2/572c2c861300009327e2b8eb
- when original url is fixed, app can be tested by replacing the two constants values (base url and end point) in RequestInterface.java class with:
    ```
    public static final String JASON_FEED_BASE_URL = "http://www.mocky.io";
    public static final String JASON_FEED_END_POINT = "v2/572c2c861300009327e2b8eb";
    ```
    
    with:
    
    ```
    public static final String JASON_FEED_BASE_URL = "https://www.abercrombie.com";
    public static final String JASON_FEED_END_POINT = "anf/nativeapp/Feeds/promotions.json";
    ```
