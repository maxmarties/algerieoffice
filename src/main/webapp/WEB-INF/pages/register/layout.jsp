<%@ include file="/WEB-INF/tags/libs/tiles_libs.jsp"%>
<compress:html removeIntertagSpaces="false" removeComments="true">
<!DOCTYPE html>
<html dir="${langage.dir}" lang="${langage.lang}">
<head>
	<tiles:insertAttribute name="meta"/>
	<tiles:insertAttribute name="link"/>
</head>
<body class="${langage.clazz} b-register">
	<noscript><c:import url="/WEB-INF/basics/loading_noscript.jsp"/></noscript>
	<div class="wrapper">
		<tiles:insertAttribute name="menu"/>
		<div class="content-register flexed flex-colone flex-jusitify">
			<div style="padding-bottom:40px;"><tiles:insertAttribute name="body"/></div>
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
	<tiles:insertAttribute name="dialog"/>
	<tiles:insertAttribute name="script"/>
</body>
</html>
</compress:html>