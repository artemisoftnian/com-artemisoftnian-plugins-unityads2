Cordova UnityAds 2 plugin
====================
# Overview #
Show unityads video ad and rewarded video ad (pays high $0.05 per ad view).

[android, ios] [cordova cli] [cocoon] [phonegap build service]

Requires unityads account http://unityads.unity3d.com

ANDROID VERSION - UnityAds SDK v2.1.2 November 27th, 2017<br>
IOS VERSION - UnityAds SDK v1.5.6 January 5th, 2016

This is open source cordova plugin.

# IMPORTANT - THIS PLUGIN STILL NOT USABLE #

# Change log #
```c

1.0.0
	Updated SDK v2.1.2
```

# Install plugin #

## Cordova cli ##
https://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-Line%20Interface - npm install -g cordova@6.0.0
```c
cordova plugin add cordova-plugin-ad-unityads2
(when build error, use github url: cordova plugin add cordova plugin add https://github.com/hackinc2000/cordova-plugin-ad-unityads2)
```

## Xdk ##
https://software.intel.com/en-us/intel-xdk - Download XDK - XDK PORJECTS - [specific project] - CORDOVA HYBRID MOBILE APP SETTINGS - Plugin Management - Add Plugins to this Project - Third Party Plugins -
```c
Plugin Source: Cordova plugin registry
Plugin ID: cordova-plugin-ad-unityads2
```

```c
Fix crosswalk build error:
crosswalk 11 build error so use crosswalk upper version (crosswalk 12 or 14)
```
<img src="https://raw.githubusercontent.com/hackinc2000/cordova-plugin-ad-unityads2/master/doc/fix_crosswalk_build_error.png"><br>

## Cocoon ##
https://cocoon.io - Create project - [specific project] - Setting - Plugins - Custom - Git Url: https://github.com/hackinc2000/cordova-plugin-ad-unityads2.git - INSTALL - Save<br>

## Phonegap build service (config.xml) ##
https://build.phonegap.com/ - Apps - [specific project] - Update code - Zip file including config.xml
```c
<gap:plugin name="cordova-plugin-ad-unityads2" source="npm" />
```

## Construct2 ##
construct2 plugin - Links are dead, keep for future reference<br>
https://dl.dropboxusercontent.com/u/186681453/pluginsforcordova/index.html<br>
How to install c2 native plugins in xdk, cocoon and cordova cli<br>
https://plus.google.com/102658703990850475314/posts/XS5jjEApJYV

# Server setting #
```c
```

<img src="https://raw.githubusercontent.com/hackinc2000/cordova-plugin-ad-unityads2/master/doc/gameId1.png"><br>
<img src="https://raw.githubusercontent.com/hackinc2000/cordova-plugin-ad-unityads2/master/doc/gameId2.png"><br>
<img src="https://raw.githubusercontent.com/hackinc2000/cordova-plugin-ad-unityads2/master/doc/gameId3.png"><br>
<img src="https://raw.githubusercontent.com/hackinc2000/cordova-plugin-ad-unityads2/master/doc/gameId4.png">

# API #
```javascript
var gameId = "REPLACE_THIS_WITH_YOUR_GAME_ID";
var videoAdPlacementId = "defaultZone";
var rewardedVideoAdPlacementId = "rewardedVideoZone";
var isTest = true;
/*
var gameId;
var videoAdPlacementId;
var rewardedVideoAdPlacementId;
var isTest = true;
//android
if (navigator.userAgent.match(/Android/i)) {
	gameId = "REPLACE_THIS_WITH_YOUR_GAME_ID";
	videoAdPlacementId = "defaultZone";
	rewardedVideoAdPlacementId = "rewardedVideoZone";
}
//ios
else if (navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i)) {
	gameId = "REPLACE_THIS_WITH_YOUR_GAME_ID";
	videoAdPlacementId = "defaultZone";
	rewardedVideoAdPlacementId = "rewardedVideoZone";
}
*/

document.addEventListener("deviceready", function(){
	//if no license key, 2% ad traffic its been share for devevelopmet support.
	//you can get paid license key: https://store.artemisoftnian.com/cordova_plugin_paid_license
	//window.unityads.setLicenseKey("yourEmailId@yourEmaildDamin.com", "yourLicenseKey");

	window.unityads.setUp(gameId, videoAdPlacementId, rewardedVideoAdPlacementId, isTest);
	
	//
	window.unityads.onVideoAdLoaded = function() {
		alert('onVideoAdLoaded');
	};	
	window.unityads.onVideoAdShown = function() {
		alert('onVideoAdShown');
	};
	window.unityads.onVideoAdHidden = function() {
		alert('onVideoAdHidden');
	};
	//
	window.unityads.onRewardedVideoAdLoaded = function() {
		alert('onRewardedVideoAdLoaded');
	};	
	window.unityads.onRewardedVideoAdShown = function() {
		alert('onRewardedVideoAdShown');
	};
	window.unityads.onRewardedVideoAdHidden = function() {
		alert('onRewardedVideoAdHidden');
	};	
	window.unityads.onRewardedVideoAdCompleted = function() {
		alert('onRewardedVideoAdCompleted');
	};
}, false);

window.unityads.showVideoAd();

window.unityads.showRewardedVideoAd();

alert(window.unityads.loadedVideoAd());//boolean: true or false
alert(window.unityads.loadedRewardedVideoAd());//boolean: true or false

alert(window.unityads.isShowingVideoAd());//boolean: true or false
alert(window.unityads.isShowingRewardedVideoAd());//boolean: true or false
```
# Examples #
<a href="https://github.com/hackinc2000/cordova-plugin-ad-unityads2/blob/master/example/basic/index.html">example/basic/index.html</a><br>

# Test #

[![](http://img.youtube.com/vi/L_TgOf-XwDY/0.jpg)](https://www.youtube.com/watch?v=L_TgOf-XwDY&feature=youtu.be "Youtube")

You can also run following test apk.
https://dl.dropboxusercontent.com/u/186681453/pluginsforcordova/unityads/apk.html

# Credits #

 Honoring who needts to be honered! Thanks to Cranberrygame for his fantastic cordova-plugin-ad-unityads plugin.

# Original Plugin Repository Information #
You can see Cordova Plugins in one page: http://cranberrygame.github.io?referrer=github
