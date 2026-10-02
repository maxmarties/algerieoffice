<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="mainheader background-container" style="background-image: url('<c:url value="${explorerCompany.header.urlCover}" />');">
	<div class="mainheader-overlay p-position" style="opacity:${explorerCompany.header.canva}; background-color:${explorerCompany.header.canvaColor};"></div>
	<div class="container h-100" style="color:${explorerCompany.header.textColor};">
		<img class="mainheader-logo" src="<c:url value="${explorerCompany.header.urlLogo}"/>" alt="<c:out value="${explorerCompany.profile.tradename}"/>" />
		<div class="mainheader-text">
			<h1 class="h-header h-header1 font-bold sh-black"><c:out value="${explorerCompany.profile.tradename}"/></h1>
			<p class="font-small">
				<i class="cmsms-icon-location-1 i-red m-r-10"></i><c:out value="${explorerCompany.profile.address}, ${explorerCompany.profile.postal}" /> 
				<span class="text-uppercase"><spring:message code="chose.wilaya${explorerCompany.profile.wilaya}"/></span>
			</p>
			<ul class="list-none list-evaluation m-t-5">
				<c:choose>
					<c:when test="${empty explorerCompany.header.evaluation}">
						<li class="font-small font-bold i-yellow"><spring:message code="tool.empty.evaluation"/></li>
					</c:when>
					<c:otherwise>
						<c:set var="countStar" value="${explorerCompany.header.evaluation / 2}" scope="page"></c:set>
						<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
						<c:if test="${explorerCompany.header.evaluation % 2 != 0}">
							<li class="i-yellow"><i class="cmsms-icon-star-half"></i></li>
						</c:if>
						<li class="font-small font-bold i-yellow m-l-5">(<c:out value="${explorerCompany.header.countEvaluation}"/>)</li>
						<li class="font-small font-bold i-green m-l-20">
							<i class="cmsms-icon-thumbs-up m-r-5"></i><c:out value="${explorerCompany.header.averageLiked()} %"/>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
			<h2 class="h-header h-header5 m-t-5 hidden-sm-down"><spring:message code="chose.activity.${explorerCompany.profile.activity}"/></h2>
		</div>
		<div class="mainheader-navbar hidden-md-down">
			<p class="nav-text font-small font-italic m-b-5">
				<spring:message code="tool.navigate.company"/> : <c:out value="${explorerCompany.header.updateDate}" /></p>
			<ul class="navbar-nav nav-flex-icons nav-company">
				<li>
					<a href="tel:+213${explorerCompany.profile.phone}" class="lien iPhone ${explorerCurrent.hasPreview ? 'disabled' : ''}">
						<i class="cmsms-icon-call-out i-navigate block"></i>
						<span class="font-mini"><spring:message code="tool.navigate.company1"/></span>
					</a>
				</li>
				<li id="submitedFavoriteForm" class="form-submit">
					<a class="lien btn-submit iFavorite ${explorerCurrent.hasPreview ? 'disabled' : !empty currentVisitor.favorite ? 'active' : ''}">
						<i class="cmsms-icon-star-3 i-navigate block"></i>
						<span class="font-mini"><spring:message code="tool.navigate.company2"/></span>
					</a>
				</li>
				<li>
					<a href="mailto:${explorerCompany.profile.email}" class="lien iMail ${explorerCurrent.hasPreview ? 'disabled' : ''}">
						<i class="cmsms-icon-mail-1 i-navigate block"></i>
						<span class="font-mini"><spring:message code="tool.navigate.company3"/></span>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>