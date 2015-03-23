package com.jeremybroutin.ribbit2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SplashActivity extends Activity {

    private static final long TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS = 2000;
    private static final String CONTAINER_ID = "GTM-NTZZ7C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TagManager tagManager = TagManager.getInstance(this);
        tagManager.setVerboseLoggingEnabled(true);

        PendingResult<ContainerHolder> pendingResult
                = tagManager.loadContainerPreferNonDefault(CONTAINER_ID, R.raw.default_binary_container);

        pendingResult.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                Container container = containerHolder.getContainer();
                Log.v("Ribbit2", "Last container refresh time is " + container.getLastRefreshTime());
                if (!containerHolder.getStatus().isSuccess()){
                    Log.e("Ribbit2", "Fail to load container.");
                    return;
                }
                else {
                    Log.e("Ribbit2", "Success to load container.");
                }
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                ContainerLoadedCallback.registerCallbacksForContainer(container);
                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
                startMainActivity();
            }
        }, TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    private void startMainActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private static class ContainerLoadedCallback implements ContainerHolder.ContainerAvailableListener{
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion){
            Container container = containerHolder.getContainer();
            registerCallbacksForContainer(container);
        }

        public static void registerCallbacksForContainer(Container container){
            container.registerFunctionCallTagCallback("customTag", new CustomTagCallBack());
        }

        private static class CustomTagCallBack implements Container.FunctionCallTagCallback{
            @Override
            public void execute(String tagName, Map<String, Object> parameters){
                Log.i("Ribbit2", "Custom function call tag: "+ tagName + " is fired.");
            }
        }
    }
}
