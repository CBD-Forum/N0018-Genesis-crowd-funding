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
				<label class="x-form-item-label">课程编号:</label>
				<div class="x-form-element">
					<select id="courseId" name="courseId"></select>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">讲师编号:</label>
				<div class="x-form-element">
					<select id="teacherId" name="teacherId"></select>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">课程开始日期:</label>
				<div class="x-form-element">
					<input name="startDate" id="startDate" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
				</div>
			</div>
			<div class="x-form-item">
				<label class="x-form-item-label">课程结束日期:</label>
				<div class="x-form-element">
					<input name="endDate" id="endDate" type="text" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/>
					<span id="datespan" style="color:red"></span>
				</div>
			</div>
			<div class="psb" style="margin-bottom:50px;">
				<a id="saveBtn" class="easyui-linkbutton searchBtn">确定</a>
				<a id="returnBtn" href="<%=path %>/course.course_plan_list.html" class="easyui-linkbutton searchBtn">返回</a>
			</div>
		</form>
	</div>

	
    <script type="text/javascript" charset="utf-8" src="<%=path %>/js/course/course_plan_add.js"></script>
    
