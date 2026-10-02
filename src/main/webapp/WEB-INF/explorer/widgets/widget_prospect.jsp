<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="widget-prospect ${!empty prospect ? 'open' : ''}">
	<c:set var="providers" value="bag,pin-1,calendar-7,coffee" scope="page"></c:set>
	<a id="insertForm" class="btn btn-document btn-simple btn-block ${explorerCurrent.hasPreview ? 'disabled' : ''}">
		<span>
			<i class="cmsms-icon-${pageScope.providers.split(',')[requestScope.documentBackword - 1]} m-r-10"></i>
			<spring:message code="overview.contact${requestScope.documentBackword}"/>
			<i class="breadview-trigger cmsms-icon-angle-down transition-35"></i>
		</span>
	</a>
	<c:if test="${!empty documentContact}">
		<div class="form-group animated onne fadeIn m-b-0">
			<form:form name="formDocument" action="/" method="POST" modelAttribute="documentContact" enctype="multipart/form-data" novalidate="novalidate">
				<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
				<spring:bind path="documentId"><form:input type="hidden" path="documentId" /></spring:bind>
				<spring:bind path="type"><form:input type="hidden" path="type" /></spring:bind>
				<spring:bind path="name">
					<div id="nameForm" class="form-group row m-t-10">
						<form:label class="col-form-label col-md-4" path="name">
							<spring:message code="tabs.username" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8"><form:input class="form-control" type="text" path="name" /><span class="error"></span></div>
					</div>
				</spring:bind>
				<spring:bind path="phone">
					<div id="phoneForm" class="form-group row">
						<form:label class="col-form-label col-md-4" path="phone"><spring:message code="tabs.phone" /></form:label>
						<div class="col-md-8"><form:input class="form-control" type="tel" path="phone" /><span class="error"></span></div>
					</div>
				</spring:bind>
				<spring:bind path="email">
					<div id="emailForm" class="form-group row">
						<form:label class="col-form-label col-md-4" path="email">
							<spring:message code="tabs.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8"><form:input class="form-control" type="email" path="email" /><span class="error"></span></div>
					</div>
				</spring:bind>
				<spring:bind path="message">
					<div id="messageForm" class="form-group row">
						<form:label class="col-form-label col-md-4" path="message">
							<spring:message code="tabs.message" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8"><form:textarea class="form-control form-area" rows="4" path="message" /><span class="error"></span></div>
					</div>
				</spring:bind>
				<spring:bind path="hasFile"><form:input type="hidden" path="hasFile" /></spring:bind>
				<div id="inputFileForm" class="form-group row">
					<label class="col-form-label col-md-4">
						<spring:message code="tabs.filereader" />
						<span class="help-text text-help"><spring:message code="txt.help.explorer3.8" /></span>
					</label>
					<div class="col-md-8">
						<div class="file-input">
							<label class="btn btn-file btn-simple btn-fixed m-r-10" for="inputFile">
								<input type="file" class="sr-only" id="inputFile" name="inputFile" accept="application/pdf">
								<span><spring:message code="btn.file"/></span>
							</label>
							<label id="resultFile"><spring:message code="tooltip.file" /></label>
							<a id="clearFile" class="btn btn-table btn-red" style="display:none;" title="<spring:message code="btn.delete.file"/>">
								<i class="cmsms-icon-trash-7"></i>
							</a>
						</div>
						<span class="error"></span>
					</div>
				</div>
				<sec:authorize access="isAnonymous()">
					<div id="g-recaptchaForm" class="form-group row" style="min-height:100px">
						<label class="col-form-label col-md-4">
							<spring:message code="lbl.recaptcha"/> <small class="min"><spring:message code="lbl.requis" /></small>
						</label>
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
							<button type="submit" class="btn btn-primary btn-explorer-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.send"/></span>
							</button>
						</div>
					</div>
				</div>
				<hr class="my-4">
				<p class="font-mini i-help text-help"><spring:message code="txt.help.explorer1.7"/> <a href="<c:url value="/infos/politique-confidentialite" />" 
					class="lien lien-hover lien-primary lien-explorer-primary" target="_blank"><spring:message code="lien.confident"/></a>.</p>
			</form:form>
		</div>
	</c:if>
</div>