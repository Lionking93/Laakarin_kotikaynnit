<%-- 
    Document   : viikkoaikataulu
    Created on : 20.11.2014, 9:54:33
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<t:pohja kenenTili="Asiakas" aktiivinenTokaTab="active" ekaTab="Omat varaukset" tokaTab="Viikkoaikataulu" kolmasTab="Hoito-ohjeet">
    <div class="tab-pane active">
        <t:lukujarjestys infoteksti="Varatut ajat on merkitty punaisella ja vapaat vihreällä. Valitse itsellesi sopiva lääkäriaika 
                         painamalla vihreätä viikkoaikatalun saraketta.">

        </t:lukujarjestys>
    </div>
</t:pohja>
