<%-- 
    Document   : luoPotilastieto
    Created on : 30.11.2014, 20:17:21
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<t:lisaaTieto minkaTiedot="Potilaan tiedot" ekaTieto="Nimi" ekanTiedonArvo="${asiakkaanNimi}" tokaTieto="Henkilötunnus" tokanTiedonArvo="${asiakkaanHetu}"
              kolmasTieto="Osoite" kolmannenTiedonArvo="${asiakkaanOsoite}" tekstikentanNimi="potilastieto"
              tekstikentanSisalto="Kirjoita allaolevaan tekstikenttään uusi potilasraportti tai hoito-ohje, 
            tai muokkaa jo olemassaolevia tietoja.">
    <div>
        <input class="btn btn-primary" name="potilasraportti" value="Lisää potilasraportti" type="submit" />
    </div>
</t:lisaaTieto>
