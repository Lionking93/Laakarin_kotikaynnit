<%-- 
    Document   : pomonSivu
    Created on : 10.12.2014, 17:40:02
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<t:pohja kenenTili="Johtaja" ekaTab="Lääkärin valinta" tokaTab="eiole" kolmasTab="eiole">
    <div class="tab-pane active">
        <h3>Valitse alta lääkäri, jolle haluat lisätä työvuoroja</h3>
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

