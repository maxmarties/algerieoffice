<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:if test="${!list.lines.isEmpty()}">
	<div class="explorer-column m-t-10">
		<div class="explorer-title">
			<h1 class="h-doc h-explorer h-explorer1">
				<spring:message code="txt.help.explorer1.5"/> <spring:message code="chose.wilaya${list.wilaya}"/> <c:out value="(${list.countResult})" />
			</h1>
		</div>
		<div class="explorer-body">
			<div class="row">
				<c:forEach var="line" items="${list.lines}">
					<div class="col-sm-6 col-md-3">
						<i class="cmsms-icon-explorer-angle m-r-10"></i>
						<a href="<c:url value="${line.urlCompany}" />" class="lien lien-explorer-help"><c:out value="${line.tradename}" /></a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</c:if>
</compress:html>