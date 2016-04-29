<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<body>
<h2>Hello World!</h2>
<form action="<%=basePath %>booklist/noCollectBooklist" method="get">
<% session.setAttribute("phoneNumber", "18814122522"); %>
<!-- <input type="text" name="phoneNumber" />  -->
id<input type="text" name="booklistId" /><br/>
<input type="submit" value="submit">
</form>
</body>
</html>
