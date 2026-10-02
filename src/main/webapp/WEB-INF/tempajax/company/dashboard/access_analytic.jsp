<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${listAccess.isEmpty()}"><p class="font-small"><spring:message code="tool.empty.analytic" /></p></c:when>
	<c:otherwise>
		<p class="font-small"><spring:message code="txt.company.analytic1.2" /></p>
		<hr class="my-2">
		<ul class="list-none list-block">
			<c:forEach var="line" items="${listAccess}">
				<li class="timeline-analytic m-b-10">
					<div class="access-item">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.tradename}" />">
						<div class="access-brand">
							<a href="<c:url value="${line.companyURL}"/>" class="lien lien-company font-bold sh-black" target="_blank"><c:out value="${line.tradename}" /></a>
							<p class="font-small i-help">
								<i class="cmsms-icon-location-1 i-red m-r-5"></i><c:out value="${line.address}" />, <c:out value="${line.postal}" /><br>
								<span class="text-uppercase"><spring:message code="chose.wilaya${line.wilaya}"/></span>
							</p>
							<p class="font-bold i-segond m-t-5" style="line-height:14px;"><spring:message code="chose.activity.${line.activity}"/></p>
							<div class="analytic-footer m-t-5">
								<span class="help-text"><spring:message code="tool.access" />: <strong><c:out value="${line.accessDate}" /></strong></span>
							</div>
						</div>
						<span class="clearfix"></span>
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>
</compress:html>