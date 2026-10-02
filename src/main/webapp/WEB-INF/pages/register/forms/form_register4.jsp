<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">4. <spring:message code="subheader.register2.4"/></h2>
<div class="alert m-t-10">
	<div id="langOverview" class="widget-company widget-overview m-auto m-t-10 m-b-10" data-dir="${langage.dir}">
		<span class="ind-language text-center text-uppercase" data-ind="${langage.lang}"><c:out value="${langage.lang}"/></span>
		<div class="company-avatar">
			<img id="avatarOverview" class="img-responsive" src="<c:url value="/static/picts/avatars/company-min.jpg"/>" 
				alt="<spring:message code="tooltip.avatar" />">
		</div>
		<div class="company-content">
			<h3 id="tradenameOverview" class="h-header i-select font-bold sh-black"></h3>
			<p class="font-mini"><i class="cmsms-icon-location-1 i-red m-r-5"></i><span id="addressOverview"></span></p>
			<p id="activityOverview" class="font-bold i-segond1 m-t-5"></p>
		</div>
		<div class="clearfix"></div>
		<ul class="navbar-nav nav-flex-icons nav-company">
			<c:set var="providers" value="phone-3,mail-5,star-5" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<a id="providerOverview${state.count}" class="lien" data-toggle="tooltip" title="<spring:message code="tooltip.navcompany${state.count}" />">
						<i class="cmsms-icon-${pageScope.provider}"></i>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<div id="g-recaptchaForm" class="form-group m-t-20" style="min-height:110px">
	<label class="col-form-label">
		<spring:message code="lbl.recaptcha"/> <small class="min"><spring:message code="lbl.requis"/></small>
	</label>
	<div class="g-recaptcha" data-sitekey="<c:out value="${recaptchaSiteKey}" />"
		data-callback="onReCaptchaSuccess" data-expired-callback="onReCaptchaExpired">
	</div>
	<span class="error"></span>
</div>
<hr class="my-4">
<p class="font-mini"><spring:message code="txt.help.register2"/> 
	<a href="<c:url value="/infos/politique-confidentialite" />" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.mention"/></a>.</p>