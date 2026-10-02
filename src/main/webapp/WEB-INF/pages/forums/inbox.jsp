<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black" title="<spring:message code="tooltip.forums.menu" />"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/forums"/>" class="lien lien-black"><spring:message code="sidebar.home.mainfooter1.12"/></a></li>
		<li class="active"><c:out value="${inbox.title}"/></li>
	</ol>
</div>
<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
	<div class="container">
		<div class="row m-t-10">
			<div class="col-lg-9 m-t-10 m-b-10">
				<div id="cardTopic" class="card-topic" data-id="${inbox.id}">
					<h1 class="h-doc h-doc2"><c:out value="${inbox.title}"/></h1>
					<div class="widget-category">
						<a href="<c:url value="/forums?category=${inbox.category}" />" class="tag-topic tag-topic${inbox.category} transition-color">
							<spring:message code="chose.topic.category${inbox.category}"/>
						</a>
					</div>
					<hr class="m-t-10 m-b-20">
					<div class="topic-inbox">
						<img src="<c:url value="${inbox.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${inbox.username}" />">
						<ul class="navbar-nav pull-right">
							<li class="dropdown brand-menu brand-fixed">
								<a class="btn btn-dropdown btn-flat-favorite btn-simple" data-toggle="dropdown"><span><i class="cmsms-icon-ellipsis-vert"></i></span></a>
								<ul class="dropdown-menu dropdown-menu-right animated slideInY" role="menu">
									<li><a class="dropdown-item iMarkeTopic">
										<i class="cmsms-icon-flag i-12 iFlag ${inbox.marked ? '' : 'invisible'}"></i><spring:message code="explorer.popup.topic1"/></a></li>
									<li class="dropdown-divider"></li>
									<li><a class="dropdown-item iRateTopic"><i class="i-12"></i><spring:message code="explorer.popup.topic2"/></a></li>
								</ul>
							</li>
						</ul>
						<div class="topic-brand">
							<a href="<c:url value="/forums/user/${inbox.autorId}"/>" class="lien lien-primary font-bold"><c:out value="${inbox.username}" /></a>
							<span class="help-text">
								<joda:format value="${inbox.createdDate}" pattern="dd MMMM, HH:mm"></joda:format>
								<i class="cmsms-icon-flag i-yellow m-l-5 iFlag ${inbox.marked ? '' : 'invisible'}"></i>
							</span>
						</div>
						<div class="clearfix"></div>
						<div class="widget-detail">
							<div class="fr-view fr-explorer"><c:out value="${inbox.detail}" escapeXml="false" /></div>
							<c:if test="${!empty inbox.modifiedDate}">
								<hr class="my-1">
								<p class="font-small"><span class="font-bold i-primary"><spring:message code="tool.navigate.company"/> : </span>
									<span class="i-help"><c:out value="${inbox.modifiedDate}"/></span></p>
							</c:if>
							<c:if test="${!empty quiz}">
								<div class="topic-quiz m-t-20">
									<div class="row row-mini">
										<div class="col-md-6 col-mini m-b-10">
											<div class="form-group m-b-0">
												<h3 class="h-header h-header5 m-b-10"><spring:message code="txt.help.topic2.1"/></h3>
												<c:forEach var="proposal" items="${quiz.proposals}" varStatus="state">
													<c:set var="proposalPersent" value="${quiz.getPesrsentProposal(state.count)}" scope="page"></c:set>
													<div class="item-quiz">
														<div class="row">
															<div class="col-6"><p id="countQuiz${state.count}" class="font-small i-help"><c:out value="${quiz.getFormattedCount(state.count)}"/></p></div>
															<div class="col-6 text-right"><p id="persentQuiz${state.count}" class="h-header font-big i-segond1"><c:out value="${pageScope.proposalPersent}%"/></p></div>
														</div>
														<div class="progress">
															<div id="progressQuiz${state.count}" class="progress-bar animated onne progress-animated" style="width:${pageScope.proposalPersent}%" role="progressbar"></div>
														</div>
													</div>
												</c:forEach>
												<hr class="my-1">
												<p><span id="countAllQuiz" class="h-header i-primary m-r-10"><c:out value="${quiz.getFormattedSumCount()}"/></span><spring:message code="txt.help.topic2.3"/></p>
											</div>
										</div>
										<div class="col-md-6 col-mini m-b-10">
											<div id="voteForm" class="form-group m-b-0">
												<h3 class="h-header h-header5 m-b-10"><spring:message code="txt.help.topic2.2"/></h3>
												<div class="form-left m-t-20" style="padding-bottom:7px;">
													<c:forEach var="proposal" items="${quiz.proposals}" varStatus="state">
														<div class="item-vote">
															<label class="ui-radio ui-radio-segond font-small m-r-20 ${!empty quiz.vote ? 'disabled' : ''}">
																<input type="radio" name="vote" value="${state.count}" selected="${quiz.vote == state.count}">
																<span class="input-span"></span><c:out value="${proposal}"/>
															</label>
														</div>
													</c:forEach>
													<span class="error"></span>
												</div>
												<hr class="my-1">
												<div id="submitVoteForm" class="form-submit font-small m-l-20">
													<c:choose>
														<c:when test="${empty quiz.vote}">
															<span class="help-text"><i class="cmsms-icon-explorer-angle m-r-5"></i>
																<a class="lien lien-segond lien-underline iPostVote btn-submit"><spring:message code="txt.help.topic2.4"/></a></span>
														</c:when>
														<c:otherwise>
															<span class="help-text">
																<spring:message code="txt.help.topic2.5"/> : 
																<span class="font-bold"><joda:format value="${quiz.postedDate}" pattern="dd MMMM, HH:mm"></joda:format></span>
															</span>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
									</div>
									<input type="hidden" id="proposalsQuiz" value="${quiz.countsToString()}" />
								</div>
							</c:if>
						</div>
						<ul class="navbar-nav nav-flex-icons nav-topic-inbox m-t-20">
							<li>
								<a class="link-like transition-35 iLikeTopic ${inbox.liked ? 'active' : ''}">
									<i class="cmsms-icon-thumbs-up-2 i-select m-r-10"></i><spring:message code="tool.view.like"/>
									<span class="iLikeCount animated m-l-10"><c:out value="${inbox.likeCount}" /></span>
								</a>
							</li>
							<li class="divider"></li>
							<li class="link-comment">
								<i class="cmsms-icon-comment i-primary m-r-10"></i><span class="iCommentCount"><c:out value="${inbox.commentCount}" /></span>
							</li>
							<li class="divider"></li>
							<li class="link-comment"><i class="cmsms-icon-user-5 i-primary m-r-10"></i><c:out value="${inbox.userCount}" /></li>
							<li class="divider"></li>
							<li class="link-comment"><i class="cmsms-icon-eye-1 i-primary m-r-10"></i><c:out value="${inbox.viewCount}" /></li>
							<li class="ml-auto"><a class="link-reply transition-35 iReplyTopic"><i class="cmsms-icon-reply i-segond m-r-10"></i><spring:message code="btn.reply"/></a></li>
						</ul>
					</div>
					<div class="topic-comments m-t-20">
						<div class="topic-commentload" style="padding-bottom:20px;display:none;">
							<div id="submitLoadForm" class="form-submit">
								<i class="cmsms-icon-angle-up m-r-10"></i><a id="submitLoad" class="lien lien-segond lien-hover lien-small font-bold btn-submit">
									<spring:message code="tool.navigate.comments"/></a>
							</div>
						</div>
						<div class="topic-loader font-small">
							<c:import url="/WEB-INF/basics/loading_topic.jsp"/>
							<ul class="list-none list-block list-replies"></ul>
						</div>
					</div>
					<div class="form-group form-replies m-b-0"></div>
					<hr class="m-t-20 m-b-20">
					<a id="repliesTopic" class="btn btn-primary btn-flat btn-big"><span><spring:message code="btn.replies"/></span></a>
				</div>
				<hr class="my-2">
				<div class="form-group m-b-20">
					<i class="cmsms-icon-explorer-back i-8 m-r-10"></i><a href="<c:url value="/forums"/>" 
						class="lien lien-primary lien-underline lien-small"><spring:message code="explorer.desktop.backword9"/></a>
				</div>
			</div>
			<div class="col-lg-3 m-t-10 m-b-10 hidden-md-down">
				<h2 class="h-header h-header4 i-primary"><spring:message code="wizard.forums.explorer1"/></h2>
				<hr class="my-1">
				<div id="loadSimultude"></div>
			</div>
		</div>
	</div>
</sec:authorize>