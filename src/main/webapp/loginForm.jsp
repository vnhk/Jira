<!doctype html>
<html lang="en">
   <head>
      <!-- Required meta tags -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <!-- Bootstrap CSS -->
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      <link href="css/style.css" type="text/css" rel="stylesheet" />
      <title>Login</title>
   </head>
   <body>
      <div class = "container mt-5">
         <form action="?action=login" method="post">
            <div class="input-group mb-3">
               <div class="input-group-prepend">
                  <span class="input-group-text" id="basic-addon1">L :</span>
               </div>
               <input type="text" class="form-control" placeholder="Login" name = "login" aria-label="Username" aria-describedby="basic-addon1">
            </div>
            <div class="input-group mb-3">
               <div class="input-group-prepend">
                  <span class="input-group-text" id="basic-addon1">P :</span>
               </div>
               <input type="password" name = "password" class="form-control" placeholder="Password" aria-label="Username" aria-describedby="basic-addon1">
            </div>
            <div class="input-group mb-3">
               <div class="input-group-prepend">
                  <input class="btn btn-outline-secondary" id="button-addon1" type="submit" value="Log in" />
               </div>
            </div>
         </form>
      </div>
      <!-- Optional JavaScript -->
      <!-- jQuery first, then Popper.js, then Bootstrap JS -->
      <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
   </body>
</html>