<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/team/users"/>" class="lien lien-black">
			<i class="cmsms-icon-user-2 m-r-5"></i><spring:message code="sidebar.company.dashboard7"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard1.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.team.user.new"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.team2.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/team/users" scope="request"></c:set>
	<c:set var="backwordPage" value="11" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<c:choose>
		<c:when test="${hasMaxUser}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.user"/> : 
					<a href="<c:url value="/company/tools/subscribes"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.premium"/></a>.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
				<div class="wizard wizard-user">
					<div class="wizard-tabbed">
						<ul class="nav nav-tabs nav-tabs2" role="tablist">
							<c:set var="providers" value="users-2,user-add-1" scope="page"></c:set>
							<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
								<li>
									<a class="lien ${state.count == 1 ? 'active' : ''}" data-toggle="tab" role="tab" 
										title="<spring:message code="wizard.team.agent${state.count}"/>">
										<i class="cmsms-icon-${pageScope.provider} i-34 transition-35"></i>
										<span class="help-tab"><spring:message code="wizard.team.agent${state.count}"/></span>
									</a>
								</li>
							</c:forEach>
						</ul>
						<div class="wizard-content">
							<div class="wizard-body">
								<c:forEach var="i" begin="1" end="2" step="1">
									<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
										<c:import url="/WEB-INF/pages/company/team/user/form_user${i}.jsp"/>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</sec:authorize>
		</c:otherwise>
	</c:choose>
</div>