<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/head.jsp"%>
<!DOCTYPE html>
<HTML>
<HEAD>
    <!--[if IE]>
    <script type="text/javascript" src="js/html5.js"></script>
    <![endif]-->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">

    </style>
    <TITLE>图表展示</TITLE>
    <script type="text/javascript">
        Ext.onReady(function () {
            var maxMonth = null;
            var secondMonth = null;
            var isTooltip = false;
            var url = '<%=basePath%>jsp/upload/queryMonthArray.action';
            AjaxSend(url,null,handleResult,isTooltip);

            function handleResult(result){
                maxMonth = result.root.maxMonth;
                secondMonth = result.root.secondMonth;
                var isTooltip = false;
                var url = '<%=basePath%>jsp/upload/queryMonthArray.action';
                Ext.define('chartModel', {
                    extend: 'Ext.data.Model',
                    fields:[
                        {name: 'address',type: 'string'},
                        {name: maxMonth,type: 'double'},
                        {name: secondMonth,type: 'double'}
                    ]
                });

                var chartStore = Ext.create('Ext.data.Store',{
                    id:'chartStore',
                    model:'chartModel',
                    loadMask:true,
                    proxy:{
                        type:'ajax',
                        url:'<%=basePath%>jsp/upload/queryChartData.action',
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
                            chartStore.proxy.extraParams = {
                                maxMonth:maxMonth,
                                secondMonth:secondMonth
                            }
                        },
                        load:function (store){
                        }
                    }
                });

                if(maxMonth != null && maxMonth != "" && secondMonth !="" && secondMonth != null){
                    chartStore.load();
                }
                //首先根据表名获取月份范围字段
                var chart = Ext.create('Ext.chart.Chart', {
                    xtype: 'chart',
                    style: 'background:#fff',
                    animate: true,
                    store: chartStore,
                    shadow: true,
                    theme: 'Category1',
                    legend: {
                        position: 'right'
                    },
                    axes: [{
                        type: 'Numeric',
                        position: 'left',
                        fields: [maxMonth, secondMonth],
                        title: '销售利润',
                        minorTickSteps: 1,
                        grid: {
                            odd: {
                                opacity: 1,
                                fill: '#ddd',
                                stroke: '#bbb',
                                'stroke-width': 0.5
                            }
                        }
                    }, {
                        type: 'Category',
                        position: 'bottom',
                        fields: ['address'],
                        title: '城市名称'
                    }],
                    series: [{
                        type: 'line',
                        highlight: {
                            size: 7,
                            radius: 7
                        },
                        tips: {
                            trackMouse: true,
                            width: 140,
                            height: 28,
                            renderer: function(storeItem, item) {
                                this.setTitle(storeItem.get('address') + ': ' + storeItem.get(maxMonth));
                            }
                        },
                        label: {
                            display: 'insideEnd',
                            'text-anchor': 'middle',
                            field: maxMonth,
                            renderer: Ext.util.Format.numberRenderer('0'),
                            orientation: 'vertical',
                            color: '#333',
                            rotate:{
                                degrees: 45
                            }
                        },
                        axis: 'left',
                        xField: 'address',
                        yField: maxMonth,
                        markerConfig: {
                            type: 'cross',
                            size: 4,
                            radius: 4,
                            'stroke-width': 0
                        }
                    }, {
                        type: 'line',
                        highlight: {
                            size: 7,
                            radius: 7
                        },
                        tips: {
                            trackMouse: true,
                            width: 140,
                            height: 28,
                            renderer: function(storeItem, item) {
                                this.setTitle(storeItem.get('address') + ': ' + storeItem.get(secondMonth));
                            }
                        },
                        axis: 'left',
                        smooth: true,
                        xField: 'address',
                        yField: secondMonth,
                        markerConfig: {
                            type: 'circle',
                            size: 4,
                            radius: 4,
                            'stroke-width': 0
                        }
                    }]
                });


                var viewport = Ext.create('Ext.Viewport', {
                    layout: {
                        type: 'fit'
                    },
                    defaults: {
                        split: true,
                        frame:true
                    },
                    items:Ext.create('Ext.form.Panel',{
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
                            type:'fit'
                        },
                        items:chart
                    })
                });
            }
        });
    </script>
</HEAD>
<body>
</body>
</HTML>