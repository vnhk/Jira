<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/layout/header.jsp" %>
<form action="?action=editdocument&documentId=${document.id}" method="post">
   <div class="form-group">
      <label for="exampleFormControlInput1">Title</label>
      <input value = "${document.title}" name = "title" class="form-control" id="exampleFormControlInput1" >
   </div>
   <div class="form-group">
      <label for="exampleFormControlInput1">Topic</label>
      <input value = "${document.topic}" name = "topic" class="form-control" id="exampleFormControlInput1">
   </div>
   <div class="form-group">
      <label for="exampleFormControlInput1">Description</label>
      <input value = "${document.description}" name = "description" class="form-control" id="exampleFormControlInput1" >
   </div>
   <div class="form-group">
      <label for="exampleFormControlTextarea1">Content</label>
      <textarea name = "content" class="form-control" id="exampleFormControlTextarea1" rows="3">${document.content}</textarea>
   </div>
   <input class="btn btn-primary mt-3" type="submit" value="Create">
</form>
<%@include file="/layout/footer.jsp" %>