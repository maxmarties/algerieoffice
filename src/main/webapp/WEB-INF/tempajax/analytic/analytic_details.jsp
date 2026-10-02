<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="row m-t-30">
	<div class="col-sm-6 col-lg-3 m-b-20">
		<div class="screen-column screen-analytic h-100">
			<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="lbl.sub.search4"/></h3></div>
			<div class="analytic-content text-center">
				<c:choose>
					<c:when test="${!chartDetail.hasPresentContact()}">
						<i class="cmsms-icon-phone-alt i-analytic m-t-10"></i>
						<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
						<span class="help-text"><spring:message code="txt.help.screen1.6"/></span>
					</c:when>
					<c:otherwise>
						<canvas id="chartAnalyticContact" style="height:220px;max-width:100%;"></canvas>
						<c:set var="chosesContact" scope="page">
							<c:forEach var="i" begin="1" end="4" step="1"><spring:message code="lbl.sub.search4.${i}" /><c:out value="${i < 4 ? ',' : ''}"/></c:forEach>
						</c:set>
						<input type="hidden" id="contactAnalyticValue" value="${chartDetail.countContactToString()}" />
						<input type="hidden" id="contactAnalyticText" value="${pageScope.chosesContact}" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="col-sm-6 col-lg-3 m-b-20">
		<div class="screen-column screen-analytic h-100">
			<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="lbl.sub.search5"/></h3></div>
			<div class="analytic-content text-center">
				<c:choose>
					<c:when test="${!chartDetail.hasPresentDigital()}">
						<i class="cmsms-icon-globe-3 i-analytic m-t-10"></i>
						<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
						<span class="help-text"><spring:message code="txt.help.screen1.8"/></span>
					</c:when>
					<c:otherwise>
						<div class="m-t-10">
							<i class="cmsms-icon-globe-1 i-segond i-34"></i>
							<p class="h-header font-bold font-big i-primary m-t-10"><c:out value="${chartDetail.getFormattedDigital(0)}"/></p>
							<p class="font-mini i-help"><spring:message code="lbl.sub.search5.1"/></p>
						</div>
						<div class="m-t-20">
							<i class="cmsms-icon-map-1 i-segond i-34"></i>
							<p class="h-header font-bold font-big i-primary m-t-10"><c:out value="${chartDetail.getFormattedDigital(1)}"/></p>
							<p class="font-mini i-help"><spring:message code="lbl.sub.search5.3"/></p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="col-sm-6 col-lg-3 m-b-20">
		<div class="screen-column screen-analytic h-100">
			<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="sidebar.admin.dashboard6.4"/></h3></div>
			<div class="analytic-content text-center">
				<c:choose>
					<c:when test="${!chartDetail.hasPresentSocial()}">
						<i class="cmsms-icon-facebook-3 i-analytic m-t-10"></i>
						<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
						<span class="help-text"><spring:message code="txt.help.screen1.7"/></span>
					</c:when>
					<c:otherwise>
						<canvas id="chartAnalyticSocial" style="height:220px;max-width:100%;"></canvas>
						<input type="hidden" id="socialAnalyticValue" value="${chartDetail.countSocialToString()}" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="col-sm-6 col-lg-3 m-b-20">
		<div class="screen-column screen-analytic h-100">
			<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="tabs.buildate"/></h3></div>
			<div class="analytic-content text-center">
				<canvas id="chartAnalyticBuild" style="height:220px;max-width:100%;"></canvas>
				<input type="hidden" id="buildsAnalyticValue" value="${chartDetail.countBuildToString()}" />
				<input type="hidden" id="buildsAnalyticText" value="${chartDetail.getYearsBuild()}" />
			</div>
		</div>
	</div>
</div>
</compress:html>