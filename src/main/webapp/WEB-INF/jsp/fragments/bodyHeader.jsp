<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header><a href="${pageContext.request.contextPath}/"><spring:message code="app.home"/></a>&nbsp;|&nbsp;<a href="<c:url value="/meals"/>"><spring:message code="app.title"/></a></header>