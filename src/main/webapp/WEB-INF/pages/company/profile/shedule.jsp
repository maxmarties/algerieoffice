<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/profile/identity"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.company.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard6.3" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard6.3"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.profile3"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
		<form:form name="sheduleForm" action="/" method="POST" modelAttribute="shedule" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="email">
				<div id="emailForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="email">
						<spring:message code="lbl.companymail" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text">
							<spring:message code="txt.help.shedulemail" />
							<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.shedulemail" />"></i>
						</span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<div class="input-group-contact">
							<i class="cmsms-icon-mail-alt icon-contact"></i>
							<form:input class="form-control" type="email" path="email" />
						</div>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="phone">
				<div id="phoneForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="phone">
						<spring:message code="tabs.phone" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.phone" /></span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<div class="input-group-phone">
							<c:set var="faholder" scope="page"><spring:message code="tool.ind.phone" /></c:set>
							<span class="input-icon"><c:out value="+213"/></span>
							<form:input class="form-control" type="tel" path="phone" placeholder="${pageScope.faholder}" />
						</div>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="fax">
				<div id="faxForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="fax">
						<spring:message code="tabs.fax" />
					</form:label>
					<div class="col-md-8 col-lg-9">
						<div class="input-group-phone">
							<span class="input-icon"><c:out value="+213"/></span>
							<form:input class="form-control" type="tel" path="fax" placeholder="${pageScope.faholder}" />
						</div>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="mobile">
				<div id="mobileForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="mobile">
						<spring:message code="tabs.mobile" />
					</form:label>
					<div class="col-md-8 col-lg-9">
						<div class="input-group-phone">
							<c:set var="faholder" scope="page"><spring:message code="tool.ind.mobile" /></c:set>
							<span class="input-icon"><c:out value="+213"/></span>
							<form:input class="form-control" type="tel" path="mobile" placeholder="${pageScope.faholder}" />
						</div>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.shedule" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text"><spring:message code="txt.help.shedule" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<c:forEach var="i" begin="1" end="7" step="1">
						<spring:bind path="days[${i - 1}]">
							<div id="days${i}Form" class="form-group row ${i == 1 ? 'm-t-0' : 'm-t-10'}">
								<form:label class="col-form-label col-sm-2" path="days[${i - 1}].stateday">
									<spring:message code="chose.day${i}" />
								</form:label>
								<div class="col-sm-10">
									<div>
										<c:forEach var="j" begin="1" end="3" step="1">
											<label class="ui-radio ui-radio-segond font-small ${j != 3 ? 'm-r-20' : ''}">
												<form:radiobutton value="${j - 1}" path="days[${i - 1}].stateday" data-attribut="days" data-days="${i}" />
												<span class="input-span"></span><spring:message code="lbl.sub.shedule1.${j}" />
											</label>
										</c:forEach>
									</div>
									<div class="row row-xs-down">
										<c:forEach var="j" begin="1" end="4" step="1">
											<div class="col-3 col-xs-down m-t-5" data-attribut="day${i}_${j < 3 ? '1' : '2'}" 
												style="${(j < 3 && shedule.days[i - 1].stateday == 0) || (j > 2 && shedule.days[i - 1].stateday != 2) ? 'display:none;' : ''}">
												<label class="font-bold"><spring:message code="lbl.sub.shedule2.${j}" /></label>
												<form:select class="form-select2-simple" path="days[${i - 1}].timeday[${j - 1}]" data-attribut="times" 
													data-time="${i}" data-shedule="${i}_${j}">
													<option></option>
													<c:forEach var="time" begin="8" end="23" step="1">
														<option value="${time}" ${shedule.days[i - 1].timeday[j - 1] == time ? 'selected' : ''}><c:out value="${time < 10 ? '0' : ''}${time}:00"/></option>
													</c:forEach>
													<option value="24" ${shedule.days[i - 1].timeday[j - 1] == 24 ? 'selected' : ''}><c:out value="00:00"/></option>
												</form:select>
											</div>
										</c:forEach>
									</div>
									<span class="error"></span>
									<c:if test="${i < 7}"><hr class="my-4"></c:if>
								</div>
							</div>
						</spring:bind>
					</c:forEach>
				</div>
			</div>
			<div class="form-group row m-b-20">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4">
					<div id="submitForm" class="form-submit m-t-20">
						<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.profile3"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<div class="form-group">
				<table class="table table-page table-panel">
					<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
					<tbody class="font-small">
						<tr><td><spring:message code="wizard.regsiter1.3" /></td><td id="emailOverview"><c:out value="${shedule.email}" /></td></tr>
						<tr><td><spring:message code="tabs.phone" /></td><td id="phoneOverview"><c:out value="${shedule.getFormattedPhone()}" /></td></tr>
						<tr><td><spring:message code="tabs.fax" /></td><td id="faxOverview"><c:out value="${shedule.getFormattedFax()}" /></td></tr>
						<tr><td><spring:message code="tabs.mobile" /></td><td id="mobileOverview"><c:out value="${shedule.getFormattedMobile()}" /></td></tr>
					</tbody>
				</table>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-20"><spring:message code="lbl.shedule"/></h3>
			<div class="form-group m-t-10 m-b-20">
				<table class="table table-page table-panel">
					<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
					<tbody class="font-small">
						<c:forEach var="i" begin="1" end="7" step="1">
							<tr>
								<td><spring:message code="chose.day${i}" /></td>
								<td id="dayOverview${i}">
									<c:choose>
										<c:when test="${shedule.days[i - 1].stateday == 0}"><spring:message code="lbl.sub.shedule1.1" /></c:when>
										<c:otherwise><c:out value="${shedule.days[i - 1].getFormattedTime()}" /></c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>