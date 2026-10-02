<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="analytic-title"><h3 class="h-doc h-doc3"><spring:message code="subheader.screen.sector1.2.1"/></h3></div>
<div class="analytic-content text-center">
	<i class="cmsms-icon-building i-analytic m-t-10"></i>
	<p class="h-header font-bold font-value i-primary m-t-30"><c:out value="${chartStrict.getFormattedValue(0)}"/></p>
	<p class="i-help m-t-10"><spring:message code="lbl.sub.companies"/></p>
	<div class="row m-t-20">
		<div class="col-6">
			<p class="h-header font-big">
				<i class="cmsms-icon-commerical-building i-green i-effetif m-r-5"></i><c:out value="${chartStrict.getFormattedValue(1)}"/>
			</p>
		</div>
		<div class="col-6">
			<p class="h-header font-big">
				<i class="cmsms-icon-warehouse i-red i-effetif m-r-5"></i><c:out value="${chartStrict.getFormattedValue(2)}"/>
			</p>
		</div>
	</div>
</div>
</compress:html>