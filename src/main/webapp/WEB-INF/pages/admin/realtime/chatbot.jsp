<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/realtime/companies"/>" class="lien lien-black">
			<i class="cmsms-icon-flag m-r-5"></i><spring:message code="sidebar.admin.dashboard9"/></a></li>
		<li><a href="<c:url value="/admin/realtime/chatbots"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard9.7"/></a></li>
		<li class="active"><spring:message code="btn.viewmore" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="btn.viewmore"/></h1>
		<p><spring:message code="txt.admin.realtime7.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/realtime/chatbots" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="page-container">
		<div class="form-group row">
			<label class="col-form-label col-md-4 col-lg-3">
				<spring:message code="txt.help.explorer3.9" />
				<span class="help-text"><spring:message code="txt.help.contact1.2" /></span>
			</label>
			<div class="col-md-8 col-lg-9">
				<div class="chatbot-frame">
					<ul class="list-none list-block chatbot-list">
						<li class="chatbot-item chatbot-left">
							<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
							<div class="chatbot-brand">
								<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
								<div class="chatbot-pull font-small">
									<img height="16" class="m-r-5" src="<c:url value="/static/vectors/emoticons/1f44b.png"/>"/>
									<c:choose>
										<c:when test="${empty chatbotDetail.tradename}"><spring:message code="txt.chatbot.home1"/></c:when>
										<c:otherwise><spring:message code="txt.chatbot.explorer1" arguments="${chatbotDetail.tradename}"/></c:otherwise>
									</c:choose>
								</div>
							</div>
							<span class="clearfix"></span>
						</li>
						<li class="chatbot-item chatbot-left">
							<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.${chatbotDetail.key}2"/></div></div>
							<span class="clearfix"></span>
						</li>
						<li class="chatbot-item chatbot-right">
							<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.${chatbotDetail.key}2.${chatbotDetail.domaine}"/></div></div>
						</li>
						<li class="chatbot-item chatbot-left">
							<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
							<div class="chatbot-brand">
								<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
								<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.${chatbotDetail.key}3"/></div>
							</div>
							<span class="clearfix"></span>
						</li>
						<li class="chatbot-item chatbot-right">
							<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.${chatbotDetail.key}3.${chatbotDetail.discute}"/></div></div>
						</li>
						<c:choose>
							<c:when test="${!empty chatbotDetail.tradename}">
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer4.${chatbotDetail.discute}"/></div>
									</div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-right">
									<div class="chatbot-brand"><div class="chatbot-pull font-small">
										<c:out value="${!empty chatbotDetail.email ? chatbotDetail.email : chatbotDetail.message}"/></div></div>
								</li>
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer5"/></div>
									</div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-right">
									<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.home5.3.1"/></div></div>
								</li>
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer6"/></div>
									</div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-left">
									<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer7"/></div></div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-right">
									<div class="chatbot-brand"><div class="chatbot-pull font-small">
										<spring:message code="txt.chatbot.explorer7.${chatbotDetail.account ? '1' : '2'}"/></div></div>
								</li>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${chatbotDetail.discute == 1}">
										<li class="chatbot-item chatbot-left">
											<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
											<div class="chatbot-brand">
												<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
												<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.home4.1"/></div>
											</div>
											<span class="clearfix"></span>
										</li>
										<li class="chatbot-item chatbot-right">
											<div class="chatbot-brand"><div class="chatbot-pull font-small">
												<spring:message code="txt.chatbot.home4.1.${chatbotDetail.account ? '1' : '2'}"/></div></div>
										</li>
										<c:choose>
											<c:when test="${chatbotDetail.account}">
												<li class="chatbot-item chatbot-left">
													<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
													<div class="chatbot-brand">
														<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
														<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.home5.1"/></div>
													</div>
													<span class="clearfix"></span>
												</li>
											</c:when>
											<c:otherwise>
												<li class="chatbot-item chatbot-left">
													<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
													<div class="chatbot-brand">
														<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
														<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.home5.2"/></div>
													</div>
													<span class="clearfix"></span>
												</li>
												<li class="chatbot-item chatbot-right">
													<div class="chatbot-brand"><div class="chatbot-pull font-small"><c:out value="${chatbotDetail.message}" />
														<span class="font-mini i-segond block m-t-5"><c:out value="${chatbotDetail.email}" /></span></div></div>
												</li>
												<li class="chatbot-item chatbot-left">
													<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
													<div class="chatbot-brand">
														<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
														<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.home5.3"/></div>
													</div>
													<span class="clearfix"></span>
												</li>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<li class="chatbot-item chatbot-left">
											<img src="<c:url value="${chatbotDetail.urlAvatar}"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
											<div class="chatbot-brand">
												<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
												<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.home4.2"/></div>
											</div>
											<span class="clearfix"></span>
										</li>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
				<span class="help-text m-t-10"><c:out value="${chatbotDetail.postedDate}"/></span>
			</div>
		</div>
	</div>
</div>