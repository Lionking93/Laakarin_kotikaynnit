<%-- 
    Document   : kirjautuminen
    Created on : 18.11.2014, 13:56:24
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MediHome-ajanvarausjärjestelmä - Kirjaudu sisään</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
    </head>
    <body>
        <div class="row">
            <div class="col-sm-2">
            </div>
            <div class="col-sm-3">
                <h1>MediHome-ajanvarausjärjestelmä</h1>
                <b><t:virhe></t:virhe></b>
            </div>
        </div>
        <br/>
        <form class="form-horizontal" role="form" action="login" method="POST">
            <div class="form-group">
                <label for="syotaTunnus" class="col-sm-2 control-label">Asiakastunnus</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="syotaTunnus" name="username" value="${username}">
                </div>
            </div>
            <div class="form-group">
                <label for="syotaSalasana" class="col-sm-2 control-label">Salasana</label>
                <div class="col-sm-4">
                    <input type="password" class="form-control" id="syotaSalasana" name="password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-2">
                    <button class="btn btn-primary" type="submit">Kirjaudu</button>
                </div>
                <div class="col-sm-2">
                    <input class="btn btn-primary" name="luoTunnus" type="submit" value="Luo uusi tunnus" />
                </div>
            </div>
        </form>
    </body>
</html>

