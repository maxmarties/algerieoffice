<%@ include file="/WEB-INF/tags/libs/tiles_libs.jsp"%>
<compress:html removeIntertagSpaces="false" removeComments="true">
<!DOCTYPE html>
<html dir="${langage.dir}" lang="${langage.lang}">
<head>
	<tiles:insertAttribute name="meta"/>
	<tiles:insertAttribute name="link"/>
</head>
<body class="error-screen ${langage.clazz}">
	<noscript><c:import url="/WEB-INF/basics/loading_noscript.jsp"/></noscript>
	<div class="wrapper background-container"
		style="background-image: radial-gradient(circle at center, rgba(24,31,42,.9) 50%, rgb(24,31,42) 100%), url('<c:url value="/static/picts/images/notfound-min.jpg" />');">
		<tiles:insertAttribute name="menu"/>
		<div class="content-register flexed flex-colone flex-jusitify">
			<tiles:insertAttribute name="body"/>
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
	<tiles:insertAttribute name="script"/>
</body>
</html>
</compress:html>