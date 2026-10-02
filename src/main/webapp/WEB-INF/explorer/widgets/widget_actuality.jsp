<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="explorer-actu">
	<c:set var="actuality" value="${explorerPage.actualities.get(0)}" scope="page"></c:set>
	<div class="widget-header background-container" 
		style="background-image: radial-gradient(circle at center, rgba(32,42,56,.2) 10%, #242A38 100%), url('${pageScope.actuality.photoURL}');">
		<a href="<c:url value="/marketplace/actualites/${pageScope.actuality.id}" />" class="inner-link"></a>
		<h3 class="h-header h-header4 text-white sh-black"><c:out value="${pageScope.actuality.title}" /></h3>
		<p class="font-bold font-small text-segond m-t-10"><joda:format value="${pageScope.actuality.actuDate}" pattern="dd MMM yyyy"></joda:format></p>
	</div>
	<ul class="list-none m-t-5">
		<c:forEach var="i" begin="1" end="2">
			<c:if test="${i < explorerPage.actualities.size()}">
				<c:set var="actuality" value="${explorerPage.actualities.get(i)}" scope="page"></c:set>
				<li>
					<span class="item-provider p-left"><c:out value="00${i + 1}"/></span><p><a href="<c:url value="/marketplace/actualites/${pageScope.actuality.id}" />" 
						class="lien lien-explorer-more h-header"><c:out value="${pageScope.actuality.title}" /></a></p>
				</li>
			</c:if>
		</c:forEach>
		<li>
			<i class="cmsms-icon-th-list-3 m-r-10"></i>
			<a href="<c:url value="${explorerCurrent.companyURL}/actualites"/>" 
				class="lien lien-explorer-more h-header"><spring:message code="explorer.desktop.element2.1"/></a>
		</li>
	</ul>
</div>