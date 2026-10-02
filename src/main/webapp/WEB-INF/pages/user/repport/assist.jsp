<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/repports/testimonial"/>" class="lien lien-black">
			<i class="cmsms-icon-bug m-r-5"></i><spring:message code="sidebar.user.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard6.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.user.dashboard6.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.repport2.1"/></p>
	</div>
	<div class="form-container">
		<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
			<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="txt.user.repport2.2"/></h2>
			<form:form name="assistForm" action="/" method="POST" modelAttribute="assist" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="object">
					<div id="objectForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="object">
							<spring:message code="tabs.object" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.assist1.1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="object" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="message">
					<div id="messageForm" class="form-group row m-b-20">
						<form:label class="col-form-label col-md-4 col-lg-3" path="message">
							<spring:message code="tabs.message" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.assist1.5" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:textarea class="form-control form-area" rows="5" path="message" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="app">
					<div id="appForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="app">
							<spring:message code="lbl.sub.assist1" />
							<span class="help-text"><spring:message code="txt.help.assist1.2" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<c:forEach var="i" begin="1" end="3" step="1">
								<label class="ui-radio ui-radio-segond font-small m-r-20">
									<form:radiobutton value="${i}" path="app" />
									<span class="input-span"></span><spring:message code="chose.assist.app${i}" />
								</label>
							</c:forEach>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="management">
					<div id="managementForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="management">
							<spring:message code="tabs.manage" />
							<span class="help-text"><spring:message code="txt.help.assist1.3" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<c:forEach var="i" begin="1" end="3" step="1">
								<label class="ui-radio ui-radio-segond font-small m-r-20">
									<form:radiobutton value="${i}" path="management" />
									<span class="input-span"></span><spring:message code="chose.assist.management${i}" />
								</label>
							</c:forEach>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="program">
					<div id="programForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="program">
							<spring:message code="lbl.sub.assist2" />
							<span class="help-text"><spring:message code="txt.help.assist1.4" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<c:forEach var="i" begin="1" end="3" step="1">
								<label class="ui-radio ui-radio-segond font-small m-r-20">
									<form:radiobutton value="${i}" path="program" />
									<span class="input-span"></span><spring:message code="chose.assist.program${i}" />
								</label>
							</c:forEach>
						</div>
					</div>
				</spring:bind>
				<div class="form-group row m-b-20">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.send"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>