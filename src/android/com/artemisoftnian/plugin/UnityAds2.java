package com.artemisoftnian.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

class ResultMessage{
	String message;
	PluginResult.Status status;
}

public class UnityAds2 extends CordovaPlugin {

	final UnityAdsListener unityAdsListener = new UnityAdsListener();

    protected static final String TAG = "UnityAds2";
	protected CallbackContext context;

	protected String TEST_GAME_ID = "1700140";
	protected String TEST_VIDEO_AD_PLACEMENT_ID = "video";
	protected String TEST_REWARDED_VIDEO_AD_PLACEMENT_ID = "rewardedVideo";

	protected ResultMessage _result = new ResultMessage();

	PluginResult result = new PluginResult(PluginResult.Status.ERROR, "SOMTHING_WENT_WRONG");

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
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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
		String msg;
		Boolean isTest;
		Boolean isDebug;
		
		context = callbackContext;


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
		UnityAds.setDebugMode(isDebug); //Logs are verbose when set to true, and minimal when false.
		PluginResult result = new PluginResult(PluginResult.Status.OK, String.format("[\"%s\",\"%s\"]", "UNITYADS", "INITIALIZING"));		
		result.setKeepCallback(true);
		callbackContext.sendPluginResult(result);

		result = new PluginResult(PluginResult.Status.OK, String.format("[\"%s\",\"%s\"]", "UNITYADS", "IS_READY"));		
		result.setKeepCallback(true);
		callbackContext.sendPluginResult(result);			


	}

	private void GetPlacementState(JSONArray args, CallbackContext callbackContext){
		String videoAdPlacementId;
		String msg;	
		UnityAds.PlacementState state;

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

		state = UnityAds.getPlacementState(videoAdPlacementId);

		if(state == UnityAds.PlacementState.NOT_AVAILABLE){
			msg = String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "NOT_AVAILABLE");
		}else if(state == UnityAds.PlacementState.DISABLED){
			msg = String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "DISABLED");
		}else if(state == UnityAds.PlacementState.WAITING){
			msg = String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "WAITING");
		}else if(state == UnityAds.PlacementState.NO_FILL){
			msg = String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "NO_FILL");
		}else{
			msg = String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "READY");
		}

		PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
		result.setKeepCallback(true);
		callbackContext.sendPluginResult(result);		

	}

	private void ShowVideoAd(JSONArray args, CallbackContext callbackContext){
		
		String videoAdPlacementId;
		String msg;	
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
			msg = String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "READY");
		}else{
			msg = String.format("[\"%s\",\"%s\"]", videoAdPlacementId, "NOT_READY");
		}

		PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
		result.setKeepCallback(true);
		callbackContext.sendPluginResult(result);	

	}


	/* UNITY ADS LISTENER */	
	class UnityAdsListener implements IUnityAdsListener {

		@Override //This method is called when the specified ad placement becomes ready to show an ad campaign.
		public void onUnityAdsReady(final String placementId) {

			Log.d(TAG, String.format("%s", "onUnityAdsReady"));
			Log.d(TAG, placementId);

			PluginResult result = new PluginResult(PluginResult.Status.OK, String.format("[\"%s\",\"%s\"]", placementId, "READY"));
			result.setKeepCallback(true);
			context.sendPluginResult(result);
		}
		
		@Override //called at the start of video playback for the ad campaign being shown.
		public void onUnityAdsStart(String placementId) {
			Log.d(TAG, String.format("%s", "onUnityAdsStart"));
			Log.d(TAG, placementId);
	
			PluginResult result = new PluginResult(PluginResult.Status.OK, String.format("[\"%s\",\"%s\"]", placementId, "SHOWING"));
			result.setKeepCallback(true);
			context.sendPluginResult(result);
		}

		@Override //called after the ad placement is closed.
		public void onUnityAdsFinish(String placementId, UnityAds.FinishState state) {
			String msg;						
			if(state == UnityAds.FinishState.SKIPPED){
				msg = String.format("[\"%s\",\"%s\"]", placementId, "SKIPPED");
			}else if(state == UnityAds.FinishState.COMPLETED){
				msg = String.format("[\"%s\",\"%s\"]", placementId, "COMPLETED");
			}else{
				msg = String.format("[\"%s\",\"%s\"]", placementId, "ERROR");
			}

			Log.d(TAG, String.format("%s", "onUnityAdsFinish"));
			Log.d(TAG, msg);

			PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
			context.sendPluginResult(result);
		}

		@Override  //called when an error occurs with Unity Ads. 
		public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
			String msg;	
			
			Log.d(TAG, String.format("%s", "onUnityAdsError"));
			Log.d(TAG, String.format("%s", message));
			
			if(error == UnityAds.UnityAdsError.NOT_INITIALIZED){
				msg = String.format("[\"%s\",\"%s\"]", message, "NOT_INITIALIZED");
			}else if(error == UnityAds.UnityAdsError.INITIALIZE_FAILED){
				msg = String.format("[\"%s\",\"%s\"]", message, "INITIALIZE_FAILED");
			}else{
				msg = String.format("[\"%s\",\"%s\"]", message, "INTERNAL_ERROR");
			}

			PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
			result.setKeepCallback(true);
			context.sendPluginResult(result);				
	
		}

	}           
}