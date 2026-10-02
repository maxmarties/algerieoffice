<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${list.size() >= 5}">
	<div class="screen-container m-t-10">
		<div class="container">
			<div class="header-screen header-actu"><h2 class="h-header m-auto"><spring:message code="subheader.screen.marketplace5.2"/></h2></div>
			<p class="parag-blog header-actu text-center m-auto"><spring:message code="txt.search.company2"/></p>
			<ul class="nav navlogo-skills text-center m-t-30">
				<c:set var="maxItems" value="${list.size() == 10 ? 10 : 5}" scope="page"></c:set>
				<c:forEach var="i" begin="1" end="${pageScope.maxItems}" step="1">
					<c:set var="line" value="${list.get(i - 1)}" scope="page"></c:set>
					<li><a href="<c:url value="${pageScope.line.companyURL}"/>" class="m-auto transition-35" title="<c:out value="${pageScope.line.tradename}"/>">
							<img class="img-responsive" src="<c:url value="${pageScope.line.urlAvatar}"/>" alt="<c:out value="${pageScope.line.tradename}" />"></a></li>
				</c:forEach>
			</ul>
			<div class="text-center m-t-40 m-b-30">
				<a href="<c:url value="/recherche/entreprises" />" class="btn btn-segond btn-big" style="min-width:260px;">
					<span><spring:message code="txt.search.company2.1"/><i class="cmsms-icon-explorer-arrow m-l-10"></i></span></a>
			</div>
		</div>
	</div>
</c:if>
</compress:html>