<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<div class="row mt-md-40">
		<div class="col-lg-8 col-md-6 m-b-20">
			<div class="login-screen background-container flexed flex-colone flex-jusitify"
				style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/login-min.jpg" />');">
				<div>
					<h1 class="h-header h-header1 i-white sh-black"><spring:message code="header.confirm1"/></h1>
					<p class="i-gray m-t-20"><spring:message code="txt.confirm1"/></p>
				</div>
				<div><c:import url="/WEB-INF/basics/footer_social.jsp"/></div>
			</div>
		</div>
		<div class="col-lg-4 col-md-6 m-b-20">
			<div class="login-form">
				<h2 class="h-header h-header3 i-primary"><spring:message code="header.confirm1.1"/></h2>
				<p class="m-t-10"><spring:message code="txt.confirm1.1"/></p>
				<hr class="my-4">
				<div class="alert alert-success">
					<i class="cmsms-icon-mail-alt i-alert"></i>
					<p class="p-alert"><spring:message code="txt.confirm1.2"/> : <span class="i-select"><c:out value="${email}"/></span></p>
				</div>
				<span class="help-text m-t-20"><spring:message code="txt.confirm1.3"/></span>
				<div id="submitForm" class="form-submit form-block m-t-20">
					<button type="submit" class="btn btn-primary btn-submit btn-block"><span><spring:message code="lien.resendme"/></span></button>
				</div>
				<hr class="my-4">
				<p class="font-small m-t-20"><spring:message code="txt.confirm1.4"/>
					<a href="mailto:<spring:message code="app.contact" />" class="lien lien-hover lien-primary"><spring:message code="app.contact"/></a>.</p>
			</div>
		</div>
	</div>
</div>