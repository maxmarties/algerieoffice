<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/settings/general"/>" class="lien lien-black">
			<i class="cmsms-icon-cog-5 m-r-5"></i><spring:message code="sidebar.user.dashboard7"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard7.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.user.dashboard7.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.setting1.4"/></p>
	</div>
	<div class="page-container">
		<form:form name="cookiesForm" action="/" method="POST" modelAttribute="cookies" enctype="utf8" novalidate="novalidate">
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.sub.setting8.3" />
					<span class="help-text"><spring:message code="txt.help.setting9.3" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<c:forEach var="i" begin="1" end="8" step="1">
						<div class="m-b-5">
							<spring:bind path="cookies[${i - 1}]">
								<label class="ui-checkbox ui-checkbox-segond font-small">
									<form:checkbox path="cookies[${i - 1}]" /><span class="input-span"></span><spring:message code="comp.setting7.4.${i}" />
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