<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form modelAttribute="${modelAttr}" action="">

<spring:bind path="*">
<c:if test="${not empty status.errorCodes}">
<c:forEach items="${status.errorCodes}" var="code" varStatus="sts">
<div id="<c:out value='${code}'/>"><c:out value="${status.errorMessages[sts.index]}"/></div>
</c:forEach>
</c:if>
</spring:bind>

</form:form>