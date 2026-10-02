<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/portfolio/works"/>" class="lien lien-black">
			<i class="cmsms-icon-book m-r-5"></i><spring:message code="sidebar.company.dashboard3"/></a></li>
		<li><a href="<c:url value="/company/portfolio/actus"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard3.2"/></a></li>
		<li class="active"><spring:message code="${empty actu.id ? 'sidebar.company.dashboard1.2' : 'btn.edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.portfolio.actu.${empty actu.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.portfolio2.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/portfolio/actus" scope="request"></c:set>
	<c:set var="backwordPage" value="7" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<c:choose>
		<c:when test="${empty actu.id && hasMaxActu}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.actu"/> : 
					<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.premium"/></a>.
				</p>
			</div>
		</c:when>
		<c:when test="${empty actu.id && !currentCompany.enabled}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.portfolio"/> : 
					<a href="<c:url value="/company/profile/identity"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.identity"/></a>.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="page-container">
				<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
					<form:form name="actuForm" action="/" method="POST" modelAttribute="actu" enctype="multipart/form-data" novalidate="novalidate">
						<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
						<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
						<div id="fileForm" class="form-group row">
							<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
							<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
							<label class="col-form-label col-md-4 col-lg-3">
								<spring:message code="lbl.photo" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.portfolio2.1" /></span>
							</label>
							<div class="col-md-8 col-lg-9">
								<div class="avatar-content avatar-actu">
									<div class="avatar-view">
										<img id="avatarImg" class="img-responsive transition-35"
											src="<c:url value="${actu.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
										<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
									</div>
									<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
										style="${!actu.hasAvatar ? 'display:none;' : ''}"><i class="cmsms-icon-trash-7"></i>
									</a>
								</div>
								<span class="error"></span>
							</div>
						</div>
						<spring:bind path="title">
							<div id="titleForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="title">
									<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.portfolio2.2" /></span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<form:input class="form-control" type="text" path="title" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="description">
							<div id="descriptionForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="description">
									<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.portfolio2.3" /></span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<form:textarea class="form-control form-area" rows="3" path="description" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="actuDate">
							<div id="actuDateForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="actuDate">
									<spring:message code="tabs.actu" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<div class="col-md-4 col-lg-3">
									<div class="input-group-icon date">
										<form:input class="form-control" type="text" path="actuDate" />
										<span class="input-group-addon" style="display:none;"></span>
									</div>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="urlExtern">
							<div id="urlExternForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="urlExtern">
									<spring:message code="tabs.url" />
									<span class="help-text"><spring:message code="txt.help.portfolio2.4" /></span>
								</form:label>
								<div class="col-md-8 col-lg-9">
									<c:set var="faholder" scope="page"><spring:message code="tool.ind.actu" /></c:set>
									<form:input class="form-control" type="url" path="urlExtern" placeholder="${pageScope.faholder}" />
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<spring:bind path="hasPublished">
							<div id="hasPublishedForm" class="form-group row m-b-20">
								<form:label class="col-form-label col-md-4 col-lg-3" path="hasPublished">
									<spring:message code="tabs.state" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<div class="col-md-4 col-lg-3">
									<label class="ui-radio ui-radio-segond font-small m-r-10">
										<form:radiobutton value="${true}" path="hasPublished" />
										<span class="input-span"></span><spring:message code="chose.published1" />
									</label>
									<label class="ui-radio ui-radio-segond font-small">
										<form:radiobutton value="${false}" path="hasPublished" />
										<span class="input-span"></span><spring:message code="chose.published2" />
									</label>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<div class="form-group row">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<spring:bind path="hasNotified">
									<label class="ui-checkbox ui-checkbox-segond font-small">
										<form:checkbox path="hasNotified" />
										<span class="input-span"></span><spring:message code="comp.portfolio2.${empty actu.id ? '1' : '2'}" />
									</label>
								</spring:bind>
							</div>
						</div>
						<div class="form-group row m-t-0 m-b-20">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<hr class="my-4">
								<div id="submitForm" class="form-submit m-t-20">
									<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
										<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty actu.id ? 'save' : 'update'}"/></span>
									</button>
								</div>
							</div>
						</div>
					</form:form>
				</sec:authorize>
			</div>
		</c:otherwise>
	</c:choose>
</div>