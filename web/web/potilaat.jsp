<%-- 
    Document   : potilaat
    Created on : 20.11.2014, 13:57:40
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pohja kenenTili="Lääkäri" aktiivinenKolmasTab="active" ekaTab="Työtehtävät" tokaTab="Viikkoaikataulu" kolmasTab="Potilaat">
    <div id="potilaat" class="tab-pane">
        <table class="table table-striped">
            <tr>
                <th>Nimi</th>
                <th colspan="2">Asiakastunnus</th>
            </tr>
            <tr>
                <td>Asko Seppälä</td>
                <td>0058</td>
                <td><a href="potilaan_tiedot.html" class="btn btn-default">Avaa potilaan tiedot</a></td>
            </tr>
        </table>
    </div>
</t:pohja>
