<%-- 
    Document   : viikkoaikatauluEkatab
    Created on : 14.12.2014, 12:12:16
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<t:pohja kenenTili="Asiakas" aktiivinenTokaTab="active" ekaTab="Omat Varaukset" tokaTab="Valitse Lääkäri" kolmasTab="Hoito-ohjeet">
    <div class="tab-pane active">
        <h3>Valitse alta lääkäri, jolta haluat varata kotikäynnin</h3>
        <form>
            <select name="laakarinId">
                <c:forEach var="laakari" items="${laakarit}">
                    <option value="${laakari.id}"><c:out value="${laakari.nimi}" /></option>
                </c:forEach>
            </select>
            <input class="btn btn-primary" type="submit" name="valitseLaakari" value="Valitse lääkäri" />
        </form>
    </div>
</t:pohja>
