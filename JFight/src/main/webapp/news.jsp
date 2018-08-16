<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="/Styles/styles.css">

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
        <div class="col text-justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Culpa minima nesciunt
            officia. Adipisci amet asperiores, at consequuntur culpa cum delectus deserunt dolore dolorem eaque et
            facilis fugiat fugit hic illum, inventore ipsa iure molestiae nihil nisi nobis non officia omnis praesentium
            quas quod rerum sapiente sed similique soluta tempora ullam veritatis voluptatibus. A aliquid architecto
            asperiores aspernatur assumenda cum deserunt ea eos error id impedit inventore ipsum itaque, magni molestiae
            necessitatibus neque non odit, omnis porro provident quaerat quo reprehenderit rerum saepe sed sunt ut velit
            vero voluptatem? A alias dolorem eum illum numquam obcaecati quo, soluta ut velit voluptas.
        </div>
        <div class="col text-justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Culpa minima nesciunt
            officia. Adipisci amet asperiores, at consequuntur culpa cum delectus deserunt dolore dolorem eaque et
            facilis fugiat fugit hic illum, inventore ipsa iure molestiae nihil nisi nobis non officia omnis praesentium
            quas quod rerum sapiente sed similique soluta tempora ullam veritatis voluptatibus. A aliquid architecto
            asperiores aspernatur assumenda cum deserunt ea eos error id impedit inventore ipsum itaque, magni molestiae
            necessitatibus neque non odit, omnis porro provident quaerat quo reprehenderit rerum saepe sed sunt ut velit
            vero voluptatem? A alias dolorem eum illum numquam obcaecati quo, soluta ut velit voluptas.
        </div>
        <div class="col text-justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab adipisci architecto
            aspernatur beatae blanditiis commodi consequuntur cum earum hic id impedit ipsa iste itaque labore
            laboriosam laudantium natus nemo nisi non obcaecati odio omnis optio quibusdam, quod, quos repellat
            similique sit ullam vel veritatis. A ab, accusamus accusantium at blanditiis consequatur cupiditate
            doloribus eius eos eum excepturi exercitationem fuga harum hic inventore ipsam itaque iure mollitia neque
            non, obcaecati odio officiis, quaerat quis quisquam ratione sequi tempora ullam voluptate voluptates!
            Aperiam fuga id nam, nulla similique sint veritatis voluptates! Asperiores dicta dolores exercitationem
            ipsum magnam perferendis possimus, quasi saepe soluta?
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
<script src="Scripts/news.js"></script>
</body>
</html>
