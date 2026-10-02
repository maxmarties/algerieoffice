<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/membres"/>" class="lien lien-black"><spring:message code="wizard.screen.navbar6"/></a></li>
		<li class="active"><c:out value="${profile.username}"/></li>
	</ol>
</div>
<div class="screen-container screen-mini">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="subheader.screen.account3"/></h1></div>
	</div>
</div>
<div class="screen-navnews">
	<div class="container">
		<div class="form-group text-center m-b-0">
			<ul class="list-none list-inline list-screenletter">
				<c:forEach var="urlLetter" items="${urlLetters}" varStatus="state">
					<li><a href="<c:url value="/membres?letter=${urlLetter}"/>" class="btn btn-letter btn-simple"><spring:message code="chose.letter${state.count}"/></a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<c:set var="hrefBackword" value="/membres" scope="request"></c:set>
<c:set var="screenBackword" value="1" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_backword.jsp"/>
<div class="screen-container screen-segond">
	<div class="container">
		<div class="screen-column widget-box">
			<c:if test="${!empty profile.tradename}"><span class="ind-pro text-center btn-warning"><spring:message code="tool.ind.pro" /></span></c:if>
			<div class="widget-account">
				<div class="img-circle img-container pull-left">
					<img src="<c:url value="${profile.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${profile.username}" />">
				</div>
				<div class="account-brand">
					<h2 class="h-header h-header3 i-header inline-block">
						<c:if test="${!empty profile.sexe}"><spring:message code="lbl.sub.sexe${profile.sexe}"/>. </c:if><c:out value="${profile.username}" />
						<i class="cmsms-icon-lock i-locked m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.memberlock" />" 
							style="${hasLocked ? '' : 'display:none;'}"></i>
					</h2><c:if test="${profile.hasCompleted}"><i class="cmsms-icon-feather i-dollar m-l-10"></i></c:if>
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
					<div class="row m-t-10">
						<div class="col-md-6 col-lg-8">
							<p class="m-t-10 m-b-10">
								<c:choose>
									<c:when test="${!empty profile.tradename}">
										<span class="font-bold"><spring:message code="wizard.screen.autor5" /></span>: 
										<c:choose>
											<c:when test="${!empty profile.companyURL}">
												<a href="<c:url value="${profile.companyURL}" />" class="lien lien-black lien-underline"><c:out value="${profile.tradename}" /></a>
											</c:when>
											<c:otherwise><c:out value="${profile.tradename}" /></c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<span class="font-bold"><spring:message code="lbl.sub.account4.6" /></span> : <spring:message code="lbl.sub.pro2" />
									</c:otherwise>
								</c:choose>
							</p>
						</div>
						<div class="col-md-6 col-lg-4">
							<c:if test="${!hasLeader}">
								<ul class="navbar-nav nav-flex-icons">
									<li class="ml-auto">
										<a class="btn btn-primary btn-simple btn-fixed iMessage" data-avatar="${profile.urlAvatar}" data-name="${profile.username}" 
											data-login="${profile.hasOnline}"><span><spring:message code="tool.navigate.membre2"/></span></a>
									</li>
									<li id="submitedFavoriteForm" class="form-submit m-l-5">
										<a class="btn btn-segond btn-flat-favorite btn-simple btn-submit iFavorite ${hasFavorite ? 'active' : ''}" 
											title="<spring:message code="tool.navigate.membre1"/>"><span><i class="cmsms-icon-star-1"></i></span></a>
									</li>
									<li class="dropdown brand-menu brand-fixed m-l-5">
										<a class="btn btn-dropdown btn-flat-favorite btn-simple" data-toggle="dropdown"><span><i class="cmsms-icon-ellipsis-vert"></i></span></a>
										<ul class="dropdown-menu dropdown-menu-right animated slideInY" role="menu">
											<li><a class="dropdown-item iLock ${hasLocked ? 'disabled' : ''}"><spring:message code="explorer.popup.home3.1"/></a></li>
											<li><a class="dropdown-item iRate"><spring:message code="explorer.popup.home3.2"/></a></li>
										</ul>
									</li>
								</ul>
							</c:if>
						</div>
					</div>
					<hr class="my-1">
					<div class="row">
						<div class="col-sm-6 m-t-5">
							<p class="font-small font-bold"><spring:message code="lbl.sub.account2"/>: 
								<span class="i-help"><joda:format value="${profile.createDate}" pattern="MMMM yyyy"></joda:format></span></p>
						</div>
						<div class="col-sm-6 m-t-5">
							<p class="text-right">
								<c:if test="${profile.hasOnline}">
									<i class="cmsms-icon-record i-green m-r-5"></i><span class="font-bold i-primary"><spring:message code="tabs.published" /></span> - 
								</c:if>
								<span class="i-help"><spring:message code="lbl.sub.account3"/> 
								<span id="momentLogin" style="display:none;"><joda:format value="${profile.loginDate}" pattern="ddMMyyyy HH:mm:ss"></joda:format></span></span>
							</p>
						</div>
					</div>
				</div>
				<span class="clearfix"></span>
			</div>
		</div>
		<div class="row row-mini">
			<div class="col-md-6 col-lg-8 col-mini m-t-10">
				<div class="screen-column h-100">
					<div class="widget-subtitle"><h3 class="h-doc h-doc3"><spring:message code="subheader.account1.1"/></h3></div>
					<div class="widget-body">
						<div class="widget-parag font-small">
							<c:choose>
								<c:when test="${!empty profile.biography}"><c:out value="${profile.biography}" /></c:when>
								<c:otherwise><span class="help-text"><spring:message code="txt.help.account1.6" /></span></c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-lg-4 col-mini m-t-10">
				<div class="screen-column h-100">
					<div class="widget-subtitle"><h3 class="h-doc h-doc3"><spring:message code="subheader.account1.2"/></h3></div>
					<div class="widget-body">
						<div class="widget-parag">
							<ul class="list-none list-block list-count-favorite">
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
				</div>
			</div>
		</div>
		<div class="row row-mini">
			<div class="col-md-8 col-mini m-t-10">
				<div class="screen-column h-100">
					<div class="widget-subtitle"><h3 class="h-doc h-doc3"><spring:message code="subheader.account1.3"/></h3></div>
					<div class="widget-body">
						<div class="widget-parag">
							<ul class="navbar-nav nav-flex-icons m-t-20">
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
							<p class="font-small m-t-30 m-b-10">
								<span class="font-bold"><c:out value="${profile.username}" /></span> 
								<spring:message code="txt.help.account1.3" arguments="${profile.averageActivities()}" />
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-mini m-t-10">
				<div class="screen-column h-100">
					<div class="widget-subtitle"><h3 class="h-doc h-doc3">
						<spring:message code="sidebar.admin.dashboard2.3"/> (<c:out value="${profile.countVerified()}/5"/>)
					</h3></div>
					<div class="widget-body">
						<div class="widget-parag">
							<div class="table-responsive">
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
				</div>
			</div>
		</div>
		<c:if test="${profile.hasPresentContact()}">
			<div class="screen-column m-t-10">
				<div class="widget-subtitle"><h3 class="h-doc h-doc3"><spring:message code="subheader.agent2"/></h3></div>
				<div class="widget-body">
					<div class="widget-parag">
						<div class="table-responsive m-b-10">
							<table class="table table-widget table-empty">
								<thead><tr><th style="width:140px;"></th><th style="width:calc(100-140px);"></th></tr></thead>
								<tbody class="font-small">
									<c:if test="${!empty profile.phone}">
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
					</div>
				</div>
			</div>
		</c:if>
	</div>
</div>
<c:import url="/WEB-INF/fields/blog/blog_marketplace.jsp"/>