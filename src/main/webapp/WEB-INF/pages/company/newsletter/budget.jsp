<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/newsletter/template"/>" class="lien lien-black">
			<i class="cmsms-icon-email m-r-5"></i><spring:message code="sidebar.company.dashboard12"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard12.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard12.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.newsletter2"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.budget1"/></h2>
		<div class="row row-mini m-t-10">
			<c:set var="providers" value="at-3,mail-6,math" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="col-sm-4 col-md-3 col-lg-2 col-mini m-t-10">
					<div class="card-analytic card-refering card-left h-100">
						<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
						<div class="card-brand text-right">
							<span class="text-truncate font-small i-help"><spring:message code="tool.newsletter.budget1.${state.count}" /></span>
							<p class="h-header ${state.count == 3 ? 'i-primary' : currBudget.hasPresentBuget(state.count) ? 'i-green' : 'i-red'}">
								<c:out value="${currBudget.getFormattedBudget(state.count)}"/></p>
						</div>
						<span class="clearfix"></span>
						<div class="card-footer m-t-5">
							<p class="font-mini"><spring:message code="tool.newsletter.budget2.${state.count}" /></p>
						</div>
					</div>
				</div>
			</c:forEach>
			<div class="col-sm-12 col-md-3 col-lg-6 col-mini m-t-10">
				<div class="divider-left" style="margin:10px;padding:0 20px;">
					<p class="h-header i-primary"><spring:message code="tool.newsletter.budget3.1" /></p>
					<p class="font-small i-help m-t-5"><spring:message code="tool.newsletter.budget3.2" /></p>
					<hr class="my-1">
					<p class="font-mini"><spring:message code="tool.newsletter.budget3.3" /></p>
				</div>
			</div>
		</div>
		<hr class="my-4">
		<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.budget2"/></h2>
		<div id="budgetResult" class="m-t-20">
			<c:choose>
				<c:when test="${checkOrder}">
					<div class="alert alert-info">
						<i class="cmsms-icon-info-circled-3 i-alert"></i>
						<p class="p-alert"><spring:message code="txt.company.newsletter2.1"/></p>
					</div>
				</c:when>
				<c:otherwise>
					<div class="wizard wizard-promote">
						<form:form name="orderForm" action="/" method="POST" modelAttribute="order" enctype="multipart/form-data" novalidate="novalidate">
							<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
							<div class="wizard-pane">
								<ul class="nav nav-tabs nav-tabs2" role="tablist">
									<c:set var="providers" value="dollar,credit-card" scope="page"></c:set>
									<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
										<li>
											<a class="lien ${state.count == 1 ? 'active' : ''}" data-toggle="tab" role="tab" 
												title="<spring:message code="wizard.marketplace.order${state.count}"/>">
												<i class="cmsms-icon-${pageScope.provider} i-34 transition-35"></i>
												<span class="help-tab"><spring:message code="wizard.marketplace.order${state.count}"/></span>
											</a>
										</li>
									</c:forEach>
								</ul>
								<div class="wizard-content">
									<div class="wizard-body">
										<c:forEach var="i" begin="1" end="2" step="1">
											<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
												<c:import url="/WEB-INF/pages/company/newsletter/budget/form_budget${i}.jsp"/>
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
								            		<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'right' : 'left'}-4"></i><spring:message code="btn.promote.order"/></span>
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
		</div>
	</sec:authorize>
</div>