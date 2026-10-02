<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/data/activities" />" class="lien lien-black">
			<i class="cmsms-icon-folder m-r-5"></i><spring:message code="sidebar.admin.dashboard6"/></a></li>
		<li><a href="<c:url value="/admin/data/activities" />" class="lien lien-black"><spring:message code="sidebar.admin.dashboard6.1"/></a></li>
		<li class="active"><spring:message code="${empty activity.id ? 'btn.add' : 'btn.edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="btn.${empty activity.id ? 'add.activity' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.activity"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/data/activities" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-md-10 col-lg-8">
			<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
				<form:form name="activityForm" action="/" method="POST" modelAttribute="activity" enctype="utf8" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="code">
						<div id="codeForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="code">
								<spring:message code="tabs.code" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.code" /></span>
							</form:label>
							<div class="col-md-8 col-lg-4">
								<form:input class="form-control" type="text" path="code" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="sector">
						<div id="sectorForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="sector">
								<spring:message code="tabs.sector" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:select class="form-select2" path="sector" >
									<option></option>
									<c:forEach var="i" begin="1" end="31">
										<option value="${i}" ${activity.sector == i ? 'selected' : ''}><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.sector${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="url">
						<div id="urlForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="url">
								<spring:message code="lbl.identify" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.url" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<div class="input-group-icon input-group-check" data-input="">
									<form:input class="form-control" type="url" path="url" />
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="checkedUrl"><form:input type="hidden" path="checkedUrl" /></spring:bind>
					<div class="form-group row">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<hr class="my-4">
							<div id="submitForm" class="form-submit m-t-20">
								<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
		            				<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty activity.id ? 'save' : 'update'}"/></span>
		            			</button>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>
		</div>
	</div>
</div>