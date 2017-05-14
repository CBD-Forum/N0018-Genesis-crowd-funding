<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript">
var conId='${id}';
</script>
	
	<div id="addTeacher" style="display: block; overflow: auto;" class="add">
		<form id="teacherForm" method="post">
			<div class="x-form-item">
				<label class="x-form-item-label">课程名称:</label>
				<div class="x-form-element">
					<input name="courseName" type="text"/>
				</div>
			</div>
			
			<div class="x-form-item">
				<label class="x-form-item-label">课程介绍:</label>
				<div class="x-form-element">
					<textarea rows="14" name="courseDes" placeholder="课程介绍"></textarea>
				</div>
			</div>
			<div class="psb" style="margin-top:10px; margin-bottom: 10px;"><a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a><a id="returnBtn" href="<%=path %>/course.course_list.html" class="easyui-linkbutton searchBtn">返回</a></div>
		</form>
	</div>
		
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/course/course_add.js"></script>
    
