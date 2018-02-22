var exec = require('cordova/exec');

var PLUGIN_NAME = 'UnityAds2';
var loadedVideoAd= false;
var loadedRewardedVideoAd= false;
var isShowingVideoAd= false;
var isShowingRewardedVideoAd= false;
//
var onVideoAdLoaded= null;
var onVideoAdShown= null;
var onVideoAdHidden= null;
//
var onRewardedVideoAdLoaded= null;
var onRewardedVideoAdShown= null;
var onRewardedVideoAdHidden= null;
var onRewardedVideoAdCompleted= null;


exports.test = function(arg0, success, error) {
    exec(success, error, PLUGIN_NAME, "test", [arg0]);
};

// MAIN FUNCTIONS
exports.setUp = function(gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest, isDebug) {
    exec(
        function (result) {
            console.log('UnityAds Initialized:', result);
            
            if (typeof result == "string") {
                if (result == "onVideoAdLoaded") {
                    loadedVideoAd = true;

                    if (onVideoAdLoaded)
                        onVideoAdLoaded();
                }					
                if (result == "onVideoAdShown") {
                    loadedVideoAd = false;
                    isShowingVideoAd = true;
                
                    if (onVideoAdShown)
                        onVideoAdShown();
                }
                else if (result == "onVideoAdHidden") {
                    isShowingVideoAd = false;
                
                    if (onVideoAdHidden)
                        onVideoAdHidden();
                }
                //
                else if (result == "onRewardedVideoAdLoaded") {
                    loadedRewardedVideoAd = true;

                    if (onRewardedVideoAdLoaded)
                        onRewardedVideoAdLoaded();
                }					
                else if (result == "onRewardedVideoAdShown") {
                    loadedRewardedVideoAd = false;
                    isShowingRewardedVideoAd = true;
                
                    if (onRewardedVideoAdShown)
                        onRewardedVideoAdShown();
                }
                else if (result == "onRewardedVideoAdHidden") {
                    isShowingRewardedVideoAd = false;
                
                    if (onRewardedVideoAdHidden)
                        onRewardedVideoAdHidden();
                }
                else if (result == "onRewardedVideoAdCompleted") {
                    if (onRewardedVideoAdCompleted)
                        onRewardedVideoAdCompleted();
                }
            }
            else {
                //var event = result["event"];
                //var location = result["message"];				
                //if (event == "onXXX") {
                //	if (self.onXXX)
                //		self.onXXX(location);
                //}
}            
        },
        function (error) {
            console.log('UnityAds Setup Failed:', error);
        },
        PLUGIN_NAME,
        'setUp',			
        [gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest, isDebug]
    ); 
};

exports.showVideoAd = function() {
    exec(null,null, PLUGIN_NAME,'showVideoAd', [] ); 
};

exports.showRewardedVideoAd = function() {
    exec(null,null, PLUGIN_NAME,'showRewardedVideoAd', [] ); 
};
exports.loadedVideoAd = function() {
    return this.loadedVideoAd;
};

exports.loadedRewardedVideoAd = function() {
    return this.loadedRewardedVideoAd;
};
exports.isShowingVideoAd = function() {
    return this.isShowingVideoAd;
};

exports.isShowingRewardedVideoAd = function() {
    return this.isShowingRewardedVideoAd;
};

//VIDEO ADS FUNCTIONS
exports.onVideoAdLoaded = function(){}
exports.onVideoAdShown  = function(){}
exports.onVideoAdHidden = function(){}

//REWARDED VIDEO ADS FUNCTIONS
exports.onRewardedVideoAdLoaded = function(){}
exports.onRewardeddVideoAdShown = function(){}
exports.onRewardedVideoAdHidden = function(){}
exports.onRewardedVideoAdCompleated = function(){}

//USER INTERACTION WITH ADS
exports.onAdDownloadButtonClick = function(){}