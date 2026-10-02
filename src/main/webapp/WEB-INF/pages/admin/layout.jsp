<%@ include file="/WEB-INF/tags/libs/tiles_libs.jsp"%>
<compress:html removeIntertagSpaces="false" removeComments="true">
<!DOCTYPE html>
<html dir="${langage.dir}" lang="${langage.lang}">
<head>
	<tiles:insertAttribute name="meta"/>
	<tiles:insertAttribute name="link"/>
</head>
<body class="${langage.clazz} menu-collapse ${currentConfig.menuCollapse ? 'sidebar-collapse' : ''}">
	<noscript><c:import url="/WEB-INF/basics/loading_noscript.jsp"/></noscript>
	<div class="wrapper">
		<tiles:insertAttribute name="menu"/>
		<tiles:insertAttribute name="aside" />
		<div class="content-wrapper content-admin flexed flex-colone flex-jusitify ${!empty requestScope.applyFooter ? 'apply-footer' : ''}">
			<c:import url="/WEB-INF/basics/loading_dashboard.jsp"/>
			<div><tiles:insertAttribute name="body"/></div>
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
	<tiles:insertAttribute name="chater"/>
	<tiles:insertAttribute name="dialog"/>
	<tiles:insertAttribute name="script"/>
</body>
</html>
</compress:html>