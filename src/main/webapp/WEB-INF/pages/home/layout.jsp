<%@ include file="/WEB-INF/tags/libs/tiles_libs.jsp"%>
<compress:html removeIntertagSpaces="false" removeComments="true">
<!DOCTYPE html>
<html dir="${langage.dir}" lang="${langage.lang}">
<head>
	<tiles:insertAttribute name="meta"/>
	<tiles:insertAttribute name="link"/>
</head>
<body id="page-top" class="body-screen b-white ${langage.clazz}">
	<noscript><c:import url="/WEB-INF/basics/loading_noscript.jsp"/></noscript>
	<c:set var="ignoreResponsive" value="1" scope="request"></c:set>
	<c:if test="${hasLoading}"><tiles:insertAttribute name="load"/></c:if>
	<div class="wrapper">
		<tiles:insertAttribute name="menu"/>
		<tiles:insertAttribute name="aside" />
		<div class="content-wrapper content-home flexed flex-colone flex-jusitify">
			<div class="page-screen">
				<tiles:insertAttribute name="banner"/>
				<tiles:insertAttribute name="body"/>
			</div>
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
	<tiles:insertAttribute name="chatbot"/>
	<tiles:insertAttribute name="chater"/>
	<tiles:insertAttribute name="privacy"/>
	<tiles:insertAttribute name="dialog"/>
	<tiles:insertAttribute name="script"/>
</body>
</html>
</compress:html>