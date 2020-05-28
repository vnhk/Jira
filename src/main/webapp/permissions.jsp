<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="project.jsp" %>
<h2 style = "text-align:center;">
   Users with roles in this Project: <br/>
</h2>
<div class="table-responsive-xl">
   <table class="table-bordered table table-dark table-hover ">
      <caption>List of Users and Roles</caption>
      <thead>
         <tr>
            <th scope="col">ID</th>
            <th scope="col">Username</th>
            <th scope="col">Role</th>
         </tr>
      </thead>
      <c:set var="count" value="0" scope="page" />
      <c:forEach items="${permissions}" var="permission">
         <tbody>
            <tr>
               <th scope="row"> ${count+1}</th>
               <br>
               <td> ${names.get(count)}</td>
               <td>
                  <c:choose>
                     <c:when test="${permission.roleId=='1'}">
                        Manager
                     </c:when>
                     <c:when test="${permission.roleId=='2'}">
                        Employee
                     </c:when>
                     <c:otherwise>
                        Customer
                     </c:otherwise>
                  </c:choose>
               </td>
            </tr>
         </tbody>
         <c:set var="count" value="${count + 1}" scope="page"/>
      </c:forEach>
   </table>
</div>
<%@include file="/layout/footer.jsp" %>