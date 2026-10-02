<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/overview/header"/>" class="lien lien-black">
			<i class="cmsms-icon-website m-r-5"></i><spring:message code="sidebar.company.dashboard4"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard4.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard4.3"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.overview3"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<div class="row">
			<div class="col-md-6 col-lg-4 m-t-10">
				<form:form name="maincatalogForm" action="/" method="POST" modelAttribute="maincatalog" enctype="multipart/form-data" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
					<spring:bind path="titles"><form:input type="hidden" path="titles" /></spring:bind>
					<spring:bind path="textsAlt"><form:input type="hidden" path="textsAlt" /></spring:bind>
					<spring:bind path="photosUUID"><form:input type="hidden" path="photosUUID" /></spring:bind>
					<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
					<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
					<spring:bind path="updateCatalog"><form:input type="hidden" path="updateCatalog" /></spring:bind>
					<spring:bind path="style">
						<div id="styleForm" class="form-group">
							<label class="col-form-label">
								<spring:message code="lbl.sub.catalog1" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</label>
							<div class="m-t-10">
								<label class="ui-radio ui-radio-segond font-small">
									<form:radiobutton value="${true}" path="style" />
									<span class="input-span"></span><spring:message code="lbl.sub.catalog1.1" />
								</label>
								<span class="help-text m-b-20"><spring:message code="txt.help.catalog1.1" /></span>
								<label class="ui-radio ui-radio-segond font-small">
									<form:radiobutton value="${false}" path="style" />
									<span class="input-span"></span><spring:message code="lbl.sub.catalog1.2" />
								</label>
								<span class="help-text"><spring:message code="txt.help.catalog1.2" /></span>
							</div>
						</div>
					</spring:bind>
					<hr class="my-4">
					<div id="itemsForm" class="form-group">
						<label class="col-form-label">
							<spring:message code="lbl.photos" /> 
							<small class="min">(<span id="itemSize"><c:out value="${maincatalog.titles.size()}"/></span>/10)</small>
							<span class="help-text"><spring:message code="txt.help.catalog1" /></span>
						</label>
						<table id="tableItems" class="table table-page m-t-10 ${!maincatalog.titles.isEmpty() ? 'm-b-20' : ''}">
							<thead>
								<tr>
									<th style="width:26px;"></th><th style="width:calc(60% - 34px);"></th><th style="width:calc(40% - 60px);"></th>
									<th style="width:34px;"></th><th style="width:34px;"></th>
								</tr>
							</thead>
							<tbody class="font-small">
								<c:forEach var="itemTitle" items="${maincatalog.titles}" varStatus="state">
									<tr id="lineItem${state.count}" data-ident="${maincatalog.idents.get(state.count - 1)}" 
										data-uuid="${maincatalog.photosUUID.get(state.count - 1)}">
										<td><i class="cmsms-icon-picture i-help"></i></td>
										<td class="i-input"><c:out value="${itemTitle}" /></td>
										<td><c:out value="${maincatalog.buildTextAlt(state.count - 1)}" /></td>
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
						<input type="hidden" id="countItems" value="${maincatalog.titles.size()}" />
						<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${maincatalog.titles.size() == 10 ? 'disabled' : ''}">
							<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.photo"/></span>
						</a>
						<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
					</div>
					<hr class="my-4">
					<p class="font-mini">
						<spring:message code="txt.company.overview1.1"/> 
						<a href="<c:url value="https://imagecompressor.com/fr/" />" class="lien lien-hover lien-primary" target="_blank">
						<spring:message code="lien.optimizilla"/></a> <spring:message code="txt.company.overview1.2"/>
					</p>
					<div class="form-group m-t-20 m-b-20">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
							</button>
						</div>
					</div>
				</form:form>
			</div>
			<div class="col-md-6 col-lg-8 m-t-20">
				<div class="bn-overview bn-body" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
					<div id="diapoOverview" class="bn-box diapo-overview" style="${!maincatalog.style ? 'display:none;' : ''}">
						<div class="diapo-header background-container" style="background-image: url('${maincatalog.photoURL(0)}');"></div>
						<div class="diapo-body m-t-20">
							<div class="owl-carousel owl-theme">
								<c:forEach var="i" begin="1" end="10">
									<div class="item background-container" data-attribut="diapo-selected" data-item="${i}"
										style="background-image: url('${maincatalog.photoURL(i - 1)}');" title="<c:out value="${maincatalog.title(i - 1)}"/>">
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<div id="catalogOverview" class="bn-box catalog-overview" style="${maincatalog.style ? 'display:none;' : ''}">
						<div class="row row-mini">
							<div class="col-sm-6 col-mini m-b-10">
								<div id="quickCatalog1" class="quick-catalog background-container" 
									style="background-image: url('${maincatalog.photoURL(0)}');" title="<c:out value="${maincatalog.title(0)}"/>">
								</div>
							</div>
							<div class="col-sm-6 col-mini">
								<div class="row row-mini">
									<c:forEach var="i" begin="1" end="4">
										<div class="col-sm-6 col-mini m-b-10">
											<div id="quickCatalog${i + 1}" class="quick-catalog background-container" 
												style="background-image: url('${maincatalog.photoURL(i)}');" title="<c:out value="${maincatalog.title(i)}"/>">
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
						<div class="row row-mini">
							<div class="col-sm-6 col-mini">
								<div class="row row-mini">
									<c:forEach var="i" begin="5" end="8">
										<div class="col-sm-6 col-mini m-b-10">
											<div id="quickCatalog${i + 1}" class="quick-catalog background-container" 
												style="background-image: url('${maincatalog.photoURL(i)}');" title="<c:out value="${maincatalog.title(i)}"/>">
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
							<div class="col-sm-6 col-mini m-b-10">
								<div id="quickCatalog10" class="quick-catalog background-container" 
									style="background-image: url('${maincatalog.photoURL(9)}');" title="<c:out value="${maincatalog.title(9)}"/>">
								</div>
							</div>
						</div>
					</div>
				</div>
				<hr class="my-4">
				<p class="font-mini"><spring:message code="txt.company.overview3.1"/></p>
			</div>
		</div>
	</sec:authorize>
</div>