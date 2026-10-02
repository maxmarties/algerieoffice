<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="container">
	<div class="row mt-md-40 mt-mdup-20">
		<div class="col-md-6 col-lg-8 m-b-20">
			<div class="login-screen background-container flexed flex-colone flex-jusitify"
				style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 50%, rgba(24,31,42,.9) 100%), url('<c:url value="/static/picts/images/login-min.jpg" />');">
				<div>
					<h1 class="h-header h-header1 i-white sh-black"><spring:message code="header.confirm2"/></h1>
					<p class="i-gray m-t-20"><span class="m-r-5"><spring:message code="txt.confirm2"/></span><a href="mailto:<spring:message code="app.support" />" 
						class="lien lien-hover lien-segond1 font-bold"><spring:message code="app.support"/></a>.</p>
				</div>
				<div><c:import url="/WEB-INF/basics/footer_social.jsp"/></div>
			</div>
		</div>
		<div class="col-md-6 col-lg-4 m-b-20">
			<div class="login-form">
				<h2 class="h-header h-header3 i-primary"><spring:message code="header.login.update"/></h2>
				<p class="m-t-10"><spring:message code="txt.login3"/> :</p>
				<hr class="my-4">
				<sec:authorize access="hasAuthority('PASSWORD_PRIVILEGE')">
					<form:form name="updateForm" action="/" method="POST" modelAttribute="pass" enctype="utf8" novalidate="novalidate">
						<spring:bind path="password">
							<div id="passwordForm" class="form-group">
								<form:label class="col-form-label" path="password">
									<spring:message code="lbl.password"/> <small class="min"><spring:message code="lbl.requis"/></small>
								</form:label>
								<form:input class="form-control" type="password" path="password" value=""/>
								<span class="error"></span>
							</div>
						</spring:bind>
						<spring:bind path="matching">
							<div id="matchingForm" class="form-group">
								<form:label class="col-form-label" path="matching">
									<spring:message code="lbl.matches"/> <small class="min"><spring:message code="lbl.requis"/></small>
								</form:label>
								<form:input class="form-control" type="password" path="matching" value=""/>
								<span class="error"></span>
							</div>
						</spring:bind>
						<div id="submitForm" class="form-submit form-block m-t-10">
							<button type="submit" class="btn btn-primary btn-submit btn-block"><span><spring:message code="btn.update"/></span></button>
						</div>
					</form:form>
				</sec:authorize>
				<hr class="my-4">
				<p class="m-t-10"><a href="<c:url value="/users/login"/>" class="lien lien-hover lien-primary lien-small"><spring:message code="lien.login"/></a></p>
			</div>
		</div>
	</div>
</div>