# LazyLibraryAndroid


 Simple android library that makes production faster
 
 # Feature of LazyLibray
 - 1 API Fetching 
 - 2 Broadcast Management
 - 3 One time Otp Verification
 - 4 No need to make RecyclerView Adapter
 
 
 # How to use 
 
 Lazy Libray has been rebuilt on AndoridX, If you are still using the androd support Library, please use 1.4.2 
 repositories {
  ```
  maven {
        jcenter()
        maven { url 'https://jitpack.io'
    }
}
```
```
dependencies {
   implementation 'com.github.wisdomrider:LazyLibraryAndroid:1.2.1' 
}
```


# API Fetching
You need to extend your Application Class with LazyApp
```
class YourApplicationClass : LazyApp(
    "https://baseurl.com/",
    enableLogin = true,
    enableBasicAuthentication = true,
    userName = "username",
    password = "password"
) {
    lateinit var api: Api
    override fun onCreate() {
        super.onCreate()
        api = retrofit.create(Api::class.java)
    }
}
```

 LazyApp takes max 5 parameters all of them are optional except Base URL
 
 1st  parameters represent BASE_URL 
 
enableLogin = By default enableLogn is false if you make it true than you can see your Api Long on Verbose, where it   shows  header, Request Body and Response Body

enableBasicAuthentication = By default enableBasicAuthentication is false if you make it true, you need to provide a username and password as parameter as above. It active the server Basic Authentication (API)
 
Your first step has been completed

You need Api interface to define rest in point for this you need to add dependencie 'com.squareup.retrofit2:retrofit:2.6.1' on build.gradle
Now
```
 public interface Api {
  @GET("/api/......")
  Call<ResponseClass> getMyData()
 }
 
```
Now you need to extend your MainActivity class with LazyBase()
```
class MainActivity: LazyBase() {
    overide fun onCreate(saveInstanceState: Bundle?) {
     ..................
     var myApi = application as YourApplicationClass 
     myApi.api.getMyData().fetch({
       // your response      
     },{
        // Throwable  
       },showProgressBar = true, fetchData = false, progressBarTittle = "Fetching Secure REST API")
     }
 }
 ```
showProgressBar = By default showProgressBar if false if you make it true than you can get default progressBar while  loading data  
 
 fetchData = By default fetchData is true if you make it false than it does not fetch data from the server
 
 progressBarTitle = You can give your title while loading progress bar example loading.... etc

# Adding Interceptor on Header
Lazy Library use OkHttp3 Interceptor to add Intercepot on Header you can use
```
myApi.api.lazyInterceptor(
tokenHeader = "HeaderName",
tokenValue= "Bearer ldkjfkdjfkdjfldjfkdjkfjd..0..."
)
```
# example
```
myApi.lazyInterceptor(
            tokenHeader = "Authorization"
            ,
            tokenValue =
            "Bearer eyJ0eXAiOiJK"
        )
```     
After adding Interceptor on Header Now you do not need to add every time token on Header while fetching (or requesting) data from the Server


# Broadcast Management
 You do not need to register your bored cast in Manifests. All things will provide by Lazy
 
 # Sending Object on broad cast
  Make a Pojo class where you can set you data on Family class
  `
  class Family(var name: String, var relation: String): Serializable
  `
  You need to implement Serializable
  
  Example
  ```

      lazy.receiveBroadcast {
        var family =  it.extras.getSerializable("data") as Family
           Toast.makeText(this, family.name, Toast.LENGTH_LONG).show()
        }
        var family = Family("family name", "relation")
        family.sendToBroadcast()
```
        
# Simple Broad cast
Example
```
 lazy.receiveBroadcast { intent ->
           //you will recive your boradcast
        }
var intent = Intent()
intent.sendToBroadcast()
```
# One time Otp VerificationLazy 
Lazy provides you One time Otp Verification option for that you need to use LazyPinView  LazyPinView is child class of `com.chaos.view.PinView` for more details you can  use this link [GitHub Page] (https://github.com/ChaosLeung/PinView)

It provides you one itme OtpVerification
## Example 
Xml file
```
<org.wisdomrider.lazylibrary.LazyPinView
            android:id="@+id/lazyPingView"
            style="@style/PinWidget.PinView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@+id/tv_message"
            android:layout_centerHorizontal="true"
            android:layout_marginTop="64dp"
            android:layout_marginRight="8dp"
            android:cursorVisible="true"
            android:hint="Enter code hear"
            android:inputType="text"
            android:padding="4dp"
            android:textColor="@color/colorPrimary"
            android:textSize="18sp"
            android:textStyle="bold"
            app:cursorColor="@color/colorPrimary"
            app:cursorWidth="2dp"
            app:hideLineWhenFilled="true"
            app:itemCount="4"
            app:itemHeight="48dp"
            app:itemRadius="4dp"
            app:itemSpacing="0dp"
            app:itemWidth="48dp"
            app:lineWidth="2dp"
            app:viewType="rectangle"/>
``` 
java File you can use

`enableOneTimeOtpCode( "phoneNumber" , lazyPingView)`

You need to call enableOneTimeOtpCode method for auto readable message on PinView. It takes two parameters phonenumber and pinView.In 1st parameter you need to provide PhoneNumber form where your otp is being generated and in 2nd you need to provide lazyPinView object.






## No need to make RecyclerView Adapter
You can directly set your adapter on RecyclerView. For this you can call LazyRecyclerAdapter.
# Example
 ```
  recycler.layoutManager = LinearLayoutManager(this@MainActivity)
  recycler.adapter = LazyRecyclerAdapter(
                        R.layout.row, object : LazyViewHolder {
                            override fun lazyOnBindViewHolder(
                                holder: LazyRecyclerAdapter.WisdomHolder,
                                list: List<Any?>,
                                position: Int
                            ) {
                             // you can get list postion
                             // your can get your list
                             // you can get your row 
                            }
                        }, yourList
                    )
 
 ```
LazyRecyclerAdapter takes 3 parameters first one is your RecycelrView row layout second one is LazyViewHolder Object and Third one is list which you want to display on RecyclerView.


