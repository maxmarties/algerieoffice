<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-carte explorer-cartehome map-outer m-t-5">
	<c:choose>
		<c:when test="${explorerPage.linked.hasEmbded}"><c:out value="${explorerPage.linked.urlEmbded}" escapeXml="false" /></c:when>
		<c:otherwise><iframe width="100%" height="120" src="${explorerPage.linked.urlEmbded}"></iframe></c:otherwise>
	</c:choose>
</div>