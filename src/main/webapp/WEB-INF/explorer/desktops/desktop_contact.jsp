<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<form:form name="contactForm" action="/" method="POST" modelAttribute="explorerContact" enctype="utf8" novalidate="novalidate">
	<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
	<span class="font-small"><span class="i-red">*</span> <spring:message code="tool.explorer.requis" /></span>
	<spring:bind path="pro">
		<div id="proForm" class="form-group row">
			<label class="col-form-label col-md-4">
				<spring:message code="lbl.sub.pro" /> <small class="min">*</small>
			</label>
			<div class="col-md-8">
				<ul class="nav">
					<li class="m-r-10"><spring:message code="lbl.sub.pro1" /></li>
					<li><label class="ui-switch ui-switch-segond">
						<form:checkbox path="pro" /><span class="input-span"></span><span class="layer-span"></span>
					</label></li>
					<li class="m-l-10"><spring:message code="lbl.sub.pro2" /></li>
				</ul>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="object">
		<div id="objectForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="object">
				<spring:message code="lbl.sub.object" /> <small class="min">*</small>
			</form:label>
			<div class="col-md-8">
				<c:set var="faholder" scope="page"><spring:message code="chose.contact" /></c:set>
				<form:select class="form-select2-simple" path="object" data-placeholder="${pageScope.faholder}">
					<option></option>
					<c:forEach var="i" begin="1" end="5" step="1">
						<option value="${i}" ${explorerContact.object == i ? 'selected' : ''}><spring:message code="chose.contact${i}" /></option>
					</c:forEach>
				</form:select>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="sexe">
		<div id="sexeForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="sexe">
				<spring:message code="tabs.sexe" /> <small class="min">*</small>
			</form:label>
			<div class="col-md-8">
				<label class="ui-radio ui-radio-segond font-small m-r-10">
					<form:radiobutton value="${true}" path="sexe" /><span class="input-span"></span><spring:message code="chose.sexe1" />
				</label>
				<label class="ui-radio ui-radio-segond font-small">
					<form:radiobutton value="${false}" path="sexe" /><span class="input-span"></span><spring:message code="chose.sexe2" />
				</label>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="lastname">
		<div id="lastnameForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="lastname"><spring:message code="lbl.lastname" /> <small class="min">*</small></form:label>
			<div class="col-md-8"><form:input class="form-control" type="text" path="lastname" /><span class="error"></span></div>
		</div>
	</spring:bind>
	<spring:bind path="firstname">
		<div id="firstnameForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="firstname"><spring:message code="lbl.firstname" /> <small class="min">*</small></form:label>
			<div class="col-md-8"><form:input class="form-control" type="text" path="firstname" /><span class="error"></span></div>
		</div>
	</spring:bind>
	<spring:bind path="function">
		<div id="functionForm" class="form-group row" style="${!explorerContact.pro ? 'display:none;' : ''}">
			<form:label class="col-form-label col-md-4" path="function"><spring:message code="tabs.function" /></form:label>
			<div class="col-md-8"><form:input class="form-control" type="text" path="function" /><span class="error"></span></div>
		</div>
	</spring:bind>
	<spring:bind path="phone">
		<div id="phoneForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="phone"><spring:message code="tabs.phone" /> <small class="min">*</small></form:label>
			<div class="col-md-8"><form:input class="form-control" type="tel" path="phone" /><span class="error"></span></div>
		</div>
	</spring:bind>
	<spring:bind path="email">
		<div id="emailForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="email"><spring:message code="tabs.email" /> <small class="min">*</small></form:label>
			<div class="col-md-8"><form:input class="form-control" type="email" path="email" /><span class="error"></span></div>
		</div>
	</spring:bind>
	<spring:bind path="postal">
		<div id="postalForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="postal"><spring:message code="lbl.postal" /> <small class="min">*</small></form:label>
			<div class="col-md-4"><form:input class="form-control" type="text" path="postal" /><span class="error"></span></div>
		</div>
	</spring:bind>
	<spring:bind path="message">
		<div id="messageForm" class="form-group row">
			<form:label class="col-form-label col-md-4" path="message"><spring:message code="tabs.message" /> <small class="min">*</small></form:label>
			<div class="col-md-8"><form:textarea class="form-control form-area" rows="4" path="message" /><span class="error"></span></div>
		</div>
	</spring:bind>
	<sec:authorize access="isAnonymous()">
		<div id="g-recaptchaForm" class="form-group row" style="min-height:100px">
			<label class="col-form-label col-md-4"><spring:message code="lbl.recaptcha"/> <small class="min">*</small></label>
			<div class="col-md-8">
				<div class="g-recaptcha" data-sitekey="<c:out value="${recaptchaSiteKey}" />" 
					data-callback="onReCaptchaSuccess" data-expired-callback="onReCaptchaExpired"></div>
				<span class="error"></span>
			</div>
		</div>
	</sec:authorize>
	<div class="form-group row m-t-0">
		<div class="col-md-4 hidden-sm-down"></div>
		<div class="col-md-8">
			<hr class="my-2">
			<div id="submitForm" class="form-submit m-b-10">
				<button type="submit" class="btn btn-explorer-segond btn-submit btn-add btn-left">
					<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.send"/></span>
				</button>
			</div>
		</div>
	</div>
	<hr class="my-4">
	<p class="font-mini"><spring:message code="txt.help.explorer1.7"/>
		<a href="<c:url value="/infos/politique-confidentialite" />" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.confident"/></a>.</p>
</form:form>