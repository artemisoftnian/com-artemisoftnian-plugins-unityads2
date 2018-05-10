## UnityAds 2 Plugin 

Cordova / PhoneGap Plugin for UnityAds ver 2.x

## Contents

1.  [Description](#description)
2.  [Plugin Background](#plugin-background)
3.  [Features](#features)
4.  [Quick Start](#quick-start)
5.  [Installation](#installation)
6.  [Usage](#usage)
7.  [Methods](#methods)
8.  [Screenshots](#screenshots)
9.  [Credits](#credits)
10. [Donations](#donations)

## DESCRIPTION

This Cordova / PhoneGap plugin enables the integration of Unity Ads video ads into your cordova mobile games or apps in a way that both increases player engagement and puts more money in your pocket over the gamer’s lifetime. Unity Ads offers the highest Average Revenue Per User (ARPU) of any global rewarded video ad network.


## PLUGIN BACKGROUND

A couple of years ago, and after a great mistake, I got banned from Google AdsMob network. After appels to the mercy of Google, explaining what happens... I remain banned for life.  This put me on a very bad situation those days, with a very short list of good monetizing alternatives.  This ends the day I learn about UnityAds

After the end of the UnityAds 1.x Api era, I have to move forward and Implement UnityAds 2.x on my projects. Implement it on the Unity Game Engine was a breeze, But then I didn't found any solutions to implement version 2.x on my html5, cordova, construct 2 or 3 projects.  That my friends, makes this cordova plugin a reality. Oh well, it was basically, necessity!

## FEUTURES

Platforms supported:
- [x] iOS, via SDK v2.x.x (see [Release Notes](https://github.com/Unity-Technologies/unity-ads-android))
- [x] Android, via Android SDK v2.x.x (see [Release Notes](https://github.com/Unity-Technologies/unity-ads-ios))

Ad Types:
- [x] Non Rewarded Video
- [x] Rewarded Video


## QUICK START
```bash
	# create a demo project
    cordova create test1 com.yournamehere.test1 Test1
    cd test1
    cordova platform add android
    cordova platform add ios

    # now add the plugin, cordova CLI will handle dependency automatically
    cordova plugin add com-artemisoftnian-plugins-unityads2

    # now remove the default www content, copy the demo html file to www
    rm -r www/*;
    cp plugins/com-artemisoftnian-plugins-unityads2/example/basic/* www/;

	# now build and run the demo in your device or emulator
    cordova prepare; 
    cordova run android; 
    cordova run ios;
    # or import into Xcode / eclipse
```

## INSTALLATION

* If use with Cordova CLI:
```bash
cordova plugin add com-artemisoftnian-plugins-unityads2
```

* Using NPM
npm install com-artemisoftnian-plugins-unityads2


## USAGE

Step 1: Go and create a new project for the desire platform in [UnityAds portal](https://operate.dashboard.unity3d.com), 
Step 2: Write the specified id's in your javascript code.

```javascript
// select the right Ad Id according to platform
var gameId = "Android or iOS Game ID";
var videoAdPlacementId = "Video Placement ID";
var rewardedVideoAdPlacementId = "Rewarded Placement ID";
```

After playing around and complete your tests, remember to put `isTest = false` when building for production.

## METHODS

```javascript
// Initialize UnityAds
UnityAdsInit(gameId, isTest, isDebug, fn);

// Video Ads Related (Regular/Rewarded)
ShowVideoAd(videoPlacementId, fn);
GetPlacementState(videoAdPlacementId, fn);
```


## Screenshots

Android Video Ads

| ![ScreenShot](https://github.com/artemisoftnian/com-artemisoftnian-plugins-unityads2/raw/master/doc/android/android_1.png) |  ![ScreenShot](https://github.com/artemisoftnian/com-artemisoftnian-plugins-unityads2/raw/master/doc/android/android_2.png) | ![ScreenShot](https://github.com/artemisoftnian/com-artemisoftnian-plugins-unityads2/raw/master/doc/android/android_3.png) |
|--|--|--|

iOS Video Ads

| ![ScreenShot](https://github.com/artemisoftnian/com-artemisoftnian-plugins-unityads2/raw/master/doc/ios/ios_1.png) | ![ScreenShot](https://github.com/artemisoftnian/com-artemisoftnian-plugins-unityads2/raw/master/doc/ios/ios_2.png) | ![ScreenShot](https://github.com/artemisoftnian/com-artemisoftnian-plugins-unityads2/raw/master/doc/ios/ios_3.png) |
|--|--|--|

* Important DON'T GET BANNED (Remember my little mistake): Please read carfully the [Unity Monetization Services Terms of Service](https://unity3d.com/legal/monetization-services-terms-of-service). And most important: DON'T CLICK ON YOUR OWN APP ADS. Don't said I haven't warn You!



## Credits

This project is created and maintained by Waldemar Medina.
Available for project outsourcing and or consulting services. [Let me hear about it](mailto:waldemar_medina@hotmail.com). I can help in your project.

Check Out my [UnityAds addon for Construct 3](https://www.construct.net/make-games/addons/100/unityads)

## Donations

Now that You end your reading, (I know You where curious) please consider supporting Artemisoftnian with a donation, a cup of coffee or maybe a slice of pizza :)

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=4RK9RWFNTBNUA)

or You can become a Patreon and support even more deeply, mysterious and secret stuff:

[![patreon](https://c5.patreon.com/external/logo/become_a_patron_button.png)](https://www.patreon.com/artemisoftnian)