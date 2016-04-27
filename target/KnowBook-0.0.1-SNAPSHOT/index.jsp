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
<form action="<%=basePath %>users/registe" method="post">
<input type="text" name="phoneNumber" />
<input type="password" name="password" />
<!-- <input type="text" name="bookName" />
<input type="text" name="bookpicture" />
<input type="text" name="bookAuthor" />
<input type="text" name="bookClass" />
<input type="text" name="bookSummary" />
<input type="text" name="recommenReason" /> -->
<input type="submit" value="cha"/>
</form>
</body>
</html>
