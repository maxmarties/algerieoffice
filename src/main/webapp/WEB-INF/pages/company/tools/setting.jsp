<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/tools/setting"/>" class="lien lien-black">
			<i class="cmsms-icon-wrench m-r-5"></i><spring:message code="sidebar.company.dashboard10"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard10.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard10.1"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.tools1"/></p>
	</div>
	<div class="page-container">
		<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
			<form:form name="settingForm" action="/" method="POST" modelAttribute="setting" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.setting1"/></h2>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.sub.setting1.1" />
						<span class="help-text"><spring:message code="txt.help.setting1.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<c:forEach var="i" begin="1" end="2" step="1">
							<div class="m-b-5">
								<spring:bind path="params[${i - 1}]">
									<label class="ui-checkbox ui-checkbox-segond font-small">
										<form:checkbox path="params[${i - 1}]" /><span class="input-span"></span><spring:message code="comp.setting1.${i}" />
									</label>
								</spring:bind>
							</div>
						</c:forEach>
					</div>
				</div>
				<spring:bind path="service">
					<div id="serviceForm" class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3"><spring:message code="lbl.sub.setting1.2" /></label>
						<div class="col-md-8 col-lg-9">
							<div>
								<c:forEach var="i" begin="1" end="2" step="1">
									<label class="ui-radio ui-radio-segond font-small m-r-10">
										<form:radiobutton value="${i == 2}" path="service" />
										<span class="input-span"></span><spring:message code="chose.post.type${i}" />
									</label>
								</c:forEach>
							</div>
						</div>
					</div>
				</spring:bind>
				<hr class="my-2">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.setting2"/></h2>
				<spring:bind path="posthome">
					<div class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.sub.setting2.1" />
							<span class="help-text"><spring:message code="txt.help.setting2.1" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<c:forEach var="i" begin="1" end="2" step="1">
								<div  class="m-b-5">
									<label class="ui-radio ui-radio-segond font-small m-r-10">
										<form:radiobutton value="${i == 1}" path="posthome" />
										<span class="input-span"></span><spring:message code="comp.setting2.${i}" />
									</label>
								</div>
							</c:forEach>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="indexed">
					<div class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.sub.setting2.2" />
							<span class="help-text"><spring:message code="txt.help.setting2.2" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<c:forEach var="i" begin="1" end="2" step="1">
								<div  class="m-b-5">
									<label class="ui-radio ui-radio-segond font-small m-r-10">
										<form:radiobutton value="${i == 1}" path="indexed" />
										<span class="input-span"></span><spring:message code="comp.setting2.${i + 2}" />
									</label>
								</div>
							</c:forEach>
							<p class="font-mini m-t-10"><spring:message code="txt.help.setting2.3" />: 
								<a href="<c:url value="/company/manage/preferences"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.preference"/></a>.</p>
						</div>
					</div>
				</spring:bind>
				<hr class="my-2">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.setting3"/><i class="cmsms-icon-dollar i-dollar m-l-5"></i></h2>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3"><spring:message code="btn.alert1" /></label>
					<div class="col-md-8 col-lg-9">
						<div class="m-b-5">
							<spring:bind path="params[2]">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="params[2]" /><span class="input-span"></span><spring:message code="comp.setting3.1" />
								</label>
							</spring:bind>
						</div>
						<div class="row m-t-10">
							<label class="col-form-label col-6 col-md-3 col-lg-2 m-t-5"><spring:message code="txt.help.setting3.1" /></label>
							<div class="col-6 col-md-7 col-lg-4">
								<input class="form-control disaload" type="text" value="${setting.alertname}" disabled />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3"><spring:message code="txt.help.setting3.2" /></label>
					<div class="col-md-8 col-lg-9">
						<spring:bind path="params[3]">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="params[3]" /><span class="input-span"></span><spring:message code="comp.setting3.2" />
							</label>
						</spring:bind>
					</div>
				</div>
				<hr class="my-2">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.setting4"/><i class="cmsms-icon-dollar i-dollar m-l-5"></i></h2>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<c:out value="AMP"/>
						<span class="help-text"><spring:message code="txt.help.setting4.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<spring:bind path="params[4]">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="params[4]" /><span class="input-span"></span><spring:message code="comp.setting4.1" />
							</label>
						</spring:bind>
					</div>
				</div>
				<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
					<div class="form-group row">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<hr class="my-2">
							<a href="<c:url value="/company/delete"/>" class="lien lien-red lien-hover"><spring:message code="lien.explorer.delete"/></a>
							<span class="help-text m-t-5"><spring:message code="txt.help.setting4.2" /></span>
						</div>
					</div>
				</sec:authorize>
				<div class="form-group row m-t-10 m-b-30">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<hr class="my-1">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
							</button>
						</div>
						<p class="font-mini m-t-20"><i class="cmsms-icon-dollar i-dollar m-r-10"></i><spring:message code="txt.help.preference3.6"/></p>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>