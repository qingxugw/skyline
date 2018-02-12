<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>角色用户管理</TITLE>
<script type="text/javascript">
Ext.onReady(function() {
//    Ext.Loader.setConfig({enabled:true});
    Ext.tip.QuickTipManager.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL='<%=basePath%>resources/images/s.gif';
    Ext.MessageBox.buttonText.yes = "是";
    Ext.MessageBox.buttonText.no = "否";
    Ext.MessageBox.buttonText.ok = "确定";
    var opType = "";
    var rolerecord = "";
    var isTooltip = false;
    var ids = "";
    //记录勾选的记录id
    var idArray = new Ext.util.MixedCollection();
    //            顶部面板
    //左侧grid
    Ext.define('roleModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'roleId',type: 'string'},
            {name: 'roleNo',type: 'string'},
            {name: 'roleName',type: 'string'},
            {name: 'roleDesc',type: 'string'},
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
        anchor:'100% 20%',
        model:'userModel',
        header:false,
        bodyBorder:true,
        frame:true,
        defaults:{
            xtype:'textfield',
            labelAlign:'right',
            labelWidth:70,
            width:170
        },
        buttonAlign:'center',
        layout:{
            type:'table',
            columns:3
        },
        items:[{
            id:'roleNo',
            fieldLabel:'角色编码'
        },{
            id:'roleName',
            fieldLabel:'角色名称'
        },status],
        buttons:[{
            text:'查询',
            id:'queryButton',
            iconCls:'search',
            handler:function (){
                gridStore.loadPage(1);
            }
        }]
    });
    //构建gridpanel
    var gridStore = Ext.create('Ext.data.Store',{
        model:'roleModel',
        pageSize:20,
        loadMask:true,
        proxy:{
            type:'ajax',
            url:'<%=basePath%>jsp/base/queryRoleList.action',
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
                rolerecord = null;
                gridStore.proxy.extraParams = {
                    roleNo:Ext.getCmp('roleNo').getValue(),
                    roleName:Ext.getCmp('roleName').getValue(),
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
        anchor:'100% 80%',
        header:false,
        bodyBorder:false,
        autoScroll:true,
        forceFit:true,
        frame:true,
        store:gridStore,
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {text:'角色编码',dataIndex:'roleNo'},
            {text:'角色名称',dataIndex:'roleName'},
            {text:'角色描述',dataIndex:'roleDesc'},
            {text:'状态',dataIndex:'status',renderer:changeStatus}
        ],
        bbar:pagingBar,
        listeners: {
            'selectionchange': function(view, records) {
                if (records[0]) {
                    rolerecord = records[0];
                    userGridStore.load();
                }
            }
        }
    });
    //左侧grid面板
    var leftPanel = Ext.create('Ext.panel.Panel',{
        id: 'west-panel',
        iconCls:'edit',
        region:'west',
        height:'100%',
        width:'50%',
        title:'角色列表',
        bodyBorder:false,
        frame:true,
        buttonAlign:'center',
        layout:{
            type:'anchor'
        },
        items:[queryFormPanel,grid]
    });

    var centerBar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            xtype:'button',
            text: '保存关联',
            iconCls: 'savebutton',
            handler: function (){
                if(rolerecord == null){
                    Ext.MessageBox.show({title:'提示',msg:'请先选择角色',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                    return;
                }
                var url = '<%=basePath%>jsp/base/saveRoleUser.action';
                var params = {};
                params.roleId = rolerecord.data.roleId;
                params.version = rolerecord.data.version;
                params.ids = idArray.getRange().join(',');
                var isTooltip = false;
                //调用ajax 发送数据到后台
                AjaxSend(url,params,handleResult,isTooltip);
            }
        }]
    });
    //右侧用户面板
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
            {name: 'checked',type: 'boolean'},
            {name: 'createDate',type: 'string', defaultValue: undefined,convert:dateFormatYMDHIMS},
            {name: 'modifyDate',type: 'string', defaultValue: undefined,convert:dateFormatYMDHIMS}
        ]
    });
    var userStatusStore = Ext.create('Ext.data.Store',{
        model:'comboxModel',
        data:[{key:'',value:'全部'},{key:'1',value:'启用'},{key:'0',value:'禁用'}],
        autoLoad:true,
        listeners:{
            load:function(){
            }
        }
    });
    var userStatus = Ext.create('Ext.form.ComboBox',{
        id:'userStatus',
        labelAlign:'right',
        fieldLabel:'状态',
        store:userStatusStore,
        queryMode:'local',
        displayField:'value',
        valueField:'key'
    });
    //开始创建查询表单
    var queryUserFormPanel = Ext.create('Ext.form.Panel',{
        anchor:'100% 20%',
        model:'userModel',
        header:false,
        bodyBorder:false,
        frame:true,
        defaults:{
            xtype:'textfield',
            labelAlign:'right',
            labelWidth:70,
            width:170
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
        },userStatus],
        buttons:[{
            text:'查询',
            id:'queryUserButton',
            iconCls:'search',
            handler:function (){
                userGridStore.loadPage(1);
            }
        }]
    });
    //构建gridpanel
    var userGridStore = Ext.create('Ext.data.Store',{
        id:'userGridStore',
        model:'userModel',
        loadMask:true,
        proxy:{
            type:'ajax',
            url:'<%=basePath%>jsp/base/queryUserCheckedList.action',
            actionMethods:{
                //这个是指定查询的时候使用post方法   解决默认get方式 提交参数乱码的问题
                read:'POST'
            },
            reader:{
                root:'root'
            }
        },
        autoLoad:false,
        listeners:{
            beforeload:function (){
                userGridStore.proxy.extraParams = {
                    roleId:rolerecord.data.roleId,
                    userNo:Ext.getCmp('userNo').getValue(),
                    userName:Ext.getCmp('userName').getValue(),
                    status:Ext.getCmp('userStatus').getValue()
                }
            },
            load:function (store){
                var arry = [];
                store.each(function(record) {
                    if (record.data.checked == true){
                        arry.push(record);
                    }
                    checkboxModel.select(arry, true);
                });
            }
        }
    });
    //选择模型
    var checkboxModel = Ext.create('Ext.selection.CheckboxModel', {
        listeners : {
            select : function (me, record,index,eOpts) {
                if (!idArray.contains(record.get('userId'))) {
                    idArray.add(record.get('userId'));
                }
            },
            deselect:function (me, record,index,eOpts) {
                if(idArray.indexOf(record.get('userId'))!=-1){
                    idArray.remove(record.get('userId'));
                }
            }
        }
    });
    var userGrid = Ext.create('Ext.grid.Panel',{
        anchor:'100% 80%',
        header:false,
        bodyBorder:false,
        autoScroll:true,
        columnLines:true,
        rowLines:true,
        frame:true,
        store:userGridStore,
        selModel:checkboxModel,
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {text:'用户名',dataIndex:'userNo'},
            {text:'用户中文名',dataIndex:'userName'},
            {text:'分部',dataIndex:'subsectionName'},
            {text:'QQ',dataIndex:'userQQ'},
            {text:'邮件',dataIndex:'userMail'},
            {text:'状态',dataIndex:'status',renderer:changeStatus}
        ]
    });
    var centerPanel = Ext.create('Ext.panel.Panel', {
        id: 'center-panel',
        iconCls:'edit',
        region:'center',
        header:false,
        frame:true,
        autoScroll: true,
        width:'50%',
        height:'100%',
        layout:{
            type:'anchor'
        },
        items:[queryUserFormPanel,userGrid],
        listeners:{

        },
        tbar:centerBar
    });


    var viewport = Ext.create('Ext.Viewport', {
        layout: {
            type: 'border'
        },
        defaults: {
            split: true
        },
        items: [leftPanel,centerPanel]
    });
    //rendder status 转化 0 1 为 禁用  启用
    function changeStatus(val) {
        if (val == 0) return "禁用";
        if (val == 1) return "启用";
    }
    function handleResult(result){
        Ext.example.msg('提示','操作成功了,亲!');
    }
});
</script>
</HEAD>
<body>
</body>
</HTML>