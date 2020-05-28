<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="layout/header.jsp" %>
<br />
<c:if test="${!empty error}">
</c:if>
<c:choose>
   <c:when test="${!empty error}">
      <div class="alert alert-danger" role="alert">
         <h4 class="alert-heading">Something went wrong!</h4>
         <p>
            ${error}
         </p>
         <hr>
         <p class="mb-0">If the problem persists contact an administrator.</p>
      </div>
   </c:when>
   <c:when test="${!empty warning}">
      <div class="alert alert-warning" role="alert">
         <h4 class="alert-heading">Attention!</h4>
         <p>
            ${warning}
         </p>
         <hr>
         <p class="mb-0">If the problem persists contact an administrator.</p>
      </div>
   </c:when>
   <c:otherwise>
      <div class="alert alert-info" role="alert">
         <h4 class="alert-heading">Information:</h4>
         <p>
            ${message}
         </p>
      </div>
   </c:otherwise>
</c:choose>
<%@include file="layout/footer.jsp" %>