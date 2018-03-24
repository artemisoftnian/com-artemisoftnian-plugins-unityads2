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
import jdk.nashorn.internal.codegen.CompilerConstants.Call;
import android.app.Activity;

import android.util.Log;
import android.Manifest;

import java.util.ArrayList;

import com.sun.glass.ui.MenuItem.Callback;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAds.PlacementState;

class ResultMessage{
	String message;
	PluginResult.Status status;
}

public class UnityAds2 extends CordovaPlugin {

	final UnityAdsListener unityAdsListener = new UnityAdsListener();

    protected static final String TAG = "UnityAds2";
	protected CallbackContext context;
	//protected UnityAds UnityAds = new UnityAds();
	//
	protected String TEST_GAME_ID = "1700140";
	protected String TEST_VIDEO_AD_PLACEMENT_ID = "video";
	protected String TEST_REWARDED_VIDEO_AD_PLACEMENT_ID = "rewardedVideo";
	//
	protected ResultMessage _result = new ResultMessage();


	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
	}
	

	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
		//
	}
	
	@Override
	public void onResume(boolean multitasking) {
		super.onResume(multitasking);
		SendLastMessage(_result.status, _result.message);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//
	}	

    @Override
    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext){
	   
		final UnityAds2 self = this;
		context = callbackContext;

		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run(){

				if (action.equals("UnityAdsInit")) {
					self.UnityAdsInit(args, callbackContext);
				}
				else if(action.equals("ShowVideoAd")){
					self.ShowVideoAd(args,callbackContext);
				}
				else if(action.equals("GetPlacementState")){
					self.GetPlacementState(args, callbackContext);
				}
				else{
					callbackContext.error("Method not found");
				}
			}			
		});		

        return true;
    }

    private void UnityAdsInit(JSONArray args,  CallbackContext callbackContext){

		String gameId;
		Boolean isTest;
		Boolean isDebug;


		try{
			gameId = args.getString(0);
			isTest = args.getBoolean(1);
			isDebug = args.getBoolean(2);			

		}catch(JSONException e){
			callbackContext.error("Invalid Game ID");
			return;
		}

		if(!UnityAds.isSupported()){
			callbackContext.error("Device not supported by UnityAds");
			return;
		}	

		if (gameId.equals("")){
			callbackContext.error("GameID Not Supplied");
			return;
		}

		if(gameId.equals("TEST")){
			gameId = TEST_GAME_ID;
		}

		if(UnityAds.isInitialized()){
			callbackContext.error("UnityAds already initialized");
			return;				
		}

		UnityAds.initialize(this.cordova.getActivity(), gameId, unityAdsListener, isTest);		
		callbackContext.success("INITIALIZING");
		UnityAds.setDebugMode(isDebug); //Logs are verbose when set to true, and minimal when false.
	}

	private void GetPlacementState(JSONArray args, CallbackContext callbackContext){
		String videoAdPlacementId;
		UnityAds.PlacementState result;

		try{
			videoAdPlacementId = args.getString(0);
		}catch(JSONException e){
			callbackContext.error( "Invalid PlacementID" );
			return;
		}
		Log.d(TAG, videoAdPlacementId);

		if(videoAdPlacementId.equals("TEST")){
			videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID;
		}

		if(videoAdPlacementId.equals("TEST_REWARDED")){
			videoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID;
		}	

		result = UnityAds.getPlacementState(videoAdPlacementId);

		if(result == UnityAds.PlacementState.NOT_AVAILABLE){
			callbackContext.error(String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "NOT_AVAILABLE"));
		}else if(result == UnityAds.PlacementState.DISABLED){
			callbackContext.error(String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "DISABLED"));
		}else if(result == UnityAds.PlacementState.WAITING){
			callbackContext.error(String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "WAITING"));
		}else if(result == UnityAds.PlacementState.NO_FILL){
			callbackContext.error(String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "NO_FILL"));
		}else{
			callbackContext.error(String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "READY"));
		}

	}

	private void ShowVideoAd(JSONArray args, CallbackContext callbackContext){
		
		String videoAdPlacementId;
		try{
			videoAdPlacementId = args.getString(0);
		}catch(JSONException e){
			callbackContext.error( "Invalid PlacementID" );
			return;
		}

		if(videoAdPlacementId.equals("")){
			callbackContext.error("PlacementID not specified");
			return;
		}
		
		if(videoAdPlacementId.equals("TEST")){
			videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID;
		}

		if(videoAdPlacementId.equals("TEST_REWARDED")){
			videoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID;
		}	

		Log.d(TAG, videoAdPlacementId);

		if(UnityAds.isReady()){
			UnityAds.show(this.cordova.getActivity(), videoAdPlacementId);
		}else{
			callbackContext.error(String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "NOT_READY"));
		}
	}


	private void SendLastMessage(PluginResult.Status status, String message){	
		
        if(status == PluginResult.Status.OK)
			context.success(message);
		else
		    context.error(message);
	}


	/* UNITY ADS LISTENER */	
	class UnityAdsListener implements IUnityAdsListener {

		@Override //This method is called when the specified ad placement becomes ready to show an ad campaign.
		public void onUnityAdsReady(final String zoneId) {

			Log.d(TAG, String.format("%s", "onUnityAdsReady"));
			Log.d(TAG, zoneId);
			
			_result.message = String.format("[\"%s\",\"%s\"]", zoneId, "READY");
			_result.status = PluginResult.Status.OK;
		}
		
		@Override //called at the start of video playback for the ad campaign being shown.
		public void onUnityAdsStart(String zoneId) {
			Log.d(TAG, String.format("%s", "onUnityAdsStart"));
			Log.d(TAG, zoneId);
	
			_result.message = String.format("[\"%s\",\"%s\"]", zoneId, "SHOWING");
			_result.status=PluginResult.Status.OK;			
		}

		@Override //called after the ad placement is closed.
		public void onUnityAdsFinish(String zoneId, UnityAds.FinishState result) {

			if(result == UnityAds.FinishState.SKIPPED){
				_result.message = String.format("[\"%s\",\"%s\"]", zoneId, "SKIPPED");
			}else if(result == UnityAds.FinishState.COMPLETED){
				_result.message = String.format("[\"%s\",\"%s\"]", zoneId, "COMPLETED");
			}else{
				_result.message = String.format("[\"%s\",\"%s\"]", zoneId, "ERROR");
			}

			Log.d(TAG, String.format("%s", "onUnityAdsFinish"));
			Log.d(TAG, _result.message);

			_result.status = PluginResult.Status.OK;			

		}

		@Override  //called when an error occurs with Unity Ads. 
		public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
			Log.d(TAG, String.format("%s", "onUnityAdsError"));
			Log.d(TAG, String.format("%s", message));

			if(error == UnityAds.UnityAdsError.NOT_INITIALIZED){
				context.error(String.format("[\"%s\",\"%s\"]", message, "NOT_INITIALIZED"));
			}else if(error == UnityAds.UnityAdsError.INITIALIZE_FAILED){
				context.error(String.format("[\"%s\",\"%s\"]", message, "INITIALIZE_FAILED"));
			}else{
				context.error(String.format("[\"%s\",\"%s\"]", message, "INTERNAL_ERROR"));
			}
	
		}

	}           
}
