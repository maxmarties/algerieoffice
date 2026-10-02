<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<h2 class="h-header h-header4 i-primary font-normal">
	<spring:message code="wizard.help.begginer2"/> <span class="i-help">(<c:out value="${begginer.countReleased()}/${begginer.countTasks()}"/>)</span>
</h2>
<p class="font-small m-t-10"><spring:message code="txt.company.help1.3"/></p>
<hr class="my-4">
<c:choose>
	<c:when test="${begginer.countReleased() == 0}">
		<div class="alert alert-info m-t-10"><i class="cmsms-icon-award i-alert"></i><p class="p-alert"><spring:message code="txt.company.help1.4"/></p></div>
	</c:when>
	<c:otherwise>
		<ul class="list-none list-block list-begginer">
			<c:set var="liens" value="overview/header,overview/presentation,profile/briefcase,profile/linked,profile/contact,profile/location,profile/linked,profile/linked,overview/catalog,overview/slider,overview/about,overview/think,posts/new,posts/categories,portfolio/actus,overview/timeline,team/agents,portfolio/partners,portfolio/works,manage/sticky,profile/identity" scope="page"></c:set>
			<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
				<c:if test="${!begginer.tasks[state.count - 1]}">
					<li>
						<span class="checkbox-span pull-left"></span>
						<div class="begginer-brand">
							<span class="font-bold i-primary"><spring:message code="txt.help.begginer${state.count}"/></span>
							<span class="ind-time m-l-10"><i class="cmsms-icon-stopwatch-1 m-r-5"></i><spring:message code="txt.help.begginer${state.count}.1"/></span>
							<p class="font-small m-t-5"><spring:message code="txt.help.begginer${state.count}.2"/></p>
							<p class="font-small m-t-10">
								<i class="cmsms-icon-${langage.lang == 'ar' ? 'left' : 'right'}-hand-1 m-r-10"></i>
								<a href="<c:url value="/company/${pageScope.lien}"/>" class="lien lien-primary lien-underline"><spring:message code="txt.help.begginer${state.count}.3"/></a>
							</p>
						</div>
						<span class="clearfix"></span>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>