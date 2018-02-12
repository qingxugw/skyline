/*
  skyline 个人国际化js文件
  如果想多了解skyline请访问skyline 的开源博客
  http://my.oschina.net/skyline520
 */
/**
 * by skyline
 */
Ext.onReady(function(){
    if(Ext.form.field.VTypes){
        Ext.apply(Ext.form.field.VTypes, {
            daterangeText : '起始日期必须早于结束日期',//update
            passwordText : '两次输入密码不一致',//add
            mobileText : '请输入正确的手机号,你妹的',//add
            phoneText : '请输入正确的座机号,你丫的',//add
            qqText : 'QQ号都输不对,你火星来的吧'//add
        });
    }
});
