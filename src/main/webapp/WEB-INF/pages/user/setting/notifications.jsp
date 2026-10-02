<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/settings/general"/>" class="lien lien-black">
			<i class="cmsms-icon-cog-5 m-r-5"></i><spring:message code="sidebar.user.dashboard7"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard7.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.setting.notifications"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.setting1.2"/></p>
	</div>
	<div class="page-container">
		<form:form name="notisesForm" action="/" method="POST" modelAttribute="notises" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3"><spring:message code="tooltip.popup.notifications" /></label>
				<div class="col-md-8 col-lg-9">
					<c:forEach var="i" begin="1" end="5" step="1">
						<div class="m-b-5">
							<spring:bind path="notifications[${i - 1}]">
								<label class="ui-checkbox ui-checkbox-segond font-small ${i == 1 ? 'disabled' : ''}">
									<form:checkbox path="notifications[${i - 1}]" /><span class="input-span"></span><spring:message code="comp.setting6.1.${i}" />
								</label>
							</spring:bind>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="tabs.email" />
					<span class="help-text"><spring:message code="txt.help.setting8.1" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<c:forEach var="i" begin="1" end="5" step="1">
						<div class="m-b-5">
							<spring:bind path="communications[${i - 1}]">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="communications[${i - 1}]" /><span class="input-span"></span><spring:message code="comp.setting6.2.${i}" />
								</label>
							</spring:bind>
						</div>
						<c:if test="${i == 2}"><hr class="my-1"></c:if>
					</c:forEach>
					<span class="help-text"><spring:message code="txt.help.setting8.2" /></span>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3"><spring:message code="lbl.sub.setting7" /></label>
				<div class="col-md-8 col-lg-9">
					<c:forEach var="i" begin="1" end="4" step="1">
						<div class="m-b-5">
							<spring:bind path="sounds[${i - 1}]">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="sounds[${i - 1}]" /><span class="input-span"></span><spring:message code="comp.setting6.3.${i}" />
								</label>
							</spring:bind>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="form-group row m-t-10 m-b-30">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-1">
					<div id="submitForm" class="form-submit m-t-10">
						<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>