<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">5. <spring:message code="wizard.marketplace.order2"/></h2>
<p class="font-small m-t-20"><spring:message code="txt.company.marketplace5.1"/></p>
<hr class="my-4">
<div class="table-container m-t-20">
	<div class="table-responsive">
		<table class="table table-order">
			<thead>
				<tr>
					<c:set var="cols" value="46,14,20,20" scope="page"></c:set>
					<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
						<th class="${state.count != 1 ? 'text-center' : ''}" style="width:${pageScope.col}%;">
							<spring:message code="tabs.order${state.count}"/>
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<spring:message code="sidebar.company.dashboard2.1"/>
						<c:forEach var="i" begin="1" end="4" step="1">
							<span id="overviewCredit${i}" class="help-text creditOverview" style="display:none;">
								<spring:message code="overview.promote.order${i}" arguments="${order.orderCredit.get(i - 1)}" /></span>
						</c:forEach>
					</td>
					<td class="text-center"><c:out value="1"/></td>
					<td class="formuleOverview text-center"></td>
					<td class="formuleOverview text-center"></td>
				</tr>
				<tr class="footer-tr">
					<td><spring:message code="tool.order.total"/></td>
					<td class="text-center"><c:out value="1"/></td>
					<td class="formuleOverview text-center"></td>
					<td class="formuleOverview text-center"></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<hr class="my-4">
<h3 class="h-header h-header5 i-primary m-t-10"><spring:message code="subheader.order"/></h3>
<div class="row m-t-20">
	<div class="col-sm-2 m-b-20 hidden-xs-down">
		<div class="card-order"><img class="img-responsive" src="<c:url value="/static/vectors/order-min.jpg"/>" alt="algerie-poste"></div>
	</div>
	<div class="col-sm-10 m-b-20">
		<div id="inputFileForm" class="form-group row m-t-10">
			<label class="col-form-label col-md-4 col-lg-3">
				<spring:message code="lbl.sub.order1" /> <small class="min"><spring:message code="lbl.requis" /></small>
				<span class="help-text"><spring:message code="txt.help.building" /></span>
			</label>
			<div class="col-md-8 col-lg-9">
				<div class="file-input">
					<label class="btn btn-file btn-simple btn-fixed m-r-10" for="inputFile">
						<input type="file" class="sr-only" id="inputFile" name="inputFile" accept="image/*">
						<span><spring:message code="btn.file"/></span>
					</label>
					<label id="resultFile"><spring:message code="tooltip.file" /></label>
				</div>
				<span class="error"></span>
			</div>
		</div>
		<hr class="my-4 m-b-20">
		<a id="insertOrder" class="btn btn-success btn-simple btn-add btn-left">
			<span><i class="cmsms-icon-down-open"></i><spring:message code="lbl.sub.order2"/></span>
		</a>
		<div id="insertFormOrder" class="form-group b-gray animated onne fadeIn" style="display:none;">
			<div class="page-header m-t-10">
				<h4 class="h-header h-header5 i-primary"><spring:message code="lbl.sub.order3"/></h4>
				<p class="font-small"><spring:message code="txt.company.marketplace5.2.1"/></p>
			</div>
			<hr class="my-4">
			<table class="table m-b-10">
				<thead><tr><th style="width:30%;"></th><th style="width:70%;"></th></tr></thead>
				<tbody class="font-small">
					<c:forEach var="i" begin="1" end="4" step="1">
						<tr><td><spring:message code="lbl.sub.order2.${i}"/></td><td class="font-bold"><spring:message code="app.order.ccp${i}"/></td></tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<hr class="my-2">
<p class="font-mini i-help m-b-20"><spring:message code="txt.company.marketplace5.2"/></p>