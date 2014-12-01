<%-- 
    Document   : luoPotilastieto
    Created on : 30.11.2014, 18:48:12
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>MediHome-ajanvarausjärjestelmä - Potilaan tiedot</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        <script src="js/jquery-1.11.1.min.js"></script>
    </head>
    <body>
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
                <form><input class="btn btn-default" type="submit" name="etusivulle" value="Palaa etusivulle" /></form>
            </div>
        </div>
        <div class="tabbable">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#hoito-ohjeet" data-toggle="tab" class="tabit">Hoito-ohjeet</a></li>
                <li class=""><a href="#potilasraportit" data-toggle="tab" class="tabit">Potilasraportit</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="hoito-ohjeet">
                    <table class="table table-bordered">
                        <tr>
                            <th>Lisäysajankohta</th>
                            <th>Oireet</th>
                            <th colspan="2">Hoito-ohje</th>
                        </tr>
                        <c:forEach var="oire" items="${oirekuvaukset}" varStatus="paikka">
                            <tr>
                                <td><c:out value="${lisaysajankohdatOirekuvaus[paikka.index]}" /></td>
                                <td><c:out value="${oire.lisattavaTeksti}" /></td>
                                <td><c:if test=""></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                        <!--<tr>
                            <td>15.11.2014, 15:02</td>
                            <td>Pää kipee</td>
                            <td>2*400 mg Buranaa</td>
                            <td><a href="luo_potilastieto.html" class="btn btn-default">Muokkaa hoito-ohjetta</a></td>
                        </tr>
                        <tr>
                            <td>20.11.2014, 12:18</td>
                            <td>Borrelioosi</td>
                            <td>Doksisykliini-kuuri</td>
                            <td><a href="luo_potilastieto.html" class="btn btn-default">Muokkaa hoito-ohjetta</a></td>
                        </tr>!-->
                    </table>
                </div>
                <div id="potilasraportit" class="tab-pane">
                    <table class="table table-bordered">
                        <tr>
                            <th>Lisäysajankohta</th>
                            <th colspan="2">Raportti</th>
                        </tr>
                        <c:forEach var="raportti" items="${potilasraportit}" varStatus="monesko">
                            <tr>
                                <td><c:out value="${lisaysajankohdatPotilasraportti[monesko.index]}" /></td>
                                <td><c:out value="${raportti.lisattavaTeksti}" /></td>
                                <td><a href="luo_potilastieto.html" class="btn btn-default">Muokkaa potilasraporttia</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
        <script src="js/bootstrap.js"></script>
    </body>
</html>
