/**
 * 图片上传
 * 需要包含两部分，其中name必须为file，id必须按照规则，例如
 * <input type="file" name ="file" id="cuIdPhotoFile">
 * <input type="hidden" id="cuIdPhoto" name="cuIdPhoto">
 * <img id="cuIdPhotoSrc" src=""  style="display: none;"/>
 *
 * @param fileInputId 上传文件input=hidden的id。例如cuIdPhoto
 * @param type 上传文件的业务类型
 * @param width 图片要被压缩的宽度
 */
function picUpload(fileInputId, type, width) {
    var data = {
        "type" : type,
        "width" : width
    };

    $('#' + fileInputId + "File").fileupload({
        type: "POST",
        cache:false,
        async: false, //同步，即此代码执行时，其他的不可执行。
        dataType: "json",
        formData: data,
        url: 'upload/uploadPic',
        success: function(json) {
            if (json.success) {
                var array = json.data;

                $("#" + fileInputId + "Src").attr("src", array);
                $("#" + fileInputId + "Src").show();
                $("#" + fileInputId).val(array);
            }else{
                if(json.msg){
                    showMsg(json.msg);
                }else{
                    showMsg("上传图片过程中有错误发生，请稍后再试。");
                }
            }
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            console.log(progress);

            // $('#progress .progress-bar').css(
            //     'width',
            //     progress + '%'
            // );
        }
    });
}


/**
 * PPT上传
 * 需要包含两部分，其中name必须为file，id必须按照规则，例如
 * <input type="file" name ="file" id="cuIdPhotoFile">
 * <input type="hidden" id="cuIdPhoto" name="cuIdPhoto">
 *
 * @param fileInputId 上传文件input=hidden的id。例如cuIdPhoto
 * @param type 上传文件的业务类型
 */
function pptUpload(fileInputId, type) {
    var data = {
        "type" : type
    };

    $('#' + fileInputId + "File").fileupload({
        type: "POST",
        cache:false,
        async: false, //同步，即此代码执行时，其他的不可执行。
        dataType: "json",
        formData: data,
        url: 'upload/uploadPpt',
        success: function(json) {
            if (json.success) {
                var array = json.data;
                $("#" + fileInputId).val(array);
                showMsg("上传完毕");
            }else{
                if(json.msg){
                    showMsg(json.msg);
                }else{
                    showMsg("上传图片过程中有错误发生，请稍后再试。");
                }
            }
        }
    });
}

/**
 * 音频文件上传
 * 需要包含两部分，其中name必须为file，id必须按照规则，例如
 * <input type="file" name ="file" id="cuIdPhotoFile">
 * <input type="hidden" id="cuIdPhoto" name="cuIdPhoto">
 *
 * @param fileInputId 上传文件input=hidden的id。例如cuIdPhoto
 * @param type 上传文件的业务类型
 * @param minDuration 最小的音频时长（秒）
 */
function audioUpload(fileInputId, type, minDuration, callback) {
    var data = {
        "type" : type,
        "minDuration" : minDuration
    };

    $('#' + fileInputId + "File").fileupload({
        type: "POST",
        cache:false,
        async: false, //同步，即此代码执行时，其他的不可执行。
        dataType: "json",
        formData: data,
        url: 'upload/uploadMultimediaFile',
        add: function (e, data) {
            $("#coverDiv").show();
            setTimeout(function(){
                data.submit();
            }, 200)
        },
        success: function(json) {
            $("#coverDiv").hide();
            if (json.success) {
                var array = json.data;
                $("#" + fileInputId).val(array);
                if(callback){
                    callback.call(this, array);
                }
                showMsg("上传完毕");
            }else{
                if(json.msg){
                    showMsg(json.msg);
                }else{
                    showMsg("上传图片过程中有错误发生，请稍后再试。");
                }
            }
        }
    });
}

/**
 * 图片上传
 * 需要包含两部分，其中name必须为file，id必须按照规则，例如
 * <input type="file" name ="file" id="cuIdPhotoFile">
 * <input type="hidden" id="cuIdPhoto" name="cuIdPhoto">
 * <img id="cuIdPhotoSrc" src=""  style="display: none;"/>
 *
 * @param fileInputId 上传文件input=hidden的id。例如cuIdPhoto
 * @param type 上传文件的业务类型
 * @param width 图片要被压缩的宽度
 */
function fileUpload(fileInputId, type, callBack) {
    var data = {
        "type" : type
    };

    $('#' + fileInputId + "File").fileupload({
        type: "POST",
        cache:false,
        async: false, //同步，即此代码执行时，其他的不可执行。
        dataType: "json",
        formData: data,
        url: 'upload/uploadFile',
        success: function(json) {
            if (json.success) {
                showMsg("上传成功。");
                eval(callBack);
            }else{
                if(json.msg){
                    showMsg(json.msg);
                }else{
                    showMsg("上传文件过程中有错误发生，请稍后再试。");
                }
            }
        }
    });
}
