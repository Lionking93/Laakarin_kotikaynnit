<%-- 
    Document   : omatVaraukset
    Created on : 20.11.2014, 9:30:24
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:pohja kenenTili="Asiakas" aktiivinenEkaTab="active" ekaTab="Omat varaukset" tokaTab="Viikkoaikataulu" kolmasTab="Hoito-ohjeet">
    <div class="tab-pane active">
            <table class="table table-striped">
                <tr>
                    <th>Vastaava lääkäri</th>
                    <th>Varauksen ajankohta</th>
                </tr>
                <tr>
                    <td>Pentti Virtanen</td>
                    <td>Ke 19.11.2014, 09:00-09:45</td>
                </tr>
            </table>
        </div>
</t:pohja>
