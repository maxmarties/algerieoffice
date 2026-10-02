<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black" title="<spring:message code="tooltip.forums.menu" />"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/forums"/>" class="lien lien-black"><spring:message code="sidebar.home.mainfooter1.12"/></a></li>
		<li class="active"><spring:message code="btn.${empty topic.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
	<div class="container m-t-20">
		<h2 class="h-header h-header2 i-primary"><spring:message code="header.forums.${empty topic.id ? 'new' : 'edit'}"/></h2>
		<p class="m-t-5"><spring:message code="txt.forums.topic"/></p>
		<hr class="my-1">
		<form:form name="topicForm" action="/" method="POST" modelAttribute="topic" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
			<spring:bind path="category">
				<div id="categoryForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="category">
						<spring:message code="tabs.category" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.topic1.1" /></span>
					</form:label>
					<div class="col-md-4 col-lg-3">
						<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
						<form:select class="form-select2-simple" path="category" data-placeholder="${pageScope.faholder}" disabled="${!empty topic.id}">
							<option></option>
							<c:forEach var="i" begin="1" end="10" step="1">
								<option value="${i}" ${topic.category == i ? 'selected' : ''}><spring:message code="chose.topic.category${i}" /></option>
							</c:forEach>
						</form:select>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="title">
				<div id="titleForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="title">
						<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.topic1.2" /></span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="title" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="detail"><form:input type="hidden" path="detail" /></spring:bind>
			<div id="editorForm" class="form-group form-editor row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text"><spring:message code="txt.help.topic1.3" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<nav class="navbar navbar-editor">
						<ul class="nav">
							<li>
								<button id="resetEditor" class="btn btn-editor btn-simple ${empty topic.detail ? 'disabled' : ''}" 
									type="button" title="<spring:message code="tooltip.reset"/>">
									<i class="cmsms-icon-arrows-cw i-red"></i>
								</button>
							</li>
						</ul>
						<ul class="nav ml-auto">
							<li class="m-r-5">
								<button id="previewEditor" class="btn btn-editor btn-simple" type="button" title="<spring:message code="btn.preview"/>">
									<i class="cmsms-icon-search-6"></i>
								</button>
							</li>
							<li>
								<button id="editEditor" class="btn btn-editor btn-simple disabled" type="button" title="<spring:message code="btn.editor"/>">
									<i class="cmsms-icon-edit-1"></i>
								</button>
							</li>
						</ul>
					</nav>
					<div id="editor"><c:if test="${!empty topic.id}"><c:out value="${topic.detail}" escapeXml="false" /></c:if></div>
					<span class="error"></span>
				</div>
			</div>
			<spring:bind path="language">
				<div id="languageForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="language">
						<spring:message code="tooltip.langage" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.topic1.4" /></span>
					</form:label>
					<div class="col-md-4 col-lg-3">
						<c:set var="chosers" value="fr,en,ar" scope="page"></c:set>
						<c:set var="providers" value="Français,English,العربية" scope="page"></c:set>
						<form:select class="form-select2-simple" path="language" >
							<option></option>
							<c:forEach var="choser" items="${pageScope.chosers}" varStatus="state">
								<option value="${pageScope.choser}" ${topic.language == pageScope.choser ? 'selected' : ''}><c:out value="${pageScope.providers.split(',')[state.count - 1]}"/></option>
							</c:forEach>
						</form:select>
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<div class="form-group row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4 m-t-20">
					<spring:bind path="hasQuiz">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="hasQuiz" />
							<span class="input-span"></span><spring:message code="comp.quiz" />
						</label>
					</spring:bind>
					<spring:bind path="proposals"><form:input type="hidden" path="proposals" /></spring:bind>
					<div id="proposalsForm" class="form-group animated fadeInUp m-t-20 m-b-0" style="${!topic.hasQuiz ? 'display:none;' : ''}">
						<table id="tableItems" class="table table-proposal ${!topic.proposals.isEmpty() ? 'm-b-20' : ''}">
							<thead>
								<tr>
									<th style="width:26px;"></th><th style="width:calc(100% - 94px);"></th>
									<th style="width:34px;"></th><th style="width:34px;"></th>
								</tr>
							</thead>
							<tbody class="font-small">
								<c:forEach var="proposal" items="${topic.proposals}" varStatus="state">
									<tr id="lineItem${state.count}">
										<td><i class="cmsms-icon-ok-circled i-help"></i></td>
										<td class="i-input"><c:out value="${proposal}"/></td>
										<td class="btn-td"></td>
										<td class="btn-td"></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<input type="hidden" id="countItems" value="${topic.proposals.size()}" />
						<c:if test="${empty topic.id}">
							<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left">
								<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.quiz"/></span>
							</a>
							<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
						</c:if>
						<span class="error"></span>
					</div>
				</div>
			</div>
			<div class="form-group row m-b-40">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4 m-t-20">
					<div id="submitForm" class="form-submit m-t-10">
						<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty topic.id ? 'save' : 'update'}"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</sec:authorize>