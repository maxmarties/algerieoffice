<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/newsletter/template"/>" class="lien lien-black">
			<i class="cmsms-icon-email m-r-5"></i><spring:message code="sidebar.company.dashboard12"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard12.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard12.1"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.newsletter1"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<form:form name="templateForm" action="/" method="POST" modelAttribute="template" enctype="multipart/form-data" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<div class="row">
				<div class="col-lg-6 m-b-20">
					<div id="fileForm" class="form-group row">
						<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
						<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
						<label class="col-form-label col-md-4">
							<spring:message code="lbl.companyabout" />
							<span class="help-text"><spring:message code="txt.help.newsletter1.4" /></span>
						</label>
						<div class="col-md-8">
							<div class="avatar-content avatar-extrawid">
								<div class="avatar-view">
									<img id="avatarImg" class="img-responsive transition-35"
										src="<c:url value="${template.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
									<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
								</div>
								<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
									style="${!template.hasAvatar ? 'display:none;' : ''}"><i class="cmsms-icon-trash-7"></i>
								</a>
							</div>
							<span class="error"></span>
						</div>
					</div>
					<hr class="my-4">
					<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.slider2"/></h2>
					<spring:bind path="paneColor">
						<div id="paneColorForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="paneColor">
								<spring:message code="lbl.fadecolor" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.newsletter1.5" /></span>
							</form:label>
							<div class="col-md-4">
								<div class="input-group-icon">
									<form:input class="form-control form-color" type="text" path="paneColor" />
									<span class="input-group-color"><i class="input-color-result"></i></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="textColor">
						<div id="textColorForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="textColor">
								<spring:message code="lbl.textcolor" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.newsletter1.6" /></span>
							</form:label>
							<div class="col-md-4">
								<div class="input-group-icon">
									<form:input class="form-control form-color" type="text" path="textColor" />
									<span class="input-group-color"><i class="input-color-result"></i></span>
								</div>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<hr class="my-4">
					<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="tabs.url"/></h2>
					<spring:bind path="title">
						<div id="titleForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="title">
								<spring:message code="tabs.title" />
								<span class="help-text"><spring:message code="txt.help.newsletter1.7" /></span>
							</form:label>
							<div class="col-md-8">
								<form:input class="form-control" type="text" path="title" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div id="urlForm" class="form-group row">
						<label class="col-form-label col-md-4">
							<spring:message code="lbl.sub.newsletter1.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.newsletter1.8" /></span>
						</label>
						<div class="col-md-8">
							<c:forEach var="i" begin="1" end="3" step="1">
								<div class="${i != 1 ? 'm-t-5' : ''}">
									<label class="ui-radio ui-radio-segond font-small">
										<form:radiobutton value="${i}" path="target" />
										<span class="input-span"></span><spring:message code="chose.newsletter.target${i}" />
									</label>
									<c:if test="${i == 3}">
										<spring:bind path="url">
											<c:set var="faholder" scope="page"><spring:message code="tool.ind.linked" /></c:set>
											<form:input class="form-control" type="url" path="url" placeholder="${pageScope.faholder}" disabled="${template.target != 3}" />
											<span class="error"></span>
										</spring:bind>
										<span class="help-text m-t-5"><spring:message code="txt.help.linked2.2" /></span>
									</c:if>
								</div>
							</c:forEach>
						</div>
					</div>
					<spring:bind path="label">
						<div id="labelForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="label">
								<spring:message code="lbl.sub.marketplace1.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4">
								<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
								<form:select class="form-select2-simple" path="label" data-placeholder="${pageScope.faholder}">
									<option></option>
									<c:forEach var="i" begin="1" end="5" step="1">
										<option value="${i}" ${template.label == i ? 'selected' : ''}><spring:message code="chose.button${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<hr class="my-4">
					<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="lbl.sub.display2.9"/></h2>
					<spring:bind path="description">
						<div id="descriptionForm" class="form-group row">
							<form:label class="col-form-label col-md-4" path="description">
								<spring:message code="tabs.descrptif" />
								<span class="help-text"><spring:message code="txt.help.newsletter1.9" /></span>
							</form:label>
							<div class="col-md-8">
								<form:textarea class="form-control form-area" rows="3" path="description" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div class="form-group row m-b-30">
						<div class="col-md-4 hidden-sm-down"></div>
						<div class="col-md-8">
							<hr class="my-4">
							<div id="submitForm" class="form-submit m-t-10">
								<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
									<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-6 m-b-20">
					<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="wizard.marketplace.promote3"/></h2>
					<div class="bn-overview bn-body m-t-20" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
						<div class="bn-box template-overview m-auto">
							<div class="item-template">
								<spring:bind path="params[0]">
									<label class="ui-checkbox ui-checkbox-segond font-small">
										<form:checkbox path="params[0]" /><span class="input-span"></span>
									</label>
								</spring:bind>
								<img id="coverOverview" class="img-responsive" src="<c:url value="${template.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
							</div>
							<div class="item-template" data-color="text" data-background="pane" style="color:${template.textColor};background:${template.paneColor};">
								<spring:bind path="params[1]">
									<label class="ui-checkbox ui-checkbox-segond font-small">
										<form:checkbox path="params[1]" /><span class="input-span"></span>
									</label>
								</spring:bind>
								<h3 class="h-header h-header3 text-center" style="padding:24px;"><spring:message code="txt.help.newsletter1.1" /></h3>
							</div>
							<div class="item-template">
								<spring:bind path="params[2]">
									<label class="ui-checkbox ui-checkbox-segond font-small disabled">
										<form:checkbox path="params[2]" /><span class="input-span"></span><spring:message code="lbl.requis" />
									</label>
								</spring:bind>
								<p class="text-center" style="padding:74px 24px;"><spring:message code="txt.help.newsletter1.2" /></p>
							</div>
							<div class="item-template" data-color="text" data-background="pane" style="color:${template.textColor};background:${template.paneColor};">
								<spring:bind path="params[3]">
									<label class="ui-checkbox ui-checkbox-segond font-small">
										<form:checkbox path="params[3]" /><span class="input-span"></span>
									</label>
								</spring:bind>
								<h4 id="titleOverview" class="h-header h-header4 text-center" style="padding-top:24px;${empty template.title ? 'display:none;' : ''}"><c:out value="${template.title}"/></h4>
								<div class="form-group text-center m-b-0" style="padding:20px 0;">
									<a id="labelOverview" class="font-small font-bold" data-color="pane" data-background="text" 
										style="display:inline-block;padding:8px 24px;border-radius:3px;color:${template.paneColor};background:${template.textColor};">
										<spring:message code="chose.button${template.label}" />
									</a>
								</div>
							</div>
						</div>
						<div class="template-overview m-auto">
							<div class="item-template">
								<spring:bind path="params[4]">
									<label class="ui-checkbox ui-checkbox-segond font-small">
										<form:checkbox path="params[4]" /><span class="input-span"></span>
									</label>
								</spring:bind>
								<h4 class="h-header h-header4 text-center" style="padding:24px;">
									<spring:message code="tool.newsletter.social" /> <c:out value="${currentCompany.tradename}"/></h4>
								<div class="form-group m-b-20">
									<ul class="navbar-nav nav-flex-icons navbar-linked">
										<c:set var="providers" value="facebook,twitter,google,linkedin" scope="page"></c:set>
										<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
											<c:set var="builder" value="${socials.buildSocial(pageScope.provider)}" scope="page"></c:set>
											<c:if test="${!empty pageScope.builder}">
												<li><a href="<c:url value="${pageScope.builder}" />" class="btn btn-linked btn-${pageScope.provider}" 
													target="_blank"><i class="cmsms-icon-${pageScope.provider}"></i></a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
							<hr>
							<div class="item-template font-mini i-help" style="line-height:14px;">
								<p id="descriptionOverview" style="${empty template.description ? 'display:none;' : ''}"><c:out value="${template.description}"/></p>
								<p class="m-t-10"><spring:message code="txt.mail.footer2.1" /></p>
								<p>&copy;<c:out value="${currYear}"/> <spring:message code="app.copyright" /></p>
							</div>
						</div>
					</div>
					<hr class="my-4">
					<p class="font-mini"><spring:message code="txt.help.newsletter1.3"/></p>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>