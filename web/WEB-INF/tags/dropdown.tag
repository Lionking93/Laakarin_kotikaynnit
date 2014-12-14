<%-- 
    Document   : dropdown
    Created on : 20.11.2014, 9:56:54
    Author     : leo
--%>

<%@tag description="dropdown-valikko" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- The list of normal or fragment attributes can be specified here: --%>

<%-- any content can be specified here e.g.: --%>
<div class="dropdown lisattavalaakari">
    <div class="dropdown-toggle" id="dropdownvalikko"
         data-toggle="dropdown">Lisää lääkäri
    </div>
    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownvalikko">
        <c:forEach var="laakari" items="${laakarit}">
            <li class="lisattavalaakari" role="presentation">
                <form>
                    <input type="hidden" name="lisattavaLaakari" value="${laakari.id}" />
                    <input type="submit" name="laakarinNimi" value="${laakari.nimi}">
                </form>
            </li>
        </c:forEach>
    </ul>
</div>