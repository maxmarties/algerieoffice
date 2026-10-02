<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="container">
	<div class="row m-t-20">
		<div class="col-lg-8 m-b-20">
			<div class="register-form">
				<h1 class="h-header h-header2 i-primary m-t-10"><spring:message code="header.register1"/></h1>
				<p class="m-t-10"><spring:message code="txt.register1"/></p>
				<hr class="my-4">
				<div class="form-group" style="min-height:70px;">
					<h2 class="h-header h-header4 i-primary"><spring:message code="subheader.register1.1"/> :</h2>
					<c:set var="providers" value="facebook,google,linkedin" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}">
						<a class="btn btn-social btn-${pageScope.provider} m-r-10 m-t-10" title="${pageScope.provider}" 
							data-toggle="social" data-social="${pageScope.provider}">
							<i class="cmsms-icon-${pageScope.provider}"></i>
						</a>
					</c:forEach>
				</div>
				<div class="social-auth-hr font-small m-t-20 m-b-20"><span><spring:message code="lbl.or"/></span></div>
				<h2 class="h-header h-header4 i-primary"><spring:message code="subheader.register1.2"/> :</h2>
				<form:form name="userForm" action="/" method="POST" modelAttribute="user" enctype="utf8" novalidate="novalidate">
					<div class="row m-t-20">
						<spring:bind path="firstname">
							<div id="firstnameForm" class="form-group col-md-6">
								<form:label class="col-form-label" path="firstname">
									<spring:message code="lbl.firstname" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<form:input class="form-control" type="text" path="firstname" />
								<span class="error"></span>
							</div>
						</spring:bind>
						<spring:bind path="lastname">
							<div id="lastnameForm" class="form-group col-md-6">
								<form:label class="col-form-label" path="lastname">
									<spring:message code="lbl.lastname" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</form:label>
								<form:input class="form-control" type="text" path="lastname" />
								<span class="error"></span>
							</div>
						</spring:bind>
					</div>
					<spring:bind path="email">
						<div id="emailForm" class="form-group">
							<form:label class="col-form-label" path="email">
								<spring:message code="tabs.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<form:input class="form-control" type="email" path="email" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<div class="row">
						<spring:bind path="password">
							<div id="passwordForm" class="form-group col-md-6">
								<form:label class="col-form-label" path="password">
									<spring:message code="lbl.password"/> <small class="min"><spring:message code="lbl.requis"/></small>
								</form:label>
								<form:input class="form-control" type="password" path="password" />
								<span class="error"></span>
								<span class="help-text"><spring:message code="txt.help.password" /></span>
							</div>
						</spring:bind>
						<div id="matchesForm" class="form-group col-md-6">
							<label class="col-form-label" for="matches">
								<spring:message code="lbl.matches" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</label>
							<input class="form-control" type="password" id="matches" name="matches" />
							<span class="error"></span>
						</div>
					</div>
					<div id="g-recaptchaForm" class="form-group" style="min-height:110px">
						<label class="col-form-label">
							<spring:message code="lbl.recaptcha"/> <small class="min"><spring:message code="lbl.requis"/></small>
						</label>
						<div class="g-recaptcha" data-sitekey="<c:out value="${recaptchaSiteKey}" />"
							data-callback="onReCaptchaSuccess" data-expired-callback="onReCaptchaExpired">
						</div>
						<span class="error"></span>
					</div>
					<spring:bind path="hasAccepte">
						<div class="form-group m-t-10">
				        	<label class="ui-checkbox ui-checkbox-segond font-small">
				            	<form:checkbox path="hasAccepte" />
								<span class="input-span"></span><spring:message code="comp.news" />
							</label>
	            		</div>
					</spring:bind>
					<div class="form-group text-right m-t-20">
						<div id="submitForm" class="form-submit">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-login-1"></i><spring:message code="btn.signin"/></span>
							</button>
						</div>
					</div>
				</form:form>
				<hr class="my-4">
				<p class="font-small m-t-20"><spring:message code="txt.help.register1"/>
					<a href="<c:url value="/infos/cgu" />" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.terme"/></a>.</p>
				<p class="font-small m-b-10"><spring:message code="txt.help.login"/>
					<a href="<c:url value="/users/login" />" class="lien lien-hover lien-primary"><spring:message code="btn.partner2"/></a> !</p>
			</div>
		</div>
		<div class="col-lg-4 m-b-20">
			<div class="register-screen background-container flexed flex-colone flex-jusitify"
				style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 50%, rgba(24,31,42,.9) 100%), url('<c:url value="/static/picts/images/register1-min.jpg" />');">
				<div class="register-user">
					<h3 class="h-header h-header3 i-white sh-black"><spring:message code="txt.register1.1"/></h3>
					<ul class="list-none list-block">
						<c:set var="providers" value="target-4,bell-4,heart-6,user,award" scope="page"></c:set>
						<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
							<li>
								<i class="cmsms-icon-${pageScope.provider} breadview-trigger pull-left"></i>
								<p class="font-small sh-black"><spring:message code="txt.register1.1.${state.count}"/></p>
								<span class="clearfix"></span>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="widget-more">
					<spring:message code="txt.register1.2"/>, <a href="<c:url value="/register/company"/>" 
						class="lien lien-hover lien-segond1 font-bold"><spring:message code="lien.partner"/></a>.
				</div>
			</div>
		</div>
	</div>
</div>