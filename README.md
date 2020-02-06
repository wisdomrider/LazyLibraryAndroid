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
   implementation 'com.chaos.view:pinview:1.4.3'
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
 
 enableLogin = By default enableLogn is false if you make it true than you can see your Api Long on Verbose, where it shows  header, Request Body and Response Body
 
 enableBasicAuthentication = By default enableBasicAuthentication is false if you make it true, you need to provide a username and password as parameter as above. It active the server Basic Authentication (API)
 
Your first step has been completed

You need Api interface to define rest in point for this you need to add dependencie 'com.squareup.retrofit2:retrofit:2.6.1' on build.gradle

Now 
 public interface Api {
  @GET("/api/......")
  Call<ResponseClass> getMyData()
 }
 


Now you need to extend your MainActivity class with LazyBase()

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
  
  showProgressBar = By default showProgressBar if false if you make it true than you can get default progressBar while  loading data  
  
fetchData = By default fetchData is true if you make it false than it does not fetch data from the server
 
progressBarTitle = You can give your title while loading progress bar example loading.... etc


# Adding Interceptor on Header
Lazy Library use OkHttp3 Interceptor
to add Intercepot on Header you can use

myApi.api.lazyInterceptor(
tokenHeader = "HeaderName",
tokenValue= "Bearer ldkjfkdjfkdjfldjfkdjkfjd..0..."
)

# example
myApi.lazyInterceptor(
            tokenHeader = "Authorization"
            ,
            tokenValue =
            "Bearer eyJ0eXAiOiJK"
        )
        
After adding Interceptor on Header Now you do not need to add every time token on Header while fetching (or requesting) data from the Server


# Broadcast Management
 You do not need to register your bored cast in Manifests. All things will provide by Lazy
 
 # Sending Object on broad cast
  Make a Pojo class where you can set you data on Family class
  
  class Family(var name: String, var relation: String): Serializable
  
  You need to implement Serializable
  
  Example 
      lazy.receiveBroadcast {
        var family =  it.extras.getSerializable("data") as Family
           Toast.makeText(this, family.name, Toast.LENGTH_LONG).show()
        }
        var family = Family("family name", "relation")
        family.sendToBroadcast()
        
# Simple Broad cast
Example
 lazy.receiveBroadcast { intent ->
           //you will recive your boradcast
        }
var intent = Intent()
intent.sendToBroadcast()
    
