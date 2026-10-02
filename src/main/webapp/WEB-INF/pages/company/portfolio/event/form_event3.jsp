<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<h2 class="h-header h-header5 i-primary font-normal">3. <spring:message code="wizard.portfolio.event3"/></h2>
<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
<spring:bind path="eventsDate"><form:input type="hidden" path="eventsDate" /></spring:bind>
<spring:bind path="clocksOpen"><form:input type="hidden" path="clocksOpen" /></spring:bind>
<spring:bind path="clocksClose"><form:input type="hidden" path="clocksClose" /></spring:bind>
<spring:bind path="wilayas"><form:input type="hidden" path="wilayas" /></spring:bind>
<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
<spring:bind path="updateCalendar"><form:input type="hidden" path="updateCalendar" /></spring:bind>
<div id="itemsForm" class="form-group row">
	<label class="col-form-label col-md-4 col-lg-3">
		<spring:message code="lbl.dates" /> <small class="min"><spring:message code="lbl.requis" /></small>
		<span class="help-text"><spring:message code="txt.help.portfolio3.5" /></span>
	</label>
	<div class="col-md-8 col-lg-9">
		<table id="tableItems" class="table table-page m-t-10 ${!event.eventsDate.isEmpty() ? 'm-b-20' : ''}">
			<thead>
				<tr>
					<th style="width:26px;"></th><th style="width:25%;"></th>
					<th style="width:25%;"></th><th style="width:calc(50% - 94px);"></th>
					<th style="width:34px;"></th><th style="width:34px;"></th>
				</tr>
			</thead>
			<tbody class="font-small">
				<c:forEach var="itemDate" items="${event.eventsDate}" varStatus="state">
					<tr id="lineItem${state.count}" data-ident="${event.idents.get(state.count - 1)}">
						<td><i class="cmsms-icon-calendar-7 i-help"></i></td>
						<td class="i-input"><c:out value="${itemDate}" /></td>
						<td><c:out value="${event.parseClock(state.count - 1)}" /></td>
						<td><spring:message code="chose.wilaya${event.wilayas.get(state.count - 1)}" /></td>
						<td class="btn-td">
							<a class="btn btn-table btn-yellow" title="<spring:message code="btn.edit" />" 
								onclick="editLineItem('${state.count}');"><i class="cmsms-icon-pencil-5"></i></a>
						</td>
						<td class="btn-td">
							<a class="btn btn-table btn-red" title="<spring:message code="btn.delete" />" 
								onclick="deleteLineItem('${state.count}');"><i class="cmsms-icon-trash-7"></i></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<span class="block"><span class="error"></span></span>
		<input type="hidden" id="countItems" value="${event.eventsDate.size()}" />
		<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${event.eventsDate.size() == 10 ? 'disabled' : ''}">
			<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.date"/></span>
		</a>
		<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
		<c:set var="chosesWilaya" scope="page">
			<c:forEach var="i" begin="1" end="48" step="1"><spring:message code="chose.wilaya${i}" /><c:out value="${i < 48 ? ',' : ''}"/></c:forEach>
		</c:set>
		<input type="hidden" id="chosesWilaya" value="${pageScope.chosesWilaya}" />
		<hr class="my-4">
	</div>
</div>
<div class="row m-t-20 m-b-20">
	<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
	<div class="col-md-8 col-lg-9">
		<spring:bind path="hasNotified">
			<label class="ui-checkbox ui-checkbox-segond font-small">
				<form:checkbox path="hasNotified" />
				<span class="input-span"></span><spring:message code="comp.portfolio3.${empty event.id ? '1' : '2'}" />
			</label>
		</spring:bind>
	</div>
</div>