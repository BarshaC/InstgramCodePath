package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8MGA3lyIzN7OCqH0B1Az7fYTU16VkT6duTZreOc3")
                .clientKey("Z0aallduZLaSesGCPkIw7O2CYLhbL7vYS2kVMa8u")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
