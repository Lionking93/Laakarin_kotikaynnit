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
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <script src="js/jquery-1.11.1.min.js"></script>
    </head> 
    <body>
        <p>${kenenTili}</p>
        <div class="row">
            <div class="col-xs-10"><h2>Hei ${kayttajanNimi}! Tervetuloa käyttämään MediHome-ajanvarausjärjestelmää!</h2></div>
            <div class="col-xs-2" id="kirjautuminen"><form><button type="submit" name="kirjauduUlos">Kirjaudu ulos</button></form></div>
        </div>
        <br/>
        <div class="tabbable">
            <form>
                <ul class="nav-tabs nav">
                            <c:if test="${ekaTab != 'eiole'}">
                        <li class="${aktiivinenEkaTab}"><a class="tabit"><input class="btn btn-link" type="submit" name="ekaTab" value="${ekaTab}" /></a></li>
                            </c:if>
                            <c:if test="${tokaTab != 'eiole'}">
                        <li class="${aktiivinenTokaTab}"><a class="tabit"><input class="btn btn-link" type="submit" name="tokaTab" value="${tokaTab}" /></a></li>
                            </c:if>
                            <c:if test="${kolmasTab != 'eiole'}">       
                        <li class="${aktiivinenKolmasTab}"><a class="tabit"><input class="btn btn-link" type="submit" name="kolmasTab" value="${kolmasTab}" /></a></li>
                            </c:if>
                </ul>
            </form>
            <jsp:doBody/>
        </div>
        <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    </body>
</html>