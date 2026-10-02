<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="explorer.mainmenu7.3"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="txt.infos.politic1"/></h1></div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.politic1.1"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.politic1.2"/></p>
			<p class="m-t-20"><spring:message code="txt.infos.politic1.2.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.politic1.2.2"/> :</p>
			<ul class="m-t-20"><c:forEach var="i" begin="1" end="4" step="1"><li class="m-b-5"><spring:message code="txt.infos.politic1.2.2.${i}"/></li></c:forEach></ul>
			<p class="m-t-20"><spring:message code="txt.infos.politic1.2.3"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.politic2.1"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.politic2.2"/> :</p>
			<ul class="m-t-20">
				<c:forEach var="i" begin="1" end="2" step="1">
					<li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.politic2.2.${i}.1"/></span> : <spring:message code="txt.infos.politic2.2.${i}.2"/></li>
				</c:forEach>
			</ul>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.politic3.1"/></h2>
		<div class="m-t-20 m-l-20">
			<h3 class="h-header h-header4 i-primary"><spring:message code="txt.infos.politic3.2"/></h3>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.2.1"/></p>
			<h3 class="h-header h-header4 i-primary m-t-30"><spring:message code="txt.infos.politic3.3"/></h3>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.3.1"/> :</p>
			<ul class="m-t-20"><c:forEach var="i" begin="1" end="6" step="1"><li class="m-b-10"><spring:message code="txt.infos.politic3.3.1.${i}"/></li></c:forEach></ul>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.3.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.politic3.3.3"/></p>
			<h3 class="h-header h-header4 i-primary m-t-30"><spring:message code="txt.infos.politic3.4"/></h3>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.4.1"/> :</p>
			<h4 class="h-header h-header6 font-bold m-t-20">1. <spring:message code="txt.infos.politic3.4.1.1"/></h4>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.4.1.2"/></p>
			<h4 class="h-header h-header6 font-bold m-t-20">2. <spring:message code="txt.infos.politic3.4.2.1"/></h4>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.4.2.2"/></p>
			<h4 class="h-header h-header6 font-bold m-t-20">3. <spring:message code="txt.infos.politic3.4.3.1"/></h4>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.4.3.2"/> :</p>
			<ul class="m-t-20"><c:forEach var="i" begin="1" end="5" step="1"><li class="m-b-5"><spring:message code="txt.infos.politic3.4.3.2.${i}"/></li></c:forEach></ul>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.4.4"/></p>
			<h3 class="h-header h-header4 i-primary m-t-30"><spring:message code="txt.infos.politic3.5"/></h3>
			<p class="m-t-20"><spring:message code="txt.infos.politic3.5.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.politic3.5.2"/></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.politic4.1"/></h2>
		<p class="m-t-20 m-l-20"><spring:message code="txt.infos.politic4.2"/></p>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.politic5.1"/></h2>
		<ul class="m-t-20 m-b-40">
			<c:forEach var="i" begin="1" end="6" step="1">
				<li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.politic5.1.${i}.1"/></span> : <spring:message code="txt.infos.politic5.1.${i}.2"/></li>
			</c:forEach>
		</ul>
	</div>
</div>