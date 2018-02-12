<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp"%>
<html>
<head>
    <title>SOP办公平台</title>
    <style type="text/css">
        <!--
        .td_norepeat{
            background-image:url(resources/images/top.png);
            background-repeat:no-repeat;
        }
        -->
    </style>
    <script type="text/javascript">
        Ext.onReady(function() {
//            顶部面板
            var items = [];
            var topPanel = Ext.create('Ext.form.Panel',{
                region: 'north',
                iconCls:'grid',
                title: 'skyline办公平台',
                header:false,
                height: 100,
                split: true,
                collapsible: true,
                contentEl:'topDiv',
                bbar:[{
                    xtype:'button',
                    text:'绿',
                    handler:function(){
                        changeCSS("resources/css/ext-all.css");
                    }
                },{
                    xtype:'button',
                    text:'深',
                    handler:function(){
                        changeCSS("resources/css/ext-all-access.css");
                    }
                },{
                    xtype:'button',
                    text:'灰',
                    handler:function(){
                        changeCSS("resources/css/ext-all-gray.css");
                    }
                },'->',{
                    xtype:'button',
                    iconCls:'password',
                    text:'更改密码',
                    handler:function(){
                        Ext.MessageBox.show({title:'提示',msg:'亲,本功能尚未开启哦!',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                        return ;
                        location.href = 'system/sysOperator/changePassword';
                    }
                },{
                    xtype:'button',
                    iconCls:'logout',
                    text:'退出系统',
                    handler:function(){
                        Ext.MessageBox.buttonText.yes = "是";
                        Ext.MessageBox.buttonText.no = "否";
                        Ext.MessageBox.confirm("提示","确定注销登录么",function (e){
                            if( e == "yes"){
                                location.href = 'logout.action';
                            }
                        });
                    }
                }]
            });
            Ext.define('treeModel', {
                extend: 'Ext.data.Model',
                fields: [
                    {name: 'operationId',type: 'string'},
                    {name: 'menuName',type: 'string'},
                    {name: 'menuLink',type: 'string'},
                    {name: 'text',type: 'string'},
                    {name: 'menuIndex',type: 'int',convert: null},
                    {name: 'menuLevel',type: 'int',convert: null}
                ]
            });
            //构造树菜单开始
            <s:iterator value="session.tabList" var="tab">
            var store_${tab.operationId} = Ext.create('Ext.data.TreeStore', {
                id:'store_${tab.operationId}',
                model:'treeModel',
                nodeParam:'operationId',
                autoLoad:false,
                folderSort: true,
                sorters: [{
                    property: 'menuIndex',
                    direction: 'ASC'
                }],
                proxy: {
                    type:'ajax',
                    url:'privateChildTree.action',
                    reader: {
                        type: 'json'
                    }
                },
                root: {
                    id: '${tab.operationId}',
                    text: '${tab.menuName}',
                    iconCls: '${tab.iconCls}',
                    expanded: true
                },
                listeners:{
                    'load':function(){
                        tree_${tab.operationId}.expandAll();
                    }
                }
            });

            var tree_${tab.operationId} = Ext.create('Ext.tree.Panel', {
                store:store_${tab.operationId},
                margins: '0 0 0 0',
                autoHeight:true,
                autoScroll: true,
                split: true,
                rootVisible:false,
                lines:true,
                containerScroll: true,
                listeners:{
                    'itemclick':function(view,record,item,index,evt,options){
                        if(!record.data.leaf) return;
                        if(Ext.getCmp(record.data.operationId)){
                            Ext.getCmp(record.data.operationId).show();
                        }else{
                            if(record.data.menuLink !=null&&record.data.menuLink!=""){
                                Ext.example.msg('提示',"《" + record.data.menuName + '》页面加载中,请稍候...');
                                contentPanel.add({
                                    title:record.data.menuName,
                                    id: record.data.operationId,
                                    iconCls: record.data.iconCls,
                                    closable:true,
                                    html:'<iframe src='+record.data.menuLink+' style="width:100%;height:100%"/>'
                                }).show();
                            }else{
                                //还没有设置路径的菜单提示，并且不打开
                                Ext.MessageBox.show({title:'提示',msg:'亲,您还没有为《'+record.data.menuName+'》设置菜单路径哦!',icon:Ext.MessageBox.WARNING,buttons:Ext.MessageBox.OK});
                                return ;
                            }
                        }
                    }
                }
            });
            var item_${tab.operationId} = Ext.create('Ext.form.Panel',{
                title:'${tab.menuName}',
                layout:'fit',
                containerScroll:true,
                autoScroll:true,
                iconCls:'${tab.iconCls}',
                items:[tree_${tab.operationId}]
            });

            items.push(item_${tab.operationId});
            </s:iterator>
            //构造树菜单结束
//            左侧菜单树
            var accordion = Ext.create('Ext.form.Panel',{
                stateId: 'navigation-panel',
                id: 'west-panel',
                region:'west',
                iconCls:'systemmenu',
                title:'系统菜单',
                split: true,
                collapsible: true,
                width: 190,
                margins: '0 0 0 5',
                layout:'accordion',
                layoutConfig: {
                    titleCollapse: true,
                    animate: true
                },
                items:items
            });
//            中间的面板
            var tabCloseMenu = Ext.create('Ext.ux.TabCloseMenu', {
                closeTabText:'关闭当前标签页',
                closeOthersTabsText:'关闭其他标签页',
                closeAllTabsText:'关闭所有标签页',
                listeners: {
                    aftermenu: function () {
                        currentItem = null;
                    },
                    beforemenu: function (menu, item) {
                        menu.child('[text="关闭当前标签页"]').setIconCls("saved");
                        menu.child('[text="关闭其他标签页"]').setIconCls("edit");
                        menu.child('[text="关闭所有标签页"]').setIconCls("delete");
                        currentItem = item;
                    }
                }
            });
            var contentPanel = Ext.createWidget('tabpanel', {
                id:'center',
                region:'center',
                enableTabScroll:true,
                activeTab:0,
                items:[{
                    id:'homePage',
                    title:'首页',
                    reorderable: false,
                    iconCls:'systemmenu',
                    autoScroll:true,
                    html:'<div align="left"style="padding-top: 50px;padding-left:150px;font-size: 18px;white-space: normal;" width="300px">' +
                            '前言' +
                            '<br/><br/>' +
                            'demo 展示平台' +
                            '<br/><br/>' +
                            '系统特点' +
                            '<br/><br/>' +
                            '\ta)使用ehcache 缓存存储用户登录信息，同时可以结合 terracotta 进行分布式部署，可以避免传统分布式部署中因session 复制带来的性能问题' +
                            '<br/><br/>' +
                            '\tb)系统中菜单配置可以使用右键菜单管理，这个实现当时花了我一点时间，我感觉右键更符合传统的操作习惯' +
                            '<br/><br/>' +
                            '\tc)系统利用struts2 拦截器 判断用户是否登录' +
                            '<br/><br/>' +
                            '\td)使用p6spy 打印出hibernate 中已经替换了占位符参数的sql语句，为hibernate的调试不在感到苦恼' +
                            '</p></div>'
                }],
                listeners:{
                    tabchange:function(){
                        // todo 这里检测session,是否失效
                    }
                },
                plugins:[
                    tabCloseMenu,
                    Ext.create('Ext.ux.TabReorderer'),
                    {
                        ptype:'tabscrollermenu',
                        maxText:15,
                        pageSize:5
                    }
                ]
            });
            var viewport = Ext.create('Ext.Viewport', {
                layout: {
                    type: 'border',
                    padding: 5
                },
                defaults: {
                    split: true
                },
                items: [topPanel,accordion,contentPanel,{
                    region: 'south',
                    align:'center',
                    height: 25,
                    titleAlign:'center',
                    title: '2012-许多年之后@skyline 版权所有,学习交流'
                }]
            });
        });

        function changePassword(){
            location.href = 'changePassword';
        }
        function changeCSS(url){
            Ext.util.CSS.swapStyleSheet("ext_theme",url);
            var time=new  Date();
            time.setTime(time.getTime()+365*24*60*60*1000);
            document.cookie ="css=" + url + "; expires="+time.toGMTString();
        }
    </script>
</head>
<body>
<div id = "topDiv">
    <table width="100%" border="0" height=100 cellpadding="0" cellspacing="0" >
        <tr>
            <td class="td_norepeat" bgcolor="#023D79" align="right" >
                <font color='white' size="2" face="微软雅黑">欢迎您：<s:property value="session.user.userName" /></font>&nbsp;&nbsp;&nbsp;&nbsp;　
            </td>
        </tr>
    </table>
</div>
</body>
</html>
