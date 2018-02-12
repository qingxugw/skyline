<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>用户管理</TITLE>
<script type="text/javascript">
Ext.onReady(function() {
//    Ext.Loader.setConfig({enabled:true});
    Ext.tip.QuickTipManager.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL='/skyline/resources/images/s.gif';
    Ext.MessageBox.buttonText.yes = "是";
    Ext.MessageBox.buttonText.no = "否";
    Ext.MessageBox.buttonText.ok = "确定";
    var opType = "";
    var record = "";
    var isTooltip = false;
    Ext.define('userModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'userId',type: 'string'},
            {name: 'userNo',type: 'string'},
            {name: 'userName',type: 'string'},
            {name: 'password',type: 'string'},
            {name: 'subsectionName',type: 'string'},
            {name: 'subsectionId',type: 'string'},
            {name: 'userPhone',type: 'string'},
            {name: 'userTel',type: 'string'},
            {name: 'userMobile',type: 'string'},
            {name: 'userQQ',type: 'string'},
            {name: 'userMsn',type: 'string'},
            {name: 'userMail',type: 'string'},
            {name: 'userRemark',type: 'string'},
            {name: 'version',type: 'int'},
            {name: 'status',type: 'string'},
            {name: 'createUser',type: 'string'},
            {name: 'modifyUser',type: 'string'},
            {name: 'createDate',type: 'string', defaultValue: undefined,convert:dateFormatYMDHIMS},
            {name: 'modifyDate',type: 'string', defaultValue: undefined,convert:dateFormatYMDHIMS}
        ]
    });
    var statusStore = Ext.create('Ext.data.Store',{
        model:'comboxModel',
        data:[{key:'',value:'全部'},{key:'1',value:'启用'},{key:'0',value:'禁用'}],
        autoLoad:true,
        listeners:{
            load:function(){
            }
        }
    });
    var status = Ext.create('Ext.form.ComboBox',{
        id:'status',
        labelAlign:'right',
        fieldLabel:'状态',
        store:statusStore,
        queryMode:'local',
        displayField:'value',
        valueField:'key'
    });
    //开始创建查询表单
    var queryFormPanel = Ext.create('Ext.form.Panel',{
        height:'100%',
        width:1200,
        model:'userModel',
        header:false,
        bodyBorder:false,
        bodyStyle:'padding:20px 10px 0 10px',
        frame:true,
        defaults:{
            xtype:'textfield',
            labelAlign:'right',
            labelWidth:100,
            width:200
        },
        buttonAlign:'center',
        layout:{
            type:'table',
            columns:3
        },
        items:[{
            id:'userNo',
            fieldLabel:'用户名'
        },{
            id:'userName',
            fieldLabel:'用户中文名'
        },status],
        buttons:[{
            text:'查询',
            id:'queryButton',
            iconCls:'search',
            handler:function (){
                gridStore.loadPage(1);
            }
        },{
            text:'添加用户',
            iconCls:'add',
            handler:function (){
                opType = "add";
                addWin.setTitle("添加用户");
                addWin.setIconCls("add");
                Ext.getCmp('password').setDisabled(false);
                Ext.getCmp('password').setVisible(true);
                Ext.getCmp('confirmpassword').setDisabled(false);
                Ext.getCmp('confirmpassword').setVisible(true);
                addWin.show();
            }
        },{
            text:'编辑用户',
            iconCls:'edit',
            handler:function (){
                if(record != null && record != ""){
                    opType = "edit";
                    addWin.setTitle("编辑用户");
                    addWin.setIconCls("edit")
                    addPanel.getForm().loadRecord(record);
                    Ext.getCmp('password').setDisabled(true);
                    Ext.getCmp('password').setVisible(false);
                    Ext.getCmp('confirmpassword').setDisabled(true);
                    Ext.getCmp('confirmpassword').setVisible(false);
                    addWin.show();
                }else{
                    Ext.MessageBox.show({title:'提示',msg:'亲,请先选择一行数据哦!',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                    return ;
                }
            }
        },{
            text:'删除用户',
            iconCls:'delete',
            handler:function (){
                if(record != null && record != ""){
                    Ext.MessageBox.confirm("提示","确定要删除当前记录吗",function (e){
                        opType = "delete";
                        if( e == "yes"){
                            var url = '<%=basePath%>jsp/base/deleteUser.action';
                            var params = new Object();
                            params.opType = opType;
                            params.userId = record.data.userId;
                            params.version = record.data.version;
                            var isTooltip = false;
                            //调用ajax 发送数据到后台
                            AjaxSend(url,params,handleDeleteResult,isTooltip);
                        }
                    });
                }else{
                    Ext.MessageBox.show({title:'提示',msg:'亲,请先选择一行数据哦!',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                    return ;
                }
            }
        }]
    });
    //构建gridpanel
    var gridStore = Ext.create('Ext.data.Store',{
        model:'userModel',
        pageSize:20,
        loadMask:true,
        proxy:{
            type:'ajax',
            url:'<%=basePath%>jsp/base/queryUserList.action',
            actionMethods:{
                //这个是指定查询的时候使用post方法   解决默认get方式 提交参数乱码的问题
                read:'POST'
            },
            reader:{
                root:'root',
                totalProperty:'totalProperty'
            }
        },
        autoLoad:true,
        listeners:{
            beforeload:function (){
                record = null;
                gridStore.proxy.extraParams = {
                    userNo:Ext.getCmp('userNo').getValue(),
                    userName:Ext.getCmp('userName').getValue(),
                    status:Ext.getCmp('status').getValue()
                }
            }
        }
    });
    var pagingBar = Ext.create('Ext.PagingToolbar',{
        store:gridStore,
        displayInfo:true,
        displayMsg:'当前显示{0} - {1} 条,共 {2} 条',
        emptyMsg:'我真的没有数据了,亲'
    });
    var grid = Ext.create('Ext.grid.Panel',{
        height:400,
        width:1150,
        header:false,
        bodyBorder:false,
        autoScroll:true,
        store:gridStore,
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {text:'用户名',dataIndex:'userNo'},
            {text:'用户中文名',dataIndex:'userName'},
            {text:'分部',dataIndex:'subsectionName'},
            {text:'办公电话',dataIndex:'userPhone'},
            {text:'家庭电话',dataIndex:'userTel'},
            {text:'手机',dataIndex:'userMobile'},
            {text:'QQ',dataIndex:'userQQ'},
            {text:'MSN',dataIndex:'userMsn'},
            {text:'邮件',dataIndex:'userMail'},
            {text:'状态',dataIndex:'status',renderer:changeStatus},
            {text:'创建人',dataIndex:'createUser'},
            {text:'创建时间',dataIndex:'createDate'},
            {text:'最后一次修改人',dataIndex:'modifyUser'},
            {text:'最后一次修改时间',dataIndex:'modifyDate'}
        ],
        bbar:pagingBar,
        listeners: {
            'selectionchange': function(view, records) {
                if (records[0]) {
                    record = records[0];
                }
            }
        }
    });
    //添加 编辑页面弹出窗口
    var addPanel = Ext.create('Ext.form.Panel',{
        height:300,
        width:600,
        model:'userModel',
        frame:true,
        header:false,
        bodyBorder:false,
        bodyStyle:'padding:20px 0px 0px 0px',
        defaults:{
            xtype:'textfield',
            labelAlign:'right',
            labelWidth:100,
            width:250
        },
        layout:{
            type:'table',
            columns:2
        },
        items:[{
            fieldLabel:'登录号',
            afterLabelTextTpl: required,
            name:'userNo',
            maxLength:20,
            allowBlank:false
        },{
            name:'userName',
            afterLabelTextTpl: required,
            allowBlank:false,
            maxLength:50,
            fieldLabel:'中文名'
        },{
            id:'password',
            itemId:'pass',
            name:'password',
            inputType: 'password',
            afterLabelTextTpl: required,
            fieldLabel:'密码',
            maxLength:50,
            allowBlank:false
        },{
            id:'confirmpassword',
            name:'confirmpassword',
            inputType: 'password',
            vtype:'password',
            fieldLabel:'确认密码',
            initialPassField: 'pass',
            maxLength:50,
            allowBlank:false
        },{
            name:'userPhone',
            vtype:'mobile',
            maxLength:20,
            fieldLabel:'办公电话'
        },{
            name:'userTel',
            vtype:'phone',
            maxLength:20,
            fieldLabel:'家庭电话'
        },{
            name:'userMobile',
            vtype:'mobile',
            maxLength:20,
            fieldLabel:'手机'
        },{
            name:'userQQ',
            vtype:'qq',
            maxLength:20,
            fieldLabel:'QQ'
        },{
            name:'userMsn',
            maxLength:100,
            fieldLabel:'MSN'
        },{
            name:'userMail',
            vtype:'email',
            maxLength:100,
            fieldLabel:'邮件'
        },{
            fieldLabel:'状态',
            xtype: 'radiogroup',
            columns: 2,
            colspan:2,
            vertical: true,
            items: [
                {boxLabel: '启用', name: 'status',inputValue: '1',checked: true},
                {boxLabel: '禁用', name: 'status',inputValue: '0'}
            ]
        },{
            name:'userRemark',
            xtype:'textareafield',
            width:500,
            colspan:2,
            maxLength:500,
            fieldLabel:'备注'
        },{
            name:'userId',
            hidden:true
        },{
            name:'version',
            hidden:true,
            value:0
        }]
    });
    var addWin = Ext.create('Ext.window.Window',{
        title:'添加用户',
        iconCls:'add',
        height:350,
        width:600,
        constrain:true,
        closeAction:'hide',
        modal:true,
        buttonAlign:'center',
        items:[addPanel],
        buttons:[{
            id:'saveButton',
            text:'保存',
            iconCls:'savebutton',
            listeners:{
                click:function (){
                    var form = addPanel.getForm();
                    if(form.isValid()){
                        //验证通过
                        var url = '<%=basePath%>jsp/base/saveUser.action';
                        var params = form.getFieldValues();
                        params.opType = opType;
                        var isTooltip = false;
                        //调用ajax 发送数据到后台
                        AjaxSend(url,params,handleResult,isTooltip);
                    }else{
                        //验证失败
                        Ext.MessageBox.show({title:'提示',msg:'亲,请按照提示填写数据哦!',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                        return ;
                    }
                }
            }
        },{
            id:'cancelButton',
            text:'取消',
            iconCls:'cancelbutton',
            listeners:{
                click:function (){
                    var form = addPanel.getForm();
                    addWin.hide();
                    form.reset();
                }
            }
        }],
        listeners:{
            close:function(){
                //触发取消按钮的 单击 事件
                Ext.getCmp('cancelButton').fireEvent('click',Ext.getCmp('cancelButton'))
            }
        }
    });
    //构建viewport 显示页面组件
    var viewport = Ext.create('Ext.container.Viewport',{
        layout:{
            type:'vbox'
        },
        defaults:{
            split:true,
            autoScroll:true
        },
        items:[queryFormPanel,grid]
    });

    function handleResult(result){
        Ext.example.msg('提示','操作成功了,亲!');
        addWin.hide();
        //还得load一下数据
        gridStore.loadPage(1);
    }
    function handleDeleteResult(result){
        Ext.example.msg('提示','删除成功了,亲!');
        //还得load一下数据
        gridStore.loadPage(1);
    }
    //rendder status 转化 0 1 为 禁用  启用
    function changeStatus(val) {
        if (val == 0) return "禁用";
        if (val == 1) return "启用";
    }

});
</script>
</HEAD>
<body>
</body>
</HTML>