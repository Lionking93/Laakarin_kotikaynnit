<%-- 
    Document   : dropdown
    Created on : 20.11.2014, 9:56:54
    Author     : leo
--%>

<%@tag description="dropdown-valikko" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>

    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownvalikko">
        <li role="presentation">
            <table>
                <tr>
                    <th>Hoitava lääkäri:</th>
                    <td> Pentti Virtanen</td>
                </tr>
                <tr>
                    <th>Varattu aika:</th>
                    <td> 08:00-08:45</td>
                </tr>
            </table>
        </li>
        <li role="presentation"><a role="menuitem" href="#" class="btn btn-default">Varaa aika</a></li>
    </ul>
