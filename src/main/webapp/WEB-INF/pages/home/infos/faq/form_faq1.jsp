<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:forEach var="i" begin="1" end="15" step="1">
	<h2 class="h-doc h-header3 i-primary m-t-30"><spring:message code="txt.infos.faq1.${i}.1"/></h2>
	<p class="m-t-20 m-l-20"><spring:message code="txt.infos.faq1.${i}.2"/></p>
</c:forEach>