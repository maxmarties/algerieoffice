<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/marketplace"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li><a href="<c:url value="/marketplace/actualites"/>" class="lien lien-black"><spring:message code="wizard.screen.navbar5"/></a></li>
		<li class="active"><c:out value="${inbox.title}"/></li>
	</ol>
</div>
<div class="screen-container ${!empty sponsoreScreen ? 'screen-aobubs' : ''}">
	<div class="container">
		<c:if test="${!empty sponsoreScreen}"><c:import url="/WEB-INF/fields/screen/screen_sponsore.jsp"/></c:if>
		<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.news"/></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_find.jsp"/>
	</div>
</div>
<c:set var="hrefBackword" value="/marketplace/actualites" scope="request"></c:set>
<c:set var="screenBackword" value="2" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_backword.jsp"/>
<div id="screenComments" class="screen-container screen-segond">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 m-t-10 m-b-10">
				<div class="screen-column card-actuality">
					<p class="ind-screen"><spring:message code="explorer.desktop.grid2" /></p>
					<h1 class="h-doc h-doc2"><c:out value="${inbox.title}"/></h1>
					<div class="widget-category font-small">
						<i class="cmsms-icon-calendar-7 i-segond m-r-10"></i><span class="i-help"><joda:format value="${inbox.actuDate}" pattern="dd MMM yyyy"></joda:format></span>
					</div>
					<div class="widget-body">
						<div class="widget-thumbnail"><img class="img-responsive" src="<c:url value="${inbox.photoURL}"/>" alt="<c:out value="${inbox.title}" />"></div>
						<div class="widget-descriptif"><c:out value="${inbox.description}"/></div>
						<p class="font-small i-help"><span class="font-bold"><spring:message code="tool.navigate.company"/></span>: <c:out value="${inbox.modifiedDate}" /></p>
						<div class="widget-about-post m-t-20">
							<ul class="navbar-nav nav-flex-icons">
								<li class="link-autor">
									<img src="<c:url value="${inbox.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${inbox.tradename}" />">
									<span class="autor-brand"><a href="<c:url value="${inbox.companyURL}"/>" class="lien lien-black"><c:out value="${inbox.tradename}" /></a></span>
									<span class="clearfix"></span>
								</li>
								<li class="link-comment h-header">
									<i class="cmsms-icon-comment i-primary m-r-10"></i><span id="iCommentActu"><c:out value="${inbox.commentCount}" /></span>
								</li>
								<li>
									<a id="iLikeActu" class="link-like transition-35 ${hasLiked ? 'active' : ''}">
										<i class="cmsms-icon-thumbs-up-2 i-select m-r-10"></i><spring:message code="tool.view.like"/>
										<span class="iLikeCount animated m-l-10"><c:out value="${inbox.likeCount}" /></span>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="explorer-auth-hr text-center m-t-20 m-b-20"><span class="line"></span><span class="h-header"><spring:message code="tool.view.comment"/></span></div>
				<sec:authorize access="isAnonymous()">
					<a id="iLoginActu" class="btn btn-primary btn-flat btn-big btn-block"><span><i class="cmsms-icon-lock-5 m-r-10"></i><spring:message code="tool.view.comments"/></span></a>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
					<div class="screen-column card-comments">
						<div class="screen-viewload m-b-20" style="display:none;">
							<div id="submitLoadForm" class="form-submit">
								<a id="submitLoad" class="lien lien-segond lien-underline lien-small btn-submit">
									<i class="cmsms-icon-angle-up m-r-10"></i><spring:message code="tool.navigate.comments"/></a>
							</div>
						</div>
						<div class="screen-loader font-small">
							<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
							<ul class="list-none list-block comments-list"></ul>
						</div>
						<hr class="my-4">
						<div class="card-form">
							<img src="<c:url value="${currentUser.getIconUrl()}"/>" class="img-circle pull-left" alt="<spring:message code="tooltip.avatar" />">
							<form:form name="commentForm" action="/" method="POST" modelAttribute="comment" enctype="utf8" novalidate="novalidate">
								<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
								<spring:bind path="actualityId"><form:input type="hidden" path="actualityId" /></spring:bind>
								<div class="card-brand">
									<spring:bind path="message">
										<div id="messageForm" class="form-group">
											<c:set var="faholder" scope="page"><spring:message code="lbl.comment" /></c:set>
											<form:textarea class="form-control form-area" rows="4" path="message" placeholder="${pageScope.faholder}" />
											<span class="error"></span>
										</div>
									</spring:bind>
									<div id="submitForm" class="form-submit">
										<button type="submit" class="btn btn-primary btn-submit btn-fixed"><span><spring:message code="btn.comment"/></span></button>
									</div>
								</div>
							</form:form>
							<span class="clearfix"></span>
						</div>
					</div>
				</sec:authorize>
			</div>
			<div class="col-lg-4 m-t-10 m-b-10 hidden-md-down">
				<div class="screen-sticky"><c:import url="/WEB-INF/fields/screen/screen_newsletter.jsp"/></div>
			</div>
		</div>
	</div>
</div>