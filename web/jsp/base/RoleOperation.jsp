<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>角色菜单管理</TITLE>
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
    var record = "";
    var isTooltip = false;
    var roleIds = "";
    //            顶部面板
    Ext.define('treeModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'id',type: 'string'},
            {name: 'operationId',type: 'string'},
            {name: 'parentId',type: 'string'},
            {name: 'menuName',type: 'string'},
            {name: 'menuLink',type: 'string'},
            {name: 'text',type: 'string'},
            {name: 'menuIndex',type: 'int'},
            {name: 'menuLevel',type: 'int'},
            {name: 'version',type: 'int'},
            {name: 'status',type: 'string'},
            {name: 'createUser',type: 'string'},
            {name: 'createDate',type: 'string',convert:dateFormatYMDHIMS},
            {name: 'modifyUser',type: 'string'},
            {name: 'modifyDate',type: 'string',convert:dateFormatYMDHIMS},
            {name: 'iconCls',type: 'string'},
            {name: 'collapsedCls',type: 'string'},
            {name: 'expandedCls',type: 'string'}
        ]
    });
    //构造树菜单开始
    var store = Ext.create('Ext.data.TreeStore', {
        id:'store',
        model:'treeModel',
        nodeParam:'operationId',
        autoLoad:false,
        sortable:true,
        folderSort: true,
        sorters: [{
            property: 'menuIndex',
            direction: 'ASC'
        }],
        proxy: {
            type:'ajax',
            url:'<%=basePath%>jsp/base/queryOperationCheckedList.action',
            reader: {
                type: 'json'
            }
        },
        root: {
            id: '-1',
            operationId:'-1',
            text: '虚拟根节点',
            expanded: true,
            rootVisible:true
        },
        listeners:{
            'load':function(){
                centerPanel.expandAll();
            }
        }
    });

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
                record = null;
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
        frame:true,
        forceFit:true,
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
                    record = records[0];
                    //加入 roleId参数
                    store.proxy.extraParams = {
                        roleId:record.data.roleId
                    }
                    store.load();
                }
            }
        }
    });
    //左侧grid面板
    var leftPanel = Ext.create('Ext.panel.Panel',{
        id: 'west-panel',
        iconCls:'edit',
        region:'west',
        height:'50%',
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

    var treeTbar = Ext.create('Ext.toolbar.Toolbar', {
        items: [{
            xtype:'button',
            text: '保存关联',
            iconCls: 'savebutton',
            handler: function (){
                if(record == null){
                    Ext.MessageBox.show({title:'提示',msg:'请先选择角色',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                    return;
                }
                var records = centerPanel.getView().getChecked(),
                ids = [];

                Ext.Array.each(records, function(rec){
                    ids.push(rec.get('operationId'));
                });
                var url = '<%=basePath%>jsp/base/saveRoleOperation.action';
                var params = {};
                params.roleId = record.data.roleId;
                params.version = record.data.version;
                params.ids = ids.join(',');
                var isTooltip = false;
                //调用ajax 发送数据到后台
                AjaxSend(url,params,handleResult,isTooltip);
            }
        }]
    });
    //右侧菜单树
    var centerPanel = Ext.create('Ext.tree.Panel', {
        id: 'center-panel',
        iconCls:'edit',
        region:'center',
        title:'系统菜单树',
        store:store,
        header:false,
        autoScroll: true,
        enableDragDrop:false,
        split: true,
        rootVisible:false,
        lines:true,
        containerScroll: true,
        height:'100%',
        width:'100%',
        listeners:{
            itemexpand:function(node,eOpts){
                node.set({iconCls:node.data.expandedCls});
            },
            itemcollapse:function(node,eOpts){
                node.set({iconCls:node.data.collapsedCls});
            },
            checkchange:function (node,checked,eOpts){
                //选中事件
                setChildChecked(node,checked);
                setParentChecked(node,checked);
            }
        },
        tbar:treeTbar
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
    function setChildChecked(node,checked){
        node.expand();
        node.set({checked:checked});
        if(node.hasChildNodes()){
            node.eachChild(function(child) {
                setChildChecked(child,checked);
            });
        }
    }
    function setParentChecked(node,checked){
        node.set({checked:checked});
        var parentNode = node.parentNode;
        if(parentNode !=null){
            var flag = false;
            parentNode.eachChild(function(child) {
                if(child.data.checked == true){
                    flag = true;
                }
            });
            if(checked == false){
                if(!flag){
                    setParentChecked(parentNode,checked);
                }
            }else{
                if(flag){
                    setParentChecked(parentNode,checked);
                }
            }
         }
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