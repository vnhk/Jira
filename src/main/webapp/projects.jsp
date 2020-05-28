<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/adminPanel.jsp" %>
<h2>
    Your Projects:
</h2>
<table class="table-bordered table table-dark table-hover ">
 <caption>List of projects</caption>
  <thead>
    <tr>
      <th scope="col">id</th>
      <th scope="col">Name</th>
      <th scope="col">Description</th>
      <th scope="col">Link</th>
    </tr>
  </thead>
  <c:forEach items="${projects}" var="project">
  <tbody>
    <tr>
      <th scope="row"> ${project.id}</th>
      <td> ${project.name}</td>
      <td> ${project.description}</td>
      <td>
         <p>
            <a class = ".bg-info" href = "?action=showoneproject&projectId=${project.id}">Show</a></td>
         </p>
    </tr>
  </tbody>
  </c:forEach>
</table>

<%@include file="/layout/footer.jsp" %>