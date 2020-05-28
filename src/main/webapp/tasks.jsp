<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>

<h2>
    Your Tasks:
</h2>
<table class="table-bordered table table-dark table-hover ">
 <caption>List of projects</caption>
  <thead>
    <tr>
      <th scope="col">id</th>
      <th scope="col">Name</th>
      <th scope="col">Description</th>
      <th scope="col">Assignee</th>
      <th scope="col">Status</th>
    </tr>
  </thead>
  <c:forEach items="${tasks}" var="task">
  <tbody>
    <tr>
      <th scope="row"> ${task.id}</th>
      <td> ${task.name}</td>
      <td> ${task.description}</td>
      <td>
        ${task.employee}
      </td>
       <td>
               ${task.status}
       </td>
    </tr>
  </tbody>
  </c:forEach>
</table>


</div>
<%@include file="/layout/footer.jsp" %>
