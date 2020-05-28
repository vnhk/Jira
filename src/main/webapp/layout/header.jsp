<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!doctype html>
<html lang="en">
   <head>
      <title> Project Management </title>
      <!-- Required meta tags -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <!-- Bootstrap CSS -->
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      <link href="css/style.css" type="text/css" rel="stylesheet" />
      <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
      <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
   </head>
   <body>
      <div class="pos-f-t ">
         <div class="collapse" id="navbarToggleExternalContent">
            <div class="bg-dark p-4">
               <h5 class="text-white h4">Project Management System - Jira
                  <br/>
                  <a href = "https://www.facebook.com/example_site/" target="_blank">Contact us.</a>
               </h5>
               <span class="text-muted">Project Management</span>
            </div>
         </div>
         <nav class="navbar navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
            JIRA
            <span class="navbar-toggler-icon"></span>
            </button>
            <a href="?action=showprojects">
               <h5 class="text-white h4" >Home</h5>
            </a>
            <a href="?action=logout">
               <h5 class="text-white h4" >Logout</h5>
            </a>
         </nav>
      </div>
      <div class = "container-fluid w-75">