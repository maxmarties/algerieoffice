<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="container">
	<div class="row mt-md-40">
		<div class="col-md-6 col-lg-8 m-b-20">
			<div class="login-screen background-container flexed flex-colone flex-jusitify"
				style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 50%, rgba(24,31,42,.9) 100%), url('<c:url value="/static/picts/images/login-min.jpg" />');">
				<div>
					<h1 class="h-header h-header1 i-white sh-black"><spring:message code="header.login1"/></h1>
					<p class="i-gray m-t-20"><spring:message code="txt.login1"/></p>
					<hr class="my-4 i-light">
					<p class="font-small i-white m-t-20"><span class="m-r-5"><spring:message code="txt.login1.2"/></span><a href="<c:url value="/register/user"/>" 
						class="lien lien-hover lien-segond1 font-bold"><spring:message code="btn.partner1"/></a></p>
					<p class="font-small i-white"><span class="m-r-5"><spring:message code="txt.login1.3"/></span><a href="<c:url value="/register/company"/>" 
						class="lien lien-hover lien-segond1 font-bold"><spring:message code="lien.partner"/></a></p>
				</div>
				<div><c:import url="/WEB-INF/basics/footer_social.jsp"/></div>
			</div>
		</div>
		<div class="col-md-6 col-lg-4 m-b-20">
			<div class="login-form">
				<h2 class="h-header h-header3 i-primary"><spring:message code="btn.login"/></h2>
				<p class="m-t-10"><spring:message code="txt.login1.1"/></p>
				<hr class="my-4">
				<div class="form-group">
					<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.login1"/> :</h3>
					<c:set var="providers" value="facebook,google,linkedin" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}">
						<a class="btn btn-social btn-${pageScope.provider} m-r-10 m-t-10" title="${pageScope.provider}"
							data-toggle="social" data-social="${pageScope.provider}">
							<i class="cmsms-icon-${pageScope.provider}"></i>
						</a>
					</c:forEach>
				</div>
				<div class="social-auth-hr font-small m-b-10"><span><spring:message code="lbl.or"/></span></div>
				<h3 class="h-header h-header5 i-primary"><spring:message code="subheader.login2"/> :</h3>
				<form name='form' action="<c:url value="/users/login"/>" method='POST' novalidate="novalidate" onsubmit="return isValidForm();">
					<div id="usernameForm" class="form-group m-t-10">
						<label class="col-form-label" for="username">
			            	<spring:message code="lbl.login.email"/> <small class="min"><spring:message code="lbl.requis" /></small>
            			</label>
            			<input class="form-control" type="email" id="username" name="username" value="" />
            			<span class="error"></span>
					</div>
					<div id="passwordForm" class="form-group">
						<label class="col-form-label" for="password">
			            	<spring:message code="lbl.login.password"/> <small class="min"><spring:message code="lbl.requis" /></small>
            			</label>
            			<input class="form-control" type="password" id="password" name="password" />
            			<span class="error"></span>
					</div>
					<div class="form-group">
			        	<label class="ui-checkbox ui-checkbox-segond font-small" for="remember-me">
			            	<input type="checkbox" id="remember-me" name="remember-me">
							<span class="input-span"></span><spring:message code="comp.rememberme" />
						</label>
            		</div>
					<div class="form-group m-t-10">
            			<button type="submit" class="btn btn-primary btn-block"><span><spring:message code="btn.partner2"/></span></button>
            		</div>
				</form>
				<hr class="my-4">
				<p class="m-t-10"><a href="<c:url value="/users/resend-token"/>" class="lien lien-hover lien-primary lien-small">
					<spring:message code="lien.confirm"/></a></p>
				<p><a href="<c:url value="/users/reset-password"/>" class="lien lien-hover lien-primary lien-small">
					<spring:message code="lien.password"/></a></p>
			</div>
		</div>
	</div>
	<div class="bn-login text-center hidden-md-down m-t-30">
		<ul class="navbar-nav nav-flex-icons">
			<c:set var="providers" value="message,bell-3,time,lock-3,settings-alt,lifebuoy-3" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li class="transition-35">
					<i class="cmsms-icon-${pageScope.provider} i-28"></i>
					<span class="font-mini"><spring:message code="tool.anonym.login1.${state.count}"/></span>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>