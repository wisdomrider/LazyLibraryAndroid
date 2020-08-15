# LazyLibaryAndroid

### Description

 LazyLibrary helps you to boost your performance in android development with the help of Extension functions.You must have kotlin configured before using this library.

 ### How to add it in your project?

 Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.wisdomrider:LazyLibraryAndroid:2.0.1'
	}

### Then follow these steps 

After syncing the gradle files create a class with name Application and extend it with **LazyApp** as given below:
#### Filename: Application.kt
    class Application : LazyApp() {
        override fun onCreate() {
            super.onCreate()
          //later we will add modules here  
        }
    }

After that go to Manifest file and in <application part  add a name with Application reference as shown below:
### Filename:	AndroidManifest.xml
	<application  
	  ...
	  android:name=".Application"
	  ...>

Now you can easily use lazylibrary in your app just change the appcompact activity with lazybase and you are good to go as shown below:

	class MainActivity : LazyBase() {  
		//instead of extending class with AppCompactActivity
	    override fun onCreate(savedInstanceState: Bundle?) {  
	        super.onCreate(savedInstanceState)  
	        setContentView(R.layout.activity_main)  
	    }  
	}
why to do that? because lazybase provides a extension functions which helps you.Now you have access to lazy function which does all the things.

### Getting started !

Lazylibrary works with **module concept**. if you want to use a module then you have to register it in application.kt and it will be provided to you in all the application for example lets use **toastmodule**.
First go to **application.kt** file and add this below super.onCreate() method like this:
			
	override fun onCreate() {  
	    super.onCreate()  
	    inject(ToastModule::class.java)  
	}
Now you have access to toast module in your application to use it lets move to mainactivity.kt and in oncreate lets add a toast method as shown below:

	override fun onCreate(savedInstanceState: Bundle?) {  
	    ...
	    setContentView(R.layout.activity_main)  
	    "Checking Toast".toast().lazy()  
	}
As shown above now you can toast any string object with extension funtion toast followed by **.lazy()** toast function also provides optional parameters for the time of the toast by default toast uses length short but you can pass custom time or length long as shown below:

	override fun onCreate(savedInstanceState: Bundle?) {  
	    ...
	    setContentView(R.layout.activity_main)  
		"Checking Toast".toast(Toast.LENGTH_LONG).lazy()
	}

Likewise lazy consists of various modules which needs to be registered in the application.kt first then it will be provided to you in activity and fragments which extends the lazybase class.

# [Click here](https://github.com/wisdomrider/LazyLibraryAndroid/wiki/Lazy-Modules-List) to view all modules with their details.

If you want to contribute to this project feel free to make a issue and if you got any problems contact newmac1000@gmail.com or wisdomrider@hotmail.com
