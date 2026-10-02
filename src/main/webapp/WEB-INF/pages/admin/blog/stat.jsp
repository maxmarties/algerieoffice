<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/blog/all"/>" class="lien lien-black">
			<i class="cmsms-icon-quote-right m-r-5"></i><spring:message code="sidebar.admin.dashboard5"/></a></li>
		<li><a href="<c:url value="/admin/blog/stats"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard5.3"/></a></li>
		<li class="active"><spring:message code="btn.update" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.blog.stat"/></h1>
		<p><spring:message code="txt.admin.blogstat"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/blog/stats" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-lg-9">
			<form:form name="statsForm" action="/" method="POST" modelAttribute="blogStats" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="title"><form:input type="hidden" path="title" /></spring:bind>
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3"><spring:message code="tabs.title" /></label>
					<div class="col-md-8 col-lg-9">
						<input class="form-control disaload" type="text" value="${blogStats.title}" disabled />
					</div>
				</div>
				<spring:bind path="viewCount">
					<div id="viewCountForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="viewCount">
							<spring:message code="tabs.eye" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-3">
							<form:input class="form-control" type="number" path="viewCount" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="simultude">
					<div id="simultudeForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="simultude">
							<spring:message code="tabs.simultude" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-3">
							<form:input class="form-control" type="number" path="simultude" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="market">
					<div id="marketForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="market">
							<spring:message code="tabs.potential" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-3">
							<form:input class="form-control" type="number" path="market" />
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
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>