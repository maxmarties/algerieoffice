<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:if test="${!currentConfig.cookieCollapse}">
	<div id="privacyCenter" class="privacy-center animated fadeInUp hidden-sm-down">
		<h2 class="h-header h-header4 i-primary"><spring:message code="txt.infos.privacy1.1"/></h2>
		<div class="row">
			<div class="col-sm-6 col-md-8 m-t-10">
				<p class="font-mini">
					<spring:message code="txt.infos.privacy1.2"/><a href="<c:url value="/infos/mentions-legales?sect=cookies"/>" class="lien lien-table lien-underline m-l-5" 
					target="_blank"><spring:message code="lien.privacy"/></a>.
				</p>
			</div>
			<div class="col-sm-6 col-md-4 m-t-10">
				<a id="privacyAccept" class="btn btn-primary btn-block"><span style="padding:10px;"><spring:message code="txt.infos.privacy1.3"/></span></a>
				<hr class="m-t-10 m-b-10">
				<i class="cmsms-icon-explorer-angle m-r-10"></i><a id="privacyMore" class="lien lien-segond lien-hover font-bold"><spring:message code="txt.infos.privacy1.4"/></a>
			</div>
		</div>
	</div>
	<c:import url="/WEB-INF/fields/modals/modal_privacy.jsp"/>
</c:if>