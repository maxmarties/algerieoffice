<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/manage/display"/>" class="lien lien-black">
			<i class="cmsms-icon-sliders m-r-5"></i><spring:message code="sidebar.company.dashboard5"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard5.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard5.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.manage2"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
		<div class="row">
			<div class="col-md-8 m-b-20">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.sticky"/></h2>
				<form:form name="stickyForm" action="/" method="POST" modelAttribute="sticky" enctype="utf8" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="title">
						<div id="titleForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="title">
								<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.sticky1.1" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<c:choose>
									<c:when test="${empty sticky.title}"><c:set var="faholder" scope="page"><spring:message code="lbl.sub.sticky1.1" /></c:set></c:when>
									<c:otherwise><c:set var="faholder" value="${sticky.title}" scope="page"></c:set></c:otherwise>
								</c:choose>
								<form:input class="form-control" type="text" path="title" value="${pageScope.faholder}" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="description">
						<div id="descriptionForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="description">
								<spring:message code="tabs.descrptif" />
								<span class="help-text"><spring:message code="txt.help.sticky1.2" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:textarea class="form-control form-area" rows="3" path="description" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="label">
						<div id="labelForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="label">
								<spring:message code="lbl.sub.marketplace1.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.sticky1.3" /></span>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<c:choose>
									<c:when test="${empty sticky.label}"><c:set var="faholder" scope="page"><spring:message code="tooltip.contact" /></c:set></c:when>
									<c:otherwise><c:set var="faholder" value="${sticky.label}" scope="page"></c:set></c:otherwise>
								</c:choose>
								<form:input class="form-control" type="text" path="label" value="${pageScope.faholder}" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div id="urlExternForm" class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="tabs.url" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.sticky1.4" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<div>
								<label class="ui-radio ui-radio-segond font-small">
									<form:radiobutton value="${false}" path="target" />
									<span class="input-span"></span><spring:message code="lbl.sub.sticky1.2.2" />
								</label>
								<spring:bind path="urlExtern">
									<c:set var="faholder" scope="page"><spring:message code="tool.ind.linked" /></c:set>
									<form:input class="form-control" type="url" path="urlExtern" placeholder="${pageScope.faholder}" disabled="${sticky.target}" />
									<span class="error"></span>
								</spring:bind>
							</div>
							<div class="m-t-10">
								<label class="ui-radio ui-radio-segond font-small">
									<form:radiobutton value="${true}" path="target" />
									<span class="input-span"></span><spring:message code="lbl.sub.sticky1.2.1" />
								</label>
							</div>
						</div>
					</div>
					<div class="form-group row m-b-30">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<hr class="my-4">
							<div id="submitForm" class="form-submit m-t-20">
								<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
									<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
								</button>
							</div>
						</div>
					</div>
				</form:form>
			</div>
			<div class="col-md-4 m-b-20">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="wizard.marketplace.promote3"/></h2>
				<div class="bn-overview bn-body m-t-20" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
					<div class="widget-sticky m-auto">
						<h3 id="titleOverview" class="h-doc">
							<c:choose>
								<c:when test="${empty sticky.title}"><spring:message code="lbl.sub.sticky1.1" /></c:when>
								<c:otherwise><c:out value="${sticky.title}" /></c:otherwise>
							</c:choose>
						</h3>
						<div class="widget-body i-white m-t-20">
							<p id="descriptionOverview" class="h-header font-small"><c:out value="${sticky.description}" /></p>
							<div class="m-t-20">
								<a class="btn btn-sticky btn-simple btn-block">
									<span id="hrefOverview">
										<c:choose>
											<c:when test="${empty sticky.label}"><spring:message code="tooltip.contact" /></c:when>
											<c:otherwise><c:out value="${sticky.label}" /></c:otherwise>
										</c:choose>
									</span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
</div>