(function($){
    var setting = {
        actionUrl   :"",
        auto : false ,
        multi       :false,
        fileTypeDesc:"任意格式",
        fileTypeExts:"*.*",
        entityName   :""
    };
    function uploadify_onSelectError(file, errorCode, errorMsg) {
        var msgText = "上传失败<br/>";
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                msgText += "每次最多上传 3个文件";
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                msgText += "文件大小为0";
                break;
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;
                break;
            default:
                msgText += "错误代码：" + errorCode + "\n" + errorMsg;
        }
        var data = {state:"400",title:"上传过程出错",message:msgText};
        _show(data);
    };
    function uploadify_onUploadError(){
        if (errorCode == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED
            || errorCode == SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {
            return;
        }
        var msgText = "上传失败<br/>";
        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
                msgText += "HTTP 错误\n" + errorMsg;
                break;
            case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
                msgText += "上传文件丢失，请重新上传";
                break;
            case SWFUpload.UPLOAD_ERROR.IO_ERROR:
                msgText += "IO错误";
                break;
            case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
                msgText += "安全性错误\n" + errorMsg;
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                msgText += "每次最多上传 " + this.settings.uploadLimit + "个";
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
                msgText += errorMsg;
                break;
            case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
                msgText += "找不到指定文件，请重新操作";
                break;
            case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
                msgText += "参数错误";
                break;
            default:
                msgText += "文件:" + file.name + "\n错误码:" + errorCode + "\n"
                + errorMsg + "\n" + errorString;
        }
        var data = {state:"400",title:"上传过程出错",message:msgText};
        _show(data);
        return parameters;
    }
    //执行删除操作

    var methods = {
        init:function(options){
            return this.each(function(){
                var o =$.extend(o, setting);
                if (options) $.extend(o,options);
                var divMsg = '<div id="import_message_alert'+ o.entityName+'"></div>';
               $(this).parent().append(divMsg);
                
                var o =$.extend(o, setting);
                if (options) $.extend(o,options);
                var resultMsg = '<div id="resultMsg'+ o.entityName+'" class="alert alert-success"><button class="close" data-dismiss="alert">×</button><i class="fa-fw fa fa-info"></i></div>';
                $(this).uploadify({
                	auto : o.auto ,
                    height         : 20,
                    width           : 30,
                    buttonClass     : "btn",
                    multi           : o.multi,
                    fileSizeLimit   : '30MB',
                    fileDataName    : 'uploadify',
                    progressData    : 'percentage',
                    removeCompleted: false,
                    fileTypeDesc  : o.fileTypeDesc,
                    fileTypeExts    : o.fileTypeExts,
                    buttonText      : '添加',
                    swf             : '/static/uploadify/uploadify.swf',
                    uploader        : o.actionUrl,
                    uploadLimit     : 10,
                    overrideEvents  :['onDialogClose','onSelectError','onUploadError'],
                    onUploadStart   :function(file){
                        $("#import_message_alert"+ o.entityName).empty();
                        var result = '<strong>正在努力为您上传！</strong>'+file.name+' 请耐心等待...';
                        $("#import_message_alert"+ o.entityName).append(resultMsg);
                        $("#resultMsg").append(result);
                    },
                    onInit :function(){
                        $("div#"+$(this).attr("id")+"-button").removeClass("uploadify-button");
                    },
                    onUploadError   : uploadify_onUploadError,
                    onSelectError   : uploadify_onSelectError,
                    onUploadSuccess : function(file, data, response) {
                        $("#import_message_alert"+ o.entityName).empty();
                        if(data.success= "true"){
                        	top.$.jBox.tip("上传成功!");
                        }else{
                        	top.$.jBox.tip("上传失败!");
                        }
                    }
                });
            });
        }
    };

    $.fn.upload = function( method ) {
        if ( methods[method] ) {
            return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.multiselect2side' );
        }
    };
})(jQuery);