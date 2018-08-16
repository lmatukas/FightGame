<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="Styles/styles.css">
</head>
<body>

<nav class="navbar navbar-light bg-light navbar-expand-md bg-faded justify-content-center">
    <a class="navbar-brand d-flex w-50 mr-auto" href="/user">${userName}</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse collapse w-100" id="navbarSupportedContent">
        <ul class="navbar-nav w-100 justify-content-center">
            <li class="nav-item active">
                <a class="nav-link" href="/news">News <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/challenge">Fight</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/user">Account</a>
            </li>
        </ul>
        <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
            <li class="nav-item">
                <a class="nav-link" href="/login.jsp">Logout</a>
            </li>
        </ul>
    </div>
</nav>

<br>

<div class="container">
    <div class="row">
        <div class="col">
            <h3 id="userName">${userName}</h3>
        </div>
        <div class="col">
            <h3>Challenge:</h3>
            <table class="table table-dark">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Player</th>
                    <th scope="col">ID</th>
                    <th scope="col">Challenge him</th>
                </tr>
                </thead>
                <tbody>
                <java:forEach var="item" items="${readyToFightList}">
                    <tr>
                        <th scope="row"></th>
                        <td>${item.get("userName")}</td>
                        <td>${item.get("userId")}</td>
                        <td>
                            <div class="form-check">
                                <input type="checkbox" value="${item.get("userId")}" name="player">
                            </div>
                        </td>
                    </tr>
                </java:forEach>
                </tbody>
            </table>
            <button type="button" class="btn btn-lg btn-danger float-right" id="challengeBtn">Challenge</button>
        </div>
    </div>
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
<script src="Scripts/challenge.js"></script>
</body>
</html>
