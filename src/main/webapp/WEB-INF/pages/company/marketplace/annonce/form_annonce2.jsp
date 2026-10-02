<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">2. <spring:message code="wizard.marketplace.annonce2"/></h2>
<spring:bind path="type">
	<div id="typeForm" class="form-group row">
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="tabs.type" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace2.1" /></span>
		</label>
		<div class="col-md-8 col-lg-9">
			<div>
				<c:forEach var="i" begin="1" end="5" step="1">
					<label class="ui-radio ui-radio-segond font-small m-r-20">
						<form:radiobutton value="${i}" path="type" />
						<span class="input-span"></span><spring:message code="chose.annonce${i}" />
					</label>
				</c:forEach>
			</div>
			<span class="error"></span>
		</div>
	</div>
</spring:bind>
<div id="createForm" class="animated onne fadeIn m-b-0" style="${empty annonce.id ? 'display:none;' : ''}">
	<spring:bind path="title">
		<div id="titleForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="title">
				<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.marketplace2.2" /></span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<form:input class="form-control" type="text" path="title" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="identify">
		<div id="identifyForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="identify">
				<spring:message code="lbl.identify" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text">
					<spring:message code="txt.help.url" />
					<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.identify" />"></i>
				</span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<div class="input-group">
					<div class="input-group-lien">
						<c:out value="${!empty currentCompany.url ? currentCompany.url : '@'}"/><c:out value="${urlMarket}"/>
					</div>
					<form:input class="form-control" type="text" path="identify" />
				</div>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<div id="dateForm" class="form-group row">
		<label class="col-form-label col-md-4 col-lg-3">
			<spring:message code="tabs.period" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.marketplace2.3" /></span>
		</label>
		<div class="col-md-8 col-lg-6">
			<div class="row">
				<spring:bind path="startDate">
					<div id="startDateForm" class="form-group col-md-6">
						<div class="input-group-icon date">
							<c:set var="faholder" scope="page"><spring:message code="tabs.explorer.dateOn" /></c:set>
							<form:input class="form-control" type="text" path="startDate" placeholder="${pageScope.faholder}" />
							<span class="input-group-addon" style="display:none;"></span>
						</div>
						<span class="error"></span>
					</div>
				</spring:bind>
				<spring:bind path="endDate">
					<div id="endDateForm" class="form-group col-md-6">
						<div class="input-group-icon date">
							<c:set var="faholder" scope="page"><spring:message code="tabs.explorer.dateOff" /></c:set>
							<form:input class="form-control" type="text" path="endDate" placeholder="${pageScope.faholder}" />
							<span class="input-group-addon" style="display:none;"></span>
						</div>
						<span class="error"></span>
					</div>
				</spring:bind>
			</div>
		</div>
	</div>
	<input type="hidden" id="maxkeysword" value="${maxkeysword}" />
	<spring:bind path="keysword">
		<div id="keyswordForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="keysword">
				<spring:message code="tabs.keys" />
				<span class="help-text"><spring:message code="txt.help.keys" arguments="${maxkeysword}" /></span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<form:input class="form-control" type="text" path="keysword" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="urlExtern">
		<div id="urlExternForm" class="form-group row">
			<form:label class="col-form-label col-md-4 col-lg-3" path="urlExtern">
				<spring:message code="lbl.externurl" />
				<span class="help-text"><spring:message code="txt.help.marketplace2.4" /></span>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<c:set var="faholder" scope="page"><spring:message code="tool.ind.annonce" /></c:set>
				<form:input class="form-control" type="url" path="urlExtern" placeholder="${pageScope.faholder}" />
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
	<spring:bind path="hasPublished">
		<div id="hasPublishedForm" class="form-group row m-b-20">
			<form:label class="col-form-label col-md-4 col-lg-3" path="hasPublished">
				<spring:message code="tabs.state" /> <small class="min"><spring:message code="lbl.requis" /></small>
			</form:label>
			<div class="col-md-8 col-lg-9">
				<label class="ui-radio ui-radio-segond font-small m-r-10">
					<form:radiobutton value="${true}" path="hasPublished" />
					<span class="input-span"></span><spring:message code="chose.published1" />
				</label>
				<label class="ui-radio ui-radio-segond font-small">
					<form:radiobutton value="${false}" path="hasPublished" />
					<span class="input-span"></span><spring:message code="chose.published2" />
				</label>
				<span class="error"></span>
			</div>
		</div>
	</spring:bind>
</div>