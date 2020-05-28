<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>

<form class = "mt-5" action="?action=createproject" method="post">
  <div class="form-group">
    <label for="exampleFormControlInput1">Project Name</label>
    <input name = "name" class="form-control" id="exampleFormControlInput1">
  </div>
  <div class="form-group">
    <label for="exampleFormControlTextarea1">Project Description</label>
    <textarea name = "description" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
  </div>
    <input class="btn btn-primary" type="submit" value="Create">
</form>

<%@include file="/layout/footer.jsp" %>