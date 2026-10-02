<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/newsletter/template"/>" class="lien lien-black">
			<i class="cmsms-icon-email m-r-5"></i><spring:message code="sidebar.company.dashboard12"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard12.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard12.3"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.newsletter3"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<c:choose>
			<c:when test="${!currentCompany.enabled}">
				<div class="alert alert-warning">
					<i class="cmsms-icon-attention i-alert"></i>
					<p class="p-alert">
						<spring:message code="message.premium.emailing"/> : 
						<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.identity"/></a>.
					</p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="wizard wizard-annonce">
					<form:form name="emailingForm" action="/" method="POST" modelAttribute="emailing" enctype="utf8" novalidate="novalidate">
						<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
						<div class="wizard-pane">
							<ul class="nav nav-tabs nav-tabs4" role="tablist">
								<c:set var="providers" value="email,magnet-2,at-3,paper-plane-3" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<li>
										<a class="lien ${state.count == 1 ? 'active' : ''}" data-toggle="tab" role="tab" 
											title="<spring:message code="wizard.newsletter.emailing${state.count}"/>">
											<i class="cmsms-icon-${pageScope.provider} i-34 transition-35"></i>
											<span class="help-tab"><spring:message code="wizard.newsletter.emailing${state.count}"/></span>
										</a>
									</li>
								</c:forEach>
							</ul>
							<div class="wizard-content">
								<div class="wizard-body">
									<c:forEach var="i" begin="1" end="4" step="1">
										<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
											<c:import url="/WEB-INF/pages/company/newsletter/emailing/form_emailing${i}.jsp"/>
										</div>
									</c:forEach>
								</div>
								<div class="wizard-footer">
									<div class="pull-left">
										<button type="button" class="btn btn-segond btn-previous btn-add btn-left" style="display:none;">
											<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'left' : 'right'}-4"></i><spring:message code="btn.previous"/></span>
										</button>
									</div>
									<div id="submitForm" class="form-submit pull-right">
										<button type="button" class="btn btn-primary btn-next btn-add btn-right">
					            			<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'right' : 'left'}-4"></i><spring:message code="btn.next"/></span>
					            		</button>
					            		<button type="submit" class="btn btn-primary btn-finish btn-submit btn-add btn-left" style="display:none;">
					            			<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.send"/></span>
					            		</button>
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