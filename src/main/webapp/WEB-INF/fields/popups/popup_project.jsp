<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li class="dropdown app-menu hidden-sm-down">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.popup.project"/>">
		<i class="cmsms-icon-ellipsis-vert"></i>
	</a>
	<ul class="dropdown-menu dropdown-menu-right" role="menu">
		<li class="dropdown-header"><span class="font-bold i-primary"><spring:message code="tooltip.popup.project"/></span></li>
		<c:set var="liens" value="infos/cgu,user/settings/security,user/settings/cookies,user/repports/assist,user/account/help,user/repports/testimonial,user/dashboard?style=${currentConfig.defaultStyle == 3 ? '2' : '3'}" scope="page"></c:set>
		<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
			<li>
				<a href="<c:url value="/${pageScope.lien}"/>" class="popup-item transition-35">
					<c:choose>
						<c:when test="${state.count == 7}">
							<i class="cmsms-icon-${currentConfig.defaultStyle == 3 ? 'sun-1' : 'moon'} i-primary i-22"></i>
							<spring:message code="dashboard.app.popup${currentConfig.defaultStyle == 3 ? '8' : '7'}"/>
						</c:when>
						<c:otherwise><spring:message code="dashboard.app.popup${state.count}"/></c:otherwise>
					</c:choose>
				</a>
			</li>
			<c:if test="${state.count == 5 || state.count == 6}"><li class="dropdown-divider"></li></c:if>
		</c:forEach>
	</ul>
</li>