<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>
<c:if test = "${!empty adminActions}">
   <div class="card text-white bg-secondary mb-5 mt-5">
      <div class="card-body">
         <div class="row">
            <div class="col-sm">
               <a type="button" href="?action=displayuserform"  class="btn btn-dark btn-lg btn-block">Create new User</a>
            </div>
            <div class="col-sm">
               <a type="button" href="?action=showallproject"  class="btn btn-dark btn-lg btn-block">Show all Projects</a>
            </div>
            <div class="col-sm">
               <a type="button" href="?action=displayprojectform"  class="btn btn-dark btn-lg btn-block">Create new Project</a>
            </div>
         </div>
      </div>
   </div>
</c:if>