<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">1. <spring:message code="wizard.tools.subscribe1"/></h2>
<p class="font-small m-t-10"><spring:message code="txt.company.tools4.3.1"/></p>
<hr class="my-4">
<spring:bind path="prices"><form:input type="hidden" path="prices" /></spring:bind>
<div id="packForm" class="form-group m-t-20">
	<div class="form-container m-auto">
		<div class="row" data-toggle="buttons">
			<c:forEach var="i" begin="1" end="4" step="1">
				<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
					<div class="form-order m-auto">
						<div class="header-order font-small ${i == 3 ? 'header-segond' : ''}"><spring:message code="chose.subscribe.order${i + 1}"/></div>
						<div class="credit-order text-truncate text-center"><spring:message code="tool.order.subscribe1"/></div>
						<div class="formule-order text-center">
							<div class="inline-flexed text-truncate">
								<span class="order-value"><c:out value="${subscribe.getFormattedPrice(i - 1)}" /> <spring:message code="tool.order.devise"/></span>
								<span class="order-taxe"><spring:message code="tool.order.ht"/></span>
							</div>
						</div>
						<span class="help-text text-center"><spring:message code="tool.order.subscribe2" /></span>
						<div class="footer-order">
							<label class="btn btn-radio btn-block btn-add btn-left">
								<span><i class="cmsms-icon-ok-5"></i><spring:message code="chose.label" /></span>
								<form:radiobutton class="hidden" value="${i}" id="pack${i}" path="pack"/>
							</label>
						</div>
					</div>
					<hr class="my-1">
					<span class="help-text">
						<spring:message code="txt.help.subscribe1.${i}" /> 
						<c:if test="${i == 3}"><span class="i-segond"><spring:message code="txt.help.subscribe3.3" /></span></c:if>
					</span>
				</div>
			</c:forEach>
		</div>
	</div>
	<span class="error"></span>
</div>
<div class="text-center m-t-30">
	<a id="insertDetail" class="btn btn-file btn-simple btn-add btn-right">
		<span><i class="cmsms-icon-down-open"></i><spring:message code="lbl.sub.order4.1"/></span>
	</a>
</div>
<div id="insertFormDetail" class="form-group b-gray animated onne fadeIn" style="display:none;">
	<div class="page-header m-t-10">
		<h4 class="h-header h-header4 i-primary"><spring:message code="lbl.sub.order4.2"/></h4>
		<p class="font-small"><spring:message code="txt.company.tools4.4.2"/></p>
	</div>
	<hr class="my-4">
	<div class="table-responsive">
		<table class="table table-page table-console">
			<thead>
				<tr>
					<th style="width:calc(100% - 700px);"></th><th style="width:140px;"></th><th style="width:140px;"></th>
					<th style="width:140px;"></th><th style="width:140px;"></th><th style="width:140px;"></th>
				</tr>
			</thead>
			<tbody class="font-small">
				<tr class="console-header i-primary">
					<td><spring:message code="subheader.subscribe1" /></td>
					<c:forEach var="i" begin="1" end="5" step="1">
						<td class="${i == 4 ? 'td-premium td-border' : ''}"><spring:message code="chose.subscribe.order${i}" /></td>
					</c:forEach>
				</tr>
				<c:forEach var="i" begin="1" end="3" step="1">
					<tr>
						<td><spring:message code="subheader.subscribe1.${i}" /></td>
						<c:forEach var="j" begin="1" end="5" step="1">
							<td class="${j == 4 ? 'td-premium' : ''}">
								<c:choose>
									<c:when test="${i == 3 && j == 1}"><span class="i-help"><c:out value="--"/></span></c:when>
									<c:otherwise><i class="cmsms-icon-ok-circled-1 i-segond i-18"></i></c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr class="console-header i-primary">
					<td><spring:message code="subheader.subscribe2" /></td>
					<c:forEach var="i" begin="1" end="5" step="1">
						<td class="${i == 4 ? 'td-premium' : ''}"><spring:message code="chose.subscribe.order${i}" /></td>
					</c:forEach>
				</tr>
				<tr>
					<td><spring:message code="subheader.subscribe2.1" /></td>
					<c:set var="providers" value="4,8,12,16,20" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<td class="${state.count == 4 ? 'td-premium' : ''}"><c:out value="${pageScope.provider}"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td><spring:message code="subheader.subscribe2.2" /></td>
					<c:set var="providers" value="3,10,200,1000,2500" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<td class="${state.count == 4 ? 'td-premium' : ''}"><c:out value="${pageScope.provider}"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td><spring:message code="subheader.subscribe2.3" /></td>
					<c:set var="providers" value="3,10,50,250,500" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<td class="${state.count == 4 ? 'td-premium' : ''}"><c:out value="${pageScope.provider}"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td><spring:message code="subheader.subscribe2.4" /></td>
					<c:set var="providers" value="3,10,50,500,1000" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<td class="${state.count == 4 ? 'td-premium' : ''}"><c:out value="${pageScope.provider}"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td><spring:message code="subheader.subscribe2.5" /></td>
					<c:set var="providers" value="30 MB,200 MB,500 MB,2 GB,5 GB" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<td class="${state.count == 4 ? 'td-premium' : ''}"><c:out value="${pageScope.provider}"/></td>
					</c:forEach>
				</tr>
				<tr class="console-header i-primary">
					<td><spring:message code="subheader.subscribe3" /></td>
					<c:forEach var="i" begin="1" end="5" step="1">
						<td class="${i == 4 ? 'td-premium' : ''}"><spring:message code="chose.subscribe.order${i}" /></td>
					</c:forEach>
				</tr>
				<c:forEach var="i" begin="1" end="4">
					<tr>
						<td><spring:message code="subheader.subscribe3.${i}" /></td>
						<c:forEach var="j" begin="1" end="5" step="1">
							<td class="${j == 4 ? 'td-premium' : ''}">
								<c:choose>
									<c:when test="${j == 1 || (j == 2 && i == 4)}"><span class="i-help"><c:out value="--"/></span></c:when>
									<c:otherwise><i class="cmsms-icon-ok-circled-1 i-segond i-18"></i></c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr>
					<td><spring:message code="subheader.subscribe3.5" /></td>
					<c:set var="providers" value="3%,10%,30%,74%,100%" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<td class="${state.count == 4 ? 'td-premium' : ''}"><c:out value="${pageScope.provider}"/></td>
					</c:forEach>
				</tr>
				<tr class="console-header i-primary">
					<td><spring:message code="subheader.subscribe4" /></td>
					<c:forEach var="i" begin="1" end="5" step="1">
						<td class="${i == 4 ? 'td-premium' : ''}"><spring:message code="chose.subscribe.order${i}" /></td>
					</c:forEach>
				</tr>
				<c:forEach var="i" begin="1" end="4">
					<tr>
						<td><spring:message code="subheader.subscribe4.${i}" /></td>
						<c:forEach var="j" begin="1" end="5" step="1">
							<td class="${j == 4 ? 'td-premium' : ''}">
								<c:choose>
									<c:when test="${j == 5 || (j == 4 && i <= 2)}"><i class="cmsms-icon-ok-circled-1 i-segond i-18"></i></c:when>
									<c:otherwise><span class="i-help"><c:out value="--"/></span></c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr class="console-header i-primary">
					<td><spring:message code="subheader.subscribe5" /></td>
					<c:forEach var="i" begin="1" end="5" step="1">
						<td class="${i == 4 ? 'td-premium' : ''}"><spring:message code="chose.subscribe.order${i}" /></td>
					</c:forEach>
				</tr>
				<tr>
					<td>
						<spring:message code="subheader.subscribe5.1" />
						<span class="help-text"><spring:message code="subheader.subscribe5.2" /></span>
					</td>
					<td><span class="i-help"><c:out value="--"/></span></td>
					<c:forEach var="i" begin="1" end="4" step="1">
						<td class="h-header font-bold ${i == 3 ? 'td-premium' : ''} i-18">
							<c:out value="${subscribe.getFormattedPrice(i - 1)}" /> <spring:message code="tool.order.devise"/>
						</td>
					</c:forEach>
				</tr>
				<tr class="console-footer">
					<td></td>
					<c:forEach var="i" begin="1" end="5" step="1">
						<td><a class="lien lien-hover lien-primary ${i == 1 ? 'disabled' : 'iSubscribe'}" data-subscribe="${i}"><spring:message code="subheader.subscribe5" /></a></td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<p class="font-small text-center m-t-20"><spring:message code="txt.help.subscribe2.1"/> <a href="#" class="lien lien-hover lien-primary" target="_blank">
	<spring:message code="lien.subscribe"/></a> <spring:message code="txt.help.subscribe2.2"/></p>
<hr class="my-2 m-t-30">
<p class="font-mini i-help m-b-20"><spring:message code="txt.company.tools4.4.1"/></p>