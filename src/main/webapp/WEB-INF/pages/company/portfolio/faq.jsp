<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/portfolio/works"/>" class="lien lien-black">
			<i class="cmsms-icon-book m-r-5"></i><spring:message code="sidebar.company.dashboard3"/></a></li>
		<li><a href="<c:url value="/company/portfolio/faqs"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard3.4"/></a></li>
		<li class="active"><spring:message code="${empty faq.id ? 'sidebar.company.dashboard1.2' : 'btn.edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.portfolio.faq.${empty faq.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.portfolio4.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/portfolio/faqs" scope="request"></c:set>
	<c:set var="backwordPage" value="9" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<c:choose>
		<c:when test="${empty faq.id && hasMaxFaq}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.faq"/> : 
					<a href="<c:url value="/company/tools/subscribes"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.premium"/></a>.
				</p>
			</div>
		</c:when>
		<c:when test="${empty faq.id && !currentCompany.enabled}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.portfolio"/> : 
					<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.identity"/></a>.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="page-container">
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
					<form:form name="faqForm" action="/" method="POST" modelAttribute="faq" enctype="utf8" novalidate="novalidate">
						<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
						<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
						<spring:bind path="question">
							<div id="questionForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="question">
									<spring:message code="tabs.question" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.portfolio4.1" /></span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<form:input class="form-control" type="text" path="question" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="detail"><form:input type="hidden" path="detail" /></spring:bind>
						<div id="editorForm" class="form-group form-note row">
							<label class="col-form-label col-md-4 col-lg-3">
								<spring:message code="lbl.response" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.portfolio4.2" /></span>
							</label>
							<div class="col-md-8 col-lg-9">
								<div id="editor"><c:if test="${!empty faq.id}"><c:out value="${faq.detail}" escapeXml="false" /></c:if></div>
								<span class="error"></span>
							</div>
						</div>
						<spring:bind path="urlExtern">
							<div id="urlExternForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="urlExtern">
									<spring:message code="tabs.url" />
									<span class="help-text"><spring:message code="txt.help.portfolio4.3" /></span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<c:set var="faholder" scope="page"><spring:message code="tool.ind.faq" /></c:set>
									<form:input class="form-control" type="url" path="urlExtern" placeholder="${pageScope.faholder}" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="hasPublished">
							<div id="hasPublishedForm" class="form-group row m-b-20">
								<form:label class="col-form-label col-md-4 col-lg-3" path="hasPublished">
									<spring:message code="tabs.state" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<div class="col-md-4 col-lg-3">
									<label class="ui-radio ui-radio-segond font-small m-r-10">
										<form:radiobutton value="${true}" path="hasPublished" />
										<span class="input-span"></span><spring:message code="chose.published1" />
									</label>
									<label class="ui-radio ui-radio-segond font-small">
										<form:radiobutton value="${false}" path="hasPublished" />
										<span class="input-span"></span><spring:message code="chose.published2" />
									</label>
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
										<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty faq.id ? 'save' : 'update'}"/></span>
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