<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-sticky hidden-md-down">
	<div class="screen-column screen-alert m-t-20">
		<div class="sidebar-header bn-segond"><i class="cmsms-icon-bell-1 m-r-10"></i><spring:message code="lbl.sub.search22"/></div>
		<div class="widget-body">
			<h3 class="h-header h-header5 i-primary"><spring:message code="txt.search.alert${requestScope.treeviewAlert}"/></h3>
			<p class="font-small i-help m-t-10"><spring:message code="txt.search.alert"/></p>
			<hr class="my-2">
			<c:choose>
				<c:when test="${requestScope.treeviewAlert == 1}"><c:set var="lien" value="posts" scope="page"></c:set></c:when>
				<c:when test="${requestScope.treeviewAlert == 2}"><c:set var="lien" value="ads" scope="page"></c:set></c:when>
				<c:when test="${requestScope.treeviewAlert == 3}"><c:set var="lien" value="events" scope="page"></c:set></c:when>
				<c:otherwise><c:set var="lien" value="jobs" scope="page"></c:set></c:otherwise>
			</c:choose>
			<a href="<c:url value="/user/alerts/${pageScope.lien}/new"/>" class="btn btn-segond btn-block"><span><spring:message code="header.alert.new"/></span></a>
		</div>
	</div>
	<c:import url="/WEB-INF/fields/screen/screen_topics.jsp"/>
</div>