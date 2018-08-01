/* 图片自动上传 */
var uploadCount = $("#uploadCount").val() || 0,
    uploadList = [],
    deleteList = [];
var uploadCountDom = $("#uploadCount");
weui.uploader('#uploaderCustom', {
    url: GLOBAL_CONFIG.basePath + 'upload/uploadPic',
    auto: true,
    type: 'file',
    fileVal: 'file',
    onBeforeQueued: function(files) {
        if (["image/jpg", "image/jpeg", "image/png", "image/gif"].indexOf(this.type) < 0) {
            showMsg('请上传图片');
            return false;
        }
        if (this.size > 10 * 1024 * 1024) {
            showMsg('请上传不超过10M的图片');
            return false;
        }
        if (files.length > 5) {
            // 防止一下子选中过多文件
            showMsg('最多只能上传5张图片，请重新选择');
            return false;
        }
        if (uploadCount + 1 > 5) {
            showMsg('最多只能上传5张图片');
            return false;
        }

        ++uploadCount;
        uploadCountDom.val(uploadCount);
    },
    onQueued: function() {
        uploadList.push(this);
    },
    onBeforeSend: function(data, headers){
        $.extend(data, { type: "speaker_protocol" }); // 可以扩展此对象来控制上传参数
    }
});

// 缩略图预览
$('#uploaderCustomFiles').bind('click', function (e) {
    var target = e.target;

    while (!target.classList.contains('weui-uploader__file') && target) {
        target = target.parentNode;
    }
    if (!target) return;

    var url = target.getAttribute('style') || '';
    var id = target.getAttribute('data-id');

    if (url) {
        url = url.match(/url\((.*?)\)/)[1].replace(/"/g, '');
    }
    var gallery = weui.gallery(url, {
        className: 'custom-name',
        onDelete: function() {
            weui.confirm('确定删除该图片？', function () {
                --uploadCount;
                uploadCountDom.val(uploadCount);

                for (var i = 0, len = uploadList.length; i < len; ++i) {
                    var file = uploadList[i];
                    if (file.id == id) {
                        file.stop();
                        break;
                    }
                }

                // 以前上传的图片，那需要从服务器删除掉
                if(id.indexOf("old_") == 0){
                    var trueId = id.substr(4, id.length);
                    deleteList.push(trueId);
                }
                target.remove();
                gallery.hide();
            });
        }
    });
});