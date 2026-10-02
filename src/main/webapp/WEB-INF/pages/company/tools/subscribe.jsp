<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/tools/setting"/>" class="lien lien-black">
			<i class="cmsms-icon-wrench m-r-5"></i><spring:message code="sidebar.company.dashboard10"/></a></li>
		<li><a href="<c:url value="/company/tools/subscribes"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard10.4"/></a></li>
		<li class="active"><spring:message code="header.tools.subscribe" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.tools.subscribe"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.tools4.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/tools/subscribes" scope="request"></c:set>
	<c:set var="backwordPage" value="22" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
		<c:choose>
			<c:when test="${!currentCompany.enabled}">
				<div class="alert alert-warning">
					<i class="cmsms-icon-attention i-alert"></i>
					<p class="p-alert">
						<spring:message code="message.premium.subscribe"/> : 
						<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.identity"/></a>.
					</p>
				</div>
			</c:when>
			<c:when test="${empty subscribe}">
				<div class="alert alert-info m-t-20">
					<i class="cmsms-icon-info-circled-3 i-alert"></i>
					<p class="p-alert"><spring:message code="txt.company.tools4.2"/></p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="wizard wizard-promote">
					<form:form name="subscribeForm" action="/" method="POST" modelAttribute="subscribe" enctype="multipart/form-data" novalidate="novalidate">
						<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
						<div class="wizard-pane">
							<ul class="nav nav-tabs nav-tabs3" role="tablist">
								<c:set var="providers" value="dollar,money,credit-card" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<li>
										<a class="lien ${state.count == 1 ? 'active' : ''}" data-toggle="tab" role="tab" 
											title="<spring:message code="wizard.tools.subscribe${state.count}"/>">
											<i class="cmsms-icon-${pageScope.provider} i-34 transition-35"></i>
											<span class="help-tab"><spring:message code="wizard.tools.subscribe${state.count}"/></span>
										</a>
									</li>
								</c:forEach>
							</ul>
							<div class="wizard-content">
								<div class="wizard-body">
									<c:forEach var="i" begin="1" end="3" step="1">
										<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
											<c:import url="/WEB-INF/pages/company/tools/subscribe/form_subscribe${i}.jsp"/>
										</div>
									</c:forEach>
								</div>
								<div class="wizard-footer">
									<div class="pull-left">
										<button type="button" class="btn btn-segond btn-previous btn-add btn-left" style="display:none;">
											<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'left' : 'right'}-4"></i><spring:message code="btn.back"/></span>
										</button>
									</div>
									<div class="pull-right">
										<div id="submitForm" class="form-submit pull-right">
											<button type="button" class="btn btn-primary btn-next btn-add btn-right">
							            		<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'right' : 'left'}-4"></i><spring:message code="btn.next"/></span>
							            	</button>
							            	<button type="submit" class="btn btn-primary btn-finish btn-submit btn-add btn-left" style="display:none;">
							            		<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.send"/></span>
							            	</button>
										</div>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</form:form>
				</div>
			</c:otherwise>
		</c:choose>
	</sec:authorize>
</div>