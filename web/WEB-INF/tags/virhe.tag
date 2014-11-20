

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<c:if test="${virheViesti != null}"> 
    <div class="alert alert-warning">Virhe! ${virheViesti}</div>
</c:if>