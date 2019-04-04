package com.example.administrator.myapplication.app.config;

import android.content.Context;

/**
 * @author ChenYe
 *         created by on 2018/1/23 0023. 09:53
 **/

public class SiteControl {

    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getVisorConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }
}
