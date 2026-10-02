<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/marketplace/promotes"/>" class="lien lien-black">
			<i class="cmsms-icon-pin-1 m-r-5"></i><spring:message code="sidebar.company.dashboard2"/></a></li>
		<li><a href="<c:url value="/company/marketplace/campaigns"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard2.4"/></a></li>
		<li class="active"><spring:message code="btn.${empty campaign.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.marketplace.campaign.${empty campaign.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.marketplace6.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/marketplace/campaigns" scope="request"></c:set>
	<c:set var="backwordPage" value="20" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<c:choose>
		<c:when test="${empty campaign.id && !currentCompany.enabled}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.marketplace"/> :  
					<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.identity"/></a>.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="page-container">
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
					<form:form name="campaignForm" action="/" method="POST" modelAttribute="campaign" enctype="utf8" novalidate="novalidate">
						<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
						<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
						<spring:bind path="type">
							<div id="typeForm" class="form-group row" data-toggle="buttons">
								<form:label class="col-form-label col-md-4 col-lg-3" path="type">
									<spring:message code="lbl.sub.marketplace1.4" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.marketplace5.1" /></span>
								</form:label>
								<div class="col-md-8 col-lg-6">
									<ul class="nav nav-campaign m-b-10">
										<c:set var="providers" value="bag,pin-1,calendar-7" scope="page"></c:set>
										<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
											<li>
												<label class="btn btn-campaign btn-simple m-auto ${!empty campaign.id ? 'disabled' : ''} ${campaign.type == state.count ? 'active' : ''}">
													<i class="cmsms-icon-${pageScope.provider} i-24"></i>
													<span class="help-text m-t-10"><spring:message code="wizard.screen.navbar${state.count}"/></span>
													<form:radiobutton class="hidden" value="${state.count}" path="type" disabled="${!empty campaign.id}" />
												</label>
											</li>
										</c:forEach>
									</ul>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="documentId"><form:input type="hidden" path="documentId" /></spring:bind>
						<div id="documentCampaignForm" class="form-group row">
							<label class="col-form-label col-md-4 col-lg-3" for="documentCampaign">
								<spring:message code="tabs.campaign" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.marketplace5.2" /></span>
							</label>
							<div class="col-md-8 col-lg-6">
								<hr class="my-1">
								<c:choose>
									<c:when test="${empty campaign.id}">
										<div class="campaign-load" style="display:none;">
											<c:import url="/WEB-INF/basics/loading_span.jsp"/>
											<div id="campaignLoad"></div>
											<span class="error"></span>
										</div>
										<p class="font-small text-center compaign-parag"><spring:message code="txt.help.marketplace5.3" /></p>
									</c:when>
									<c:otherwise><input class="form-control disaload" type="text" value="${campaign.documentTitle}" disabled /></c:otherwise>
								</c:choose>
							</div>
						</div>
						<spring:bind path="sector">
							<div id="sectorForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="sector">
									<spring:message code="lbl.sub.annonce1" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.marketplace5.4" /></span>
								</form:label>
								<div class="col-md-4 col-lg-6">
									<c:set var="faholder" scope="page"><spring:message code="chose.sector" /></c:set>
									<form:select class="form-select2" path="sector" data-placeholder="${pageScope.faholder}">
										<option></option>
										<option value="32" ${campaign.sector == 32 ? 'selected' : ''}><spring:message code="chose.sector.all" /></option>
										<c:forEach var="i" begin="1" end="31">
											<option value="${i}" ${campaign.sector == i ? 'selected' : ''}><spring:message code="chose.sector${i}" /></option>
										</c:forEach>
									</form:select>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="wilaya">
							<div id="wilayaForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="wilaya">
									<spring:message code="lbl.contract.location" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.marketplace5.5" /></span>
								</form:label>
								<div class="col-md-4 col-lg-6">
									<c:set var="faholder" scope="page"><spring:message code="tooltip.search.location" /></c:set>
									<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.faholder}">
										<option></option>
										<option value="49" ${campaign.wilaya == 49 ? 'selected' : ''}><spring:message code="chose.wilaya.all" /></option>
										<c:forEach var="i" begin="1" end="48">
											<option value="${i}" ${i == campaign.wilaya ? 'selected' : ''}><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
										</c:forEach>
									</form:select>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<div class="form-group row m-b-20">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<hr class="my-4">
								<div id="submitForm" class="form-submit m-t-20">
									<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
										<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty campaign.id ? 'save' : 'update'}"/></span>
									</button>
								</div>
							</div>
						</div>
					</form:form>
				</sec:authorize>
			</div>
		</c:otherwise>
	</c:choose>
</div>