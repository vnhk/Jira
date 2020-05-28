<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>

<div class="card text-white bg-secondary mb-3 mt-3">
   <div class="card-body border-top">
      <h5 class="card-title border-top h1">#${project.id} </h5>
      <h6 class="card-subtitle mb-2 h2">${project.name}</h6>
      <p class="card-text border-top h3">${project.description}</p>
      <div class="row mt-5">
      <c:choose>
          <c:when test="${role==0}">
             <div class="col-sm">
                         <a type="button" href="?action=deleteproject&projectId=${project.id}"  class="btn btn-primary btn-lg btn-block">Delete this Project</a>
                      </div>
                      <div class="col-sm">
                         <a type="button" href="?action=showdocuments&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Show Documents</a>
                      </div>
                      <div class="col-sm">
                         <a type="button" href="?action=displayusertoprojectform&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Add User to Project</a>
                      </div>
                      <div class="col-sm">
                         <a type="button" href="?action=showprojectdetails&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Show Project Details</a>
                      </div>
          </c:when>
          <c:when test="${role==1}">
                    <div class="col-sm">
                       <a type="button" href="?action=showtasks&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Show my Tasks</a>
                     </div>
                    <div class="col-sm">
                      <a type="button" href="?action=showdocuments&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Show Documents</a>
                   </div>
          </c:when>
          <c:otherwise>
                <div class="col-sm">
                  <a type="button" href="?action=showdocuments&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Show Documents</a>
                  </div>

                <div class="col-sm">
                   <a type="button" href="?action=displaytaskform&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Create Task</a>
                  </div>

                  <div class="col-sm">
                     <a type="button" href="?action=showprojectdetails&projectId=${project.id}"  class="btn btn-secondary btn-lg btn-block">Show Project Details</a>
                  </div>
          </c:otherwise>
      </c:choose>


      </div>
   </div>
</div>
<%@include file="/layout/footer.jsp" %>
