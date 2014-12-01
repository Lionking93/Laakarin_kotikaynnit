<%-- 
    Document   : lisaaTieto
    Created on : 30.11.2014, 20:20:44
    Author     : leo
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="minkaTiedot"%>
<%@attribute name="ekaTieto" %>
<%@attribute name="ekanTiedonArvo" %>
<%@attribute name="tokaTieto" %>
<%@attribute name="tokanTiedonArvo" %>
<%@attribute name="kolmasTieto" %>
<%@attribute name="kolmannenTiedonArvo" %>
<%@attribute name="tekstikentta" %>
<%@attribute name="tekstikentanSisalto" %>

<%-- any content can be specified here e.g.: --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MediHome-ajanvarausjärjestelmä - Kuvaile oireitasi</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-theme.css" rel="stylesheet">
    </head>
    <body>
        <form>
            <div>
                <h2>${minkaTiedot}</h2>
                <br/>
                <table class="table">
                    <tr>
                        <th>${ekaTieto}: </th>
                        <td><c:out value="${ekanTiedonArvo}" /></td>
                    </tr>
                    <tr>
                        <th>${tokaTieto}: </th>
                        <td><c:out value="${tokanTiedonArvo}" /></td>
                    </tr>
                    <tr>
                        <th>${kolmasTieto}: </th>
                        <td><c:out value="${kolmannenTiedonArvo}" /></td>
                    </tr>
                </table>
            </div>
            <p>${tekstikentanSisalto} <br />
                <t:virhe></t:virhe>
            </p>
            <div class="row">
                <textarea class="col-xs-5" name="${tekstikentta}" rows="4"><c:out value="${syotettyTeksti.kuvaus}" /></textarea>
                <div class="col-xs-7"></div>
            </div>
            <jsp:doBody/>
        </form>
    </body>
</html>