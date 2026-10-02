<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/repports/testimonial"/>" class="lien lien-black">
			<i class="cmsms-icon-bug m-r-5"></i><spring:message code="sidebar.user.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard6.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.user.dashboard6.3"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.repport3.1"/></p>
	</div>
	<div class="form-container">
		<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
			<form:form name="problemForm" action="/" method="POST" modelAttribute="problem" enctype="multipart/form-data" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="type">
					<div id="typeForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="type">
							<spring:message code="lbl.sub.problem1" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.problem1.1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
							<form:select class="form-select2-simple" path="type" data-placeholder="${pageScope.faholder}">
								<option></option>
								<c:forEach var="i" begin="1" end="5" step="1">
									<option value="${i}"><spring:message code="chose.problem.type${i}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="message">
					<div id="messageForm" class="form-group row m-b-20">
						<form:label class="col-form-label col-md-4 col-lg-3" path="message">
							<spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.problem1.2" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:textarea class="form-control form-area" rows="5" path="message" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="hasFile"><form:input type="hidden" path="hasFile" /></spring:bind>
				<div id="fileForm" class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="tabs.filereader" />
						<span class="help-text"><spring:message code="txt.help.problem1.3" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="file-input">
							<label class="btn btn-file btn-simple btn-fixed m-r-10" for="inputFile">
								<input type="file" class="sr-only" id="inputFile" name="inputFile" accept="image/*">
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
				<div class="form-group row m-b-20">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-paper-plane-3"></i><spring:message code="btn.send"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</sec:authorize>
	</div>
</div>