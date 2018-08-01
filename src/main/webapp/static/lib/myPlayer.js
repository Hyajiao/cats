$(function(){
	if(play_url && play_url != ''){
        var flashvars={
			f:play_url,
			c:0,
            loaded:'loadedHandler'
	/*	    p:1*/
		};
		var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
		var video=[play_url +'->video/mp4'];
		CKobject.embed('/static/lib/ckplayer/ckplayer.swf','video_box','ckplayer_a1','100%','100%',false,flashvars,video,params);

	}
})

function loadedHandler(){
    if (CKobject.getObjectById('ckplayer_a1').getType()) {
        // alert('播放器已加载，调用的是HTML5播放模块');
        CKobject.getObjectById('ckplayer_a1').addListener('play',playHandler);
        // CKobject.getObjectById('ckplayer_a1').addListener('ended',endedHandler);
        // CKobject.getObjectById('ckplayer_a1').addListener('paused',pausedHandler);
    } else {
        // alert('播放器已加载，调用的是Flash播放模块');
        CKobject.getObjectById('ckplayer_a1').addListener('play','playHandler');
        // CKobject.getObjectById('ckplayer_a1').addListener('ended','endedHandler');
        // CKobject.getObjectById('ckplayer_a1').addListener('paused','pausedHandler');
    }
}

function playHandler(){
    SynapseAnalytics.clickHotArea(".iemVideo video", "点击播放视频", null);
}
