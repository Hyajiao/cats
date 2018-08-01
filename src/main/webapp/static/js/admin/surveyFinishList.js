$(document).ready(function () {

    var surveyId = $("#surveyId").val();

    //检索
    $("#searchBtn").bind("click", function () {
        var form = document.forms[0];
        form.action = "admin/survey/" + surveyId + "/finish";
        $("#page").val(1);
        form.submit();
    });

    //回车提交表单
    $("#keyword").keydown(function (event) {
        if (event.keyCode == 13) {
            $("#searchBtn").click();
        }
    });

    $('#startDate').datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });

    $('#endDate').datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });

    /**发放奖励*/
    $(".rewardBtn").bind("click", function(){
        var btnId = this.id;
        csfrId = btnId.replace("rewardBtn_", "");

        var data = {
            id : csfrId
        };

        $.ajax({
            type: "post",
            url: "admin/survey/" + surveyId + "/issueReward",
            data: data,
            dataType: "json",
            success: function (json) {
                if(json.success){
                    showMsg("发放成功！");
                    $("#" + btnId).parent().html("已发送");
                }else{
                    showMsg(json.msg);
                }
            }
        });
    });

    /**导出按钮*/
    $("#exportBtn").click(function(){
        $("#searchForm")[0].action = "admin/survey/" + surveyId + "/finish/export";
        $("#searchForm").submit();
    });

    // 全选，取消选择
    $("#cbAll").click(function () {
        var isSelected = $("#cbAll").prop("checked");
        if(isSelected == true){
            $("input[name='cb']").each(function(){
                $(this)[0].checked = true;
            });
        }else{
            $("input[name='cb']").each(function(){
                $(this)[0].checked = false;
            });
        }
    });

    // 批量发放奖励
    $("#batchRewardBtn").click(function () {
        var checkArray = "";
        $("input[name='cb']:checked").each(function () {
            checkArray = checkArray + $(this).attr("id") + ",";
        });
        if(checkArray.length > 0){
            checkArray.substr(0, checkArray.length - 1);
        }

        if(checkArray == ""){
            showMsg("请选择要发放奖励的记录");
        }else{
            $.ajax({
                type: "post",
                url: "admin/survey/" + surveyId + "/issueReward/batch",
                data: { ids : checkArray },
                dataType: "json",
                success: function (json) {
                    if(json.success){
                        showMsg("发放成功！");
                        var form = document.forms[0];
                        form.action = "admin/survey/" + surveyId + "/finish";
                        form.submit();
                    }else{
                        showMsg(json.msg);
                    }
                }
            });
        }
    });

    // 发放全部奖励
    $("#allRewardBtn").click(function () {
        $('#viewModal').modal('show');
    });

    // 发放全部奖励-确定
    $("#sureBtn").click(function () {
        $.ajax({
            type: "post",
            url: "admin/survey/" + surveyId + "/issueReward/all",
            dataType: "json",
            success: function (json) {
                $('#viewModal').modal('hide');
                if(json.success){
                    showMsg("发放成功！");
                    var form = document.forms[0];
                    form.action = "admin/survey/" + surveyId + "/finish";
                    form.submit();
                }else{
                    showMsg(json.msg);
                }
            }
        });
    });

});