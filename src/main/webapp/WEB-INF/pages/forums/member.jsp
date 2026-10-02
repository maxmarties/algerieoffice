<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black" title="<spring:message code="tooltip.forums.menu" />"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/forums"/>" class="lien lien-black"><spring:message code="sidebar.home.mainfooter1.12"/></a></li>
		<li class="active"><c:out value="${member.username}"/></li>
	</ol>
</div>
<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
	<div class="container">
		<div class="widget-account m-t-20">
			<div class="img-circle img-container pull-left"><img src="<c:url value="${member.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${member.username}" />"></div>
			<div class="account-brand">
				<h2 class="h-header h-header3 i-primary"><c:out value="${member.username}" /></h2>
				<p class="font-small i-help">
					<c:choose>
						<c:when test="${!empty member.companyURL}"><a href="${member.companyURL}" class="lien lien-black lien-underline" target="_blank"><c:out value="${member.tradename}"/></a></c:when>
						<c:otherwise><c:out value="${member.tradename}"/></c:otherwise>
					</c:choose>
				</p>
				<div class="row">
					<div class="col-md-6 col-lg-8 m-t-10">
						<p class="font-small m-t-10">
							<i class="cmsms-icon-globe-1 i-blue m-r-5"></i><a href="<c:url value="${member.userURL}" />" 
								class="lien lien-primary lien-underline" target="_blank"><c:out value="${member.userURL}" /></a>
						</p>
					</div>
					<div class="col-md-6 col-lg-4 m-t-10 text-right">
						<c:if test="${currentUser.getUserId() != member.userId}">
							<a class="btn btn-file btn-simple iMessage" data-avatar="${member.urlAvatar}" data-name="${member.username}" style="min-width:180px;">
								<span><spring:message code="tool.navigate.membre2"/></span></a>
						</c:if>
					</div>
				</div>
				<hr class="my-1">
				<div class="row">
					<div class="col-sm-6 m-b-5">
						<p class="font-small font-bold"><spring:message code="lbl.sub.account2"/>: 
							<span class="i-help"><joda:format value="${member.createDate}" pattern="MMMM yyyy"></joda:format></span></p>
					</div>
					<div class="col-sm-6 m-b-5">
						<p class="text-right">
							<span class="i-help"><spring:message code="lbl.sub.account3"/> 
							<span id="momentLogin" style="display:none;"><joda:format value="${member.loginDate}" pattern="ddMMyyyy HH:mm:ss"></joda:format></span></span>
						</p>
					</div>
				</div>
			</div>
			<span class="clearfix"></span>
		</div>
		<div class="forums-container m-t-10">
			<div class="table-responsive">
				<table id="tableList" class="table table-forums">
					<thead>
						<tr>
							<th class="sorter-false" style="width:50px;"><spring:message code="tabs.illustr" /></th>
							<th class="th-title" style="width:calc(100% - 670px);"><spring:message code="tabs.topic" /></th>
							<th class="sorter-false" style="width:210px;"></th>
							<th class="th-icon" style="width:80px;"><i class="cmsms-icon-comment-3"></i></th>
							<th class="th-icon" style="width:80px;"><i class="cmsms-icon-eye-3"></i></th>
							<th class="th-icon" style="width:160px;"><i class="cmsms-icon-time"></i></th>
							<th class="sorter-false" style="width:90px;"></th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${topics.isEmpty()}"><tr class="empty-tr font-small"><td colspan="7"><spring:message code="tool.empty.table" /></td></tr></c:when>
							<c:otherwise>
								<c:forEach var="topic" items="${topics}" varStatus="state">
									<tr>
										<td><a href="<c:url value="/forums/topic/${topic.id}" />" class="ind-language ind-${topic.language}"><c:out value="${topic.language}" /></a></td>
										<td class="td-brand result-td">
											<img src="<c:url value="${topic.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${topic.username}"/>">
											<div class="brand-colspan">
												<a href="<c:url value="/forums/topic/${topic.id}" />" class="lien lien-primary sh-black">
													<c:out value="${topic.title}" />
												</a>
												<c:if test="${topic.quiz}"><i class="cmsms-icon-mic-2 i-green m-l-10"></i></c:if>
												<span class="help-text">
													<a href="<c:url value="/forums?category=${topic.category}" />" class="tag-topic tag-topic${topic.category} transition-color">
														<spring:message code="chose.topic.category${topic.category}"/>
													</a>
												</span>
											</div>
											<span class="clearfix"></span>
										</td>
										<td class="td-avatars">
											<c:if test="${!topic.usersAvatar.isEmpty()}">
												<ul class="navbar-nav nav-flex-icons">
													<c:forEach var="userAvatar" items="${topic.usersAvatar}" varStatus="subState">
														<li class="${subState.count == 1 ? 'ml-auto' : ''}"><img src="<c:url value="${userAvatar}"/>" class="img-circle"></li>
													</c:forEach>
												</ul>
											</c:if>
										</td>
										<td class="h-header font-small text-center ${topic.commentCount == 0 ? 'i-help' : topic.commentCount > 100 ? 'i-red' : ''}">
											<c:out value="${topic.parseCommentCount()}"/></td>
										<td class="h-header font-small text-center ${topic.viewCount == 0 ? 'i-help' : topic.viewCount > 100 ? 'i-blue' : ''}">
											<c:out value="${topic.parseViewCount()}"/></td>
										<td class="font-mini font-bold text-center i-help"><joda:format value="${topic.createdDate}" pattern="dd MMMM, HH:mm"></joda:format></td>
										<td><c:if test="${topic.marked}"><i class="cmsms-icon-flag i-yellow sh-black"></i></c:if></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</sec:authorize>