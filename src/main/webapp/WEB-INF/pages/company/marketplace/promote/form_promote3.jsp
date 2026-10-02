<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">3. <spring:message code="wizard.marketplace.promote3"/></h2>
<div class="row m-t-20">
	<div class="col-md-6 m-b-20">
		<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.marketplace3.1"/></h3>
		<p class="font-small m-t-10"><spring:message code="txt.company.marketplace1.2.1"/></p>
		<hr class="my-4">
		<div class="form-group m-t-20">
			<label class="col-form-label"><spring:message code="lbl.sub.marketplace1.2" /> :</label>
			<ul class="font-small m-t-10">
				<c:forEach var="i" begin="1" end="31" step="1">
					<li id="sectorOverview${i}" style="${promote.inSectors(i) ? '' : 'display:none;'}"><spring:message code="chose.sector${i}"/></li>
				</c:forEach>
			</ul>
		</div>
		<div class="form-group m-t-20">
			<label class="col-form-label"><spring:message code="lbl.sub.marketplace1.3" /> :</label>
			<ul class="font-small m-t-10">
				<li id="targetOverview" style="${promote.wilayas.isEmpty() ? '' : 'display:none;'}"><spring:message code="comp.target"/></li>
				<c:forEach var="i" begin="1" end="48" step="1">
					<li id="targetOverview${i}" style="${promote.inWilayas(i) ? '' : 'display:none;'}"><spring:message code="chose.wilaya${i}"/></li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="col-md-6 m-b-20">
		<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.marketplace3.2"/></h3>
		<p class="font-small m-t-10"><spring:message code="txt.company.marketplace1.2.2"/></p>
		<hr class="my-4">
		<div class="bn-overview bn-body" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
			<div class="widget-promote bn-box m-auto">
				<img id="avatarOverview" class="img-responsive m-b-10" src="<c:url value="${promote.urlAvatar}"/>" 
					alt="<c:out value="${promote.title}" />" style="${promote.hasAvatar ? '' : 'display:none;'}">
				<h4 id="titleOverview" class="h-header h-headerAds i-primary"><c:out value="${promote.title}" /></h4>
				<p id="descriptionOverview" class="font-small m-t-5"><c:out value="${promote.description}" /></p>
				<hr>
				<div class="m-t-10">
					<a target="_blank" class="btn btn-segond btn-simple btn-block">
						<span id="hrefOverview"><spring:message code="chose.label${promote.label}"/></span></a>
				</div>
			</div>
		</div>
		<hr class="my-4">
		<p class="font-mini">
			<spring:message code="txt.company.marketplace1.3.1"/> 
			<a href="<c:url value="/infos/charte-bonnes-pratiques"/>" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.promote"/></a>. 
			<spring:message code="txt.company.marketplace1.3.2"/>
		</p>
	</div>
</div>