<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="Styles/styles.css">
</head>
<body>
<div class="container top5" id="loginContainer">
    <form action="/login" method="post">
        <h3 class="text-center">Login</h3>
        <div class="form-group">
            <label for="emailLogin">Email</label>
            <input type="Email" class="form-control" id="emailLogin" aria-describedby="emailHelp"
                   placeholder="Enter Email" name="email">
        </div>
        <div class="form-group">
            <label for="passwordLogin">Password</label>
            <input type="password" class="form-control" id="passwordLogin" placeholder="Enter password" name="password"
                   pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,50}$" required
                   title="Enter at least 8 characters, one uppercase and one number">


        </div>
        <div class="form-group">
            <a href="#" id="goToReg">
                <small>Register</small>
            </a>
        </div>
        <input type="submit" class="btn btn-primary" value="login">
    </form>
</div>

<div class="container hide" id="registerContainer">
    <form action="/login" method="post">
        <h3 class="text-center top5">Register</h3>
        <div class="form-group">
            <label for="nameRegister">Account Name</label>
            <input type="text" class="form-control" id="nameRegister" aria-describedby="emailHelp"
                   pattern="[A-Za-z\d]{3,50}" required title="Enter at least 3 characters"
                   placeholder="Enter name" name="regName">
        </div>
        <div class="form-group">
            <label for="passwordRegister">Password</label>
            <input type="password" class="form-control" id="passwordRegister" placeholder="Enter password"
                   name="regPass" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,50}$" required
                   title="Enter at least 8 characters, one uppercase and one number">
        </div>
        <div class="form-group">
            <label for="passwordConfirmRegister">Confirm Password</label>
            <input type="password" class="form-control" id="passwordConfirmRegister" placeholder="Confirm Password"
                   name="confPass" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,50}$" required
                   title="Enter at least 8 characters, one uppercase and one number">
        </div>
        <div class="form-group">
            <label for="emailRegister">Email address</label>
            <input type="email" class="form-control" id="emailRegister" aria-describedby="emailHelp"
                   placeholder="Enter email" name="regEmail" required>
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
        </div>
        <div class="form-group">
            <a href="#" id="goToLog">
                <small>Go back to Login</small>
            </a>
        </div>
        <input type="submit" class="btn btn-primary" value="register">
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
        integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
        crossorigin="anonymous"></script>
<script src="Scripts/login.js"></script>
</body>
</html>
