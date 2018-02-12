<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>系统日志查询</TITLE>
<script type="text/javascript">
Ext.onReady(function() {
//    Ext.Loader.setConfig({enabled:true});
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL='<%=basePath%>resources/images/s.gif';
    Ext.MessageBox.buttonText.yes = "是";
    Ext.MessageBox.buttonText.no = "否";
    Ext.MessageBox.buttonText.ok = "确定";
    Ext.define('logModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'moduleId',type: 'string'},
            {name: 'moduleName',type: 'string'},
            {name: 'opType',type: 'string'},
            {name: 'opTypeName',type: 'string'},
            {name: 'opContent',type: 'string'},
            {name: 'opParam',type: 'string'},
            {name: 'opIp',type: 'string'},
            {name: 'opClass',type: 'string'},
            {name: 'opMethod',type: 'string'},
            {name: 'createUser',type: 'string'},
            {name: 'createDate',type: 'string', defaultValue: undefined,convert:dateFormatYMDHIMS}
        ]
    });
    var moduleStore = Ext.create('Ext.data.Store', {
        model:'comboxModel',
        proxy: {
            type:'ajax',
            url:'<%=basePath%>jsp/base/queryModule.action'
        },
        autoLoad: true,
        listeners:{
            load:function(){
                var insert = Ext.create('comboxModel',{key:'',value:'全部'});
                this.insert(0,insert);
            }
        }
    });
    var opTypeStore = Ext.create('Ext.data.Store', {
        model:'comboxModel',
        proxy: {
            type:'ajax',
            url:'<%=basePath%>jsp/base/queryOpType.action'
        },
        autoLoad: false,
        listeners:{
            load:function(){
                var insert = Ext.create('comboxModel',{key:'',value:'全部'});
                this.insert(0,insert);
            },
            beforeload:function(){
                this.clearData();
            }
        }
    });

// Create the combo box, attached to the states data store
   var modules =  Ext.create('Ext.form.ComboBox', {
       labelAlign:'right',
       fieldLabel: '模块名称',
       store: moduleStore,
       queryMode: 'local',
       displayField: 'value',
       valueField: 'key',
       listeners:{
           select:function(combo, record,index){
               opTypeStore.clearData();
               opTypes.clearValue();
               opTypeStore.load({params:{module:modules.getValue()}});
           }
       }
    });
    var opTypes =  Ext.create('Ext.form.ComboBox', {
        labelAlign:'right',
       fieldLabel: '操作类型',
       store: opTypeStore,
       queryMode: 'remote',
       displayField: 'value',
       valueField: 'key'
    });
    var queryFormPanel = Ext.create('Ext.form.Panel',{
        anchor:'100% 20%',
        model:'logModel',
        frame:true,
        header:false,
        bodyBorder:false,
        bodyStyle:'padding:20px 10px 0px 10px',
        buttonAlign:'center',
        defaults:{
            xtype:'textfield',
            labelAlign:'right',
            labelWidth:100,
            width:200
        },
        layout:{
            type:'table',
            columns: 6
        },
        items:[modules,opTypes,{
            fieldLabel: '操作内容',
            id:'opContent',
            name: 'opContent',
            anchor:'95%'
        },{
            id:'startDate',
            name:'startDate',
            xtype:'datefield',
            format: 'Y-m-d',
            maxValue: new Date(),
            value:new Date(),
            fieldLabel:'起始时间'
        },{
            id:'endDate',
            name:'endDate',
            xtype:'datefield',
            format: 'Y-m-d',
            maxValue: new Date(),
            value:new Date(),
            fieldLabel:'结束时间'
        }],
        buttons: [{
            text: '查询',
            iconCls:'search',
            handler:function (){
                store.loadPage(1);
            }
        }]
    });
    var store = Ext.create('Ext.data.Store', {
        model: 'logModel',
        pageSize:20,
        loadMask:true,
        proxy:{
            type:'ajax',
            url:'<%=basePath%>jsp/base/queryLog.action',
            reader:{
                root:'root',
                totalProperty:'totalProperty'
            }
        },
        autoLoad:true,
        listeners:{
            beforeload:function (){
                store.proxy.extraParams = {
                    module:modules.getValue(),
                    opType:opTypes.getValue(),
                    opContent:encodeURIComponent(Ext.getCmp('opContent').getValue()),
                    startDate:dateFormatYMD(Ext.getCmp('startDate').getValue()),
                    endDate:dateFormatYMD(Ext.getCmp('endDate').getValue())
                }
            }
        }
    });

    var pagingBar = Ext.create('Ext.PagingToolbar', {
        store: store,
        displayInfo: true,
        displayMsg: '当前显示 {0} - {1}条 共 {2}条',
        emptyMsg: "我真的没有数据了,亲"
    });
    var grid = Ext.create('Ext.grid.Panel',{
        anchor:'100% 80%',
        frame:true,
        header:false,
        bodyBorder:false,
        autoScroll:true,
        store:store,
        autoExpandColumn:"opContent",  //自动扩展宽度的列
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[Ext.create('Ext.grid.RowNumberer'),
            {
            text:'模块名称',
            dataIndex:'moduleName'
        },{
            text:'操作类型',
            dataIndex:'opTypeName'
        },{
            text:'操作内容',
            width:300,
            dataIndex:'opContent'
        },{
            text:'操作参数',
            dataIndex:'opParam'
        },{
            text:'操作Java类',
            dataIndex:'opClass'
        },{
            text:'操作方法',
            dataIndex:'opMethod'
        },{
            text:'操作IP',
            dataIndex:'opIp'
        },{
            text:'操作人',
            dataIndex:'createUser'
        },{
            text:'操作时间',
            width:200,
            dataIndex:'createDate'
        }],
        bbar:pagingBar
    });
    var viewport = Ext.create('Ext.Viewport', {
        layout: {
            type: 'anchor'
        },
        defaults: {
            split:true,
            autoScroll:true
        },
        items: [queryFormPanel,grid]
    });
});
</script>
</HEAD>
<body>
</body>
</HTML>