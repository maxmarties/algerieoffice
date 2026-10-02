<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/account/profile"/>" class="lien lien-black">
			<i class="cmsms-icon-user-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard2"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard2.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard2.1"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.account2.1"/></p>
	</div>
	<div class="page-container">
		<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.account1"/></h2>
		<div class="row m-t-10">
			<label class="col-form-label col-lg-3 m-t-10">
				<spring:message code="lbl.sub.account1" />
				<span class="help-text"><spring:message code="txt.help.account1.1" /></span>
			</label>
			<div class="col-lg-9 m-t-10">
				<div class="widget-box">
					<c:if test="${profile.hasPro}"><span class="ind-pro text-center btn-warning"><spring:message code="tool.ind.pro" /></span></c:if>
					<div class="widget-account">
						<a href="<c:url value="/user/account/login"/>" class="pull-left img-circle img-container avatar-view" title="<spring:message code="btn.edit" />">
							<img src="<c:url value="${profile.urlAvatar}"/>" class="img-circle img-responsive transition-35" alt="<c:out value="${profile.username}" />">
						</a>
						<div class="account-brand">
							<h3 class="h-header h-header3 i-header inline-block">
								<c:if test="${!empty profile.sexe}"><spring:message code="lbl.sub.sexe${profile.sexe}"/>. </c:if><c:out value="${profile.username}" />
							</h3><c:if test="${profile.hasCompleted}"><i class="cmsms-icon-feather i-dollar m-l-10"></i></c:if>
							<p class="font-small i-help"><c:out value="${profile.function}" /></p>
							<p class="font-small m-t-10">
								<i class="cmsms-icon-location-1 i-red m-r-10"></i>
								<c:choose>
									<c:when test="${!empty profile.address}">
										<c:out value="${profile.address}" /> <span class="text-uppercase"><spring:message code="chose.wilaya${profile.wilaya}"/></span>
									</c:when>
									<c:otherwise><spring:message code="tool.explorer.location"/></c:otherwise>
								</c:choose>
							</p>
							<ul class="navbar-nav nav-flex-icons m-t-10">
								<li class="font-small font-bold">
									<spring:message code="lbl.sub.account2"/>: 
									<span class="i-help"><joda:format value="${profile.createDate}" pattern="MMMM yyyy"></joda:format></span>
								</li>
								<li class="ml-auto">
									<a href="<c:url value="/user/account/coordinates"/>" class="btn btn-table btn-yellow" 
										title="<spring:message code="btn.account.coordinate" />"><i class="cmsms-icon-pencil-5"></i></a>
								</li>
								<c:if test="${!empty profile.pseudoURL}">
									<a href="<c:url value="${profile.pseudoURL}" />" class="btn btn-table btn-green" target="_blank" 
										title="<spring:message code="btn.account.preview" />"><i class="cmsms-icon-paper-plane-3"></i></a>
								</c:if>
							</ul>
						</div>
						<span class="clearfix"></span>
					</div>
					<hr class="my-2">
					<div class="row m-t-20">
						<div class="col-sm-6 col-md-8 m-b-20">
							<h4 class="h-header h-header4 i-primary">
								<spring:message code="subheader.account1.1"/>
								<a href="<c:url value="/user/account/coordinates"/>" class="btn btn-table btn-yellow pull-right" 
									title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
								<span class="clearfix"></span>
							</h4>
							<p class="font small m-t-20">
								<c:choose>
									<c:when test="${!empty profile.biography}"><c:out value="${profile.biography}" /></c:when>
									<c:otherwise><span class="help-text"><spring:message code="txt.help.account1.2" /></span></c:otherwise>
								</c:choose>
							</p>
						</div>
						<div class="col-sm-6 col-md-4 m-b-20">
							<h4 class="h-header h-header4 i-primary">
								<spring:message code="subheader.account1.2"/>
								<a href="<c:url value="/user/globe/companies"/>" class="btn btn-table btn-green pull-right" 
									title="<spring:message code="btn.view" />"><i class="cmsms-icon-paper-plane-3"></i></a>
								<span class="clearfix"></span>
							</h4>
							<ul class="list-none list-block list-count-favorite m-t-20">
								<c:set var="providers" value="building-filled,user-2" scope="page"></c:set>
								<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
									<li>
										<i class="cmsms-icon-${pageScope.provider} i-primary m-r-10"></i><spring:message code="subheader.globe${state.count}" />
										<span class="h-header font-small i-help m-l-10"><c:out value="${profile.getCountFavorite(state.count)}" /></span>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<hr class="my-2">
					<div class="row m-t-20">
						<div class="col-md-8 m-b-20">
							<h4 class="h-header h-header4 i-primary">
								<spring:message code="subheader.account1.3"/>
								<a href="<c:url value="/user/dashboard"/>" class="btn btn-table btn-green pull-right" 
									title="<spring:message code="btn.viewmore" />"><i class="cmsms-icon-paper-plane-3"></i></a>
								<span class="clearfix"></span>
							</h4>
							<ul class="navbar-nav nav-flex-icons m-t-40">
								<c:forEach var="activity" items="${profile.activities}" varStatus="state">
									<div id="circleActivity${state.count}" class="circle-3d circle-activity m-auto" data-counter="${activity}">
										<div class="circle-content text-center">
											<span class="h-header"><c:out value="${activity}%" /></span>
											<span class="help-text"><spring:message code="lbl.sub.account3.${state.count}" /></span>
										</div>
										<div id="circleCanvas${state.count}" class="circle-canvas"></div>
									</div>
								</c:forEach>
							</ul>
							<p class="font-small m-t-30">
								<span class="font-bold"><c:out value="${profile.username}" /></span> 
								<spring:message code="txt.help.account1.3" arguments="${profile.averageActivities()}" />
							</p>
						</div>
						<div class="col-md-4 m-b-20">
							<h4 class="h-header h-header4 i-primary">
								<spring:message code="sidebar.admin.dashboard2.3"/> (<c:out value="${profile.countVerified()}/5"/>)
								<a href="<c:url value="/user/account/identities"/>" class="btn btn-table btn-yellow pull-right" 
									title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
								<span class="clearfix"></span>
							</h4>
							<div class="table-responsive m-t-20">
								<table class="table table-page table-verified">
									<thead><tr><th style="width:calc(100% - 34px);"></th><th style="width:34px;"></th></tr></thead>
									<tbody class="font-small">
										<c:forEach var="i" begin="1" end="5" step="1">
											<tr>
												<td><spring:message code="lbl.sub.account4.${i}"/></td>
												<td class="text-center">
													<i class="cmsms-icon-${profile.verifieds[i - 1] ? 'ok-circled-1 i-green' : 'attention-4 i-red'}"></i>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:if test="${profile.hasPresentContact()}">
						<hr class="my-2">
						<h4 class="h-header h-header4 i-primary">
							<spring:message code="subheader.agent2"/>
							<a href="<c:url value="/user/account/coordinates"/>" class="btn btn-table btn-yellow pull-right" 
								title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
							<span class="clearfix"></span>
						</h4>
						<div class="table-responsive m-t-20">
							<table class="table table-page table-empty">
								<thead><tr><th style="width:140px;"></th><th style="width:calc(100-140px);"></th></tr></thead>
								<tbody class="font-small">
									<c:if test="${profile.hasPhone}">
										<tr>
											<td><spring:message code="tabs.phone"/></td>
											<td><a href="tel:+213${profile.phone}" class="lien lien-table"><c:out value="${profile.parsePhone()}" /></a></td>
										</tr>
									</c:if>
									<c:if test="${!empty profile.website}">
										<tr>
											<td><spring:message code="tabs.website"/></td>
											<td><a href="<c:url value="${profile.website}"/>" class="lien lien-table"><c:out value="${profile.website}" /></a></td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<hr class="my-2">
		<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.account2"/></h2>
		<div class="form-group row">
			<label class="col-form-label col-md-4 col-lg-3">
				<spring:message code="lbl.email" />
				<span class="help-text"><spring:message code="txt.help.account1.4" /></span>
			</label>
			<div class="col-md-8 col-lg-9">
				<table class="table table-page">
					<thead><tr><th style="width:calc(100% - 34px);"></th><th style="width:34px;"></th></tr></thead>
					<tbody class="font-small">
						<tr>
							<td><c:out value="${profile.email}"/></td>
							<td class="btn-td">
								<a href="<c:url value="/user/account/login"/>" class="btn btn-table btn-yellow" 
									title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
							</td>
						</tr>
					</tbody>
				</table>
				<span class="help-text m-t-5"><i class="cmsms-icon-info-circled-3 i-blue m-r-10"></i><spring:message code="txt.help.account1.7" /></span>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-form-label col-md-4 col-lg-3">
				<spring:message code="lbl.sub.account5" />
				<span class="help-text"><spring:message code="txt.help.account1.5" /></span>
			</label>
			<div class="col-md-8 col-lg-9">
				<table class="table table-page">
					<thead><tr><th style="width:calc(100% - 68px);"></th><th style="width:34px;"></th><th style="width:34px;"></th></tr></thead>
					<tbody class="font-small">
						<tr>
							<td><input class="form-control disaload" type="text" id="pseudoProfile" value="${profile.getMapsiteProfileURL()}" disabled /></td>
							<td class="btn-td"><a id="pseudoCopy" class="btn btn-table btn-green" title="<spring:message code="btn.copy" />"><i class="cmsms-icon-clipboard"></i></a></td>
							<td class="btn-td"><a href="<c:url value="/user/account/login"/>" class="btn btn-table btn-yellow"
								title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-form-label col-md-4 col-lg-3"><spring:message code="lbl.sub.account6" /></label>
			<div class="col-md-4 col-lg-3">
				<c:set var="chosers" value="fr,en,ar" scope="page"></c:set>
				<c:set var="providers" value="Français,English,العربية" scope="page"></c:set>
				<select class="form-select2-simple" id="langProfile">
					<c:forEach var="choser" items="${pageScope.chosers}" varStatus="state">
						<option value="${pageScope.choser}" ${langage.lang == pageScope.choser ? 'selected' : ''}><c:out value="${pageScope.providers.split(',')[state.count - 1]}"/></option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group row m-t-10 m-b-20">
			<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
			<div class="col-md-8 col-lg-9">
				<p class="font-mini m-t-10">
					<spring:message code="txt.user.account1"/> 
					<a href="<c:url value="/contacts"/>" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.contact"/></a>
				</p>
				<hr class="my-4">
				<div class="m-t-20">
					<a href="<c:url value="${profile.pseudoURL}" />" class="btn btn-primary btn-add btn-left ${empty profile.pseudoURL ? 'disabled' : ''}" target="_blank">
						<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="lbl.sub.account6.1"/></span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>