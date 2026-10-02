<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/account/profile"/>" class="lien lien-black">
			<i class="cmsms-icon-user-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard2"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard2.2" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard2.2"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.account2.2"/></p>
	</div>
	<div class="page-container">
		<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
			<input type="hidden" id="hasCompany" value="${currentUser.hasCompany()}" />
			<form:form name="coordinatesForm" action="/" method="POST" modelAttribute="coordinates" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.account2"/></h2>
				<spring:bind path="sexe">
					<div id="sexeForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="sexe">
							<spring:message code="tabs.sexe" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<label class="ui-radio ui-radio-segond font-small m-r-10">
								<form:radiobutton value="${true}" path="sexe" />
								<span class="input-span"></span><spring:message code="chose.sexe1" />
							</label>
							<label class="ui-radio ui-radio-segond font-small">
								<form:radiobutton value="${false}" path="sexe" />
								<span class="input-span"></span><spring:message code="chose.sexe2" />
							</label>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="birthDate">
					<div id="birthDateForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="birthDate">
							<spring:message code="tabs.birthdate" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.account2.1" /></span>
						</form:label>
						<div class="col-md-4 col-lg-3">
							<div class="input-group-icon date">
								<form:input class="form-control" type="text" path="birthDate" />
								<span class="input-group-addon" style="display:none;"></span>
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<c:if test="${currentUser.hasCompany()}">
					<spring:bind path="function">
						<div id="functionForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="function">
								<spring:message code="tabs.function" />
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:input class="form-control" type="text" path="function" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
				</c:if>
				<spring:bind path="biography">
					<div id="biographyForm" class="form-group row m-b-20">
						<form:label class="col-form-label col-md-4 col-lg-3" path="biography">
							<spring:message code="tabs.biography" />
							<span class="help-text"><spring:message code="txt.help.account2.2" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:textarea class="form-control form-area" rows="3" path="biography" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<hr class="my-4">
				<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.agent2"/></h2>
				<div class="row m-t-20">
					<label class="col-form-label col-md-4 col-lg-3" for="address">
						<spring:message code="lbl.sub.order2.4" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.account2.3" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<spring:bind path="address">
							<div id="addressForm" class="form-group m-b-0">
								<form:input class="form-control" type="text" path="address" />
								<span class="error"></span>
							</div>
						</spring:bind>
						<div class="row m-t-10">
							<spring:bind path="postal">
								<div id="postalForm" class="form-group col-md-4 m-b-10">
									<c:set var="faholder" scope="page"><spring:message code="lbl.postal" /></c:set>
									<form:input class="form-control" type="text" path="postal" placeholder="${pageScope.faholder}" />
									<span class="error"></span>
								</div>
							</spring:bind>
							<spring:bind path="wilaya">
								<div id="wilayaForm" class="form-group col-md-8 m-b-10">
									<c:set var="faholder" scope="page"><spring:message code="lbl.wilaya" /></c:set>
									<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.faholder}" >
										<option></option>
										<c:forEach var="i" begin="1" end="48" step="1">
											<option value="${i}" ${coordinates.wilaya == i ? 'selected' : ''}><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
										</c:forEach>
									</form:select>
									<span class="error"></span>
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
				<spring:bind path="phone">
					<div id="phoneForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="phone">
							<spring:message code="tabs.mobile" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.account2.4" /></span>
						</form:label>
						<div class="col-md-8 col-lg-4">
							<div class="input-group-phone">
								<span class="input-icon"><c:out value="+213"/></span>
								<form:input class="form-control" type="tel" path="phone" />
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<div class="form-group row m-t-0">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<spring:bind path="hasPhone">
							<label class="ui-checkbox ui-checkbox-segond font-small">
								<form:checkbox path="hasPhone" />
								<span class="input-span"></span><spring:message code="comp.phone" />
							</label>
						</spring:bind>
						<span class="help-text"><spring:message code="txt.help.account2.5" /></span>
					</div>
				</div>
				<c:if test="${!currentUser.hasCompany()}">
					<spring:bind path="website">
						<div id="websiteForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="website">
								<spring:message code="tabs.website" />
							</form:label>
							<div class="col-md-8 col-lg-9">
								<c:set var="faholder" scope="page"><spring:message code="tool.ind.linked" /></c:set>
								<form:input class="form-control" type="url" path="website" placeholder="${pageScope.faholder}" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
				</c:if>
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