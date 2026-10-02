<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">4. <spring:message code="wizard.marketplace.order1"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.marketplace4.2"/></p>
<hr class="my-4">
<spring:bind path="orderCredit"><form:input type="hidden" path="orderCredit" /></spring:bind>
<spring:bind path="orderFormule"><form:input type="hidden" path="orderFormule" /></spring:bind>
<div id="packForm" class="form-group m-t-20">
	<div class="form-container m-auto">
		<div class="row" data-toggle="buttons">
			<c:forEach var="i" begin="1" end="4" step="1">
				<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
					<div class="form-order m-auto">
						<div class="header-order font-small ${i == 3 ? 'header-segond' : ''}"><spring:message code="chose.promote.order${i}"/></div>
						<div class="credit-order text-truncate text-center">
							<strong><c:out value="${order.getFormattedCredit(i - 1)}" /></strong> <spring:message code="tool.order.view"/>
						</div>
						<div class="formule-order text-center">
							<div class="inline-flexed text-truncate">
								<span class="order-value"><c:out value="${order.getFormattedFormule(i - 1)}" /> <spring:message code="tool.order.devise"/></span>
								<span class="order-taxe"><spring:message code="tool.order.ht"/></span>
							</div>
						</div>
						<span class="help-text text-center"><spring:message code="tool.order.help" arguments="${order.getEstimattedFormule(i - 1)}" /></span>
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
<p class="font-mini i-help m-b-20"><spring:message code="txt.company.marketplace4.3"/></p>