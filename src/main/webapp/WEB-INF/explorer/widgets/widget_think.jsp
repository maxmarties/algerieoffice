<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="row row-mini">
	<c:forEach var="photoURL" items="${explorerPage.inbox.think.photosURL}" varStatus="state">
		<c:set var="description" value="${explorerPage.inbox.think.descriptions.get(state.count - 1)}" scope="page"></c:set>
		<div class="col-sm-6 col-lg-3 col-mini m-t-10">
			<div class="explorer-column widget-think background-container h-100" 
				style="background-image: radial-gradient(circle at center, rgba(32,42,56,.54) 20%, #242A38 100%), url('<c:url value="${photoURL}"/>');">
				<h2 class="h-header h-header1 text-truncate text-yellow sh-black ${empty pageScope.description ? 'm-t-20' : ''}">
					<c:out value="${explorerPage.inbox.think.thinks.get(state.count - 1)}"/></h2>
				<h3 class="h-header h-header3 text-truncate text-white ${empty pageScope.description ? 'm-t-10 m-b-20' : 'm-t-20'}">
					<c:out value="${explorerPage.inbox.think.titles.get(state.count - 1)}"/></h3>
				<c:if test="${!empty pageScope.description}"><p class="font-small text-gray m-t-10"><c:out value="${pageScope.description}"/></p></c:if>
			</div>
		</div>
	</c:forEach>
</div>