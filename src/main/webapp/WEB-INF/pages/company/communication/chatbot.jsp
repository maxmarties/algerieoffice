<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/communication/notices"/>" class="lien lien-black">
			<i class="cmsms-icon-cloud m-r-5"></i><spring:message code="sidebar.company.dashboard8"/></a></li>
		<li><a href="<c:url value="/company/communication/chatbots"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard8.7"/></a></li>
		<li class="active"><spring:message code="btn.viewmore" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.communication.chatbot.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.communication7.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/communication/chatbots" scope="request"></c:set>
	<c:set var="backwordPage" value="23" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="page-container">
		<c:choose>
			<c:when test="${currentCompany.hasPremium()}">
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="txt.help.explorer3.9" />
						<span class="help-text"><spring:message code="txt.help.contact1.2" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="chatbot-frame">
							<ul class="list-none list-block chatbot-list">
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${currentCompany.iconurl}"/>" class="img-circle pull-left" alt="${currentCompany.tradename}" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small">
											<img height="16" class="m-r-5" src="<c:url value="/static/vectors/emoticons/1f44b.png"/>"/>
											<spring:message code="txt.chatbot.explorer1" arguments="${currentCompany.tradename}"/>
										</div>
									</div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-left">
									<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer2"/></div></div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-right">
									<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer2.${chatbotDetail.domaine}"/></div></div>
								</li>
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${currentCompany.iconurl}"/>" class="img-circle pull-left" alt="${currentCompany.tradename}" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer3"/></div>
									</div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-right">
									<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer3.${chatbotDetail.discute}"/></div></div>
								</li>
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${currentCompany.iconurl}"/>" class="img-circle pull-left" alt="${currentCompany.tradename}" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small"><spring:message code="txt.chatbot.explorer4.${chatbotDetail.discute}"/></div>
									</div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-right">
									<div class="chatbot-brand"><div class="chatbot-pull font-small"><c:out value="${chatbotDetail.message}"/></div></div>
								</li>
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${currentCompany.iconurl}"/>" class="img-circle pull-left" alt="${currentCompany.tradename}" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small">
											<spring:message code="txt.chatbot.explorer5"/>
											<img height="16" class="m-l-5" src="<c:url value="/static/vectors/emoticons/1f609.png"/>"/>
										</div>
									</div>
									<span class="clearfix"></span>
								</li>
								<li class="chatbot-item chatbot-right">
									<div class="chatbot-brand"><div class="chatbot-pull font-small"><spring:message code="txt.chatbot.home5.3.1"/></div></div>
								</li>
								<li class="chatbot-item chatbot-left">
									<img src="<c:url value="${currentCompany.iconurl}"/>" class="img-circle pull-left" alt="${currentCompany.tradename}" />
									<div class="chatbot-brand">
										<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
										<div class="chatbot-pull font-small">
											<spring:message code="txt.chatbot.home5.3.2"/>
											<img height="16" class="m-l-5" src="<c:url value="/static/vectors/emoticons/1f44d.png"/>"/>
										</div>
									</div>
									<span class="clearfix"></span>
								</li>
							</ul>
						</div>
						<span class="help-text m-t-10"><c:out value="${chatbotDetail.postedDate}"/></span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning">
					<i class="cmsms-icon-dollar i-alert"></i>
					<p class="p-alert">
						<spring:message code="message.premium.communication"/> 
						<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>