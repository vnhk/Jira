<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>

<form  class = "mt-5" action="?action=addusertoproject&projectId=${projectId}" method="post">
  <div class="form-group">
    <label for="exampleFormControlInput1">Username</label>
    <input name = "username" class="form-control" id="exampleFormControlInput1" required>
  </div>
  <div class="form-check">
      <input class="form-check-input" type="radio" name="role" id="exampleRadios1" value="manager" checked>
      <label class="form-check-label" for="exampleRadios1">
        Manager
      </label>
    </div>
    <div class="form-check">
      <input class="form-check-input" type="radio" name="role" id="exampleRadios2" value="employee">
      <label class="form-check-label" for="exampleRadios2">
       Employee
      </label>
    </div>
    <div class="form-check">
        <input class="form-check-input" type="radio" name="role" id="exampleRadios3" value="customer">
        <label class="form-check-label" for="exampleRadios3">
          Customer
        </label>
      </div>
  <input class="btn btn-primary mt-3" type="submit" value="Create">
</form>

<%@include file="/layout/footer.jsp" %>


