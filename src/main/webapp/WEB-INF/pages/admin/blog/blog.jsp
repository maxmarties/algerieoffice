<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/blog/all"/>" class="lien lien-black">
			<i class="cmsms-icon-quote-right m-r-5"></i><spring:message code="sidebar.admin.dashboard5"/></a></li>
		<c:if test="${!empty blog.id}">
			<li><a href="<c:url value="/admin/blog/all"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard5.1"/></a></li>
		</c:if>
		<li class="active"><spring:message code="btn.${empty blog.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.blog.${!empty blog.id ? 'edit' : 'new'}"/></h1>
		<p><spring:message code="txt.admin.blog"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/blog/all" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<form:form name="blogForm" action="/" method="POST" modelAttribute="blog" enctype="multipart/form-data" novalidate="novalidate">
		<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
		<div class="row">
			<div class="col-lg-9">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.post1"/></h2>
				<spring:bind path="title">
					<div id="titleForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="title">
							<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.blog1.1" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.title" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="title" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="identify">
					<div id="identifyForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="identify">
							<spring:message code="lbl.identify" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.url" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.identify" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<div class="input-group">
								<div class="input-group-lien"><c:out value="${urlBlog}"/></div>
								<div class="input-group-icon input-group-check" data-input="">
									<form:input class="form-control" type="text" path="identify" />
								</div>
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="checkedIdentify"><form:input type="hidden" path="checkedIdentify" /></spring:bind>
				<spring:bind path="description">
					<div id="descriptionForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="description">
							<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.blog1.2" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.descrptif" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:textarea class="form-control form-area" rows="3" path="description" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="detail"><form:input type="hidden" path="detail" /></spring:bind>
				<div id="editorForm" class="form-group form-editor row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text">
							<spring:message code="txt.help.blog1.3" />
							<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.blogdetail" />"></i>
						</span>
					</label>
					<div class="col-md-8 col-lg-9">
						<nav class="navbar navbar-editor">
							<ul class="nav">
								<li>
									<button id="resetEditor" class="btn btn-editor btn-simple ${empty blog.detail ? 'disabled' : ''}" 
										type="button" title="<spring:message code="tooltip.reset"/>"><i class="cmsms-icon-arrows-cw i-red"></i>
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
						<div id="editor"><c:if test="${!empty blog.detail}"><c:out value="${blog.detail}" escapeXml="false" /></c:if></div>
						<span class="error"></span>
					</div>
				</div>
			</div>
			<div class="col-lg-3">
				<hr class="my-4 hidden-md-up m-b-20">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.post2"/></h2>
				<spring:bind path="hasPublished">
					<div id="hasPublishedForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-12" path="hasPublished">
							<spring:message code="tabs.state" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-4 col-lg-12">
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
				<spring:bind path="category">
					<div id="categoryForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-12" path="category">
							<spring:message code="tabs.family" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.blog1.4" /></span>
						</form:label>
						<div class="col-md-8 col-lg-12">
							<c:set var="faholder" scope="page"><spring:message code="chose.blog.family" /></c:set>
							<form:select class="form-select2-simple" path="category" data-placeholder="${pageScope.faholder}">
								<option></option>
								<c:forEach var="i" begin="1" end="10" step="1">
									<option value="${i}" ${blog.category == i ? 'selected' : ''}><spring:message code="chose.blog.family${i}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="autorId">
					<div id="autorIdForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-12" path="autorId">
							<spring:message code="tabs.autor" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.blog1.5" /></span>
						</form:label>
						<div class="col-md-4 col-lg-12">
							<form:select class="form-select2" path="autorId">
								<option></option>
								<c:forEach var="autor" items="${autors}">
									<option value="${autor.userId}" ${blog.autorId == autor.userId ? 'selected' : ''}><c:out value="${autor.email}" /></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="keysword">
					<div id="keyswordForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-12" path="keysword">
							<spring:message code="tabs.tags" />
							<span class="help-text">
								<spring:message code="txt.help.tags" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.tags" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-12">
							<form:input class="form-control" type="text" path="keysword" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="language">
					<div id="languageForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-12" path="language">
							<spring:message code="tooltip.langage" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.blog1.6" /></span>
						</form:label>
						<div class="col-md-4 col-lg-12">
							<c:set var="chosers" value="fr,en,ar" scope="page"></c:set>
							<c:set var="providers" value="Français,English,العربية" scope="page"></c:set>
							<form:select class="form-select2-simple" path="language" >
								<option></option>
								<c:forEach var="choser" items="${pageScope.chosers}" varStatus="state">
									<option value="${pageScope.choser}" ${blog.language == pageScope.choser ? 'selected' : ''}><c:out value="${pageScope.providers.split(',')[state.count - 1]}"/></option>
								</c:forEach>
							</form:select>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<hr class="my-4">
				<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.post3"/></h2>
				<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
				<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
				<div id="fileForm" class="form-group row">
					<label class="col-form-label col-md-4 col-lg-12">
						<spring:message code="lbl.photo" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.blog1.7" /></span>
					</label>
					<div class="col-md-8 col-lg-12">
						<div class="avatar-content avatar-actu">
							<div class="avatar-view">
								<img id="avatarImg" class="img-responsive transition-35"
									src="<c:url value="${blog.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
								<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
							</div>
							<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
								style="${!blog.hasAvatar ? 'display:none;' : ''}"><i class="cmsms-icon-trash-7"></i></a>
						</div>
						<span class="error"></span>
					</div>
				</div>
			</div>
		</div>
		<hr class="my-4 m-t-30">
		<div class="form-group row m-b-30">
			<div class="col-lg-9">
				<div class="row">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-9">
						<div id="submitForm" class="form-submit">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty blog.id ? 'save' : 'update'}"/></span></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>	
</div>