<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/marketplace/promotes"/>" class="lien lien-black">
			<i class="cmsms-icon-pin-1 m-r-5"></i><spring:message code="sidebar.company.dashboard2"/></a></li>
		<li><a href="<c:url value="/company/marketplace/promotes"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard2.1"/></a></li>
		<li class="active"><spring:message code="btn.${empty promote.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.marketplace.promote.${empty promote.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.marketplace1.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/marketplace/promotes" scope="request"></c:set>
	<c:set var="backwordPage" value="2" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<c:choose>
		<c:when test="${empty promote.id && !currentCompany.enabled}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.marketplace"/> :  
					<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.identity"/></a>.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
				<div class="wizard wizard-promote m-b-20">
					<form:form name="promoteForm" action="/" method="POST" modelAttribute="promote" enctype="multipart/form-data" novalidate="novalidate">
						<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
						<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
						<div class="wizard-pane">
							<ul class="nav nav-tabs nav-tabs3" role="tablist">
								<c:set var="providers" value="target,megaphone-1,search" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<li>
										<a class="lien ${state.count == 1 ? 'active' : ''}" data-toggle="tab" role="tab" 
											title="<spring:message code="wizard.marketplace.promote${state.count}"/>">
											<i class="cmsms-icon-${pageScope.provider} i-34 transition-35"></i>
											<span class="help-tab"><spring:message code="wizard.marketplace.promote${state.count}"/></span>
										</a>
									</li>
								</c:forEach>
							</ul>
							<div class="wizard-content">
								<div class="wizard-body">
									<c:forEach var="i" begin="1" end="3" step="1">
										<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
											<c:import url="/WEB-INF/pages/company/marketplace/promote/form_promote${i}.jsp"/>
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
					            			<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty promote.id ? 'save' : 'update'}"/></span>
					            		</button>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</form:form>
				</div>
			</sec:authorize>
		</c:otherwise>
	</c:choose>
</div>