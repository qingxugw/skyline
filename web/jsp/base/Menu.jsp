<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>菜单维护</TITLE>
<script type="text/javascript">
Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL='<%=basePath%>resources/images/s.gif';
    Ext.MessageBox.buttonText.yes = "是";
    Ext.MessageBox.buttonText.no = "否";
    Ext.MessageBox.buttonText.ok = "确定";
//            顶部面板
    var items = [];
    var selectNode = null;
    var currentNode = null;
    var opType = "";
    var View ;
    var indexObj = new Object();
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
            url:'<%=basePath%>jsp/base/privateChildTree.action',
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
                leftPanel.expandAll();
            }
        }
    });
//                下面构造弹出窗口
    //form 表单
    var winPanel = Ext.create('Ext.form.Panel', {
        height: 300,
        width: 600,
        model:'treeModel',
        frame:true,
        header:false,
        bodyBorder:false,
        bodyStyle:'padding:20px 10px 0px 10px;',
        defaults: {
            xtype:'textfield',
            labelAlign:'right',
            labelWidth: 100,
            width:500
        } ,
        layout: {
            type: 'vbox'
        },
        items:[{
            name:'menuName',
            fieldLabel:'菜单名称',
            allowBlank:false
        },{
            name:'menuLink',
            fieldLabel:'菜单路径'
        },{
            name:'collapsedCls',
            fieldLabel:'折叠图标'
        },{
            name:'expandedCls',
            fieldLabel:'展开图标'
        },{
            name:'iconCls',
            fieldLabel:'菜单图标'
        },{
            name:'menuLevel',
            fieldLabel:'菜单级别'
        },{
            name:'menuIndex',
            fieldLabel:'菜单排序'
        },{
            fieldLabel:'菜单状态',
            xtype: 'radiogroup',
            columns: 6,
            vertical: true,
            items: [
                {boxLabel: '启用', name: 'status',inputValue: '1'},
                {boxLabel: '禁用', name: 'status',inputValue: '0'}
            ]
        },{
            name:'operationId',
            hidden:true
        },{
            name:'parentId',
            hidden:true
        },{
            name:'version',
            hidden:true
        }]
    });
    var addWin =  Ext.create('Ext.window.Window', {
        title: '编辑菜单',
        iconCls:'edit',
        height: 300,
        width: 600,
        constrain:true,   //这个属性很牛逼啊，可以把window 限制在容器范围内
        closeAction:'hide',
        modal:true,
        animateTarget:leftPanel,
        buttonAlign:'center',
        items:[winPanel],
        buttons:[{
            text:'保存',
            iconCls:'savebutton',
            listeners:{
                click:function(){
                    var url = '<%=basePath%>jsp/base/saveMenu.action';
                    var params = winPanel.getForm().getFieldValues();
                    params.opType = opType;
                    var isTooltip = false;
                    //调用ajax 发送数据到后台
                    AjaxSend(url,params,handleResult,isTooltip);
                }
            }},{
            id:'cancelButton',
            text:'取消',
            iconCls:'cancelbutton',
            listeners:{
                click:function(){
                    if(currentNode !=null){
                        currentNode.remove();
                        if(!selectNode.hasChildNodes()){
                            selectNode.set({
                                leaf:true
                            });
                            selectNode.updateInfo();
                        }
                    }
                    addWin.hide();
                }}}
        ],
        listeners:{
            close:function(){
                //触发取消按钮的 单击 事件
                Ext.getCmp('cancelButton').fireEvent('click',Ext.getCmp('cancelButton'))
            }
        }
    });
    var addFlatMenuAction = Ext.create('Ext.Action', {
        iconCls: 'add',
        text: '添加平级菜单',
        disabled: false,
        handler: function(widget, event) {
            winPanel.getForm().reset();
            if(selectNode != null){
                opType = "add";
                addWin.setTitle('添加平级菜单');
                addWin.show();
                currentNode = selectNode.parentNode.insertBefore({
                    text: '新菜单',
                    menuName: '新菜单',
                    menuLevel:selectNode.parentNode.data.menuLevel+1,
                    menuIndex:selectNode.parentNode.childNodes.length,
                    status:1,
                    leaf: true
                });
                winPanel.loadRecord(currentNode);
            }else{
                Ext.MessageBox.alert("提示","请先选中菜单!");
            }
        }
    });
    var addChildMenuAction = Ext.create('Ext.Action', {
        iconCls: 'add',
        text: '添加子菜单',
        disabled: false,
        handler: function(widget, event) {
            opType = "add";
            winPanel.getForm().reset();
            if(selectNode !=null){
                addWin.setTitle('添加子级菜单');
                addWin.show();
                selectNode.data.leaf = false;
                currentNode = selectNode.appendChild({
                    text: '新菜单',
                    menuName: '新菜单',
                    menuLevel:selectNode.data.menuLevel+1,
                    menuIndex:selectNode.childNodes.length,
                    status:1,
                    leaf: true
                });
                currentNode.updateInfo();
                winPanel.loadRecord(currentNode);
                selectNode.expand();
            }else{
                Ext.MessageBox.alert("提示","请先选中菜单!");
                return;
            }
        }
    });
    var editCurrentMenuAction = Ext.create('Ext.Action', {
        iconCls: 'edit',
        text: '编辑当前菜单',
        disabled: false,
        handler: function(widget, event) {
            if(selectNode !=null){
                opType = "edit";
                addWin.setTitle('编辑菜单');
                winPanel.loadRecord(selectNode);
                addWin.show();
            }else{
                Ext.MessageBox.alert("提示","请先选中菜单!");
                return;
            }
        }
    });
    var deleteCurrentMenuAction = Ext.create('Ext.Action', {
        iconCls: 'delete',
        text: '删除当前菜单',
        disabled: false,
        handler: function(widget, event) {
            if(selectNode !=null){
                if(selectNode.hasChildNodes()){
                    Ext.MessageBox.show({title:'操作错误',msg:'当前选中菜单含有子菜单项，请先删除子菜单!',buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                    return;
                }
                opType = "delete";
                Ext.MessageBox.confirm("提示","确定要删除当前菜单吗",function (e){
                    if( e == "yes"){
                        currentNode = selectNode;
                        selectNode = selectNode.parentNode;
                        var url = '<%=basePath%>jsp/base/saveMenu.action';
                        var params = new Object();
                        params.opType = opType;
                        params.operationId = currentNode.get('operationId');
                        params.version = currentNode.get('version');
                        var isTooltip = false;
                        //调用ajax 发送数据到后台
                        AjaxSend(url,params,handleDeleteResult,isTooltip);
                    }
                });
            } else{
                Ext.MessageBox.alert("提示","请先选中菜单!");
                return;
            }
        }
    });
    var contextMenu = Ext.create('Ext.menu.Menu', {
        items: [
            addFlatMenuAction,
            addChildMenuAction,
            editCurrentMenuAction,
            deleteCurrentMenuAction
        ]
    });
    //左侧菜单树
    var leftPanel = Ext.create('Ext.tree.Panel', {
        id: 'west-panel',
        iconCls:'edit',
        region:'west',
        title:'系统菜单树',
        split: true,
        collapsible: true,
        store:store,
        enableDD: true,
        viewConfig: {
            plugins: {
                ptype: 'treeviewdragdrop',
                allowParentInserts:true,
                appendOnly:false
            },
            listeners: {
                drop: function (node, data, overModel, dropPosition,opts) {
                    indexObj.parentId = data.records[0].get('parentId');
                    indexObj.operationId = data.records[0].get('operationId');
                    indexObj.menuIndex = data.records[0].get('index');
                    indexObj.menuLevel = data.records[0].get('depth')-1;
                    indexObj.version = data.records[0].get('version');
                    var url = '<%=basePath%>jsp/base/changeIndex.action';
                    var isTooltip = false;
                    //调用ajax 发送数据到后台
                    AjaxSend(url,indexObj,handleChangeIndexResult,isTooltip);
                },
                beforedrop: function (node, data, overModel, dropPosition,opts){
                    indexObj.oldParentId = data.records[0].get('parentId');
                    indexObj.oldMenuIndex = data.records[0].get('index');
                    indexObj.oldMenuLevel = data.records[0].get('depth')-1;
                }
            }
        },
        autoScroll: true,
        enableDragDrop:false,
        split: true,
        rootVisible:false,
        lines:true,
        containerScroll: true,
        height:800,
        width:200,
        listeners:{
            'itemclick':function(view,record,item,index,evt,options){
                View = view;
                if(Ext.getCmp(record.data.operationId)){
                    Ext.getCmp(record.data.operationId).show();
                }else{
                    selectNode = record;
                    currentNode = null;
                    rightPanel.loadRecord(record);
                }
            },
            'itemcontextmenu':function(view,record,item,index,evt,options){
                View = view;
                evt.stopEvent();
                selectNode = record;
                currentNode = null;
                rightPanel.loadRecord(record);
                contextMenu.showAt(evt.getXY());
                return false;
            },
            itemexpand:function(node,eOpts){
                node.set({iconCls:node.data.expandedCls});
            },
            itemcollapse:function(node,eOpts){
                node.set({iconCls:node.data.collapsedCls});
            }
        }
    });
    //form 表单
    var rightPanel = Ext.create('Ext.form.Panel', {
        id:'center',
        region:'center',
        iconCls:'grid',
        autoScroll:true,
        split:true,
        title: '菜单详细信息',
        height: 100,
        frame: true,
        items:[{
            xtype:'fieldset',
            title:'基本信息',
            width:800,
            layout: {
                type: 'table',
                columns: 2
            },
            defaults: {
                xtype:'textfield',
                labelAlign:'right',
                labelWidth: 100,
                width:300,
                readOnly:true
            } ,
            items:[{
                name:'menuName',
                fieldLabel:'菜单名称'
            },{
                name:'menuLink',
                fieldLabel:'菜单路径',
                width:500
            },{
                name:'menuLevel',
                fieldLabel:'菜单级别'
            },{
                name:'menuIndex',
                fieldLabel:'菜单排序'
            },{
                name:'collapsedCls',
                fieldLabel:'折叠图标'
            },{
                name:'expandedCls',
                fieldLabel:'展开图标'
            },{
                name:'iconCls',
                fieldLabel:'菜单图标'
            },{
                fieldLabel:'菜单状态',
                xtype: 'radiogroup',
                items: [
                    {boxLabel: '启用', name: 'status',inputValue: '1',readOnly:true},
                    {boxLabel: '禁用', name: 'status',inputValue: '0',readOnly:true},
                ]
            },{
                name:'createUser',
                fieldLabel:'创建人'
            },{
                name:'createDate',
                fieldLabel:'创建日期'
            },{
                name:'modifyUser',
                fieldLabel:'最后一次更新人'
            },{
                name:'modifyDate',
                fieldLabel:'最后一次更新日期'
            }]
        }]
    });
    var topPanel = Ext.create('Ext.panel.Panel', {
        id:'north',
        region:'north',
        iconCls:'grid',
        split:false,
        height: 25,
        tbar:[
            addFlatMenuAction,
            addChildMenuAction,
            editCurrentMenuAction,
            deleteCurrentMenuAction
        ]
    });
    var viewport = Ext.create('Ext.Viewport', {
        layout: {
            type: 'border'
        },
        defaults: {
            split: true
        },
        items: [topPanel,leftPanel,rightPanel]
    });
    function handleResult(result){
        Ext.example.msg('提示','操作成功了,亲!');
        if(currentNode !=null){
            currentNode.set(result);
            currentNode.updateInfo();
            rightPanel.loadRecord(currentNode);
        }else{
            selectNode.set(result);
            selectNode.updateInfo();
            rightPanel.loadRecord(selectNode);
        }
        addWin.hide();
    }
    function handleDeleteResult(result){
        Ext.example.msg('提示','删除成功了,亲!');
        currentNode.remove();
        if(!selectNode.hasChildNodes()){
            selectNode.set({
                leaf:true
            });
            selectNode.updateInfo();
        }
    }
    function handleChangeIndexResult(){
        Ext.example.msg('提示','拖动排序成功了,亲!');
    }
});
</script>
</HEAD>
<body>
</body>
</HTML>