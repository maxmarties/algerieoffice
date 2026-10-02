<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/dashboard"/>" class="lien lien-black">
			<i class="cmsms-icon-home-2 m-r-5"></i><spring:message code="sidebar.admin.dashboard1"/></a></li>
		<li><a href="<c:url value="/user/dashboard"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard1.1"/></a></li>
		<li class="active"><spring:message code="btn.partner3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="btn.partner3"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.dashboard3"/></p>
	</div>
	<div class="page-container">
		<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')">
			<form:form name="companyForm" action="/" method="POST" modelAttribute="company" enctype="multipart/form-data" novalidate="novalidate">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.post4"/></h2>
				<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
				<div id="fileForm" class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.companylogo" />
						<span class="help-text"><spring:message code="txt.help.companylogo" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="avatar-content avatar-company">
							<div class="avatar-view">
								<img id="avatarImg" class="img-responsive transition-35" src="<c:url value="/static/picts/avatars/company-min.jpg"/>" 
									alt="<spring:message code="tooltip.avatar" />">
								<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
							</div>
							<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" style="display:none;"><i class="cmsms-icon-trash-7"></i></a>
						</div>
						<span class="error"></span>
					</div>
				</div>
				<spring:bind path="denomination">
					<div id="denominationForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="denomination">
							<spring:message code="lbl.denomination" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.denomination1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="denomination" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="tradename">
					<div id="tradenameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="tradename">
							<spring:message code="tabs.tradename" />
							<span class="help-text"><spring:message code="txt.help.tradename1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="tradename" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="activity">
					<div id="activityForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="activity">
							<spring:message code="lbl.sub.activity1" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.activity1" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.activity" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:select class="form-select2" path="activity">
								<option></option>
								<c:forEach var="choseActivity" items="${choseActivities}" varStatus="state">
									<optgroup label="<spring:message code="chose.sector${state.count}" />">
										<c:forEach var="codeActivity" items="${choseActivity}">
											<option value="${codeActivity}"><c:out value="${codeActivity}"/> - <spring:message code="chose.activity.${codeActivity}" /></option>
										</c:forEach>
									</optgroup>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="description">
					<div id="descriptionForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="description">
							<spring:message code="lbl.description" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.description" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.description" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:textarea class="form-control form-area" rows="3" path="description" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<hr class="my-4">
				<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.agent2"/></h2>
				<div class="row m-t-20">
					<label class="col-form-label col-md-4 col-lg-3" for="address">
						<spring:message code="lbl.companyadresse" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.companyaddress" /></span>
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
											<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
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
							<spring:message code="tabs.phone" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.phone" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<div class="input-group-phone">
								<c:set var="faholder" scope="page"><spring:message code="tool.ind.phone" /></c:set>
								<span class="input-icon"><c:out value="+213"/></span>
								<form:input class="form-control" type="tel" path="phone" placeholder="${pageScope.faholder}" />
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="email">
					<div id="emailForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="email">
							<spring:message code="lbl.companymail" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.shedulemail" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="email" path="email" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="lang">
					<div id="langForm" class="form-group row m-b-10">
						<form:label class="col-form-label col-md-4 col-lg-3" path="lang">
							<spring:message code="lbl.lang" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-4 col-lg-3">
							<c:set var="chosers" value="fr,en,ar" scope="page"></c:set>
							<c:set var="providers" value="Français,English,العربية" scope="page"></c:set>
							<form:select class="form-select2-simple" path="lang">
								<option></option>
								<c:forEach var="choser" items="${pageScope.chosers}" varStatus="state">
									<option value="${pageScope.choser}" ${company.lang == pageScope.choser ? 'selected' : ''}><c:out value="${pageScope.providers.split(',')[state.count - 1]}"/></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<div class="form-group row m-t-0 m-b-20">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.save"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>