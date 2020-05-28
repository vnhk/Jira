<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>

<form class = "mt-5" action="?action=createuser" method="post">
  <div class="form-group">
    <label for="usernameInput">Username</label>
    <input name = "login" class="form-control" id="usernameInput" required>
  </div>
   <div class="form-group">
      <label for="passwordInput">Password</label>
      <input name = "password" type = "password" class="form-control" id="passwordInput" required>
    </div>
  <div class="form-check">
    <input class="form-check-input" type="radio" name="admin" id="exampleRadios1" value="no" checked>
    <label class="form-check-label" for="exampleRadios1">
      Non Admin
    </label>
  </div>
  <div class="form-check">
    <input class="form-check-input" type="radio" name="admin" id="exampleRadios2" value="yes">
    <label class="form-check-label" for="exampleRadios2">
     Admin
    </label>
  </div>
  <input class="btn btn-primary" type="submit" value="Create">
</form>

<%@include file="/layout/footer.jsp" %>