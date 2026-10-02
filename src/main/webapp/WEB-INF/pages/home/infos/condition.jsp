<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="explorer.mainmenu7.2"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="txt.infos.condition1"/></h1></div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition1.1"/></h2>
		<ul class="m-t-20">
			<c:forEach var="i" begin="1" end="12" step="1">
				<li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.condition1.1.${i}.1"/></span> : <spring:message code="txt.infos.condition1.1.${i}.2"/></li>
			</c:forEach>
		</ul>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition1.2"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.condition1.2.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.2.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.2.3"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.2.4"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition1.3"/></h2>
		<p class="m-t-20 m-l-20"><spring:message code="txt.infos.condition1.3.1"/></p>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition1.4"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.condition1.4.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.4.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.4.3"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition1.5"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.condition1.5.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.5.2"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition1.6"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.condition1.6.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.6.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition1.6.3"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition2.1"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.condition2.1.1"/> : <a href="mailto:<spring:message code="app.contact" />"
				class="lien lien-black font-bold"><spring:message code="app.contact" /></a>.</p>
			<p class="m-t-10"><spring:message code="txt.infos.condition2.1.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition2.1.3"/> : <a href="mailto:<spring:message code="app.contact" />"
				class="lien lien-black font-bold"><spring:message code="app.contact" /></a>.</p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition2.2"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.condition2.2.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.condition2.2.2"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.condition2.3"/></h2>
		<p class="m-t-20 m-b-40 m-l-20"><spring:message code="txt.infos.condition2.3.1"/></p>
	</div>
</div>