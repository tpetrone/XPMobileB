package br.senai.sp.informatica.albunsmusicais;

import android.app.Application;
import android.content.Context;

/**
 * Created by pena on 08/02/17.
 */

public class Main extends Application {
    private static Main app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Context getContext() {
        return app.getBaseContext();
    }
}
