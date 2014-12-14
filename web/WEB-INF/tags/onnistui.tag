<%-- 
    Document   : onnistui
    Created on : 14.12.2014, 2:03:22
    Author     : leo
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<c:if test="${onnistunutLisays != null}"> 
    <div class="alert alert-warning">${onnistunutLisays}</div>
</c:if>