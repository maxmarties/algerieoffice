<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<sec:authorize access="isAnonymous()">
	<div id="chatbotHome" class="chatbot close" style="display:none;">
		<div class="chatbot-widget animated speed fadeInUp">
			<c:if test="${!currentConfig.chatbotCollapse}">
				<div class="chatbot-header chatbot-welcome m-b-10 animated speed fadeInUp" style="display:none;">
					<img src="<c:url value="/static/icons/icon-black-min.jpg"/>" alt="<spring:message code="app.brand"/>" />
					<p class="font-small m-t-10"><img height="16" class="m-r-10" src="<c:url value="/static/vectors/emoticons/1f44b.png"/>"/><spring:message code="txt.chatbot.home1"/></p>
					<button type="button" class="btn btn-simple" data-dismiss="chatbot-widget" title='<spring:message code="btn.close"/>'><i class="cmsms-icon-cancel-2"></i></button>
				</div>
			</c:if>
			<a class="btn btn-primary to-chatbot ml-auto" title="<spring:message code="lbl.sub.chater1.2"/>"><span><i class="cmsms-icon-comment-alt-1"></i></span></a>
		</div>
		<div class="chatbot-frame animated speed sliderInUp">
			<div class="chatbot-header bn-segond">
				<div class="img-circle img-container pull-left ind-login">
					<img src="<c:url value="/static/icons/icon-black-min.jpg"/>" class="img-circle img-responsive" alt="<spring:message code="app.brand"/>" />
				</div>
				<div class="chatbot-brand">
					<p class="h-header font-big font-bold i-white"><spring:message code="lbl.sub.chater1.2"/></p>
					<span class="font-small text-lowercase i-gray"><spring:message code="tabs.published"/></span>
				</div>
				<div class="clearfix"></div>
				<button type="button" class="btn btn-simple" data-dismiss="chatbot-frame" title='<spring:message code="btn.close"/>'><i class="cmsms-icon-cancel-2"></i></button>
			</div>
			<div class="chatbot-body">
				<div class="chatbot-content">
					<ul class="list-none list-block chatbot-list">
						<li class="chatbot-item chatbot-left">
							<img src="<c:url value="/static/icons/icon-black-min.jpg"/>" class="img-circle pull-left" alt="<spring:message code="app.brand"/>" />
							<div class="chatbot-brand">
								<span class="font-mini font-bold i-help"><spring:message code="lbl.sub.chater1.2"/></span>
								<div class="chatbot-pull font-small">
									<img height="16" class="m-r-5" src="<c:url value="/static/vectors/emoticons/1f44b.png"/>"/><spring:message code="txt.chatbot.home1"/>
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