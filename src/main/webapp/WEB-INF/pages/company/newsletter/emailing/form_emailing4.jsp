<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">4. <spring:message code="wizard.newsletter.emailing4"/></h2>
<div class="row m-t-20">
	<div class="col-md-6 m-b-20">
		<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.marketplace3.1"/></h3>
		<p class="font-small i-help m-t-10">
			<span class="font-bold countContacts"></span> <spring:message code="tool.newsletter.budget1.4" /> = 
			<span class="font-bold countContacts"></span> <spring:message code="tool.newsletter.budget1.1" /> + 
			<span class="font-bold countNewsletters"></span> <spring:message code="tool.newsletter.budget1.2" />
		</p>
		<hr class="my-4">
		<h3 class="h-header h-header4 i-primary m-t-20"><spring:message code="txt.help.newsletter4.2"/></h3>
		<div id="budgetForm" class="form-group">
			<div class="row row-mini m-b-10">
				<c:set var="providers" value="at-3,mail-6" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<div class="col-sm-6 col-mini m-t-10">
						<div class="card-analytic card-refering card-left h-100">
							<i class="cmsms-icon-${pageScope.provider} card-trigger pull-left"></i>
							<div class="card-brand text-right">
								<span class="text-truncate font-small i-help"><spring:message code="tool.newsletter.budget1.${state.count}" /></span>
								<p class="h-header ignorError ${budget.hasPresentBuget(state.count) ? 'i-green' : 'i-primary'}"><c:out value="${budget.getFormattedBudget(state.count)}"/></p>
							</div>
							<span class="clearfix"></span>
							<div class="card-footer m-t-5">
								<p class="font-small"><small class="min"><spring:message code="lbl.requis" /></small><span class="pull-right i-red ${state.count == 1 ? 'countContacts' : 'countNewsletters'}"></span></p>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<span class="error"></span>
		</div>
		<span class="help-text m-t-10"><spring:message code="txt.help.newsletter4.4" /></span>
		<hr class="m-t-20 m-b-20">
		<spring:bind path="hasResend">
			<label class="ui-checkbox ui-checkbox-segond font-small">
				<form:checkbox path="hasResend" />
				<span class="input-span"></span><spring:message code="comp.emailing" />
			</label>
		</spring:bind>
		<span class="help-text m-l-20"><spring:message code="txt.help.newsletter4.6" />: <strong><c:out value="${currentUser.user.email}"/></strong></span>
	</div>
	<div class="col-md-6 m-b-20">
		<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="txt.help.newsletter4.3"/></h3>
		<div class="bn-overview bn-body m-t-20" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
			<div class="template-overview m-auto">
				<div class="popup-widget" style="padding-bottom:10px;">
					<img src="<c:url value="${currentCompany.iconurl}"/>" class="img-circle pull-left" alt="<c:out value="${currentCompany.tradename}"/>">
					<div class="brand-header" style="padding-top:5px;">
						<span class="font-big i-primary"><c:out value="${currentCompany.tradename}" /></span>
						<span class="help-text"><i class="cmsms-icon-at-3 i-red m-r-5"></i><c:out value="${currentCompany.companymail}" /></span>
					</div>
					<span class="clearfix"></span>
				</div>
			</div>
			<div class="bn-box template-overview m-auto">
				<c:if test="${!empty template.urlCover}">
					<div class="item-template"><img class="img-responsive" src="<c:url value="${template.urlCover}"/>" alt="<spring:message code="tooltip.avatar" />"></div>
				</c:if>
				<c:if test="${template.params[1]}">
					<div class="item-template" style="color:${template.textColor};background:${template.paneColor};">
						<h3 id="subjectOverview" class="h-header h-header3 text-center" style="padding:24px;"></h3>
					</div>
				</c:if>
				<div id="templateForm" class="item-template">
					<div class="campaign-load">
						<c:import url="/WEB-INF/basics/loading_span.jsp"/>
						<div id="templateLoad" style="padding:20px 30px;"></div>
					</div>
				</div>
				<c:if test="${template.params[3]}">
					<div class="item-template" style="color:${template.textColor};background:${template.paneColor};">
						<c:if test="${!empty template.title}"><h4 class="h-header h-header4 text-center" style="padding-top:24px;"><c:out value="${template.title}"/></h4></c:if>
						<div class="form-group text-center m-b-0" style="padding:20px 0;">
							<a class="font-small font-bold" style="display:inline-block;padding:8px 24px;border-radius:3px;color:${template.paneColor};background:${template.textColor};">
								<spring:message code="chose.button${template.label == 1 ? 2 : template.label}" />
							</a>
						</div>
					</div>
				</c:if>
			</div>
			<div class="template-overview m-auto">
				<c:if test="${!empty template.socials && template.socials.hasPresent()}">
					<div class="item-template">
						<h4 class="h-header h-header4 text-center" style="padding:24px;"><spring:message code="tool.newsletter.social" /> <c:out value="${currentCompany.tradename}"/></h4>
						<div class="form-group m-b-20">
							<ul class="navbar-nav nav-flex-icons navbar-linked">
								<c:set var="providers" value="facebook,twitter,google,linkedin" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<c:set var="builder" value="${template.socials.buildSocial(pageScope.provider)}" scope="page"></c:set>
									<c:if test="${!empty pageScope.builder}">
										<li><a href="<c:url value="${pageScope.builder}" />" class="btn btn-linked btn-${pageScope.provider}" 
											target="_blank"><i class="cmsms-icon-${pageScope.provider}"></i></a></li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<hr>
				<div class="item-template font-mini i-help" style="line-height:14px;">
					<c:if test="${!empty template.description}"><p><c:out value="${template.description}"/></p></c:if>
					<p class="m-t-10"><spring:message code="txt.mail.footer2.1" /></p>
					<p>&copy;<c:out value="${currYear}"/> <spring:message code="app.copyright" /></p>
				</div>
			</div>
		</div>
		<hr class="my-4">
		<p class="font-mini"><spring:message code="txt.help.newsletter4.5"/></p>
	</div>
</div>