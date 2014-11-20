<%-- 
    Document   : lukujarjestys
    Created on : 18.11.2014, 12:43:21
    Author     : leo
--%>

<%@tag description="pohja lukujärjestykselle" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="infoteksti"%>

<%-- any content can be specified here e.g.: --%>
<table class="table table-bordered">
    <tr>
        <td colspan="6"><b>${infoteksti}</b></td>
    </tr>
    <tr>
        <th>#</th>
        <th>Maanantai</th>
        <th>Tiistai</th>
        <th>Keskiviikko</th>
        <th>Torstai</th>
        <th>Perjantai</th>
    </tr>
    <tr>
        <td>08:00-08:45</td>
        <td></td>
        <td class="vapaa">
        </td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>09:00-09:45</td>
        <td></td>
        <td></td>
        <td class="varattu"></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>10:00-10:45</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>11:00-11:45</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>12:00-12:45</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>13:00-13:45</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>14:00-14:45</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>15:00-15:45</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
</table>