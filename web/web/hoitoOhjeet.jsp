<%-- 
    Document   : hoitoOhjeet
    Created on : 20.11.2014, 9:39:35
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<t:pohja kenenTili="Asiakas" aktiivinenKolmasTab="active" ekaTab="Omat varaukset" tokaTab="Valitse lääkäri" kolmasTab="Hoito-ohjeet">
    <div class="tab-pane active">
        <table class="table table-striped">
            <tr>
                <th>Vastaava lääkäri</th>
                <th>Lisäysaika</th>
                <th>Hoito-ohje</th>
            </tr>
            <c:forEach var="hoitoOhje" items="${hoitoOhjeet}" varStatus="moneskoHoitoOhje">
                <tr>
                    <td><c:out value="${laakarit[moneskoHoitoOhje.index].nimi}" /></td>
                    <td><c:out value="${paivamaarat[moneskoHoitoOhje.index]}" /></td>
                    <td><c:out value="${hoitoOhje.lisattavaTeksti}" /></td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${hoitoOhjeenTila != null}">
            <div class="alert alert-info col-xs-3"><c:out value="${hoitoOhjeenTila}" /></div>
        </c:if>
    </div>
</t:pohja>
