<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="widget-company flexed flex-colone flex-jusitify h-100" data-dir="${requestScope.widgetCompany.lang == 'ar' ? 'rtl' : 'ltr'}" 
	data-widget="${requestScope.widgetCompany.id}">
	<div>
		<span class="ind-language text-center text-uppercase" data-ind="${requestScope.widgetCompany.lang}"><c:out value="${requestScope.widgetCompany.lang}"/></span>
		<c:if test="${!empty requestScope.widgetCompany.premium}">
			<div class="ind-premium text-center btn-warning">
				<span class="m-r-5"><c:out value="PRO"/></span>
				<ul class="list-none list-premium list-inline">
					<c:forEach var="i" begin="1" end="${requestScope.widgetCompany.premium}" step="1"><li><i class="cmsms-icon-plus-circled"></i></li></c:forEach>
				</ul>
			</div>
		</c:if>
		<a href="<c:url value="${requestScope.widgetCompany.companyURL}"/>" class="company-avatar" target="_blank" title="<c:out value="${requestScope.widgetCompany.tradename}"/>">
			<img class="img-responsive" src="<c:url value="${requestScope.widgetCompany.urlAvatar}"/>" alt="<c:out value="${requestScope.widgetCompany.tradename}" />">
		</a>
		<div class="company-content">
			<a href="<c:url value="${requestScope.widgetCompany.companyURL}"/>" class="lien lien-company font-bold sh-black explorer-result" target="_blank">
				<c:out value="${requestScope.widgetCompany.tradename}" />
			</a>
			<p class="font-small i-help">
				<i class="cmsms-icon-location-1 i-red m-r-5"></i>
				<c:choose>
					<c:when test="${empty requestScope.ignoreWilaya}">
						<c:out value="${requestScope.widgetCompany.postal}" />, 
						<span class="text-uppercase"><spring:message code="chose.wilaya${requestScope.widgetCompany.wilaya}"/></span>
					</c:when>
					<c:otherwise>
						<c:out value="${requestScope.widgetCompany.address}" />, <c:out value="${requestScope.widgetCompany.postal}" />
					</c:otherwise>
				</c:choose>
			</p>
			<c:choose>
				<c:when test="${empty requestScope.ignoreActivity}">
					<p class="font-bold i-segond m-t-5" style="line-height:14px;"><spring:message code="chose.activity.${requestScope.widgetCompany.activity}"/></p>
				</c:when>
				<c:otherwise>
					<ul class="list-none list-evaluation m-t-5">
						<c:choose>
							<c:when test="${requestScope.widgetCompany.evaluation == 0}">
								<li class="font-small font-bold i-yellow"><spring:message code="tool.empty.evaluation"/></li>
							</c:when>
							<c:otherwise>
								<c:set var="countStar" value="${requestScope.widgetCompany.note / 2}" scope="page"></c:set>
								<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
								<c:if test="${requestScope.widgetCompany.note % 2 != 0}"><li class="i-yellow"><i class="cmsms-icon-star-half"></i></li></c:if>
								<li class="font-small font-bold i-yellow m-l-5">(<c:out value="${requestScope.widgetCompany.evaluation}"/>)</li>
							</c:otherwise>
						</c:choose>
					</ul>		
				</c:otherwise>
			</c:choose>
		</div>
		<div class="clearfix"></div>
	</div>
	<ul class="navbar-nav nav-flex-icons nav-company">
		<li><a href="tel:+213${requestScope.widgetCompany.phone}" class="lien iPhone" 
			title="<spring:message code="tool.navigate.company1"/>"><i class="cmsms-icon-call-out"></i></a></li>
		<li><a href="mailto:${requestScope.widgetCompany.mail}" class="lien iMail" 
			title="<spring:message code="tool.navigate.company3"/>"><i class="cmsms-icon-mail-1"></i></a></li>
		<li id="submitedFavorite${requestScope.widgetCompany.id}Form" class="form-submit">
			<a class="lien btn-submit iFavorite ${!empty requestScope.widgetCompany.favorite ? 'active' : ''}" 
				title="<spring:message code="tool.navigate.company2"/>"><i class="cmsms-icon-star-3"></i></a>
		</li>
	</ul>
	<input type="hidden" id="currFavorite${requestScope.widgetCompany.id}" value="${requestScope.widgetCompany.favorite}" />
</div>
</compress:html>