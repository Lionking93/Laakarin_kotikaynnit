<%-- 
    Document   : omatVaraukset
    Created on : 20.11.2014, 9:30:24
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:pohja kenenTili="Asiakas" aktiivinenEkaTab="active" ekaTab="Omat varaukset" tokaTab="Valitse lääkäri" kolmasTab="Hoito-ohjeet">
    <div class="tab-pane active">
        <table class="table table-striped">
            <tr>
                <th>Vastaava lääkäri</th>
                <th>Varauksen ajankohta</th>
                <th colspan="2">Oirekuvaus</th>
            </tr>
            <c:forEach var="varaus" items="${varaukset}" varStatus="moneskoVaraus">
                <tr>
                    <td><c:out value="${varaus.laakari.nimi}" /></td>
                    <td><c:out value="${varaus.paiva.paiva}, ${paivamaarat[moneskoVaraus.index]}, ${varaus.aikaslotti.aikaslotti}" /></td>
                    <td><c:out value="${oirekuvaukset[moneskoVaraus.index].lisattavaTeksti}" /></td>
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
