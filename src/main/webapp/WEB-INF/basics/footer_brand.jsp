<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<hr class="my-2">
<div class="row">
	<div class="col-md-6 m-t-20">
		<h3 class="h-doc h-footer"><spring:message code="header.explorer.footer5"/></h3>
		<div class="widget-footer widget-column">
			<p class="h-header font-small m-b-20"><spring:message code="txt.help.explorer5.1"/></p>
			<c:import url="/WEB-INF/basics/sidebar_social.jsp"/>
		</div>
	</div>
	<div class="col-md-6 m-t-20">
		<h3 class="h-doc h-footer"><spring:message code="header.explorer.footer6"/></h3>
		<div class="widget-footer widget-column">
			<p class="h-header font-small m-b-20"><spring:message code="txt.help.explorer5.2"/></p>
			<form name="formNewsletter" action="/" method="POST" enctype="utf8" novalidate="novalidate">
				<div id="newsletterForm" class="form-group m-b-0">
					<div class="input-group-newsletter">
						<c:set var="faholder" scope="page"><spring:message code="lbl.newsletter" /></c:set>
						<input class="form-control form-simple" type="email" id="newsletter" name="newsletter" placeholder="${pageScope.faholder}"/>
						<div id="submitNewsletterForm" class="form-submit">
							<button type="submit" class="btn btn-segond btn-explorer-segond btn-submit btn-add btn-right">
								<span><i class="cmsms-icon-explorer-arrow"></i><spring:message code="header.register.user"/></span>
							</button>
						</div>
					</div>
					<span class="error"></span>
				</div>
			</form>
		</div>
	</div>
</div>