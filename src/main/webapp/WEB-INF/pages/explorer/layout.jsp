<%@ include file="/WEB-INF/tags/libs/tiles_libs.jsp"%>
<compress:html removeIntertagSpaces="false" removeComments="true">
<!DOCTYPE html>
<html dir="${explorerCompany.profile.language == 'ar' ? 'rtl' : 'ltr'}" lang="${explorerCompany.profile.language}">
<head>
	<tiles:insertAttribute name="meta"/>
	<tiles:insertAttribute name="link"/>
</head>
<body id="page-top" class="body-explorer ${explorerCompany.profile.language == 'ar' ? 'ar' : 'fr'} ${explorerCompany.menu.hasMenuFixed() ? 'page-collapse' : ''}">
	<noscript><c:import url="/WEB-INF/basics/loading_noscript.jsp"/></noscript>
	<c:if test="${hasLoading}"><tiles:insertAttribute name="load"/></c:if>
	<div class="wrapper wrapper-explorer">
		<tiles:insertAttribute name="menu"/>
		<tiles:insertAttribute name="aside" />
		<div class="content-wrapper content-explorer flexed flex-colone flex-jusitify">
			<div class="page-explorer">
				<c:choose>
					<c:when test="${explorerCurrent.hasPreview}"><c:import url="/WEB-INF/explorer/banners/banner_preview.jsp" /></c:when>
					<c:otherwise><c:import url="/WEB-INF/explorer/banners/banner_follow.jsp" /></c:otherwise>
				</c:choose>
				<tiles:insertAttribute name="mainheader"/>
				<tiles:insertAttribute name="mainmenu"/>
				<tiles:insertAttribute name="body"/>
			</div>
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
	<c:if test="${!explorerCurrent.hasPreview}"><tiles:insertAttribute name="chatbot"/></c:if>
	<tiles:insertAttribute name="chater"/>
	<tiles:insertAttribute name="dialog"/>
	<tiles:insertAttribute name="script"/>
</body>
</html>
</compress:html>