<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/alerts/posts"/>" class="lien lien-black">
			<i class="cmsms-icon-bell-1 m-r-5"></i><spring:message code="sidebar.user.dashboard4"/></a></li>
		<li><a href="<c:url value="/user/alerts/posts"/>" class="lien lien-black"><spring:message code="sidebar.user.dashboard4.1"/></a></li>
		<li class="active"><spring:message code="btn.${empty alert.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.alert.${empty alert.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.alert1.1"/></p>
	</div>
	<c:set var="backwordURL" value="/user/alerts/posts" scope="request"></c:set>
	<c:set var="backwordPage" value="19" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="form-container">
		<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
			<form:form name="alertForm" action="/" method="POST" modelAttribute="alert" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
				<spring:bind path="type"><form:input type="hidden" path="type" /></spring:bind>
				<spring:bind path="name">
					<div id="nameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="name">
							<spring:message code="tabs.alertname" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.alert1.1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="name" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="sector">
					<div id="sectorForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="sector">
							<spring:message code="lbl.sub.annonce1" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.alert1.2" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<c:set var="faholder" scope="page"><spring:message code="chose.sector" /></c:set>
							<form:select class="form-select2" path="sector" data-placeholder="${pageScope.faholder}" >
								<option></option>
								<c:forEach var="i" begin="1" end="31" step="1">
									<option value="${i}" ${alert.sector == i ? 'selected' : ''}><spring:message code="chose.sector${i}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="wilaya">
					<div id="wilayaForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="wilaya">
							<spring:message code="tabs.lieu" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-4">
							<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
							<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.faholder}" >
								<option></option>
								<c:forEach var="i" begin="1" end="48" step="1">
									<option value="${i}" ${alert.wilaya == i ? 'selected' : ''}><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="frequency">
					<div id="frequencyForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="frequency">
							<spring:message code="lbl.sub.alert1" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-4">
							<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
							<form:select class="form-select2-simple" path="frequency" data-placeholder="${pageScope.faholder}" >
								<option></option>
								<option value="6" ${alert.frequency == 6 ? 'selected' : ''}><spring:message code="chose.frequency6" /></option>
								<c:forEach var="i" begin="1" end="5" step="1">
									<option value="${i}" ${alert.frequency == i ? 'selected' : ''}><spring:message code="chose.frequency${i}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="enabled">
					<div id="serviceForm" class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.sub.alert2" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</label>
						<div class="col-md-8 col-lg-9">
							<c:forEach var="i" begin="1" end="2" step="1">
								<label class="ui-radio ui-radio-segond font-small m-r-20">
									<form:radiobutton value="${i == 1}" path="enabled" />
									<span class="input-span"></span><spring:message code="overview.alert${i}" />
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
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty alert.id ? 'save' : 'update'}"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>