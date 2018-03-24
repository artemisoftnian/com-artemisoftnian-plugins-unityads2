var exec = require('cordova/exec');

var PLUGIN_NAME = 'UnityAds2';

// MAIN FUNCTIONS
exports.UnityAdsInit = function(gameId, isTest, isDebug, fn) {

    var METHOD_NAME = "UnityAdsInit"; 
    var args = [gameId, isTest, isDebug]; 

    function success (str){
        if(str == "INIT_SUCESS"){
            self.unity_ads_state.initialized = true;
        }
        fn(null, str);
    }
    function error (str){
        fn(str, null);
    } 

    exec(success, error, PLUGIN_NAME,METHOD_NAME,args); 
};

exports.ShowVideoAd = function(videoAdPlacementId, fn) {

    var METHOD_NAME = "ShowVideoAd"; 
    var args = [videoAdPlacementId];  

    function success (str){
        fn(null, str);
    }
    function error (str){
        fn(str, null);
    } 
    exec(success, error, PLUGIN_NAME,METHOD_NAME,args);  

};

exports.GetPlacementState = function(videoAdPlacementId, fn) {

    var METHOD_NAME = "GetPlacementState"; 
    var args = [videoAdPlacementId];  

    function success (str){
        fn(null, str);
    }
    function error (str){
        fn(str, null);
    } 

    exec(success, error, PLUGIN_NAME,METHOD_NAME,args);      
};