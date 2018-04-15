import UIKit
import UnityAds


let TEST_GAME_ID = "1737958";
let TEST_VIDEO_AD_PLACEMENT_ID = "video";
let TEST_REWARDED_VIDEO_AD_PLACEMENT_ID = "rewardedVideo";


@objc(UnityAds2)
class UnityAds2: CDVPlugin, UnityAdsDelegate {
    
    var callbackID = String()
    
    override func pluginInitialize() {}
    
    
    @objc(UnityAdsInit:)
    func UnityAdsInit(command: CDVInvokedUrlCommand) {
        
        callbackID = command.callbackId
        debugPrint("[UnityAds2][CallBackID]", callbackID)
        
        var result = CDVPluginResult(status: CDVCommandStatus_ERROR)
        
        var    gameId  = command.arguments[0] as? String ?? ""
        let    isTest  = command.arguments[1] as? Bool ?? false
        let    isDebug = command.arguments[2] as? Bool ?? false
        
        
        if gameId == "" {
            result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs:  "GameID not specified" )
        }
        
        if gameId == "TEST" {
            gameId = TEST_GAME_ID
        }
        
        debugPrint( gameId, isTest )
        
        UnityAds.initialize(gameId, delegate:self, testMode: isTest)
        UnityAds.setDebugMode(isDebug)
        
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:   String(format: "[\"%@\",\"%@\"]", "UNITYADS", "INITIALIZING") )
        result?.setKeepCallbackAs(true);
        commandDelegate!.send(result, callbackId: callbackID)
        
        result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:   String(format: "[\"%@\",\"%@\"]", "UNITYADS", "IS_READY") )
        result?.setKeepCallbackAs(true);
        commandDelegate!.send(result, callbackId: callbackID)
    }
    
    @objc(GetPlacementState:)
    func GetPlacementState(command: CDVInvokedUrlCommand){
        
        self.callbackID = command.callbackId
        var result = CDVPluginResult(status: CDVCommandStatus_ERROR)
        var videoAdPlacementId = command.arguments[0] as? String ?? ""
        
        if videoAdPlacementId == "" {
            result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs:  "Invalid PlacementID" )
        }
        
        if videoAdPlacementId == "TEST" {
            videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID
        }
        
        if videoAdPlacementId == "TEST_REWARDED" {
            videoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID
        }
        
        let pState = UnityAds.getPlacementState(videoAdPlacementId)
        
        if pState == UnityAdsPlacementState.notAvailable {
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "NOT_AVAILABLE")
        }
        else if pState == UnityAdsPlacementState.disabled {
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:  "DISABLED")
        }
        else if pState == UnityAdsPlacementState.waiting {
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:  "WAITING")
        }
        else if pState == UnityAdsPlacementState.noFill {
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:  "NO_FILL")
        }
        else {
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:  "READY")
        }
        
        result?.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: command.callbackId)
    }
    
    @objc(ShowVideoAd:)
    func ShowVideoAd(command: CDVInvokedUrlCommand){
        
        callbackID = command.callbackId
        debugPrint("[UnityAds2][CallBackID]", callbackID)
        var result = CDVPluginResult(status: CDVCommandStatus_ERROR)
        var videoAdPlacementId = command.arguments[0] as? String ?? ""
        
        if videoAdPlacementId == "" {
            result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs:  "Invalid PlacementID" )
        }
        
        if videoAdPlacementId == "TEST" {
            videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID
        }
        
        if videoAdPlacementId == "TEST_REWARDED" {
            videoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID
        }
        
        if UnityAds.isReady(videoAdPlacementId) {
            UnityAds.show(self.viewController, placementId: videoAdPlacementId)
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:  String(format: "[\"%@\",\"%@\"]", videoAdPlacementId, "READY")  )
        }
        else{
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: String(format: "[\"%@\",\"%@\"]", videoAdPlacementId, "NOT_READY") )
        }
        
        result?.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: command.callbackId)
        
    }
    
    func unityAdsReady(_ placementId: String) {
        debugPrint("[UnityAds2][AdsReady]", placementId)
        let result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: String(format: "[\"%@\",\"%@\"]", placementId, "READY") )
        result?.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: callbackID)
        
    }
    
    func unityAdsDidStart(_ placementId: String) {
        debugPrint("[UnityAds2][DidStart]", placementId)
        let result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: String(format: "[\"%@\",\"%@\"]", placementId, "SHOWING")  )
        result?.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: callbackID)
    }
    
    func unityAdsDidError(_ error: UnityAdsError, withMessage message: String) {
        
        var result = CDVPluginResult(status: CDVCommandStatus_ERROR)
        
        debugPrint("[UnityAds2][DidError]", error, message)
        
        if error == .notInitialized {
            result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs:  "NOT_INITIALIZED")
        }
        else if error == .initializedFailed {
            result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "INITIALIZE_FAILED")
        }
        else {
            result = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "INTERNAL_ERROR")
        }
        
        result?.setKeepCallbackAs(true)
        commandDelegate!.send(result, callbackId: callbackID)
    }
    
    func unityAdsDidFinish(_ placementId: String, with state: UnityAdsFinishState) {
        
        debugPrint("[UnityAds2][CallBackID]", callbackID)
        
        var result = CDVPluginResult(status: CDVCommandStatus_ERROR)
        
        if state == .skipped {
            debugPrint("[UnityAds2][DidFinish]", placementId, "SKIPPED")
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: String(format: "[\"%@\",\"%@\"]", placementId, "SKIPPED") )
        }
        else if state == .completed {
            debugPrint("[UnityAds2][DidFinish]", placementId, "COMPLETED")
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: String(format: "[\"%@\",\"%@\"]", placementId, "COMPLETED"))
        }
        else {
            debugPrint("[UnityAds2][DidFinish]", placementId, "ERROR")
            result = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: String(format: "[\"%@\",\"%@\"]", placementId, "ERROR"))
        }
        
        commandDelegate!.send(result, callbackId: callbackID)
        callbackID = ""
    }
    
    
}
