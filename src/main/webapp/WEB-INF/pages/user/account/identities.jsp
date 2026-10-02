<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/account/profile"/>" class="lien lien-black">
			<i class="cmsms-icon-user-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard2"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard2.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard2.3"/></h1>
		<p class="m-t-5">
			<spring:message code="txt.user.account2.3"/>
			<span class="pull-right">
				<span class="font-bold"><c:out value="${identities.countValidIdentities()}/5"/></span> 
				<span class="font-mini"><spring:message code="tool.validate"/></span>
			</span>
		</p>
		<span class="clearfix"></span>
	</div>
	<div class="page-container">
		<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
			<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.account3.1"/></h2>
			<input type="hidden" id="id" value="${identities.id}" />
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text"><spring:message code="txt.help.account3.1" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<c:set var="stateEmail" value="${identities.enabledEmail ? 1 : 2}" scope="page"></c:set>
					<table class="table table-page">
						<thead><tr><th style="width:124px;"></th><th style="width:calc(100% - 158px);"></th><th style="width:34px;"></th></tr></thead>
						<tbody class="font-small">
							<tr>
								<td class="font-bold i-validate${pageScope.stateEmail}">
									<i class="cmsms-icon-${identities.enabledEmail ? 'ok-circled-1' : 'attention-4'} m-r-5"></i>
									<spring:message code="tool.validate${pageScope.stateEmail}"/>
								</td>
								<td><c:out value="${identities.email}"/></td>
								<td class="btn-td">
									<a href="<c:url value="/user/account/login"/>" class="btn btn-table btn-yellow" 
										title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
								</td>
							</tr>
						</tbody>
					</table>
					<c:if test="${!identities.enabledEmail}"><span class="ind-text"><spring:message code="txt.help.account3.2.3" /></span></c:if>
				</div>
			</div>
			<div class="form-group row m-b-20">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="tabs.mobile" />
					<span class="help-text"><spring:message code="txt.help.account3.2" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<c:set var="statePhone" value="${identities.enabledPhone ? 1 : 2}" scope="page"></c:set>
					<table class="table table-page">
						<c:choose>
							<c:when test="${!empty identities.phone && !identities.enabledPhone}">
								<thead><tr><th style="width:124px;"></th><th style="width:calc(100% - 158px);"></th>
									<th style="width:34px;"></th><th style="width:34px;"></th></tr></thead>
							</c:when>
							<c:otherwise>
								<thead><tr><th style="width:124px;"></th><th style="width:calc(100% - 158px);"></th><th style="width:34px;"></th></tr></thead>
							</c:otherwise>
						</c:choose>
						<tbody class="font-small">
							<tr id="linePhone">
								<td class="font-bold i-validate${pageScope.statePhone}">
									<i class="cmsms-icon-${identities.enabledPhone ? 'ok-circled-1' : 'attention-4'} m-r-5"></i>
									<spring:message code="tool.validate${pageScope.statePhone}"/>
								</td>
								<td>
									<c:choose>
										<c:when test="${!empty identities.phone}"><c:out value="${identities.getFormattedPhone()}"/></c:when>
										<c:otherwise><c:out value="-" /></c:otherwise>
									</c:choose>
								</td>
								<td class="btn-td">
									<a href="<c:url value="/user/account/coordinates"/>" class="btn btn-table btn-yellow" 
										title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
								</td>
								<c:if test="${!empty identities.phone && !identities.enabledPhone}">
									<td class="btn-td">
										<div id="submitPhoneForm" class="form-submit">
											<a class="btn btn-table btn-green btn-submit" title="<spring:message code="btn.verify" />" 
												onclick="validatePhone();"><i class="cmsms-icon-shield"></i></a>
										</div>
									</td>	
								</c:if>
							</tr>
						</tbody>
					</table>
					<c:if test="${!identities.enabledPhone}">
						<span class="ind-text"><spring:message code="txt.help.account3.2.${empty identities.phone ? '1' : '2'}" /></span>
					</c:if>
				</div>
			</div>
			<hr class="my-4">
			<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.account3.2"/></h2>
			<div class="form-group row m-t-5">
				<label class="col-form-label col-md-4 col-lg-3">
					<span class="help-text"><spring:message code="txt.help.account3.3" /></span>
				</label>
			</div>
			<c:set var="providers" value="facebook,google,linkedin" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<span class="text-capitalize"><c:out value="${pageScope.provider}"/></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<c:set var="stateSocial" value="${!empty identities.social[state.count - 1] ? 1 : 2}" scope="page"></c:set>
						<table class="table table-page">
							<thead><tr><th style="width:124px;"></th><th style="width:calc(100% - 158px);"></th><th style="width:34px;"></th></tr></thead>
							<tbody class="font-small">
								<tr>
									<td class="font-bold i-validate${pageScope.stateSocial}">
										<i class="cmsms-icon-${!empty identities.social[state.count - 1] ? 'ok-circled-1' : 'attention-4'} m-r-5"></i>
										<spring:message code="tool.validate${pageScope.stateSocial}"/>
									</td>
									<td>
										<c:choose>
											<c:when test="${empty identities.social[state.count - 1]}">
												<spring:message code="txt.help.account3.4" arguments="${pageScope.provider}" />
											</c:when>
											<c:otherwise>
												<c:set var="identityProvider" value="${identities.social[state.count - 1]}" scope="page"></c:set>
												<div class="form-identity">
													<img src="<c:url value="${pageScope.identityProvider.imageurl}"/>" class="pull-left img-circle" 
														alt="<c:out value="${pageScope.identityProvider.displayname}" />">
													<div class="identity-brand">
														<p><c:out value="${pageScope.identityProvider.displayname}"/></p>
														<span class="font-mini i-help"><c:out value="${pageScope.identityProvider.usermail}"/></span>
													</div>
													<span class="clearfix"></span>
												</div>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="btn-td">
										<div id="submit${pageScope.provider}Form" class="form-submit">
											<c:choose>
												<c:when test="${empty identities.social[state.count - 1]}">
													<a class="btn btn-table btn-green btn-submit" title="<spring:message code="btn.partner2" />" 
														onclick="validateSocial('${pageScope.provider}');"><i class="cmsms-icon-login-2"></i></a>
												</c:when>
												<c:otherwise>
													<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.revoke" />" 
														onclick="revokeSocial('${pageScope.provider}');"><i class="cmsms-icon-off-1"></i></a>
												</c:otherwise>
											</c:choose>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</c:forEach>
			<div class="form-group row m-b-20">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4"><p class="font-mini"><spring:message code="txt.help.account3.5"/></p>
				</div>
			</div>
		</sec:authorize>
	</div>
</div>