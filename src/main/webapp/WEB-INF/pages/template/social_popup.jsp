<%@ include file="/WEB-INF/tags/libs/tiles_libs.jsp"%>
<!DOCTYPE html>
<html dir="${langage.dir}" lang="${langage.lang}">
<head>
	<tiles:insertAttribute name="meta"/>
	<title><spring:message code="btn.login"/> <c:out value="${provider}" /> | <spring:message code="app.brand"/></title>
</head>
<body>
	<script type="text/javascript">
		var serverContext = '<c:url value="/"/>', error = '<c:out value="${error}"/>', message = '<c:out value="${message}"/>', provider = '<c:out value="${provider}"/>';
		var closeWindow = '<c:out value="${closeWindow}"/>', source = serverContext + "oauth2/authorization/";
		if (closeWindow) {
			window.opener.postMessage({token: error ? 'error' : 'success', message: message, source: source}, serverContext);
	        window.close();
	    }
		else {
			window.location.href = source + provider;
		}
	</script>
</body>