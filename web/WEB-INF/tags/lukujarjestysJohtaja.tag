<%-- 
    Document   : lukujarjestys
    Created on : 18.11.2014, 12:43:21
    Author     : leo
--%>

<%@tag description="pohja lukujärjestykselle" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="infoteksti"%>

<%-- any content can be specified here e.g.: --%>
<form>
    <table class="table">
        <tr>
            <td colspan="3" rowspan="2">
                <h3>${infoteksti}</h3>
                <t:onnistui></t:onnistui>
                <t:virhe></t:virhe>
                </td>
                <td colspan="2">
                    <h4>Valitse edellinen tai seuraava viikko</h4>
                </td>
                <td>
                    <input class="btn btn-primary" name="lisaaTyovuorot" type="submit" value="Hyväksy valinnat" />
                </td>
            </tr>
            <tr>
                <td>
                    <button type="submit" class="btn btn-default" name="vasenNuoli" value="-1">
                        <span class=" glyphicon glyphicon-arrow-left" />
                    </button>
                </td>
                <td>
                    <button type="submit" class="btn btn-default" name="oikeaNuoli" value="1">
                        <span class="glyphicon glyphicon-arrow-right" />
                    </button>
                </td>
                <td>
                    <input class="btn btn-primary" name="palaaLaakarinValintaan" type="submit" value="Palaa työntekijän valintaan" />
                </td>
            </tr>
        </table>
        <table class="table table-bordered">
            <tr>
                <th></th>
                <c:forEach var="paiva" items="${paivat}" varStatus="moneskoPaiva">
                <th class="paiva"><c:out value="${paiva.paiva}" /> <c:out value="${paivamaarat[moneskoPaiva.count]}" /></th>
                </c:forEach>
        </tr>
        <c:forEach var="aika" items="${ajat}" varStatus="moneskoRivi">
            <tr>
                <th><c:out value="${aika.aikaslotti}" /></th>
                    <c:forEach begin="1" end="5" varStatus="moneskoSarake">
                        <c:set var="onkoToita" value="false" />
                    <td>
                        <c:forEach var="mvuoro" items="${maaratytVuorot}">
                            <c:if test="${mvuoro.tyopaivamaara == paivamaaratDatena[moneskoSarake.count] && mvuoro.paivaId == moneskoSarake.count && mvuoro.aikaslottiId == moneskoRivi.count}">
                                <input type="checkbox" checked disabled />
                                <c:set var="onkoToita" value="true" />
                            </c:if>
                        </c:forEach>
                        <c:if test="${onkoToita == 'false'}">
                            <input type="checkbox" name="lisattyAika" value="${moneskoSarake.count}, ${moneskoRivi.count}, ${paivamaarat[moneskoSarake.count]}" />
                        </c:if>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</form>

