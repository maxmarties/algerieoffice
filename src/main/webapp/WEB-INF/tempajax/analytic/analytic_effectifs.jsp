<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.11"/></h3></div>
<div class="analytic-content text-center">
	<i class="cmsms-icon-user-group i-analytic m-t-10"></i>
	<c:choose>
		<c:when test="${!chartEffectif.hasPresent()}">
			<p class="h-header font-bold font-big i-primary m-t-30"><spring:message code="lbl.sub.undefined"/></p>
			<span class="help-text"><spring:message code="txt.help.screen1.2"/></span>
		</c:when>
		<c:otherwise>
			<p class="h-header font-bold font-value i-primary m-t-30"><c:out value="${chartEffectif.getSumFormattedValue()}"/></p>
			<p class="i-help m-t-10"><spring:message code="lbl.sub.agents"/></p>
			<div class="row m-t-20">
				<div class="col-6">
					<p class="h-header font-big"><i class="cmsms-icon-user-male i-blue i-effetif m-r-5"></i><c:out value="${chartEffectif.getFormattedValue(0)}"/></p>
				</div>
				<div class="col-6">
					<p class="h-header font-big"><i class="cmsms-icon-user-female i-red i-effetif m-r-5"></i><c:out value="${chartEffectif.getFormattedValue(1)}"/></p>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
</compress:html>