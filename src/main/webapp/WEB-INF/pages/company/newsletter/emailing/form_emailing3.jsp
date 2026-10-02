<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">3. <spring:message code="wizard.newsletter.emailing3"/></h2>
<spring:bind path="contact">
	<div id="contactForm" class="form-group row" data-toggle="buttons">
		<form:label class="col-form-label col-md-4 col-lg-3" path="type">
			<spring:message code="lbl.sub.newsletter1.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.newsletter3.3.1" /></span>
		</form:label>
		<div class="col-md-8 col-lg-6">
			<ul class="nav nav-campaign m-b-10">
				<c:set var="providers" value="user-2,commerical-building,floppy,gmail" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<li style="width:25%;">
						<label class="btn btn-campaign btn-simple m-auto" style="min-height:120px;">
							<i class="cmsms-icon-${pageScope.provider} i-24"></i>
							<span class="help-text m-t-10"><spring:message code="wizard.newsletter.emailing2.${state.count}"/></span>
							<form:radiobutton class="hidden" value="${state.count}" path="contact"/>
						</label>
					</li>
				</c:forEach>
			</ul>
			<span class="error"></span>
			<hr class="my-4">
		</div>
	</div>
</spring:bind>
<spring:bind path="contacts"><form:input type="hidden" path="contacts" /></spring:bind>
<div id="usersForm" class="form-group row animated onne fadeIn" style="display:none;">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="wizard.newsletter.emailing2.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.newsletter3.3.2.1" /></span>
	</label>
	<div class="col-md-8 col-lg-6">
		<div class="campaign-load">
			<c:import url="/WEB-INF/basics/loading_span.jsp"/>
			<div id="usersLoad"></div>
		</div>
		<span class="error"></span>
	</div>
</div>
<div id="companiesForm" class="form-group row animated onne fadeIn" style="display:none;">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="wizard.newsletter.emailing2.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.newsletter3.3.2.2" /></span>
	</label>
	<div class="col-md-8 col-lg-6">
		<h3 class="h-header h-header6"><spring:message code="txt.help.newsletter3.3.3"/></h3>
		<div class="row row-mini m-t-10">
			<div class="col-md-6 col-mini m-t-10">
				<spring:bind path="sector">
					<div id="sectorForm" class="form-group m-b-0">
						<form:label class="col-form-label" path="sector">
							<spring:message code="lbl.sub.annonce1" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<c:set var="faholder" scope="page"><spring:message code="chose.sector" /></c:set>
						<form:select class="form-select2" path="sector" data-placeholder="${pageScope.faholder}">
							<option></option>
							<option value="32"><spring:message code="chose.sector.all" /></option>
							<c:forEach var="i" begin="1" end="31"><option value="${i}"><spring:message code="chose.sector${i}" /></option></c:forEach>
						</form:select>
						<span class="error"></span>
					</div>
				</spring:bind>
			</div>
			<div class="col-md-6 col-mini m-t-10">
				<spring:bind path="wilaya">
					<div id="wilayaForm" class="form-group m-b-0">
						<form:label class="col-form-label" path="wilaya">
							<spring:message code="lbl.contract.location" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<c:set var="faholder" scope="page"><spring:message code="tooltip.search.location" /></c:set>
						<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.faholder}">
							<option></option>
							<option value="49"><spring:message code="chose.wilaya.all" /></option>
							<c:forEach var="i" begin="1" end="48">
								<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
							</c:forEach>
						</form:select>
						<span class="error"></span>
					</div>
				</spring:bind>
			</div>
		</div>
		<div class="row row-mini">
			<div class="col-md-8 col-mini m-t-5">
				<p id="resultCompanies" class="font-small m-t-10"></p>
			</div>
			<div class="col-md-4 col-mini text-right m-t-5">
				<div id="submitCompaniesForm" class="form-submit">
					<a id="uploadCompnies" class="btn btn-success btn-simple btn-submit btn-add btn-right">
						<span><i class="cmsms-icon-filter"></i><spring:message code="btn.export"/></span>
					</a>
				</div>
			</div>
		</div>
		<hr class="my-4">
		<div class="campaign-load" style="display:none;">
			<c:import url="/WEB-INF/basics/loading_span.jsp"/>
			<div id="companiesLoad"></div>
		</div>
	</div>
</div>
<div id="easylistForm" class="form-group row animated onne fadeIn" style="display:none;">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="wizard.newsletter.emailing2.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.newsletter3.3.2.4" /></span>
	</label>
	<div class="col-md-8 col-lg-6">
		<h3 class="h-header h-header6"><spring:message code="txt.help.newsletter3.3.4"/></h3>
		<spring:bind path="easyid">
			<div id="easyidForm" class="form-group m-t-10 m-b-0">
				<form:label class="col-form-label" path="easyid">
					<spring:message code="tabs.easylist" /> <small class="min"><spring:message code="lbl.requis" /></small>
				</form:label>
				<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
				<form:select class="form-select2" path="easyid" data-placeholder="${pageScope.faholder}">
					<option></option>
					<c:forEach var="easylist" items="${easylists}" ><option value="${easylist.uuid()}"><c:out value="${easylist.name}" /></option></c:forEach>
				</form:select>
				<span class="error"></span>
			</div>
		</spring:bind>
		<div class="row row-mini">
			<div class="col-md-8 col-mini m-t-5">
				<p id="resultEasylist" class="font-small m-t-10"></p>
			</div>
			<div class="col-md-4 col-mini text-right m-t-5">
				<div id="submitEasylistForm" class="form-submit">
					<a id="uploadEasylist" class="btn btn-success btn-simple btn-submit btn-add btn-right">
						<span><i class="cmsms-icon-filter"></i><spring:message code="btn.export"/></span>
					</a>
				</div>
			</div>
		</div>
		<hr class="my-4">
		<div class="campaign-load" style="display:none;">
			<c:import url="/WEB-INF/basics/loading_span.jsp"/>
			<div id="easylistLoad"></div>
		</div>
	</div>
</div>
<spring:bind path="custom">
	<div id="customForm" class="form-group row animated onne fadeIn" style="display:none;">
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="wizard.newsletter.emailing2.4" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.newsletter3.3.2.3" /></span>
		</label>
		<div class="col-md-8 col-lg-6">
			<form:textarea class="form-control form-area" rows="8" path="custom"/>
			<span class="error"></span>
			<span class="help-text m-t-10"><span class="font-bold countCustom">0</span> <spring:message code="tool.find.emailing" /></span>
		</div>
	</div>
</spring:bind>
