<%-- 
    Document   : tyotehtavat
    Created on : 20.11.2014, 13:43:43
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<t:pohja kenenTili="Lääkäri" aktiivinenEkaTab="active" ekaTab="Työtehtävät" tokaTab="eiole" kolmasTab="Potilaat">
    <div class="tab-pane active">
        <table class="table table-striped">
            <tr>
                <th>Potilas</th>
                <th>Varattu aika</th>
                <th colspan="2">Oireiden kuvaus</th>
            </tr>
            <c:forEach var="tyo" items="${tyot}" varStatus="monesko">     
                <tr>
                    <td><c:out value="${tyo.asiakas.nimi}" /></td>
                    <td><c:out value="${tyo.paiva.paiva}, ${paivamaarat[monesko.index]}, ${tyo.aikaslotti.aikaslotti}" /></td>
                    <td><c:out value="${oireet[monesko.index].lisattavaTeksti}" /></td>
                    <td>
                        <c:choose>
                            <c:when test="${tyo.onkoSuoritettu == 'false'}">
                                <form>
                                    <button class="btn btn-primary" type="submit" name="kuittaus" value="${tyo.id}">Kuittaa suoritetuksi</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-info">Suoritettu</div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${tyotehtavienTila != null}">
            <div class="col-xs-3 alert alert-info"><c:out value="${tyotehtavienTila}" /></div>
        </c:if>
    </div>
</t:pohja>
