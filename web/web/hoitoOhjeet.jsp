<%-- 
    Document   : hoitoOhjeet
    Created on : 20.11.2014, 9:39:35
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<t:pohja kenenTili="Asiakas" aktiivinenKolmasTab="active" ekaTab="Omat varaukset" tokaTab="Viikkoaikataulu" kolmasTab="Hoito-ohjeet">
    <div class="tab-pane active">
        <table class="table table-striped">
            <tr>
                <th>Vastaava lääkäri</th>
                <th>Lisäysaika</th>
                <th>Hoito-ohje</th>
            </tr>
            <tr>
                <td>Pentti Virtanen</td>
                <td>5.11.2014, 15:03</td>
                <td>Riittävästi lepoa ja kevyitä ruokia</td>
            </tr>
            <tr>
                <td>Pentti Virtanen</td>
                <td>8.11.2014, 16:15</td>
                <td>2*400 mg Buranaa</td>
            </tr>
        </table>
    </div>
</t:pohja>
