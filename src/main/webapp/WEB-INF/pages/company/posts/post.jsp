<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/posts/all"/>" class="lien lien-black">
			<i class="cmsms-icon-bag m-r-5"></i><spring:message code="sidebar.company.dashboard1"/></a></li>
		<c:if test="${!empty post.id}">
			<li><a href="<c:url value="/company/posts/all"/>" class="lien lien-black"><spring:message code="sidebar.company.dashboard1.1"/></a></li>
		</c:if>
		<li class="active"><spring:message code="btn.${empty post.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.posts.post.${empty post.id ? 'new' : 'edit'}"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.posts2.1"/></p>
	</div>
	<c:set var="backwordURL" value="/company/posts/all" scope="request"></c:set>
	<c:set var="backwordPage" value="4" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<c:choose>
		<c:when test="${empty post.id && premium.hasConsumer}">
			<div class="alert alert-warning">
				<i class="cmsms-icon-attention i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.post"/> : 
					<a href="<c:url value="/company/tools/subscribes"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.premium"/></a>.
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
				<form:form name="postForm" action="/" method="POST" modelAttribute="post" enctype="multipart/form-data" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="companyId"><form:input type="hidden" path="companyId" /></spring:bind>
					<div class="row">
						<div class="col-lg-8">
							<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.post1"/></h2>
							<spring:bind path="service">
								<div id="serviceForm" class="form-group row">
									<label class="col-form-label col-md-4 col-lg-3">
										<spring:message code="tabs.type" /> <small class="min"><spring:message code="lbl.requis" /></small>
									</label>
									<div class="col-md-8 col-lg-9">
										<div>
											<c:forEach var="i" begin="1" end="2" step="1">
												<label class="ui-radio ui-radio-segond font-small m-r-10">
													<form:radiobutton value="${i == 2}" path="service" />
													<span class="input-span"></span><spring:message code="chose.post.type${i}" />
												</label>
											</c:forEach>
										</div>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="title">
								<div id="titleForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="title">
										<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
										<span class="help-text">
											<spring:message code="txt.help.post1.1" />
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
											<div class="input-group-lien">
												<c:out value="${!empty currentCompany.url ? currentCompany.url : '@'}"/><c:out value="${urlPosts}"/>
											</div>
											<form:input class="form-control" type="text" path="identify" />
										</div>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="description">
								<div id="descriptionForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="description">
										<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
										<span class="help-text">
											<spring:message code="txt.help.post1.2" />
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
							<div id="editorForm" class="form-group form-note row">
								<label class="col-form-label col-md-4 col-lg-3">
									<spring:message code="lbl.detail" /> <small class="min"><spring:message code="lbl.requis" /></small>
								</label>
								<div class="col-md-8 col-lg-9">
									<div id="editor"><c:if test="${!empty post.id}"><c:out value="${post.detail}" escapeXml="false" /></c:if></div>
									<span class="error"></span>
								</div>
							</div>
							<hr class="my-4">
							<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.post2"/></h2>
							<spring:bind path="hasPublished">
								<div id="hasPublishedForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="hasPublished">
										<spring:message code="tabs.state" /> <small class="min"><spring:message code="lbl.requis" /></small>
									</form:label>
									<div class="col-md-4 col-lg-3">
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
									<form:label class="col-form-label col-md-4 col-lg-3" path="category">
										<spring:message code="tabs.category" />
										<span class="help-text"><spring:message code="txt.help.post1.3" /></span>
									</form:label>
									<div class="col-md-8 col-lg-9">
										<form:select class="form-select2" path="category">
											<option value="-"><c:out value="-" /></option>
											<c:forEach var="choseCategory" items="${choseCategories}">
												<option value="${choseCategory.uuid()}" ${post.category == choseCategory.uuid() ? 'selected' : ''}><c:out value="${choseCategory.name}" /></option>
											</c:forEach>
										</form:select>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<input type="hidden" id="maxkeysword" value="${premium.maxKeywords}" />
							<spring:bind path="keysword">
								<div id="keyswordForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="keysword">
										<spring:message code="tabs.keys" />
										<span class="help-text">
											<spring:message code="txt.help.keys" arguments="${premium.maxKeywords}" />
											<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.keys" />"></i>
										</span>
									</form:label>
									<div class="col-md-8 col-lg-9">
										<form:input class="form-control" type="text" path="keysword" />
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="urlExtern">
								<div id="urlExternForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-3" path="urlExtern">
										<spring:message code="lbl.externurl" />
										<span class="help-text"><spring:message code="txt.help.post1.4" /></span>
									</form:label>
									<div class="col-md-8 col-lg-9">
										<c:set var="faholder" scope="page"><spring:message code="tool.ind.post" /></c:set>
										<form:input class="form-control" type="url" path="urlExtern" placeholder="${pageScope.faholder}" />
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
						</div>
						<div class="col-lg-4">
							<hr class="my-4 hidden-md-up m-b-20">
							<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.post3"/></h2>
							<spring:bind path="textsAlt"><form:input type="hidden" path="textsAlt" /></spring:bind>
							<spring:bind path="photosUUID"><form:input type="hidden" path="photosUUID" /></spring:bind>
							<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
							<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
							<spring:bind path="photoPrincipal"><form:input type="hidden" path="photoPrincipal" /></spring:bind>
							<spring:bind path="updateFile"><form:input type="hidden" path="updateFile" /></spring:bind>
							<div id="itemsForm" class="form-group row">
								<label class="col-form-label col-md-4 col-lg-12">
									<spring:message code="lbl.photos" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.post1.5" /></span>
								</label>
								<div class="col-md-8 col-lg-12">
									<table id="tableItems" class="table table-page ${!post.textsAlt.isEmpty() ? 'm-b-20' : ''}">
										<thead>
											<tr>
												<th style="width:34px;"></th><th style="width:calc(100% - 102px);"></th>
												<th style="width:34px;"></th><th style="width:34px;"></th>
											</tr>
										</thead>
										<tbody class="font-small">
											<c:forEach var="textAlt" items="${post.textsAlt}" varStatus="state">
												<tr id="lineItem${state.count}" data-uuid="${post.photosUUID.get(state.count - 1)}">
													<td class="td-check">
														<label class="ui-radio ui-radio-segond font-small">
															<input type="radio" name="checkLineItem" value="${state.count - 1}" ${post.photoPrincipal == state.count - 1 ? 'checked' : ''}/>
															<span class="input-span"></span>
														</label>
													</td>
													<td class="i-input"><c:out value="${textAlt}" /></td>
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
									<input type="hidden" id="countItems" value="${post.textsAlt.size()}" />
									<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${post.textsAlt.size() >= 4 ? 'disabled' : ''}">
										<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.photo"/></span>
									</a>
									<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
									<hr class="my-4">
									<p class="font-mini">
										<spring:message code="txt.company.overview1.1"/> 
										<a href="<c:url value="https://imagecompressor.com/fr/" />" class="lien lien-hover lien-primary" target="_blank">
										<spring:message code="lien.optimizilla"/></a> <spring:message code="txt.company.overview1.2"/>
									</p>
								</div>
							</div>
							<hr class="my-4">
							<h2 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.post4"/></h2>
							<spring:bind path="priceType">
								<div id="priceTypeForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-12" path="priceType">
										<spring:message code="lbl.price" /> <small class="min"><spring:message code="lbl.requis" /></small>
										<span class="help-text"><spring:message code="txt.help.post1.6" /></span>
									</form:label>
									<div class="col-md-4 col-lg-12">
										<c:set var="faholder" scope="page"><spring:message code="chose.label" /></c:set>
										<form:select class="form-select2-simple" path="priceType" data-placeholder="${pageScope.faholder}">
											<option></option>
											<c:forEach var="i" begin="1" end="3" step="1">
												<option value="${i}" ${post.priceType == i ? 'selected' : ''}><spring:message code="chose.post.price${i}" /></option>
											</c:forEach>
										</form:select>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="priceValue">
								<div id="priceValueForm" class="form-group row m-t-10">
									<div class="col-md-4 hidden-md-up hidden-sm-down"></div>
									<div class="col-md-8 col-lg-12">
										<div class="input-group-phone input-group-right">
											<span class="input-icon"><spring:message code="tool.ind.capital" /></span>
											<form:input class="form-control" type="text" path="priceValue" disabled="${post.priceType != 2}" />
										</div>
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="priceParrain">
								<div id="priceParrainForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-12" path="priceParrain">
										<spring:message code="lbl.parrain" />
										<span class="help-text"><spring:message code="txt.help.post1.7" /></span>
									</form:label>
									<div class="col-md-8 col-lg-12">
										<form:input class="form-control" type="text" path="priceParrain" />
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="pricePrecision">
								<div id="pricePrecisionForm" class="form-group row">
									<form:label class="col-form-label col-md-4 col-lg-12" path="pricePrecision">
										<spring:message code="lbl.precision" />
										<span class="help-text"><spring:message code="txt.help.post1.8" /></span>
									</form:label>
									<div class="col-md-8 col-lg-12">
										<c:set var="faholder" scope="page"><spring:message code="tool.ind.precision" /></c:set>
										<form:input class="form-control" type="text" path="pricePrecision" placeholder="${pageScope.faholder}" />
										<span class="error"></span>
									</div>
								</div>
							</spring:bind>
							<div class="form-group row">
								<label class="col-form-label col-md-4 col-lg-12">
									<spring:message code="lbl.label" />
									<span class="help-text"><spring:message code="txt.help.post1.9" /></span>
								</label>
								<div class="col-md-8 col-lg-12">
									<spring:bind path="labelNew">
										<label class="ui-checkbox ui-checkbox-segond font-small m-r-20">
											<form:checkbox path="labelNew" />
											<span class="input-span"></span><spring:message code="comp.post1" />
										</label>
									</spring:bind>
									<spring:bind path="labelExclusif">
										<label class="ui-checkbox ui-checkbox-segond font-small">
											<form:checkbox path="labelExclusif" />
											<span class="input-span"></span><spring:message code="comp.post2" />
										</label>
									</spring:bind>
								</div>
							</div>
						</div>
					</div>
					<hr class="my-4 m-t-30">
					<div class="form-group row m-b-30">
						<div class="col-lg-8">
							<div class="row">
								<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
								<div class="col-md-8 col-lg-9">
									<div id="submitForm" class="form-submit m-t-10">
										<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
											<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty post.id ? 'save' : 'update'}"/></span></button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</sec:authorize>
		</c:otherwise>
	</c:choose>
</div>