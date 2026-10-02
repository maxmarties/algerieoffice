<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/posts/all"/>" class="lien lien-black">
			<i class="cmsms-icon-bag m-r-5"></i><spring:message code="sidebar.company.dashboard1"/></a></li>
		<li><a href="<c:url value="/company/posts/categories"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard1.3"/></a></li>
		<li class="active"><spring:message code="${empty category.id ? 'sidebar.company.dashboard1.2' : 'btn.edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.posts.category.${empty category.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.posts3.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/posts/categories" scope="request"></c:set>
	<c:set var="backwordPage" value="5" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="form-container">
		<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
			<form:form name="categoryForm" action="/" method="POST" modelAttribute="category" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
				<spring:bind path="name">
					<div id="nameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="name">
							<spring:message code="tabs.name" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.category1.1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="name" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="identify">
					<div id="identifyForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="identify">
							<spring:message code="lbl.identify" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.url" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.identify" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<div class="input-group">
								<div class="input-group-lien">
									<c:out value="${!empty currentCompany.url ? currentCompany.url : '@'}"/><c:out value="${urlCategories}"/>
								</div>
								<form:input class="form-control" type="text" path="identify" />
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="parentUUID">
					<div id="parentUUIDForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="name">
							<spring:message code="tabs.parent" />
							<span class="help-text"><spring:message code="txt.help.category1.2" /></span>
						</form:label>
						<div class="col-md-8 col-lg-6">
							<form:select class="form-select2" path="parentUUID" disabled="${category.hasParent}">
								<option value="-" ${empty category.parentUUID ? 'selected' : ''}><c:out value="-" /></option>
								<c:forEach var="choseCategory" items="${choseCategories}" >
									<option value="${choseCategory.uuid()}" ${choseCategory.uuid() == category.parentUUID ? 'selected' : ''}><c:out value="${choseCategory.name}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
							<c:if test="${category.hasParent}"><span class="ind-text font-bold"><spring:message code="txt.help.category1.3" /></span></c:if>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="description">
					<div id="descriptionForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="description">
							<spring:message code="tabs.descrptif" />
							<span class="help-text"><spring:message code="txt.help.category1.4" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:textarea class="form-control form-area" rows="3" path="description" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<div class="form-group row">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<spring:bind path="hasPingled">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="hasPingled" /><span class="input-span"></span><spring:message code="comp.category" /> 
							</label>
						</spring:bind>
						<hr class="my-4">
						<p class="font-mini"><spring:message code="txt.company.posts3.3"/></p>
					</div>
				</div>
				<hr class="my-4 m-t-30">
				<div class="form-group row m-b-30">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<div id="submitForm" class="form-submit m-t-10">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty category.id ? 'save' : 'update'}"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>