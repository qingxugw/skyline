<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>产品清单</TITLE>
<script type="text/javascript">
Ext.onReady(function() {
//    Ext.Loader.setConfig({enabled:true});
    Ext.tip.QuickTipManager.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.BLANK_IMAGE_URL='<%=basePath%>resources/images/s.gif';
    Ext.MessageBox.buttonText.yes = "是";
    Ext.MessageBox.buttonText.no = "否";
    Ext.MessageBox.buttonText.ok = "确定";
    var productrecord = "";
    var isTooltip = false;
    //            顶部面板
    //左侧grid
    Ext.define('productModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'productNo',type: 'string'},
            {name: 'productName',type: 'string'},
            {name: 'number',type: 'double'},
            {name: 'money',type: 'double'},
            {name: 'profit',type: 'double'}
        ]
    });

    var productBar = Ext.create('Ext.toolbar.Toolbar', {
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
            fieldLabel:'商品名称'
        },{
            id:'category',
            fieldLabel:'类别'
        },{
            id:'unit',
            fieldLabel:'单位'
        },{
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
        model:'productModel',
        pageSize:20,
        loadMask:true,
        proxy:{
            type:'ajax',
            url:'<%=basePath%>jsp/upload/queryProductList.action',
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
                productrecord = null;
                gridStore.proxy.extraParams = {
                    productNo:Ext.getCmp('productNo').getValue(),
                    productName:Ext.getCmp('productName').getValue(),
                    category:Ext.getCmp('category').getValue(),
                    unit:Ext.getCmp('unit').getValue()
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
        tbar:productBar,
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {text:'编码',dataIndex:'productNo'},
            {text:'产品名称',dataIndex:'productName'},
            {text:'客户数',dataIndex:'number'},
            {text:'销售额',dataIndex:'money'},
            {text:'利润',dataIndex:'profit'}
        ],
        bbar:pagingBar,
        listeners: {
            'selectionchange': function(view, records) {
                if (records[0]) {
                    productrecord = records[0];
                    saleGridStore.load();
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
            id:'userNo',
            fieldLabel:'用户编码'
        },{
            id:'address',
            fieldLabel:'家庭住址'
        },{
            xtype:'button',
            text:'查询',
            iconCls:'search',
            width:80,
            handler:function (){
                if(productrecord == null){
                    Ext.MessageBox.show({title:'提示',msg:'请先选择产品后再查询购买明细!',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                    return ;
                }
                saleGridStore.loadPage(1);
            }
        }]
    });
    //右侧用户面板
    Ext.define('saleModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'userNo',type: 'string'},
            {name: 'address',type: 'string'},
            {name: 'productNo',type: 'string'},
            {name: 'productName',type: 'string'},
            {name: 'money',type: 'double'},
            {name: 'number',type: 'double'},
            {name: 'saleDate',type: 'string', defaultValue: undefined,convert:dateFormatYMD}
        ]
    });



    //构建gridpanel
    var saleGridStore = Ext.create('Ext.data.Store',{
        id:'saleGridStore',
        model:'saleModel',
        loadMask:true,
        proxy:{
            type:'ajax',
            url:'<%=basePath%>jsp/upload/queryProductSaleList.action',
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
                saleGridStore.proxy.extraParams = {
                    productNo:productrecord.data.productNo,
                    userNo:Ext.getCmp('userNo').getValue(),
                    address:Ext.getCmp('address').getValue()
                }
            },
            load:function (store){
            }
        }
    });
    var salePagingBar = Ext.create('Ext.PagingToolbar',{
        store:saleGridStore,
        displayInfo:true,
        displayMsg:'当前显示{0} - {1} 条,共 {2} 条',
        emptyMsg:'我真的没有数据了,亲'
    });
    var saleGrid = Ext.create('Ext.grid.Panel',{
        anchor:'100% 50%',
        header:false,
        bodyBorder:false,
        autoScroll:true,
        columnLines:true,
        rowLines:true,
        forceFit:true,
        frame:true,
        store:saleGridStore,
        tbar:centerBar,
        viewConfig:{
            loadMask: new Ext.LoadMask(this,{msg:'我正在努力的为您加载呢...'})
        },
        columns:[
            Ext.create('Ext.grid.RowNumberer'),
            {text:'用户编码',dataIndex:'userNo'},
            {text:'家庭地址',dataIndex:'address'},
            {text:'售价',dataIndex:'money'},
            {text:'数量',dataIndex:'number'},
            {text:'销售日期',dataIndex:'saleDate'}
        ],
        bbar:salePagingBar
    });

    var viewport = Ext.create('Ext.Viewport', {
        layout: {
            type: 'anchor'
        },
        defaults: {
            split: true
        },
        items: [grid,saleGrid]
    });

});
</script>
</HEAD>
<body>
</body>
</HTML>