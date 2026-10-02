<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:forEach var="emailItem" items="${emailItems}" varStatus="state">
	<div class="flexed">
		<c:if test="${!empty emailItem.photoURL}">
			<div style="width:30%"><img class="img-responsive" src="<c:url value="${emailItem.photoURL}"/>" alt="<c:out value="${emailItem.title}" />"></div>
		</c:if>
		<div style="width:${empty emailItem.photoURL ? '100' : '70'}%">
			<div class="${empty emailItem.photoURL ? '' : 'm-l-20'}">
				<a href="<c:url value="${emailItem.identifyURL}" />" class="h-header font-big lien-pane" target="_blank"><c:out value="${emailItem.title}" /></a>
				<p class="font-small m-t-10 m-b-10"><c:out value="${emailItem.description}" /></p>
				<a href="<c:url value="${emailItem.identifyURL}" />" class="font-mini font-bold lien-text" target="_blank" 
					style="display:inline-block;padding:8px 24px;border-radius:3px;"><spring:message code="btn.explorer.detail" /></a>
			</div>
		</div>
	</div>
	<c:if test="${state.count != emailItems.size()}"><hr class="my-2"></c:if>
</c:forEach>