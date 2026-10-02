<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:forEach var="i" begin="1" end="10" step="1">
	<h2 class="h-doc h-header3 i-primary m-t-30"><spring:message code="txt.infos.faq5.${i}.1"/></h2>
	<p class="m-t-20 m-l-20"><spring:message code="txt.infos.faq5.${i}.2"/></p>
	<c:if test="${i == 3 || i == 7}"><ul class="m-t-20"><c:forEach var="j" begin="1" end="${i == 3 ? 3 : 2}" step="1"><li class="m-b-10"><spring:message code="txt.infos.faq5.${i}.2.${j}"/></li></c:forEach></ul></c:if>
</c:forEach>