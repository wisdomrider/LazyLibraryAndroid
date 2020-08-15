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
why to do that? because lazybase provides a extension functions which helps you.
