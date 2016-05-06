<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>辣鸡</title>
</head>
<body>  
<div class="panel-body">
   	<form id ="firstUpdateForm" action="<%=basePath %>upload/upload" method="post"
	    enctype="multipart/form-data" class="form-horizontal" role="form" target="hidden_frame">
		<div class="modal-body">
			<div class="form-group">
			<label class="col-sm-3 control-label">上传文件</label>
			<div class="col-sm-5">
			    <input type="file" id="firstDemoImgFile" name="imgFile">
			</div>
			</div>
		</div>
		<div class="modal-footer">
			<div id="firstUploadSucceed" style="display: none;">
			    <strong>新增成功！</strong><span id="firstUploadSucceedMsg"></span>
			</div>
			<div id="firstUploadFailed" style="display: none;">
			    <strong>对不起！新增失败</strong><span id="firstUploadFailedMsg"></span>
			</div>
		    <button id="createPeriadBtn" type="submit" class="btn btn-default">确定 </button>
		</div>
	</form> 
	<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
</div>
</body>  
</html>