package com.artemisoftnian.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;
import android.util.Log;
import android.Manifest;
import java.util.ArrayList;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.mediation.IUnityAdsExtendedListener;

/**
 * This class echoes a string called from JavaScript.
 */
class SetupData{
	String gameId;
	String videoAdPlacementId;
	String rewardedVideoAdPlacementId;
	boolean isTest;
	boolean isDebug;
}

public class UnityAds2 extends CordovaPlugin {

	final UnityAdsListener unityAdsListener = new UnityAdsListener();

    protected static final String TAG = "UnityAds2";
	protected CallbackContext context;

	String [] permissions = { Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE };

	//
	protected String TEST_GAME_ID = "1700140";
	protected String TEST_VIDEO_AD_PLACEMENT_ID = "video";
	protected String TEST_REWARDED_VIDEO_AD_PLACEMENT_ID = "rewardedVideo";
	//
	protected SetupData setupData = new SetupData();
	//
	protected int videoOrRewardedVideo;

	

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("test")) {
            test(args.getString(0), callbackContext);
            return true;
		}
		else if (action.equals("setUp")) {
			//gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest, isDebug)
			setupData.gameId = args.getString(0);
			setupData.videoAdPlacementId = args.getString(1);
			setupData.rewardedVideoAdPlacementId = args.getString(2);
			setupData.isTest = args.getBoolean(3);
			setupData.isDebug = args.getBoolean(4);  			
            setUp(setupData, callbackContext);
            return true;
		}
		else if(action.equals("showVideoAd")){
			showVideoAd(callbackContext);
			return true;
		}
		else if(action.equals("showRewardedVideoAd")){
			showRewardedVideoAd(callbackContext);
			return true;
		}
        return false;
    }

    private void test(String msg, CallbackContext callbackContext) {
        if (msg == null || msg.length() == 0) {
          callbackContext.error("Empty message!");
        } else {
          Toast.makeText( webView.getContext(),  msg, Toast.LENGTH_LONG).show();
          callbackContext.success(msg);
        }
	}
	
    private void setUp(SetupData setupData,  CallbackContext callbackContext) {

		context = callbackContext;
		
		if (setupData.gameId == null || setupData.gameId.length() == 0) {
          callbackContext.error("Please supply the game id");
        } else {
			//Initialize UnityAds
			UnityAds.initialize(cordova.getActivity(), setupData.gameId, unityAdsListener);
			//Toast.makeText( webView.getContext(),  gameId, Toast.LENGTH_LONG).show();
          	callbackContext.success("UnityAds successfully initialized");
        }
	}

	private void showVideoAd(CallbackContext callbackContext) {
        if(UnityAds.isReady()){
			UnityAds.show(cordova.getActivity(), setupData.videoAdPlacementId);
			callbackContext.success("showing Ad");
		}else{
			callbackContext.error("Ads not ready to show");
		}
	}

	private void showRewardedVideoAd(CallbackContext callbackContext) {
        if(UnityAds.isReady()){
			UnityAds.show(cordova.getActivity(), setupData.rewardedVideoAdPlacementId);
			callbackContext.success("showing Ad");
		}else{
			callbackContext.error("Ads not ready to show");
		}
	}	
	

	/* UNITY ADS LISTENER */	
	class UnityAdsListener implements IUnityAdsExtendedListener {

		@Override //This method is called when the specified ad placement becomes ready to show an ad campaign.
		public void onUnityAdsReady(final String zoneId) {

			Log.d(TAG, String.format("%s", "onUnityAdsReady"));
			Log.d(TAG, zoneId);
			
			PluginResult pra=new PluginResult(PluginResult.Status.OK, "onVideoAdLoaded");
			pra.setKeepCallback(true);
			context.sendPluginResult(pra);

			PluginResult prb=new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdLoaded");
			prb.setKeepCallback(true);
			context.sendPluginResult(prb);			
		}

		
		@Override //called at the start of video playback for the ad campaign being shown.
		public void onUnityAdsStart(String zoneId) {
			Log.d(TAG, String.format("%s", "onUnityAdsStart"));
		}

		@Override //called after the ad placement is closed.
		public void onUnityAdsFinish(String zoneId, UnityAds.FinishState result) {

			Log.d(TAG, String.format("%s", "onUnityAdsFinish"));

			if(result != UnityAds.FinishState.SKIPPED){
				if (videoOrRewardedVideo == 1) {			
				}
				else if (videoOrRewardedVideo == 2) {

					PluginResult pra=new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdCompleted");
					pra.setKeepCallback(true);
					context.sendPluginResult(pra);
				}				
			}
			else{
				Log.d(TAG, String.format("%s", "onHide"));
			
				if (videoOrRewardedVideo == 1) {			

					PluginResult pra=new PluginResult(PluginResult.Status.OK, "onVideoAdHidden");
					pra.setKeepCallback(true);
					context.sendPluginResult(pra);					
					
					if(UnityAds.isReady()){		
						pra=new PluginResult(PluginResult.Status.OK, "onVideoAdLoaded");
						pra.setKeepCallback(true);
						context.sendPluginResult(pra);				

					}					
				}
				else if (videoOrRewardedVideo == 2) {
					
					PluginResult pra=new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdHidden");
					pra.setKeepCallback(true);
					context.sendPluginResult(pra);	
					
					if(UnityAds.isReady()){
						pra=new PluginResult(PluginResult.Status.OK, "onRewardedVideoAdLoaded");
						pra.setKeepCallback(true);
						context.sendPluginResult(pra);		
					}				
				}

			}

		}

		@Override  //called when an error occurs with Unity Ads. 
		public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
			Log.d(TAG, String.format("%s", "onUnityAdsError"));
			Log.d(TAG, String.format("%s", message));
		}

		@Override //triggered whenever a placements state changes for example from WAITING to READY, ETC..
		public void onUnityAdsPlacementStateChanged(String placementId, UnityAds.PlacementState oldState, UnityAds.PlacementState newState){
			Log.d(TAG, String.format("%s", placementId));
			Log.d(TAG, String.format("%s", oldState));
			Log.d(TAG, String.format("%s", newState));
		}

		@Override //triggered whenever a user clicks the download button in the ad
		public void onUnityAdsClick(String placementId){
			Log.d(TAG, String.format("%s", placementId));
		}

	}       
}
