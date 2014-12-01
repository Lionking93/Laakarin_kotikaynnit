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

<table class="table table-bordered">
    <tr>
        <td colspan="6"><b>${infoteksti}</b></td>
    </tr>
    <tr>
        <th></th>
            <c:forEach var="paiva" items="${paivat}">
            <th class="paiva">${paiva}</th>
            </c:forEach>
    </tr>
    <tr>
        <td>08:00-08:45</td>
        <c:forEach var="aika" items="${ajat1}">
            <td class="${aika.onkoVarattu}">                
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td>09:00-09:45</td>
        <c:forEach var="aika" items="${ajat2}">
            <td class="${aika.onkoVarattu}">
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td>10:00-10:45</td>
        <c:forEach var="aika" items="${ajat3}">
            <td class="${aika.onkoVarattu}">
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td>11:00-11:45</td>
        <c:forEach var="aika" items="${ajat4}">
            <td class="${aika.onkoVarattu}">
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td>12:00-12:45</td>
        <c:forEach var="aika" items="${ajat5}">
            <td class="${aika.onkoVarattu}">
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td>13:00-13:45</td>
        <c:forEach var="aika" items="${ajat6}">
            <td class="${aika.onkoVarattu}">
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td>14:00-14:45</td>
        <c:forEach var="aika" items="${ajat7}">
            <td class="${aika.onkoVarattu}">
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
    <tr>
        <td>15:00-15:45</td>
        <c:forEach var="aika" items="${ajat8}">
            <td class="${aika.onkoVarattu}">
                <c:choose>
                    <c:when test="${!aika.onkoVarattu}">
                <form>
                    <input class ="hidden" name="laakarinNimi" value="${aika.laakari}" />
                    <input class="hidden" name="laakarinAika" value="${aika.aika}" />
                    <input class="hidden" name="ajanId" value="${aika.id}" />
                    <input class="hidden" name="ajanPvm" value="${aika.viikonpaiva}" />
                    <input class="linkkiteksti dropdown-vapaa" name="ajanvaraus" value="${aika.laakari}" type="submit" />
                </form>
                    </c:when>
                    <c:otherwise>
                        <span class="linkkiteksti"><c:out value="${aika.laakari}" /></span>
                    </c:otherwise>
                </c:choose>
            </td>
        </c:forEach>
    </tr>
</table>