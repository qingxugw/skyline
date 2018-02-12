/*
该文件基于Extjs4.1
提供公共的ajax交互的方法
Copyright (c) 2011-2012 skyline Inc
Build date: 2012-08-27 14:30:47
*/
var YMD = "Y-m-d";
var YMDHIMS = "Y-m-d H:i:s";
/**
 * ajax调用
 * 后台返回数据格式必须为json且含有success属性 如{success:true} 或者 {success:false,msg:'错误信息'}
 */
function AjaxSend(url,params,callback,isTooltip){
    if(typeof(isTooltip) == 'undefined'){
        //设置isTooltip  默认值为true
        isTooltip = true;
    }
    Ext.MessageBox.wait("", "请求中,请稍候...");
    Ext.Ajax.request({
        url:url,
        method: 'post',
        params: params,
        success:function(response,options){
            var result = Ext.JSON.decode(response.responseText);

            if(result.success){
                //todo 这里可以用来完成辅助权限、session 等判断的页面跳转
                if (isTooltip){
                    Ext.MessageBox.show({title:'提示',msg:'操作已成功!',icon:Ext.MessageBox.INFO,buttons:Ext.MessageBox.OK,
                        fn:function(result){
                            callback && callback(result);
                        }
                    });
                }else{
                    Ext.MessageBox.hide();
                    callback && callback(result);
                }
            }else{
                Ext.MessageBox.show({title:'提示',msg:result.msg,icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
            }
        },
        failure:function(response,options){
            //todo 这里最好抛出自定义异常信息
            Ext.MessageBox.show({title:'提示',msg:'操作失败!',buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
        }
    });
}
function dateFormatYMDHIMS(value){
    if(null != value){
        return Ext.Date.format(new Date(value),YMDHIMS);
    }else{
        return null;
    }
}
function dateFormatYMD(value){
    if(null != value){
        return Ext.Date.format(new Date(value),YMD);
    }else{
        return null;
    }
}
/**
 * 修复Grid的loadMask的BUG
 */
Ext.override(Ext.view.AbstractView, {
    onRender: function(){
        var me = this;

        this.callOverridden();

        if (me.mask && Ext.isObject(me.store)) {
            me.setMaskBind(me.store);
        }
    }
});
/**
 * 验证起止日期
 * 自定义验证
 */
Ext.apply(Ext.form.field.VTypes, {
    daterange: function(val, field) {
        var date = field.parseDate(val);

        if (!date) {
            return false;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = field.up('form').down('#' + field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        }
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = field.up('form').down('#' + field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    },

    daterangeText: 'Start date must be less than end date',  //skyline-lang-zh_CN.js 文件中 自己进行国际化

    password: function(val, field) {
        if (field.initialPassField) {
            var pwd = field.up('form').down('#' + field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },

    passwordText: 'Passwords do not match',     //skyline-lang-zh_CN.js 文件中 自己进行国际化
    //手机号码验证
    mobile: function(val, field) {
        var re = new RegExp("^[1][3-8]+\\d{9}$");
        return re.test(val)
    },
    mobileText: 'This is not mobilenumber',     //skyline-lang-zh_CN.js 文件中 自己进行国际化
    //座机号码验证
    phone: function(val, field) {
        var re = new RegExp("\\d{3}-\\d{8}|\\d{4}-\\d{7}");
        return re.test(val)
    },
    phoneText: 'This is not phonenumber',     //skyline-lang-zh_CN.js 文件中 自己进行国际化
    //电话号码验证
    qq: function(val, field) {
        var re = new RegExp("[1-9][0-9]{4,}");
        return re.test(val)
    },
    qqText: 'This is not qq number'     //skyline-lang-zh_CN.js 文件中 自己进行国际化
});