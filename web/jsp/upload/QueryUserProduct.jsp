<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>客户清单</TITLE>
<script type="text/javascript">
Ext.onReady(function() {
//    Ext.Loader.setConfig({enabled:true});
    Ext.tip.QuickTipManager.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL='<%=basePath%>resources/images/s.gif';
    Ext.MessageBox.buttonText.yes = "是";
    Ext.MessageBox.buttonText.no = "否";
    Ext.MessageBox.buttonText.ok = "确定";
    var userrecord = "";
    var isTooltip = false;
    //            顶部面板
    //左侧grid
    Ext.define('userModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'userNo',type: 'string'},
            {name: 'userName',type: 'string'},
            {name: 'address',type: 'string'},
            {name: 'gender',type: 'string'},
            {name: 'birthday',type: 'string', defaultValue: undefined,convert:dateFormatYMD}
        ]
    });
    var statusStore = Ext.create('Ext.data.Store',{
        model:'comboxModel',
        data:[{key:'',value:'全部'},{key:'男',value:'男'},{key:'女',value:'女'}],
        autoLoad:true,
        listeners:{
            load:function(){
            }
        }
    });
    var status = Ext.create('Ext.form.ComboBox',{
        id:'gender',
        labelAlign:'right',
        fieldLabel:'性别',
        store:statusStore,
        queryMode:'local',
        displayField:'value',
        valueField:'key'
    });
    var userBar = Ext.create('Ext.toolbar.Toolbar', {
        defaults:{
            xtype:'textfield',
            labelAlign:'right',
            labelWidth:70,
            width:170
        },
        items:[{
            id:'userNo',
            fieldLabel:'编码'
        },{
            id:'userName',
            fieldLabel:'姓名'
        },{
            id:'address',
            fieldLabel:'家庭住址'
        },status,{
            id:'queryButton',
            xtype:'button',
            text:'查询',
            iconCls:'search',
            width:80,
            handler:function (){
                gridStore.loadPage(1);
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
            url:'<%=basePath%>jsp/upload/queryUserList.action',
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
                userrecord = null;
                gridStore.proxy.extraParams = {
                    userNo:Ext.getCmp('userNo').getValue(),
                    userName:Ext.getCmp('userName').getValue(),
                    address:Ext.getCmp('address').getValue(),
                    gender:Ext.getCmp('gender').getValue()
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
        anchor:'100% 50%',
        header:false,
        bodyBorder:false,
        autoScroll:true,
        forceFit:true,
        frame:true,
        store:gridStore,
        tbar:userBar,
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {text:'编码',dataIndex:'userNo'},
            {text:'姓名',dataIndex:'userName'},
            {text:'家庭地址',dataIndex:'address'},
            {text:'生日',dataIndex:'birthday'},
            {text:'性别',dataIndex:'gender'}
        ],
        bbar:pagingBar,
        listeners: {
            'selectionchange': function(view, records) {
                if (records[0]) {
                    userrecord = records[0];
                    productGridStore.load();
                }
            }
        }
    });
    var centerBar = Ext.create('Ext.toolbar.Toolbar', {
        defaults:{
            xtype:'textfield',
            labelAlign:'right',
            labelWidth:70,
            width:170
        },
        items:[{
            id:'productNo',
            fieldLabel:'编码'
        },{
            id:'productName',
            fieldLabel:'名称'
        },{
            id:'category',
            fieldLabel:'类别'
        },{
            id:'unit',
            fieldLabel:'单位'
        },{
            xtype:'button',
            text:'查询',
            iconCls:'search',
            width:80,
            handler:function (){
                if(userrecord == null){
                    Ext.MessageBox.show({title:'提示',msg:'请先选择客户后再查询产品清单!',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                    return ;
                }
                productGridStore.loadPage(1);
            }
        }]
    });
    //右侧用户面板
    Ext.define('productModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'productId',type: 'string'},
            {name: 'productNo',type: 'string'},
            {name: 'productName',type: 'string'},
            {name: 'category',type: 'string'},
            {name: 'money',type: 'double'},
            {name: 'unit',type: 'string'}
        ]
    });



    //构建gridpanel
    var productGridStore = Ext.create('Ext.data.Store',{
        id:'productGridStore',
        model:'productModel',
        loadMask:true,
        proxy:{
            type:'ajax',
            url:'<%=basePath%>jsp/upload/queryUserProductList.action',
            actionMethods:{
                //这个是指定查询的时候使用post方法   解决默认get方式 提交参数乱码的问题
                read:'POST'
            },
            reader:{
                root:'root',
                totalProperty:'totalProperty'
            }
        },
        autoLoad:false,
        listeners:{
            beforeload:function (){
                productGridStore.proxy.extraParams = {
                    userNo:userrecord.data.userNo,
                    productNo:Ext.getCmp('productNo').getValue(),
                    productName:Ext.getCmp('productName').getValue(),
                    category:Ext.getCmp('category').getValue(),
                    unit:Ext.getCmp('unit').getValue()
                }
            },
            load:function (store){
            }
        }
    });
    var productPagingBar = Ext.create('Ext.PagingToolbar',{
        store:productGridStore,
        displayInfo:true,
        displayMsg:'当前显示{0} - {1} 条,共 {2} 条',
        emptyMsg:'我真的没有数据了,亲'
    });
    var productGrid = Ext.create('Ext.grid.Panel',{
        anchor:'100% 50%',
        header:false,
        bodyBorder:false,
        autoScroll:true,
        columnLines:true,
        rowLines:true,
        forceFit:true,
        frame:true,
        store:productGridStore,
        tbar:centerBar,
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {text:'编号',dataIndex:'productNo'},
            {text:'名称',dataIndex:'productName'},
            {text:'类别',dataIndex:'category'},
            {text:'金额',dataIndex:'money'},
            {text:'单位',dataIndex:'unit'}
        ],
        bbar:productPagingBar
    });

    var viewport = Ext.create('Ext.Viewport', {
        layout: {
            type: 'anchor'
        },
        defaults: {
            split: true
        },
        items: [grid,productGrid]
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