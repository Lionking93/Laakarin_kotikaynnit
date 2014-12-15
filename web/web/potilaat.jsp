<%-- 
    Document   : potilaat
    Created on : 20.11.2014, 13:57:40
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pohja kenenTili="Lääkäri" aktiivinenKolmasTab="active" ekaTab="Työtehtävät" tokaTab="eiole" kolmasTab="Potilaat">
    <div id="potilaat" class="tab-pane">
        <table class="table table-striped">
            <tr>
                <th colspan="2">Nimi</th>
            </tr>
            <c:forEach var="asiakas" items="${asiakkaat}">
                <tr>
                    <td><c:out value="${asiakas.nimi}" /></td>
                    <td>
                        <form>
                            <input class="hidden" name="asiakasId" value="${asiakas.id}" />
                            <input class="btn btn-primary" type="submit" name="potilaanTiedot" value="Avaa potilaan tiedot" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</t:pohja>
