import UIKit
import UnityAds

let TEST_GAME_ID = "1700140";
let TEST_VIDEO_AD_PLACEMENT_ID = "video";
let TEST_REWARDED_VIDEO_AD_PLACEMENT_ID = "rewardedVideo";


class CommandPromise: NSObject {
    var callback: String
    var delegate: CDVCommandDelegate
    
    init(id: String, comDelegate: CDVCommandDelegate) {
        delegate = comDelegate
        callback = id
        super.init()
    }
    
    func resolve(msg: String) {
        let pluginResult = CDVPluginResult(
            status: CDVCommandStatus_OK,
            messageAs: msg
        )
        
        delegate.send(
            pluginResult,
            callbackId: callback
        )
        
    }
    
    func reject(msg: String) {
        let pluginResult = CDVPluginResult(
            status: CDVCommandStatus_ERROR,
            messageAs: msg
        )
        
        delegate.send(
            pluginResult,
            callbackId: callback
        )
        
    }
}


@objc(UnityAds2)
class UnityAds2 : CDVPlugin, UnityAdsDelegate {
    
    var callbackID = String()

    @objc(UnityAdsInit:)
    func Configure(command: CDVInvokedUrlCommand) {
        
        let promise = CommandPromise(id:command.callbackId, comDelegate: self.commandDelegate)

		var	gameId  = command.arguments[0] as? String ?? ""
		let	isTest  = command.arguments[1] as? Bool ?? false
		let	isDebug = command.arguments[2] as? Bool ?? false
        
        callbackID = command.callbackId

        if gameId == "" {
            promise.reject(msg: "GameID not specified")
            return
        }
        
        if gameId == "TEST" {
            gameId = TEST_GAME_ID
        }
        
        debugPrint( gameId, isTest )
        
        UnityAds.initialize(gameId, delegate:self, testMode: isTest)
        promise.resolve(msg:"UnityAds initialized")
        UnityAds.setDebugMode(isDebug)
    }
    
    @objc(GetPlacementState:)
    func GetPlacementState(command: CDVInvokedUrlCommand){
        let promise = CommandPromise(id:command.callbackId, comDelegate: self.commandDelegate)
        var videoAdPlacementId = command.arguments[0] as? String ?? ""
        
       
        if videoAdPlacementId == "" {
            promise.reject(msg: "Invalid PlacementID")
        }
        
        if videoAdPlacementId == "TEST" {
            videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID
        }
        
        if videoAdPlacementId == "TEST_REWARDED" {
            videoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID
        }
        
        let result = UnityAds.getPlacementState(videoAdPlacementId)
        
        if result == UnityAdsPlacementState.notAvailable {
            promise.resolve(msg: "NOT_AVAILABLE")
        }
        else if result == UnityAdsPlacementState.disabled {
            promise.resolve(msg: "DISABLED")
        }
        else if result == UnityAdsPlacementState.waiting {
            promise.resolve(msg: "WAITING")
        }
        else if result == UnityAdsPlacementState.noFill {
            promise.resolve(msg: "NO_FILL")
        }
        else {
            promise.resolve(msg: "READY")
        }
    }
    
    @objc(ShowVideoAd:)
    func ShowVideoAd(command: CDVInvokedUrlCommand){
        let promise = CommandPromise(id:command.callbackId, comDelegate: self.commandDelegate)
        var videoAdPlacementId = command.arguments[0] as? String ?? ""
        
        if videoAdPlacementId == "" {
            promise.reject(msg: "Invalid PlacementID")
        }
        
        if videoAdPlacementId == "TEST" {
            videoAdPlacementId = TEST_VIDEO_AD_PLACEMENT_ID
        }
        
        if videoAdPlacementId == "TEST_REWARDED" {
            videoAdPlacementId = TEST_REWARDED_VIDEO_AD_PLACEMENT_ID
        }
        
        if UnityAds.isReady() {
            UnityAds.show(self.viewController, placementId: videoAdPlacementId)
        }
        else{
            promise.reject(msg: "NOT_READY")
        }
        
    }

    func unityAdsReady(_ placementId: String) {
        debugPrint("[[UnityAds2][AdsReady] ", placementId)
        let promise = CommandPromise(id: callbackID, comDelegate: self.commandDelegate)
        promise.resolve(msg: String(format: "[\"%0\",\"%1\"]", placementId, "READY"))
    }

    func unityAdsDidStart(_ placementId: String) {        
        debugPrint("[[UnityAds2][DidStart] ", placementId)        
        let promise = CommandPromise(id: callbackID, comDelegate: self.commandDelegate)
        promise.resolve(msg: String(format: "[\"%0\",\"%1\"]", placementId, "SHOWING"))
    }

    func unityAdsDidError(_ error: UnityAdsError, withMessage message: String) {
        
        debugPrint("[[UnityAds2][DidError] ", error, message)
        
        let promise = CommandPromise(id: callbackID, comDelegate: self.commandDelegate)
        if error == .notInitialized {
            promise.resolve(msg: "NOT_INITIALIZED")
        }
        else if error == .initializedFailed {
            promise.resolve(msg: "INITIALIZE_FAILED")
        }
        else {
            promise.resolve(msg: "INTERNAL_ERROR")
        }
    }
    
    func unityAdsDidFinish(_ placementId: String, with state: UnityAdsFinishState) {
        
        debugPrint("[[UnityAds2][DidFinish] ", placementId, state)
        
        let promise = CommandPromise(id: callbackID, comDelegate: self.commandDelegate)
        if state == .skipped {
            promise.resolve(msg: String(format: "[\"%0\",\"%1\"]", placementId, "SKIPPED"))
        }
        else if state == .completed {
            promise.resolve(msg: String(format: "[\"%0\",\"%1\"]", placementId, "COMPLETED"))
        }
        else {
            promise.resolve(msg: String(format: "[\"%0\",\"%1\"]", placementId, "ERROR"))
        }
    }

}
