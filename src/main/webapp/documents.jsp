<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>
<h2 style = "text-align:center;">
   <a type="button" href = "?action=displaydocumentform&projectId=${projectId}" class="btn btn-primary btn-lg btn-block mt-5 mb-5">Create Document</a>
   <h3 class = "text-center">Documents in Project:</h3>
</h2>
<div class="table-responsive-xl">
   <table class="table-bordered table table-dark table-hover ">
      <caption>List of documents</caption>
      <thead>
         <tr>
            <th scope="col">ID</th>
            <th scope="col">Title</th>
            <th scope="col">Topic</th>
            <th scope="col">Link</th>
         </tr>
      </thead>
      <c:forEach items="${documents}" var="document">
         <tbody>
            <tr>
               <th scope="row"> ${document.id}</th>
               <td> ${document.title}</td>
               <td> ${document.topic}</td>
               <td>
               <p>
                <a class = ".bg-info" href = "?action=showdocumentdetails&documentId=${document.id}&projectId=${projectId}" class = "btn btn-secondary btn-lg">Show</a> </td>
               </p>
            </tr>
         </tbody>
      </c:forEach>
   </table>
</div>
<%@include file="/layout/footer.jsp" %>