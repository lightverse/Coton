package com.github.lightverse.baselib.web;

import android.os.Debug;

import com.github.lightverse.baselib.util.CoLog;

import org.json.JSONException;

public class JsToNativeManager {
    public static final String TAG = "JsToNativeManager";

    private static final int SLOW_EXEC_WARNING_THRESHOLD = Debug.isDebuggerConnected() ? 60 : 16;


    private final CordovaInterface ctx;
    private final CordovaWebView app;
    CoreAndroid coreAndroid = new CoreAndroid();

    public JsToNativeManager(CordovaWebView cordovaWebView, CordovaInterface cordova) {
        this.ctx = cordova;
        this.app = cordovaWebView;
        coreAndroid.privateInitialize(cordova,app,app.getPreferences());
    }

   /**
     * Receives a request for execution and fulfills it by finding the appropriate
     * Java class and calling it's execute method.
     *
     * PluginManager.exec can be used either synchronously or async. In either case, a JSON encoded
     * string is returned that will indicate if any errors have occurred when trying to find
     * or execute the class denoted by the clazz argument.
     *
     * @param service       String containing the service to run
     * @param action        String containing the action that the class is supposed to perform. This is
     *                      passed to the plugin execute method and it is up to the plugin developer
     *                      how to deal with it.
     * @param callbackId    String containing the id of the callback that is execute in JavaScript if
     *                      this is an async plugin call.
     * @param rawArgs       An Array literal string containing any arguments needed in the
     *                      plugin execute method.
     */
    public void exec(final String service, final String action, final String callbackId, final String rawArgs) {

        CallbackContext callbackContext = new CallbackContext(callbackId, app);
        try {
            long pluginStartTime = System.currentTimeMillis();
            boolean wasValidAction = coreAndroid.execute(action, rawArgs, callbackContext);
            long duration = System.currentTimeMillis() - pluginStartTime;

            if (duration > SLOW_EXEC_WARNING_THRESHOLD) {
                CoLog.w(TAG, "THREAD WARNING: exec() call to " + service + "." + action + " blocked the main thread for " + duration + "ms. Plugin should use CordovaInterface.getThreadPool().");
            }
            if (!wasValidAction) {
                PluginResult cr = new PluginResult(PluginResult.Status.INVALID_ACTION);
                callbackContext.sendPluginResult(cr);
            }
        } catch (JSONException e) {
            PluginResult cr = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            callbackContext.sendPluginResult(cr);
        } catch (Exception e) {
            CoLog.e(TAG, "Uncaught exception from plugin", e);
            callbackContext.error(e.getMessage());
        }
    }



    public boolean shouldAllowNavigation(String url) {
        return coreAndroid.shouldAllowNavigation(url);
    }


    public CoreAndroid getCoreAndroid(){
        return coreAndroid;
    }
}
