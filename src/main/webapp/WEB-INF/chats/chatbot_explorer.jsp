<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="isAnonymous()">
	<div id="chatbotExplorer" class="chatbot chatbot-explorer close" style="display:none;">
		<div class="chatbot-widget animated speed fadeInUp">
			<c:if test="${!currentConfig.chatboterCollapse}">
				<div class="chatbot-header chatbot-welcome m-b-10 animated speed fadeInUp" style="display:none;">
					<img src="<c:url value="${explorerCompany.header.urlLogo}"/>" alt="${explorerCompany.profile.tradename}" />
					<p class="font-small m-t-10"><img height="16" class="m-r-5" src="<c:url value="/static/vectors/emoticons/1f44b.png"/>"/>
						<spring:message code="txt.chatbot.explorer1" arguments="${explorerCompany.profile.tradename}"/></p>
					<button type="button" class="btn btn-simple" data-dismiss="chatbot-widget" title='<spring:message code="btn.close"/>'><i class="cmsms-icon-cancel-2"></i></button>
				</div>
			</c:if>
			<a class="btn btn-explorer-primary to-chatbot ml-auto" title="<spring:message code="lbl.sub.chater1.2"/>"><span><i class="cmsms-icon-comment-alt-1"></i></span></a>
		</div>
		<div class="chatbot-frame animated speed sliderInUp">
			<div class="chatbot-header bn-explorer-segond">
				<div class="img-circle img-container pull-left ${hasCompanyLogin ? 'ind-login' : ''}">
					<img src="<c:url value="${explorerCompany.header.urlLogo}"/>" class="img-circle img-responsive" alt="${explorerCompany.profile.tradename}" />
				</div>
				<div class="chatbot-brand">
					<p class="h-header font-big font-bold"><spring:message code="lbl.sub.chater1.2"/></p>
					<span class="font-small"><spring:message code="lbl.sub.chater1.2.${hasCompanyLogin ? '1' : '2'}"/></span>
				</div>
				<div class="clearfix"></div>
				<button type="button" class="btn btn-simple" data-dismiss="chatbot-frame" title='<spring:message code="btn.close"/>'><i class="cmsms-icon-cancel-2"></i></button>
			</div>
			<div class="chatbot-body">
				<div class="chatbot-content">
					<ul class="list-none list-block chatbot-list">
						<li class="chatbot-item chatbot-left">
							<img src="<c:url value="${explorerCompany.header.urlLogo}"/>" class="img-circle pull-left" alt="${explorerCompany.profile.tradename}" />
							<div class="chatbot-brand">
								<span class="font-mini font-bold text-help"><spring:message code="lbl.sub.chater1.2"/></span>
								<div class="chatbot-pull font-small">
									<img height="16" class="m-r-5" src="<c:url value="/static/vectors/emoticons/1f44b.png"/>"/>
									<spring:message code="txt.chatbot.explorer1" arguments="${explorerCompany.profile.tradename}"/>
								</div>
							</div>
							<span class="clearfix"></span>
						</li>
						<li class="chatbot-item chatbot-left chatbot-loading">
							<div class="chatbot-brand"><div class="chatbot-pull"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div></div>
							<span class="clearfix"></span>
						</li>
					</ul>
				</div>
			</div>
			<div class="chatbot-footer"><p class="font-mini">&copy; <span class="yearsApp">2021</span> <spring:message code="app.copyright"/></p></div>
		</div>
	</div>
</sec:authorize>