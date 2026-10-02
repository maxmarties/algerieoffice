<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">2. <spring:message code="wizard.tools.subscribe2"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.tools4.3.2"/></p>
<hr class="my-4">
<spring:bind path="monthly">
	<div id="monthlyForm" class="form-group row">
		<form:label class="col-form-label col-md-4 col-lg-3" path="monthly">
			<spring:message code="tabs.monthly" /> <small class="min"><spring:message code="lbl.requis" /></small>
			<span class="help-text"><spring:message code="txt.help.subscribe3.1" /></span>
		</form:label>
		<div class="col-md-6 col-lg-3">
			<c:set var="faholder" scope="page"><spring:message code="chose.monthly" /></c:set>
			<form:select class="form-select2-simple" path="monthly" data-placeholder="${pageScope.faholder}">
				<option></option>
				<c:forEach var="i" begin="1" end="4" step="1">
					<option value="${i}"><spring:message code="chose.monthly${i}" /></option>
				</c:forEach>
			</form:select>
			<span class="error"></span>
			<p id="orderOffer" class="font-small m-t-10" style="display:none;"><i class="cmsms-icon-gift i-dollar m-r-10"></i><spring:message code="txt.help.subscribe3.2" /></p>
		</div>
	</div>
</spring:bind>
<div class="form-group row">
	<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
	<div class="col-md-8 col-lg-9">
		<hr class="my-4">
		<div class="table-responsive">
			<table class="table table-page table-console">
				<thead><tr><th style="width:52%;"></th><th style="width:24%;"></th><th style="width:24%;"></th></tr></thead>
				<tbody class="font-small">
					<tr class="console-header i-primary">
						<td><spring:message code="tabs.order1" /></td><td><spring:message code="tabs.order5" /></td><td><spring:message code="tabs.order3" /></td>
					</tr>
					<tr>
						<td class="font-bold"><spring:message code="overview.subscribe.order1" /></td>
						<td>
							<c:forEach var="i" begin="1" end="4" step="1">
								<span class="overviewPack overviewPack${i}" style="display:none;"><spring:message code="overview.subscribe.order${i + 1}" /></span>
							</c:forEach>
						</td>
						<td class="formuleOverview"></td>
					</tr>
					<tr>
						<td class="font-bold"><spring:message code="tabs.monthly" /></td>
						<td class="i-segond">
							<c:forEach var="i" begin="1" end="4" step="1">
								<span class="overviewMonthly overviewMonthly${i}" style="display:none;"><spring:message code="overview.monthly${i}" /></span>
							</c:forEach>
						</td>
						<td class="totalOverview"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<hr class="my-2 m-t-40">
<p class="font-mini i-help m-b-20"><spring:message code="txt.company.tools4.4.3"/></p>