<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/marketplace/promotes"/>" class="lien lien-black">
			<i class="cmsms-icon-pin-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li><a href="<c:url value="/admin/marketplace/sponsores"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4.3"/></a></li>
		<li class="active"><spring:message code="btn.${empty sponsore.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.marketplace.promote.${empty sponsore.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.sponsore"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/marketplace/sponsores" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-lg-9">
			<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
				<c:if test="${!empty sponsore.bannerURL}">
					<div class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3"><spring:message code="tabs.photo" /></label>
						<div class="col-md-8 col-lg-9"><img src="<c:url value="${sponsore.bannerURL}"/>"></div>
					</div>
				</c:if>
				<form:form name="sponsoreForm" action="/" method="POST" modelAttribute="sponsore" enctype="multipart/form-data" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="url">
						<div id="urlForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="url">
								<spring:message code="tabs.url" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.marketplace4.3" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<form:input class="form-control" type="text" path="url" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="type">
						<div id="typeForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="type">
								<spring:message code="tabs.type" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
								<form:select class="form-select2-simple" path="type" data-placeholder="${pageScope.faholder}">
									<option></option>
									<c:forEach var="i" begin="1" end="3" step="1">
										<option value="${i}" ${sponsore.type == i ? 'selected' : ''}><spring:message code="chose.sponsore${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="creditCount">
						<div id="creditCountForm" class="form-group row">
							<form:label class="col-form-label col-md-4 col-lg-3" path="creditCount">
								<spring:message code="tabs.credit" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</form:label>
							<div class="col-md-4 col-lg-3">
								<form:input class="form-control" type="number" path="creditCount" />
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="hasFile"><form:input type="hidden" path="hasFile" /></spring:bind>
					<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
					<div id="inputFileForm" class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.photo" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.marketplace4.2" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<div class="file-input">
								<label class="btn btn-file btn-simple btn-fixed m-r-10" for="inputFile">
									<input type="file" class="sr-only" id="inputFile" name="inputFile" accept="image/*">
									<span><spring:message code="btn.file"/></span>
								</label>
								<label id="resultFile">
									<c:choose>
										<c:when test="${sponsore.hasFile}"><c:out value="${sponsore.filename}" /></c:when>
										<c:otherwise><spring:message code="tooltip.file" /></c:otherwise>
									</c:choose>
								</label>
								<a id="clearFile" class="btn btn-table btn-red" style="${sponsore.hasFile ? '' : 'display:none;'}" 
									title="<spring:message code="btn.delete.file"/>">
									<i class="cmsms-icon-trash-7"></i>
								</a>
							</div>
							<span class="error"></span>
						</div>
					</div>
					<spring:bind path="filename"><form:input type="hidden" path="filename" /></spring:bind>
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
					<div class="form-group row m-b-20">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<hr class="my-4">
							<div id="submitForm" class="form-submit m-t-20">
								<button type="submit" class="btn btn-primary btn-submit btn-fixed">
									<span><spring:message code="btn.${empty sponsore.id ? 'save' : 'update'}"/></span></button>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>		
		</div>
	</div>
</div>