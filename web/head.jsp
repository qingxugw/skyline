<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ext-all-debug.js"></script>
		<%--<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ext-all.js"></script>--%>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/TabCloseMenu.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/TabScrollerMenu.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/BoxReorderer.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/TabReorderer.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/skyline-lang-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/examples.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/skyline.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/skyline-compent.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/ext-all.css" />
		<%--<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/ext-all-debug.css" />--%>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/ux/TabScrollerMenu.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/skyline.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/example.css" />
  </head>
</html>