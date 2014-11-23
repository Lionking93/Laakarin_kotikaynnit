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
                <td>${aika.laakari}</td>
                    </c:forEach>
        </tr>
        <tr><!--<input class="btn btn-link" type="submit" name="ajanvaraus" value="${aika.id}" />!-->
            <td>09:00-09:45</td>
            <c:forEach var="aika" items="${ajat2}">
                <td>${aika.laakari}</td>
            </c:forEach>
        </tr>
        <tr>
            <td>10:00-10:45</td>
            <c:forEach var="aika" items="${ajat3}">
                <td>${aika.laakari}</td>
            </c:forEach>
        </tr>
        <tr>
            <td>11:00-11:45</td>
            <c:forEach var="aika" items="${ajat4}">
                <td>${aika.laakari}</td>
            </c:forEach>
        </tr>
        <tr>
            <td>12:00-12:45</td>
            <c:forEach var="aika" items="${ajat5}">
                <td>${aika.laakari}</td>
            </c:forEach>
        </tr>
        <tr>
            <td>13:00-13:45</td>
            <c:forEach var="aika" items="${ajat6}">
                <td>${aika.laakari}</td>
            </c:forEach>
        </tr>
        <tr>
            <td>14:00-14:45</td>
            <c:forEach var="aika" items="${ajat7}">
                <td>${aika.laakari}</td>
            </c:forEach>
        </tr>
        <tr>
            <td>15:00-15:45</td>
            <c:forEach var="aika" items="${ajat8}">
                <td>${aika.laakari}</td>
            </c:forEach>
        </tr>
    </table>
</form>