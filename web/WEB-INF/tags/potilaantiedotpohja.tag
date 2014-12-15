<%-- 
    Document   : potilaantiedotpohja
    Created on : 1.12.2014, 16:44:55
    Author     : leo
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="asiakkaanNimi"%>
<%@attribute name="asiakkaanHetu" %>
<%@attribute name="asiakkaanOsoite" %>

<%-- any content can be specified here e.g.: --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>MediHome-ajanvarausjärjestelmä - Potilaan tiedot</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/bootstrap-theme.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
    </head>
    <body>
        <form>
            <p>Lääkäritili</p>
            <div class="row">
                <div class="col-xs-10">
                    <h2>Potilaan tiedot</h2>
                    <br/>
                    <table class="table">
                        <tr><th>Nimi: </th><td><c:out value="${asiakkaanNimi}" /></td></tr>
                        <tr><th>Henkilötunnus: </th><td><c:out value="${asiakkaanHetu}" /></td></tr>
                        <tr><th>Osoite: </th><td><c:out value="${asiakkaanOsoite}" /></td></tr>
                    </table>
                </div>
                <div class="col-xs-2" id="kirjautuminen">
                    <input class="btn btn-primary" type="submit" name="etusivulle" value="Palaa etusivulle" />
                </div>
            </div>
            <div class="tabbable">
                <ul class="nav-tabs nav">
                    <li class="${aktiivinenEkaTab}"><a class="tabit"><input class="btn btn-link" type="submit" name="ekaTab" value="${ekaTab}" /></a></li>
                    <li class="${aktiivinenTokaTab}"><a class="tabit"><input class="btn btn-link" type="submit" name="tokaTab" value="${tokaTab}" /></a></li>
                </ul>
                <jsp:doBody/>
            </div>