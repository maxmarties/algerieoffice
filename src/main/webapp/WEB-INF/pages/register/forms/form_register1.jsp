<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">1. <spring:message code="subheader.register2.1"/></h2>
<div class="form-group m-t-10">
	<h3 class="h-header h-header4 i-primary"><spring:message code="subheader.register1.1"/> :</h3>
	<c:set var="providers" value="facebook,google,linkedin" scope="page"></c:set>
	<c:forEach var="provider" items="${pageScope.providers}">
		<a class="btn btn-social btn-${pageScope.provider} m-r-10 m-t-10" title="${pageScope.provider}"
			data-toggle="social" data-social="${pageScope.provider}">
			<i class="cmsms-icon-${pageScope.provider}"></i>
		</a>
	</c:forEach>
</div>
<div class="social-auth-hr font-small m-t-20 m-b-20"><span><spring:message code="lbl.or"/></span></div>
<h3 class="h-header h-header4 i-primary"><spring:message code="subheader.register1.2"/> :</h3>
<div class="row m-t-10">
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
		<form:input class="form-control" type="email" path="email" onchange="updateMail(this.value);" />
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
<spring:bind path="hasAccepte">
	<div class="form-group m-t-10">
		<label class="ui-checkbox ui-checkbox-segond font-small">
			<form:checkbox path="hasAccepte" />
			<span class="input-span"></span><spring:message code="comp.news" />
		</label>
	</div>
</spring:bind>