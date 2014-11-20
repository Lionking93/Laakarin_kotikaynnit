<%-- 
    Document   : tyotehtavat
    Created on : 20.11.2014, 13:43:43
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<t:pohja kenenTili="Lääkäri" aktiivinenEkaTab="active" ekaTab="Työtehtävät" tokaTab="Viikkoaikataulu" kolmasTab="Potilaat">
    <div class="tab-pane active">
        <t:lukujarjestys infoteksti="Alla on merkitty suoritetut työtehtävät vihreällä ja kuittaamattomat punaisella. 
                                Suoritettuasi työtehtävän, kuittaa suoritus painamalla kyseistä työtehtävää vastaavaa 
                                viikkoaikataulun saraketta.">
            
        </t:lukujarjestys>
    </div>
</t:pohja>
