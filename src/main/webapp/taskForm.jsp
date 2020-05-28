<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>
<form class = "mt-5" action="?action=createtask&projectId=${projectId}" method="post">
   <div class="form-group">
      <label for="exampleFormControlInput1">Name</label>
      <input name = "name" class="form-control" id="exampleFormControlInput1">
   </div>
   <div class="form-group">
      <label for="exampleFormControlInput1">Description</label>
      <textarea name = "description" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
   </div>
   <div class="form-group">
      <label for="exampleFormControlInput1">Employee Name</label>
      <input name = "employee" class="form-control" id="exampleFormControlInput1">
   </div>
   <input class="btn btn-primary mt-3" type="submit" value="Create">
</form>
<%@include file="/layout/footer.jsp" %>