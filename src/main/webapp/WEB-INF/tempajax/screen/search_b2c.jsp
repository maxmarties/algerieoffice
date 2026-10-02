<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row row-mini">
			<c:forEach var="line" items="${list.lines}" varStatus="state">
				<div class="col-md-6 col-lg-4 col-mini m-b-10">
					<div class="widgetb2c h-100" data-dir="${line.lang == 'ar' ? 'rtl' : 'ltr'}" data-widget="${line.id}">
						<div class="inner-thumbnail background-container" 
							style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="${line.urlCover}" />');">
							<span class="ind-language text-center text-uppercase" data-ind="${line.lang}"><c:out value="${line.lang}"/></span>
							<c:if test="${!empty line.premium}">
								<div class="ind-premium text-center btn-warning">
									<span class="m-r-5"><c:out value="PRO"/></span>
									<ul class="list-none list-premium list-inline">
										<c:forEach var="i" begin="1" end="${line.premium}" step="1"><li><i class="cmsms-icon-plus-circled"></i></li></c:forEach>
									</ul>
								</div>
							</c:if>
						</div>
						<div class="widgetb2c-centent flexed flex-colone flex-jusitify h-100">
							<div>
								<a href="<c:url value="${line.companyURL}"/>" class="company-avatar" target="_blank" title="<c:out value="${line.tradename}" />">
									<img class="img-responsive" src="<c:url value="${line.urlAvatar}"/>" alt="<c:out value="${line.tradename}" />">
								</a>
								<div class="company-content">
									<div style="min-height:64px;padding-top:3px;">
										<a href="<c:url value="${line.companyURL}"/>" class="lien lien-company font-bold sh-black explorer-result" target="_blank">
											<c:out value="${line.tradename}" />
										</a>
										<p class="font-mini i-gray sh-black" style="line-height:14px;">
											<i class="cmsms-icon-location-1 i-red m-r-10"></i><c:out value="${line.address}" /></p>
									</div>
									<p class="font-bold i-segond" style="line-height:14px;"><spring:message code="chose.activity.${line.activity}"/></p>
								</div>
								<div class="clearfix"></div>
								<hr class="m-t-10 m-b-5">
								<ul class="nav list-evaluation">
									<c:choose>
										<c:when test="${line.evaluation == 0}">
											<li class="font-small font-bold i-yellow"><spring:message code="tool.empty.evaluation"/></li>
										</c:when>
										<c:otherwise>
											<c:set var="countStar" value="${line.note / 2}" scope="page"></c:set>
											<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
											<c:if test="${line.note % 2 != 0}"><li class="i-yellow"><i class="cmsms-icon-star-half"></i></li></c:if>
											<li class="font-small font-bold i-yellow m-l-5">(<c:out value="${line.evaluation}"/>)</li>
											<li class="font-small font-bold i-blue ml-auto"><i class="cmsms-icon-thumbs-up m-r-5"></i><c:out value="${line.averageLiked()}%"/></li>
										</c:otherwise>
									</c:choose>
								</ul>
							</div>
							<ul class="navbar-nav nav-flex-icons nav-company">
								<li><a href="tel:+213${line.phone}" class="lien iPhone" title="<spring:message code="tool.navigate.company1"/>"><i class="cmsms-icon-call-out"></i></a></li>
								<li><a href="mailto:${line.mail}" class="lien iMail" title="<spring:message code="tool.navigate.company3"/>"><i class="cmsms-icon-mail-1"></i></a></li>
								<li id="submitedFavorite${line.id}Form" class="form-submit">
									<a class="lien btn-submit iFavorite ${!empty line.favorite ? 'active' : ''}" 
										title="<spring:message code="tool.navigate.company2"/>"><i class="cmsms-icon-star-3"></i></a>
								</li>
							</ul>
						</div>
						<input type="hidden" id="currFavorite${line.id}" value="${line.favorite}" />
					</div>
				</div>
				<c:if test="${!empty indexEmptyCompany && state.count == indexEmptyCompany}">
					<div class="col-md-6 col-lg-4 col-mini m-b-10">
						<c:set var="widgetEmptyB2C" value="1" scope="request"></c:set>
						<c:import url="/WEB-INF/tempajax/screen/widget_emptycompany.jsp" />
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countScreenResult" value="${list.countResult}" />
<input type="hidden" id="countScreenSize" value="${list.lines.size()}" />
</compress:html>