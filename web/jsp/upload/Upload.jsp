<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
    #fi-button-msg {
        border: 2px solid #ccc;
        padding: 5px 10px;
        background: #eee;
        margin: 5px;
        float: left;
    }
    .x-debug .x-form-file-wrap .x-form-file-input {
        filter: progid:DXImageTransform.Microsoft.Alpha(Opacity=0.6);
        opacity: 0.6;
        background-color: gray;
    }
</style>
<TITLE>文件上传</TITLE>
<script type="text/javascript">
Ext.onReady(function() {
    Ext.Ajax.timeout = 120000;
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL='<%=basePath%>resources/images/s.gif';
    Ext.MessageBox.buttonText.yes = "是";
    Ext.MessageBox.buttonText.no = "否";
    Ext.MessageBox.buttonText.ok = "确定";
    // Class which shows invisible file input field.
    if (window.location.href.indexOf('debug') !== -1) {
        Ext.getBody().addCls('x-debug');
    }

    var msg = function(title, msg) {
        Ext.Msg.show({
            title: title,
            msg: msg,
            minWidth: 200,
            modal: true,
            icon: Ext.Msg.INFO,
            buttons: Ext.Msg.OK
        });
    };



    var formPanel = Ext.create('Ext.form.Panel', {
        frame: true,
        title: '文件上传',
        bodyPadding: '10 10 0',

        defaults: {
            anchor: '100%',
            allowBlank: false,
            msgTarget: 'side',
            labelWidth: 50
        },

        items: [{
            xtype: 'textfield',
            fieldLabel: '文件名'
        },{
            xtype: 'filefield',
            id: 'upload',
            emptyText: '请选择文件',
            fieldLabel: '文件',
            name: 'upload',
            buttonText: '',
            buttonConfig: {
                iconCls: 'upload'
            }
        }],

        buttons: [{
            text: '提交',
            handler: function(){
                var form = this.up('form').getForm();
                if(form.isValid()){
                    form.submit({
                        url:'<%=basePath%>jsp/upload/uploadExcelFile.action',
                        waitMsg: '上传文件读取中...请等待',
                        success: function(form, action) {
                            var data = Ext.JSON.decode(action.response.responseText);
                            //调用处理数据的存储过程
                            var url ='<%=basePath%>jsp/upload/executeProcedure.action';
                            AjaxSend(url,null,handleResult,false);
                            Ext.MessageBox.wait("", "数据入库完毕,用时["+data.time+"] 秒, 调用存储过程处理数据中,请稍候...");
                        },
                        failure: function() {
                            Ext.Msg.alert('提示', '失败!');
                        }
                    });
                }
            }
        },{
            text: '重置',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }]
    });

    var viewport = Ext.create('Ext.Viewport', {
        layout: {
            type: 'anchor'
        },
        defaults: {
            split:true,
            autoScroll:true
        },
        items: [formPanel]
    });

    function handleResult(result){
        Ext.MessageBox.show({title:'提示',msg:result.msg,icon:Ext.MessageBox.INFO,buttons:Ext.MessageBox.OK});
    }

});
</script>
</HEAD>
<body>
</body>
</HTML>