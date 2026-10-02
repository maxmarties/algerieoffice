<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="explorer.mainmenu7.6"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="explorer.mainmenu7.6"/></h1></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.infos.credit1"/></p>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.credit1.1"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.credit1.1.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.credit1.1.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.credit1.1.3"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.credit1.2"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.credit1.2.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.credit1.2.2"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.credit1.3"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.credit1.3.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.credit1.3.2"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.credit1.4"/></h2>
		<div class="m-t-20 m-b-30 m-l-20">
			<ul>
				<c:forEach var="i" begin="1" end="8" step="1">
					<li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.credit1.4.${i}.1"/></span> : <spring:message code="txt.infos.credit1.4.${i}.2"/></li>
				</c:forEach>
			</ul>
			<p class="m-t-40"><spring:message code="txt.infos.credit1.5"/></p>
			<ul class="m-t-20"><li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.credit1.5.1"/></span> : <spring:message code="txt.infos.credit1.5.2"/></li></ul>
		</div>
	</div>
</div>