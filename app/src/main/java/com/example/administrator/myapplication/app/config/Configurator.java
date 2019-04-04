package com.example.administrator.myapplication.app.config;

import android.app.Activity;

import java.util.HashMap;

/**
 * @author ChenYe
 *         created by on 2018/1/23 0023. 09:32
 *         这个类是向外提供管理配置方法的
 **/

public final class Configurator {

    private static final HashMap<Object, Object> VISOR_CONFIGS = new HashMap<>();

    private Configurator() {
        VISOR_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    private static class ConfigHolder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        VISOR_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    static Configurator getInstance() {
        return ConfigHolder.INSTANCE;
    }

    final HashMap<Object, Object> getVisorConfigs() {
        return VISOR_CONFIGS;
    }

    public final Configurator withApiHost(String host) {
        VISOR_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }


    public final Configurator withLogger(boolean isLog) {
        VISOR_CONFIGS.put(ConfigKeys.IS_LOGGER, isLog);
        return this;
    }

    public final Configurator withDebug(boolean flag) {
        VISOR_CONFIGS.put(ConfigKeys.IS_DEBUG, flag);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        VISOR_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    public final Configurator withDefaultDir(String dir) {
        VISOR_CONFIGS.put(ConfigKeys.DEFAULT_DIR, dir);
        return this;
    }

    public final Configurator withDefaultPicDir(String dir) {
        VISOR_CONFIGS.put(ConfigKeys.DEFAULT_PIC_DIR, dir);
        return this;
    }

//    public final Configurator withAccount(LoginBean account) {  //用户实体
//        VISOR_CONFIGS.put(ConfigKeys.ACCOUNT, account);
//        return this;
//    }

    public final Configurator withBaseUrl(String baseUrl) {
        VISOR_CONFIGS.put(ConfigKeys.BASE_URL, baseUrl);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) VISOR_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = VISOR_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) VISOR_CONFIGS.get(key);
    }
}
