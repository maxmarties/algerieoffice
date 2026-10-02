<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="explorer.mainmenu7.7"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="txt.infos.chart1"/></h1></div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.chart1.1"/></h2>
		<ul class="m-t-20">
			<c:forEach var="i" begin="1" end="5" step="1">
				<li class="m-b-10">
					<span class="font-bold"><spring:message code="txt.infos.chart1.1.${i}.1"/></span> :
					<c:choose>
						<c:when test="${i == 4}">
							<ul class="m-t-10"><c:forEach var="j" begin="1" end="2" step="1"><li class="m-b-10"><spring:message code="txt.infos.chart1.1.4.2.${j}"/></li></c:forEach></ul>
						</c:when>
						<c:otherwise><spring:message code="txt.infos.chart1.1.${i}.2"/></c:otherwise>
					</c:choose>
				</li>
			</c:forEach>
		</ul>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.chart1.2"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.chart1.2.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.2.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.2.3"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.chart1.3"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.chart1.3.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.3.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.3.3"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.chart1.4"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.chart1.4.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.4.2"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.chart1.5"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.chart1.5.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.5.2"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.chart1.6"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.chart1.6.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.6.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.chart1.6.3"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.chart1.7"/></h2>
		<p class="m-t-20 m-b-30 m-l-20"><spring:message code="txt.infos.chart1.7.1"/></p>
	</div>
</div>