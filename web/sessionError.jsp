<%--
  Created by IntelliJ IDEA.
  User: skyline
  Date: 12-10-30
  Time: 下午5:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp"%>
<html>
<head>
    <title>重新登录</title>
    <script type="text/javascript">
        Ext.onReady(function() {
            var viewport = Ext.create('Ext.Viewport', {
                layout: {
                    type: 'fit'
                },
                items: [{
                    xtype:'panel',
                    frame:true,
                    align:'center',
                    html:"<span style='padding:100px 100px'><a href='#' onclick='relogin()'><font size='20'>亲,登录超时,请重新登录</font></a></span>"
                }]
            });
        });
        function relogin(){
            window.parent.location.href = '/skyline/index.jsp';
        }
    </script>
</head>
<body>
</body>
</html>