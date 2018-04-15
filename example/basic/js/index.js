/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var gameId = "TEST";
var videoAdPlacementId = "TEST";
var rewardedVideoAdPlacementId = "TEST_REWARDED";
var isTest = true;
var isDebug = true;
 
var app = {

  
    // Application Constructor
    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
        document.getElementById("videoAd").addEventListener("click", this.onShowVideoAds.bind(this), false);
        document.getElementById("rvideoAd").addEventListener("click", this.onShowRewardedVideoAds.bind(this),false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {

        unityads2.UnityAdsInit(gameId, isTest, isDebug, function callback(error, result){
            
            if(error){
                console.log(error)
            }
            else{
                console.log(result);
            }

        });

    },
    onShowVideoAds: function(){

        unityads2.ShowVideoAd(videoAdPlacementId, function callback(error, result){
            
            if(error){
                console.log(error)
            }
            else{
                console.log(result);
            }

        });
    },

    onShowRewardedVideoAds: function(){

        unityads2.ShowVideoAd(rewardedVideoAdPlacementId, function callback(error, result){
            
            if(error){
                console.log(error)
            }
            else{
                console.log(result);
            }

        });
    },

    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

app.initialize();