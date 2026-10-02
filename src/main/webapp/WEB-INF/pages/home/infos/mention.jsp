<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="explorer.mainmenu7.4"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="explorer.mainmenu7.4"/></h1></div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.mention1"/></h2>
		<p class="m-t-20 m-l-20"><spring:message code="txt.infos.mention1.1"/></p>
		<div class="row">
			<div class="col-md-6 m-t-40">
				<h3 class="h-doc h-header3 i-primary"><spring:message code="txt.infos.mention1.2"/></h3>
				<div class="m-t-20 m-l-20">
					<p><spring:message code="txt.infos.mention1.2.1"/></p>
					<p class="font-small m-t-20"><spring:message code="txt.infos.mention1.2.2"/><br><spring:message code="txt.infos.mention1.2.3"/></p>
					<p class="font-small m-t-10"><spring:message code="txt.infos.mention1.2.4"/><br><spring:message code="txt.infos.mention1.2.5"/><br><spring:message code="txt.infos.mention1.2.6"/></p>
					<p class="font-small m-t-20"><spring:message code="txt.infos.mention1.3.2"/><br><spring:message code="txt.infos.mention1.3.3"/><br><spring:message code="txt.infos.mention1.3.4"/></p>
					<p class="font-small m-t-10"><spring:message code="txt.infos.mention1.3.5"/></p>
				</div>
			</div>
			<div class="col-md-6 m-t-40">
				<h3 class="h-doc h-header3 i-primary"><spring:message code="txt.infos.mention1.3"/></h3>
				<div class="m-t-20 m-l-20">
					<p><spring:message code="txt.infos.mention1.3.1"/></p>
					<p class="font-small m-t-20"><spring:message code="txt.infos.mention1.2.2"/></p>
					<p class="font-small m-t-10"><spring:message code="txt.infos.mention1.2.4"/><br><spring:message code="txt.infos.mention1.2.5"/><br><spring:message code="txt.infos.mention1.2.6"/></p>
				</div>
			</div>
		</div>
		<p class="m-t-20 m-l-20"><spring:message code="txt.infos.mention1.3.6"/></p>
		<div class="m-t-20 m-l-20">
			<p class="font-bold"><spring:message code="txt.infos.mention1.4"/> :</p>
			<p class="font-small m-t-10"><a href="mailto:<spring:message code="app.support" />" 
				class="lien lien-table"><spring:message code="app.support" /></a><span class="m-l-5"><spring:message code="txt.infos.mention1.4.1"/></span></p>
			<p class="font-small"><a href="mailto:<spring:message code="app.contact" />" 
				class="lien lien-table"><spring:message code="app.contact" /></a><span class="m-l-5"><spring:message code="txt.infos.mention1.4.2"/></span></p>
		</div>
		<h2 class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.mention1.5"/></h2>
		<div class="m-t-20 m-l-20">
			<p><spring:message code="txt.infos.mention1.5.1"/></p>
			<p class="m-t-20"><spring:message code="txt.infos.mention1.5.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.mention1.5.3"/></p>
		</div>
		<h2 id="cookiesSection" class="h-doc h-header3 i-primary m-t-40"><spring:message code="txt.infos.mention2.1"/></h2>
		<p class="m-t-20 m-l-20"><spring:message code="txt.infos.mention2.1.1"/></p>
		<div class="m-t-30 m-l-20">
			<h3 class="h-header h-header4 i-primary"><spring:message code="txt.infos.mention2.2"/></h3>
			<p class="m-t-20"><spring:message code="txt.infos.mention2.2.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.mention2.2.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.mention2.2.3"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.mention2.2.4"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.mention2.2.5"/></p>
		</div>
		<div class="m-t-30 m-l-20">
			<h3 class="h-header h-header4 i-primary"><spring:message code="txt.infos.mention2.3"/></h3>
			<p class="m-t-20"><spring:message code="txt.infos.mention2.3.1"/> :</p>
			<ul class="m-t-20">
				<c:forEach var="i" begin="1" end="5" step="1">
					<li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.mention2.3.${i}.1"/></span> : <spring:message code="txt.infos.mention2.3.${i}.2"/></li>
				</c:forEach>
			</ul>
			<p class="m-t-20"><spring:message code="txt.infos.mention2.3.6"/></p>
			<ul class="m-t-20">
				<c:forEach var="i" begin="6" end="7" step="1">
					<li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.mention2.3.${i}.1"/></span> : <spring:message code="txt.infos.mention2.3.${i}.2"/></li>
				</c:forEach>
			</ul>
		</div>
		<div class="m-t-30 m-b-40 m-l-20">
			<h3 class="h-header h-header4 i-primary"><spring:message code="txt.infos.mention2.4"/></h3>
			<p class="m-t-20"><spring:message code="txt.infos.mention2.4.1"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.mention2.4.2"/></p>
			<p class="m-t-10"><spring:message code="txt.infos.mention2.4.3"/></p>
			<p class="m-t-20"><spring:message code="txt.infos.mention2.5"/> :</p>
			<ul class="font-small m-t-20">
				<c:forEach var="i" begin="1" end="5" step="1">
					<c:set var="lien" scope="page"><spring:message code="txt.infos.mention2.5.${i}.2"/></c:set>
					<li class="m-b-10"><span class="font-bold"><spring:message code="txt.infos.mention2.5.${i}.1"/></span> : <a href="<c:url value="${pageScope.lien}"/>" 
						class="lien lien-table" target="_blank"><spring:message code="txt.infos.mention2.5.${i}.2"/></a></li>
				</c:forEach>
			</ul>
			<c:set var="lien" scope="page"><spring:message code="txt.infos.mention2.6.1"/></c:set>
			<p class="m-t-20"><spring:message code="txt.infos.mention2.6"/> : <a href="<c:url value="${pageScope.lien}"/>" 
						class="lien lien-table" target="_blank"><spring:message code="txt.infos.mention2.6.1"/></a></p>
		</div>
	</div>
</div>