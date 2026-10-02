<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/premium/subscribes"/>" class="lien lien-black">
			<i class="cmsms-icon-bookmark m-r-5"></i><spring:message code="sidebar.admin.dashboard8"/></a></li>
		<li><a href="<c:url value="/admin/premium/subscribes"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard8.1"/></a></li>
		<li class="active"><spring:message code="header.tools.subscribe" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard8.1"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.premium1.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/premium/subscribes" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-lg-9">
			<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
				<form:form name="subscribeForm" action="/" method="POST" modelAttribute="subscribe" enctype="utf8" novalidate="novalidate">
					<spring:bind path="premiumId"><form:input type="hidden" path="premiumId" /></spring:bind>
					<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
					<spring:bind path="pass">
						<div id="passForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="pass">
								<spring:message code="tabs.pack" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
								<form:select class="form-select2-simple" path="pass" data-placeholder="${pageScope.faholder}">
									<option></option>
									<c:forEach var="i" begin="1" end="4" step="1">
										<option value="${i}" ${subscribe.pass == i ? 'selected' : ''}><spring:message code="chose.subscribe.order${i + 1}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="createDate">
						<div id="createDateForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="createDate">
								<spring:message code="tabs.subscribe" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<div class="input-group-icon date">
									<form:input class="form-control" type="text" path="createDate" />
									<span class="input-group-addon" style="display:none;"></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="expiryDate">
						<div id="expiryDateForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="expiryDate">
								<spring:message code="tabs.expire" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<div class="input-group-icon date">
									<form:input class="form-control" type="text" path="expiryDate" />
									<span class="input-group-addon" style="display:none;"></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="enabled">
						<div class="form-group row">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<label class="ui-checkbox ui-checkbox-segond font-small">
				            		<form:checkbox path="enabled" />
									<span class="input-span"></span><spring:message code="comp.admin.subscribe" />
								</label>
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
			</sec:authorize>
		</div>
	</div>
</div>