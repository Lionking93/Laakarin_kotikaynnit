<%-- 
    Document   : pohja
    Created on : 18.11.2014, 12:27:25
    Author     : leo
--%>

<%@tag description="Käyttäjä-sivujen navigaatiopohja" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="kenenTili"%>
<%@attribute name="aktiivinenEkaTab" %>
<%@attribute name="aktiivinenTokaTab" %>
<%@attribute name="aktiivinenKolmasTab" %>
<%@attribute name="ekaTab" %>
<%@attribute name="tokaTab" %>
<%@attribute name="kolmasTab" %>

<%-- any content can be specified here e.g.: --%>
<!DOCTYPE html>
<html>
    <head>
        <title>MediHome-ajanvarausjärjestelmä</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
    </head> 
    <body>
        <p>${kenenTili}</p>
        <div class="row">
            <div class="col-xs-10"><h2>Hei ${kayttajanNimi}! Tervetuloa käyttämään MediHome-ajanvarausjärjestlemää!</h2></div>
            <div class="col-xs-2" id="kirjautuminen"><form><button type="submit" name="kirjauduUlos">Kirjaudu ulos</button></form></div>
        </div>
        <br/>
        <div class="tabbable">
            <form>
                <ul class="nav-tabs nav">
                    <li class="${aktiivinenEkaTab}"><a data-toggle="tab" class="tabit"><input class="btn btn-link" data-toggle="tab" type="submit" name="ekaTab" value="${ekaTab}" /></a></li>
                    <li class="${aktiivinenTokaTab}"><a data-toggle="tab" class="tabit"><input class="btn btn-link" type="submit" name="tokaTab" value="${tokaTab}" /></a></li>
                    <li class="${aktiivinenKolmasTab}"><a data-toggle="tab" class="tabit"><input class="btn btn-link" type="submit" name="kolmasTab" value="${kolmasTab}" /></a></li>
                </ul>
            </form>
            <jsp:doBody/>
        </div>
    </body>
</html>