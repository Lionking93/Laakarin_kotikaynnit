<%-- 
    Document   : pomonTokatab
    Created on : 11.12.2014, 11:18:09
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<t:pohja kenenTili="Johtaja" ekaTab="Työvuorojen lisäys" tokaTab="eiole" kolmasTab="eiole">
    <div class="tab-pane active">
        <t:lukujarjestysJohtaja infoteksti="Lisää allaolevaan taulukkoon lääkärin ${laakari.nimi} työvuorot">
        </t:lukujarjestysJohtaja>
    </div>
</t:pohja>
