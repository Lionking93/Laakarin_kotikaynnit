<%-- 
    Document   : oirekuvaus
    Created on : 27.11.2014, 22:03:31
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<t:lisaaTieto minkaTiedot="Ajanvarauksen tiedot" ekaTieto="Lääkärin nimi" ekanTiedonArvo="${laakarinNimi}" 
              tokaTieto="Varattavan ajan päivämäärä" tokanTiedonArvo="${ajanPvm}" kolmasTieto="Kellonaika"
              kolmannenTiedonArvo="${laakarinAika}" tekstikentta="oirekuvaus" tekstikentanSisalto="Kuvaile allaolevaan kenttään oireitasi.">    
    <div>
        <input class="btn btn-default" name="varaaAika" value="Varaa aika" type="submit" />
        <input class="btn btn-default" name="palaaAjanvaraukseen" value="Palaa ajanvaraukseen" type="submit" />
    </div>
</t:lisaaTieto>

