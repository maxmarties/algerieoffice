<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="screen-contact">
	<div class="slider-body">
		<div class="container">
			<div class="row">
				<div class="col-lg-4">
					<div class="flexed flex-colone flex-jusitify h-100">
						<div class="slider-overlay">
							<h1 class="h-header h-header1 text-uppercase"><spring:message code="header.explorer.mainfooter4"/></h1>
							<p class="font-large i-white sh-black m-t-20"><spring:message code="seo.contacts"/></p>
							<div class="card-contacts m-t-40">
								<span class="btn-content img-circle pull-left">
									<a href="tel:+213560607498" class="btn btn-segond btn-simple"><span><i class="cmsms-icon-phone-3 breadview-trigger"></i></span></a>
								</span>
								<div class="card-brand">
									<p class="font-bold font-mini text-uppercase i-gray"><spring:message code="app.order.autor"/></p>
									<p class="h-header font-large font-bold"><spring:message code="app.mobile"/></p>
									<p class="font-small m-t-20 i-gray"><spring:message code="app.phone"/></p>
								</div>
								<span class="clearfix"></span>
							</div>
						</div>
						<div class="slider-social hidden-md-down"><c:import url="/WEB-INF/basics/footer_social.jsp"/></div>
					</div>
				</div>
				<div class="col-lg-8">
					<div class="slider-content">
						<form:form name="contactForm" action="/" method="POST" modelAttribute="contact" enctype="utf8" novalidate="novalidate">
							<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
							<div class="row">
								<div class="col-md-6">
									<spring:bind path="lastname">
										<div id="lastnameForm" class="form-group">
											<form:label class="col-form-label" path="lastname"><spring:message code="lbl.lastname" /> <small class="min">*</small></form:label>
											<form:input class="form-control" type="text" path="lastname" /><span class="error"></span>
										</div>
									</spring:bind>
								</div>
								<div class="col-md-6">
									<spring:bind path="firstname">
										<div id="firstnameForm" class="form-group">
											<form:label class="col-form-label" path="firstname"><spring:message code="lbl.firstname" /> <small class="min">*</small></form:label>
											<form:input class="form-control" type="text" path="firstname" /><span class="error"></span>
										</div>
									</spring:bind>
								</div>
								<div class="col-md-6">
									<spring:bind path="email">
										<div id="emailForm" class="form-group">
											<form:label class="col-form-label" path="email"><spring:message code="lbl.email" /> <small class="min">*</small></form:label>
											<form:input class="form-control" type="email" path="email" /><span class="error"></span>
										</div>
									</spring:bind>
								</div>
								<div class="col-md-6">
									<spring:bind path="phone">
										<div id="phoneForm" class="form-group">
											<form:label class="col-form-label" path="phone"><spring:message code="tabs.phone" /> <small class="min">*</small></form:label>
											<form:input class="form-control" type="tel" path="phone" /><span class="error"></span>
										</div>
									</spring:bind>
								</div>
							</div>
							<spring:bind path="company">
								<div id="companyForm" class="form-group">
									<form:label class="col-form-label" path="company"><spring:message code="explorer.home.title1" /></form:label>
									<form:input class="form-control" type="text" path="company" /><span class="error"></span>
								</div>
							</spring:bind>
							<spring:bind path="object">
								<div id="objectForm" class="form-group">
									<form:label class="col-form-label" path="object"><spring:message code="lbl.sub.request" /> <small class="min">*</small></form:label>
									<c:set var="faholder" scope="page"><spring:message code="chose.contact" /></c:set>
									<form:select class="form-select2-simple" path="object" data-placeholder="${pageScope.faholder}">
										<option></option>
										<c:forEach var="i" begin="1" end="3" step="1">
											<option value="${i}"><spring:message code="chose.request${i}" /></option>
										</c:forEach>
									</form:select>
									<span class="error"></span>
								</div>
							</spring:bind>
							<spring:bind path="message">
								<div id="messageForm" class="form-group">
									<form:label class="col-form-label" path="message"><spring:message code="tabs.message" /> <small class="min">*</small></form:label>
									<form:textarea class="form-control form-area" rows="3" path="message" /><span class="error"></span>
								</div>
							</spring:bind>
							<div id="g-recaptchaForm" class="form-group" style="min-height:100px">
								<label class="col-form-label"><spring:message code="lbl.recaptcha"/> <small class="min">*</small></label>
								<div class="g-recaptcha" data-sitekey="<c:out value="${recaptchaSiteKey}" />"
									data-callback="onReCaptchaSuccess" data-expired-callback="onReCaptchaExpired"></div>
								<span class="error"></span>
							</div>
							<hr class="my-1">
							<div class="form-group m-t-30">
								<div id="submitForm" class="form-submit form-block">
									<button type="submit" class="btn btn-segond btn-simple btn-submit btn-block btn-big"><span><spring:message code="btn.send"/></span></button>
								</div>
							</div>
							<hr class="m-t-30 m-b-10">
							<span class="font-small">* <spring:message code="tool.explorer.requis" /></span>
						</form:form>
					</div>
				</div>
			</div>
			<div class="slider-social hidden-md-up"><c:import url="/WEB-INF/basics/footer_social.jsp"/></div>
		</div>
	</div>
	<div class="slider-footer">
		<div class="slider-contact background-container"
			style="background-image: radial-gradient(circle at center, rgba(24,31,42,.7) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/contacts-min.jpg" />');"></div>
		<div class="bn-primary">
			<div class="container">
				<ul class="nav navcard-companies navcard-2">
					<li>
						<a>
							<i class="cmsms-icon-location-outline breadview-trigger pull-left"></i>
							<span class="brand-card">
								<span class="h-header text-nowrap"><spring:message code="app.location1"/></span>
								<span class="block font-mini text-truncate" style="line-height:14px;"><spring:message code="app.location2"/></span>
							</span>
							<span class="clearfix"></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>