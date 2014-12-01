<%-- 
    Document   : omatVaraukset
    Created on : 20.11.2014, 9:30:24
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:pohja kenenTili="Asiakas" aktiivinenEkaTab="active" ekaTab="Omat varaukset" tokaTab="Viikkoaikataulu" kolmasTab="Hoito-ohjeet">
    <div class="tab-pane active">
        <table class="table table-striped">
            <tr>
                <th>Vastaava lääkäri</th>
                <th colspan="2">Varauksen ajankohta</th>
            </tr>
            <c:forEach var="varaus" items="${varaukset}">
                <tr>
                    <td><c:out value="${varaus.laakari}" /></td>
                    <td><c:out value="${varaus.viikonpaiva}, ${varaus.aika}" /></td>
                    <td><t:ajanperuminen peruttavanAjanId="${varaus.id}"></t:ajanperuminen></td>
                    </tr>
            </c:forEach>
            <tr>
                <td colspan="2">
                    <c:if test="${varauksenTila != null}">
                        <div class="alert alert-info col-xs-3"><c:out value="${varauksenTila}" /></div>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
</t:pohja>
