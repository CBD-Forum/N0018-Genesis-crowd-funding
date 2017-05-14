<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var conId='${id}';
</script>
	<link rel="stylesheet" type="text/css" href="<%=path%>/js/common/webuploader/webuploader.css"/>
<script type="text/javascript" src="<%=path%>/js/common/webuploader/webuploader.min.js"></script>
	<div id="addTeacher" style="display: block; overflow: auto;" class="add">
		<form id="teacherForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">讲师姓名:</label>
				<div class="x-form-element">
					<input name="teacherName" type="text"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">讲师头衔:</label>
				<div class="x-form-element">
					<input name="teacherTitle" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">讲师资料:</label>
				<div class="x-form-element">
					<textarea rows="14" name="teacherDes" placeholder="讲师资料"></textarea>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">讲师头像:</label>
				<input type="hidden" name="teacherLogo"/>
				<div class="x-form-element">
					<div id="photoDiv" style="margin-left: 10px;">
					    <!--用来存放item-->
					    <div class="filelist"></div>
					    <div id="photoBtn" style="margin-left: 16px;">选择图片</div>
					</div>
				</div>
			</div>
			<div class="psb" style="margin-top:10px; margin-bottom: 10px;">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a>
				<a id="returnBtn" href="<%=path %>/course.course_teacher_list.html" class="easyui-linkbutton searchBtn">返回</a>
			</div>
		</form>
	</div>
	
	
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/course/course_teacher_add.js"></script>
    
