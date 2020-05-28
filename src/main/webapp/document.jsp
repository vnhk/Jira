<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>

<div class="card text-white bg-secondary mb-5 mt-5">
   <div class="card-body border-top">
      <h5 class="card-title h1 border-top">#${document.id}  ${document.title}  ${document.topic}</h5>
      <h6 class="card-subtitle mb-2 h2 border-top">${document.description}</h6>
      <p class="card-text h3 border-top">${document.content}</p>
      <div class="row mt-5">
         <div class="col-sm">
            <a type="button" href="?action=deletedocument&documentId=${document.id}&projectId=${param.projectId}" class="btn btn-primary btn-lg btn-block">Delete this Document</a>
         </div>
         <div class="col-sm">
           <a type="button" href="?action=displaydocumenteditform&documentId=${document.id}"  class="btn btn-secondary btn-lg btn-block">Edit this Document</a>
         </div>
      </div>
   </div>
</div>

</div>
<%@include file="/layout/footer.jsp" %>
