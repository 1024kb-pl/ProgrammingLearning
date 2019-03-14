<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Panel</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../../js/bootstrap.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/fragments/menu.jspf"/>

<div class="container">

    <div class="row">

        <div class="col-md-4 col-md-offset-4">
            <h1>Manage account</h1>
            <br>
            <p><a href="editUser"><button type="button" class="btn btn-primary">Edycja konta</button></a></p>
            <p><a href="deleteUser"><button type="button" class="btn btn-primary">Usuń konto</button></a></p>
        </div>

    </div>

</div>

<jsp:include page="/WEB-INF/fragments/footer.jspf"/>

</body>
</html>
