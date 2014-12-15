<%-- 
    Document   : ajanperuminen
    Created on : 27.11.2014, 11:43:08
    Author     : leo
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="peruttavanAjanId"%>

<%-- any content can be specified here e.g.: --%>
<form>
    <button class="btn btn-primary" type="submit">Peru aika</button>
    <input class="hidden" name="peruaika" value="${peruttavanAjanId}" />
</form>