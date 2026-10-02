<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><div class="screen-empty m-b-20"><p><spring:message code="tool.empty.members" /></p></div></c:when>
	<c:otherwise>
		<div class="row row-mini">
			<c:forEach var="line" items="${list.lines}">
				<div class="col-md-6 col-lg-4 col-mini m-b-10">
					<div class="widget-member h-100" data-widget="${line.id}">
						<c:if test="${line.hasPro}"><span class="ind-pro text-center btn-warning"><spring:message code="lbl.premium" /></span></c:if>
						<div class="member-content flexed flex-row flex-jusitify">
							<div class="member-item">
								<div class="img-circle img-container pull-left ${line.online ? 'ind-login' : ''}">
									<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.username}" />">
								</div>
								<div class="member-brand">
									<a href="<c:url value="${line.pseudoURL}"/>" class="lien lien-company sh-black explorer-result">
										<c:out value="${line.username}" />
									</a><span class="i-certificated i-certificated${line.verified}"></span>
									<p class="font-small i-help"><c:out value="${line.function}" /></p>
									<p class="font-small m-t-10">
										<i class="cmsms-icon-location-1 i-red m-r-10"></i>
										<c:choose>
											<c:when test="${!empty line.address}">
												<c:out value="${line.address}" /><br><c:out value="${line.postal}" />, 
												<span class="text-uppercase"><spring:message code="chose.wilaya${line.wilaya}"/></span>
											</c:when>
											<c:otherwise><spring:message code="tool.explorer.location"/></c:otherwise>
										</c:choose>
									</p>
								</div>
								<span class="clearfix"></span>
							</div>
							<ul class="list-none list-block list-membres-contatc">
								<li id="submitedFavorite${line.id}Form" class="form-submit">
									<a class="btn-submit iFavorite transition-35 ${line.hasFavorite ? 'active' : ''}" 
										title="<spring:message code="tool.navigate.membre1"/>"><i class="cmsms-icon-star-1"></i></a>
								</li>
								<li><a class="iMessage transition-35" data-avatar="${line.urlAvatar}" data-name="${line.username}" data-login="${line.online}" 
									title="<spring:message code="tool.navigate.membre2"/>"><i class="cmsms-icon-mail-alt"></i></a></li>
							</ul>
						</div>
						<div class="member-footer font-small m-t-5">
							<c:choose>
								<c:when test="${!empty line.companyname}">
									<span class="font-bold"><spring:message code="tabs.company" /></span>
									<span class="pull-right">
										<c:choose>
											<c:when test="${!empty line.companyURL}">
												<a href="<c:url value="${line.companyURL}" />" class="lien lien-help lien-underline"><c:out value="${line.companyname}" /></a>
											</c:when>
											<c:otherwise><c:out value="${line.companyname}" /></c:otherwise>
										</c:choose>
									</span>
								</c:when>
								<c:otherwise><span class="font-bold"><spring:message code="lbl.sub.account4.6" /></span><span class="pull-right"><spring:message code="lbl.sub.pro2" /></span></c:otherwise>
							</c:choose>
							<span class="clearfix"></span>
						</div>
						<input type="hidden" id="currFavorite${line.id}" value="${line.hasFavorite}" />
					</div>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countScreenResult${currentPage}" value="${list.countResult}" />
<input type="hidden" id="countScreenSize${currentPage}" value="${list.lines.size()}" />
</compress:html>