<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li class="dropdown user-menu">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="sidebar.admin.dashboard2"/>">
		<img id="currentUserPhoto" class="img-circle" src="<c:url value="${currentUser.getIconUrl()}"/>" alt="<spring:message code="tooltip.avatar" />">
	</a>
	<ul class="dropdown-menu dropdown-menu-right dropdown-menu-profile" role="menu">
		<li class="popup-widget">
			<img src="<c:url value="${currentUser.getIconUrl()}"/>" class="img-circle pull-left" alt="<spring:message code="tooltip.avatar" />">
			<div class="brand-header">
				<a href="<c:url value="/membres?id=${currentUser.getUserId()}"/>" class="lien lien-company font-big sh-black text-truncate" 
					title="<spring:message code="lbl.sub.account6.1"/>" target="_blank"><c:out value="${currentUser.user.getDisplayName()}" /><i class="cmsms-icon-link-ext i-11 m-l-10"></i>
				</a>
				<span class="help-text"><spring:message code="lbl.sub.account6.2"/>: <span class="font-bold"><c:out value="${currentUser.getUserId()}" /></span></span>
				<hr>
				<p class="font-small text-truncate"><c:out value="${currentUser.user.email}" /></p>
			</div>
			<span class="clearfix"></span>
		</li>
		<c:set var="providers" value="user-1,phone-1,shield,cog-5,roadblock,history,globe-4,star-1,bell-1" scope="page"></c:set>
		<c:set var="liens" value="account/profile,account/coordinates,account/identities,settings/general,settings/blacklist,dashboard/history,globe/companies,favorite/posts,alerts/posts" scope="page"></c:set>
		<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
			<li>
				<a href="<c:url value="/user/${pageScope.lien}"/>" class="popup-item transition-35">
					<i class="cmsms-icon-${pageScope.providers.split(',')[state.count - 1]} i-primary i-22"></i><spring:message code="dashboard.user.popup${state.count}"/>
				</a>
			</li>
			<c:if test="${state.count == 4 || state.count == 6}"><li class="dropdown-divider"></li></c:if>
		</c:forEach>
		<li class="dropdown-divider"></li>
		<li><a href="<c:url value="/logout"/>" class="popup-item transition-35"><i class="cmsms-icon-off i-primary i-22"></i><spring:message code="btn.signout"/></a></li>
	</ul>
</li>