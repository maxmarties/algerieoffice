<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">1. <spring:message code="wizard.marketplace.order1"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.newsletter2.2"/></p>
<hr class="my-4">
<spring:bind path="budgetEmails"><form:input type="hidden" path="budgetEmails" /></spring:bind>
<spring:bind path="budgetSending"><form:input type="hidden" path="budgetSending" /></spring:bind>
<spring:bind path="budgetFormule"><form:input type="hidden" path="budgetFormule" /></spring:bind>
<div id="packForm" class="form-group m-t-20">
	<div class="form-container m-auto">
		<div class="row" data-toggle="buttons">
			<c:forEach var="i" begin="1" end="4" step="1">
				<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
					<div class="form-order m-auto">
						<div class="header-order font-small ${i == 3 ? 'header-segond' : ''}"><spring:message code="chose.promote.order${i}"/></div>
						<div class="credit-order text-truncate text-center">
							<p><strong><c:out value="${order.getFormattedEmails(i - 1)}" /></strong> <spring:message code="tool.newsletter.budget1.1"/></p>
							<p><strong><c:out value="${order.getFormattedSending(i - 1)}" /></strong> <spring:message code="tool.newsletter.budget1.2"/></p>
						</div>
						<div class="formule-order text-center">
							<div class="inline-flexed text-truncate">
								<span class="order-value"><c:out value="${order.getFormattedFormule(i - 1)}" /> <spring:message code="tool.order.devise"/></span>
								<span class="order-taxe"><spring:message code="tool.order.ht"/></span>
							</div>
						</div>
						<p class="help-text text-center">
							<span class="block"><spring:message code="tool.order.budget1" arguments="${order.getEstimattedEmails(i - 1)}" /></span>
							<span class="block"><spring:message code="tool.order.budget2" arguments="${order.getEstimattedSending(i - 1)}" /></span>
						</p>
						<div class="footer-order">
							<label class="btn btn-radio btn-block btn-add btn-left">
								<span><i class="cmsms-icon-ok-5"></i><spring:message code="chose.label" /></span>
								<form:radiobutton class="hidden" value="${i}" path="pack"/>
							</label>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<span class="error"></span>
</div>
<hr class="my-2 m-t-30">
<p class="font-mini i-help m-b-20"><spring:message code="txt.company.newsletter2.3"/></p>