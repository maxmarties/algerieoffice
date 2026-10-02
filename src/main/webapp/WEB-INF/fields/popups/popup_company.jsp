<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<li class="dropdown company-menu">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.popup.company"/>">
		<img id="currentCompanyLogo" src="<c:url value="${currentCompany.iconurl}"/>" alt="<c:out value="${currentCompany.tradename}"/>">
	</a>
	<ul class="dropdown-menu dropdown-menu-right dropdown-menu-profile" role="menu">
		<li class="popup-widget">
			<img src="<c:url value="${currentCompany.iconurl}"/>" class="pull-left" alt="<c:out value="${currentCompany.tradename}"/>">
			<div class="brand-header">
				<c:choose>
					<c:when test="${currentCompany.published}">
						<a href="<c:url value="${currentCompany.url}"/>" class="lien lien-company font-big sh-black text-truncate" 	
							title="<spring:message code="tooltip.followed"/>" target="_blank"><c:out value="${currentCompany.tradename}" /><i class="cmsms-icon-link-ext i-11 m-l-10"></i>
						</a>
					</c:when>
					<c:otherwise><span class="font-big i-primary"><c:out value="${currentCompany.tradename}" /></span></c:otherwise>
				</c:choose>
				<span class="help-text"><spring:message code="lbl.sub.account6.3"/>: <span class="font-bold"><c:out value="${currentCompany.companyId}" /></span></span>
				<hr>
				<p class="font-small text-truncate">
					<spring:message code="tool.view.signin"/> <span class="h-header font-bold"><joda:format value="${currentCompany.createdDate}" pattern="MMMM yyyy"></joda:format></span>
				</p>
				
			</div>
			<span class="clearfix"></span>
			<hr class="my-1">
			<ul class="navbar-nav nav-flex-icons font-small">
				<li><a href="<c:url value="/user/dashboard"/>" class="lien lien-segond lien-hover"><spring:message code="sidebar.admin.dashboard2"/></a></li>
				<li class="divider"></li>
				<li><a href="<c:url value="/logout"/>" class="lien lien-red lien-hover"><spring:message code="btn.logout"/></a></li>
			</ul>
		</li>
		<c:set var="providers" value="building-filled,user-2,sliders,flash,chart-bar-2,bookmark,wrench,rss,paper-plane-3" scope="page"></c:set>
		<c:set var="liens" value="company/profile/identity,company/team/users,company/manage/preferences,company/dashboard/journal,company/dashboard/analytic,company/tools/subscribes,company/tools/setting,solutions/publicite,explorer/preview" scope="page"></c:set>
		<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
			<li>
				<a href="<c:url value="/${pageScope.lien}"/>" class="popup-item transition-35" target="${state.count >= 8 ? '_blank' : '_self'}">
					<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} i-primary i-22"></i><spring:message code="dashboard.company.popup${state.count}"/>
					<c:choose>
						<c:when test="${state.count == 1 || state.count == 2}"><small class="min i-help m-l-5">(<spring:message code="tool.view.admin"/>)</small></c:when>
						<c:when test="${state.count == 3 || state.count == 6 || state.count == 7}"><small class="min i-help m-l-5">(<spring:message code="chose.role.manager"/>)</small></c:when>
						<c:when test="${state.count == 8}"><i class="cmsms-icon-link-ext i-11 m-l-5"></i></c:when>
					</c:choose>
				</a>
			</li>
			<c:if test="${state.count == 3 || state.count == 5 || state.count == 8}"><li class="dropdown-divider"></li></c:if>
		</c:forEach>
	</ul>
</li>