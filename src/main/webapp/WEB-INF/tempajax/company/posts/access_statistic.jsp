<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${listAccess.isEmpty()}"><p class="font-small"><spring:message code="tool.empty.statistic" /></p></c:when>
	<c:otherwise>
		<p class="font-small"><spring:message code="txt.company.posts4.2" /></p>
		<hr class="my-2">
		<ul class="list-none list-block">
			<c:forEach var="line" items="${listAccess}">
				<li class="timeline-statistic m-b-10">
					<div class="access-item">
						<img src="<c:url value="${line.photoURL}"/>" class="pull-left" alt="<c:out value="${line.title}" />">
						<div class="access-brand">
							<a href="<c:url value="${line.identifyURL}"/>" class="lien lien-company sh-black" target="_blank"><c:out value="${line.title}" /></a>
							<span class="help-text"><i class="cmsms-icon-folder-2 i-segond m-r-10"></i><c:out value="${line.category}"/></span>
							<div class="statistic-footer m-t-5">
								<ul class="list-none list-block">
									<li>
										<i class="cmsms-icon-target-4 i-blue m-r-10"></i><span class="font-small i-help"><spring:message code="tool.analytic.refering1" /></span>: 
										<span class="h-header i-primary"><c:out value="${line.view}"/></span>
									</li>
									<li>
										<i class="cmsms-icon-eye-1 i-green m-r-10"></i><span class="font-small i-help"><spring:message code="tabs.access" /></span>: 
										<span class="h-header i-primary"><c:out value="${line.clickCount}"/></span>
									</li>
									<li>
										<i class="cmsms-icon-award i-red m-r-10"></i><span class="font-small i-help"><spring:message code="chose.favorite4" /></span>: 
										<span class="h-header i-primary"><c:out value="${line.workCount}"/></span>
									</li>
								</ul>
							</div>
						</div>
						<span class="clearfix"></span>
						<div class="statistic-footer m-t-10">
							<span class="help-text"><spring:message code="tabs.buildate" /><span class="font-bold pull-right"><c:out value="${line.createdDate}"/></span></span>
							<span class="clearfix"></span>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>
</compress:html>