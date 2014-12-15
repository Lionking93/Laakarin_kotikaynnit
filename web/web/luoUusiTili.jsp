<%-- 
    Document   : luoUusiTili
    Created on : 15.12.2014, 3:38:56
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MediHome-ajanvarausjärjestelmä - Luo uusi tili</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
    </head>
    <body>
        <form>
            <h1>Täytä allaolevat kentät luodaksesi uuden käyttäjätilin</h1>
            <br />
            <b><t:virhe></t:virhe></b>
            <b><t:onnistui></t:onnistui></b>
            <table class="table table-striped" style="width: auto">
                <tr>
                    <th>Nimi:</th>
                    <td><input type="text" name="nimi" placeholder="Lisää nimi" value="${nimi}" /></td>
                </tr>
                <tr>
                    <th>Käyttäjätunnus:</th>
                    <td><input type="text" name="tunnus" placeholder="Lisää käyttäjätunnus" value="${tunnus}" /></td>
                </tr>
                <tr>
                    <th>Salasana:</th>
                    <td><input type="text" name="salasana" placeholder="Lisää salasana" value="${salasana}" /></td>
                </tr>
                <tr>
                    <th>Henkilötunnus:</th>
                    <td><input type="text" name="henkilotunnus" placeholder="Lisää henkilötunnus" value="${henkilotunnus}" /></td>
                </tr>
                <tr>
                    <th>Osoite:</th>
                    <td><input type="text" name="osoite" placeholder="Lisää osoite" value="${osoite}" /></td>
                </tr>
                <tr>
                    <td>
                        <button class="btn btn-primary" name="luoTili" type="submit">Luo tili</button>
                        <button class="btn btn-primary" name="palaaAloitussivulle" type="submit">Palaa aloitussivulle</button>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
