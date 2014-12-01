<%-- 
    Document   : dropdown
    Created on : 20.11.2014, 9:56:54
    Author     : leo
--%>

<%@tag description="dropdown-valikko" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="laakarinNimi"%>
<%@attribute name="laakarinAika" %>
<%@attribute name="ajanId" %>

<%-- any content can be specified here e.g.: --%>
<form>
    <div class="dropdown dropdown-vapaa">
        <div class="dropdown-toggle" id="dropdownvalikko"
             data-toggle="dropdown">${laakarinNimi}
        </div>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownvalikko">
            <li role="presentation">
                <table>
                    <tr>
                        <th>Hoitava lääkäri: </th>
                        <td> <c:out value="${laakarinNimi}" /></td>
                    </tr>
                    <tr>
                        <th>Kotikäyntiaika: </th>
                        <td> <c:out value="${laakarinAika}" /></td>
                    </tr>
                </table>
            </li>
            <li role="presentation">
                <a role="menuitem">
                    <button type="submit">Varaa aika</button>
                    <input class="hidden" name="ajanvaraus" value="${ajanId}" />
                </a>
            </li>
        </ul>
    </div>
</form>