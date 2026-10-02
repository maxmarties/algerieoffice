<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/manage/display"/>" class="lien lien-black">
			<i class="cmsms-icon-sliders m-r-5"></i><spring:message code="sidebar.company.dashboard5"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard5.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard5.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.manage4"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
		<form:form name="appearanceForm" action="/" method="POST" modelAttribute="appearance" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<div class="form-radio">
				<label class="ui-radio ui-radio-segond font-small">
					<form:radiobutton value="1" path="style" />
					<span class="input-span"></span><spring:message code="lbl.sub.appearance1" />
				</label>
				<span class="help-text"><spring:message code="txt.help.appeerance1.1" /></span>
				<spring:bind path="theme">
					<div id="themeForm" class="form-group row row-mini animated onne fadeIn m-t-10" style="${appearance.style == 1 ? '' : 'display:none;'}">
						<c:forEach var="i" begin="1" end="16" step="1">
							<div class="col-sm-6 col-md-3 col-lg-2 col-mini m-t-10">
								<div class="form-theme m-auto ${appearance.theme == i ? 'selected' : ''}">
									<c:if test="${!currentCompany.hasPremium() && i != 1}">
										<div class="inner-link">
											<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="btn.premium" /></a>
										</div>
									</c:if>
									<label class="ui-radio ui-radio-segond font-small ${!currentCompany.hasPremium() && i != 1 ? 'disabled' : ''}">
										<form:radiobutton value="${i}" path="theme" />
										<span class="input-span"></span><spring:message code="lbl.sub.appearance1.${i}" />
									</label>
									<div class="m-t-5"><img class="img-responsive" src="<c:url value="/static/vectors/appearance/m_appearance${i}-min.jpg"/>" 
										alt="<spring:message code="lbl.sub.appearance1.${i}" />"></div>
								</div>
							</div>
						</c:forEach>
					</div>
				</spring:bind>
			</div>
			<div class="form-radio form-radio-up">
				<label class="ui-radio ui-radio-segond font-small">
					<form:radiobutton value="2" path="style" />
					<span class="input-span"></span><spring:message code="lbl.sub.appearance2" />
				</label>
				<span class="help-text"><spring:message code="txt.help.appeerance1.2" /></span>
				<div id="customForm" class="form-group animated onne fadeIn m-b-0" style="${appearance.style == 2 ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${currentCompany.hasPremium()}">
							<hr class="my-2">
							<h3 class="h-header h-header4 i-primary m-t-20"><spring:message code="subheader.appearance"/></h3>
							<p class="font-small m-t-5"><spring:message code="txt.company.manage4.1"/></p>
							<hr class="my-4">
							<div class="form-group form-expand row m-t-20">
								<div class="col-lg-3 m-b-10">
									<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.slider4"/></h2>
									<div class="window-sidebar m-t-20">
										<ul class="window-mainsidebar list-none" data-widget="tree">
											<li id="dominoteColors" class="treeview active menu-open">
												<a class="lien"><spring:message code="lbl.sub.appearance2.1"/><i class="breadview-trigger cmsms-icon-angle-down"></i></a>
												<ul class="treeview-menu">
													<li class="help-text"><spring:message code="txt.help.appeerance1.2.1" /></li>
													<li class="form-sidebar m-t-10">
														<spring:bind path="primaryColor">
															<div id="primaryColorForm" class="form-group">
																<form:label class="col-form-label" path="primaryColor">
																	<spring:message code="lbl.sub.appearance2.1.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.2" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="primaryColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="segondColor">
															<div id="segondColorForm" class="form-group">
																<form:label class="col-form-label" path="segondColor">
																	<spring:message code="lbl.sub.appearance2.1.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.3" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="segondColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="treenColor">
															<div id="treenColorForm" class="form-group m-b-0">
																<form:label class="col-form-label" path="treenColor">
																	<spring:message code="lbl.sub.appearance2.1.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.4" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="treenColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
													</li>
													<li class="text-center">
														<hr class="my-2">
														<button class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-dominotes">
															<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.defaultColor"/></span>
														</button>
													</li>
												</ul>
											</li>
											<li id="menusColors" class="treeview">
												<a class="lien"><spring:message code="lbl.sub.appearance2.2"/><i class="breadview-trigger cmsms-icon-angle-down"></i></a>
												<ul class="treeview-menu">
													<li class="help-text"><spring:message code="txt.help.appeerance1.2.5" /></li>
													<li class="form-sidebar m-t-10">
														<spring:bind path="menuBack">
															<div id="menuBackForm" class="form-group">
																<form:label class="col-form-label" path="menuBack">
																	<spring:message code="lbl.sub.appearance2.2.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.6" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="menuBack" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="menuColor">
															<div id="menuColorForm" class="form-group">
																<form:label class="col-form-label" path="menuColor">
																	<spring:message code="lbl.sub.appearance2.2.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.7" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="menuColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="menuHover">
															<div id="menuHoverForm" class="form-group">
																<form:label class="col-form-label" path="menuHover">
																	<spring:message code="lbl.sub.appearance2.2.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.8" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="menuHover" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="popupBack">
															<div id="popupBackForm" class="form-group">
																<form:label class="col-form-label" path="popupBack">
																	<spring:message code="lbl.sub.appearance2.2.4" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.9" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="popupBack" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="popupColor">
															<div id="popupColorForm" class="form-group">
																<form:label class="col-form-label" path="popupColor">
																	<spring:message code="lbl.sub.appearance2.2.5" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.10" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="popupColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="popupHover">
															<div id="popupHoverForm" class="form-group">
																<form:label class="col-form-label" path="popupHover">
																	<spring:message code="lbl.sub.appearance2.2.6" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.2.11" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="popupHover" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
													</li>
													<li class="text-center">
														<hr class="my-2">
														<button class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-menus">
															<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.defaultColor"/></span>
														</button>
													</li>
												</ul>
											</li>
											<li id="sharedColors" class="treeview">
												<a class="lien"><spring:message code="lbl.sub.appearance2.3"/><i class="breadview-trigger cmsms-icon-angle-down"></i></a>
												<ul class="treeview-menu">
													<li class="help-text"><spring:message code="txt.help.appeerance1.3.1" /></li>
													<li class="form-sidebar m-t-10">
														<spring:bind path="sharedBack">
															<div id="sharedBackForm" class="form-group">
																<form:label class="col-form-label" path="sharedBack">
																	<spring:message code="lbl.sub.appearance2.2.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.3.2" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="sharedBack" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="sharedColor">
															<div id="sharedColorForm" class="form-group">
																<form:label class="col-form-label" path="sharedColor">
																	<spring:message code="lbl.sub.appearance2.2.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.3.3" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="sharedColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="sharedHover">
															<div id="sharedHoverForm" class="form-group">
																<form:label class="col-form-label" path="sharedHover">
																	<spring:message code="lbl.sub.appearance2.2.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.3.4" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="sharedHover" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="sharedFocus">
															<div id="sharedFocusForm" class="form-group">
																<form:label class="col-form-label" path="sharedFocus">
																	<spring:message code="lbl.sub.appearance2.2.7" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.3.5" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="sharedFocus" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
													</li>
													<li class="text-center">
														<hr class="my-2">
														<button class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-shared">
															<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.defaultColor"/></span>
														</button>
													</li>
												</ul>
											</li>
											<li id="titleColors" class="treeview">
												<a class="lien"><spring:message code="lbl.sub.appearance2.4"/><i class="breadview-trigger cmsms-icon-angle-down"></i></a>
												<ul class="treeview-menu">
													<li class="help-text"><spring:message code="txt.help.appeerance1.4.1" /></li>
													<li class="form-sidebar m-t-10">
														<spring:bind path="titleColor">
															<div id="titleColorForm" class="form-group">
																<form:label class="col-form-label" path="titleColor">
																	<spring:message code="lbl.sub.appearance2.4.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.4.2" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="titleColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="titleAfter">
															<div id="titleAfterForm" class="form-group">
																<form:label class="col-form-label" path="titleAfter">
																	<spring:message code="lbl.sub.appearance2.4.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.4.3" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="titleAfter" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="titleProduct">
															<div id="titleProductForm" class="form-group">
																<form:label class="col-form-label" path="titleProduct">
																	<spring:message code="lbl.sub.appearance2.4.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.4.4" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="titleProduct" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="titleHover">
															<div id="titleHoverForm" class="form-group">
																<form:label class="col-form-label" path="titleHover">
																	<spring:message code="lbl.sub.appearance2.2.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.4.5" /></span>
																</form:label>
																<div class="">
																	<div class="input-group-icon">
																		<form:input class="form-control form-color" type="text" path="titleHover" />
																		<span class="input-group-color"><i class="input-color-result"></i></span>
																	</div>
																	<span class="error"></span>
																</div>
															</div>
														</spring:bind>
														<spring:bind path="titleEditor">
															<div id="titleEditorForm" class="form-group">
																<form:label class="col-form-label" path="titleEditor">
																	<spring:message code="lbl.sub.appearance2.4.4" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.4.6" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="titleEditor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
													</li>
													<li class="text-center">
														<hr class="my-2">
														<button class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-title">
															<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.defaultColor"/></span>
														</button>
													</li>
												</ul>
											</li>
											<li id="pageColors" class="treeview">
												<a class="lien"><spring:message code="lbl.sub.appearance2.5"/><i class="breadview-trigger cmsms-icon-angle-down"></i></a>
												<ul class="treeview-menu">
													<li class="help-text"><spring:message code="txt.help.appeerance1.5.1" /></li>
													<li class="form-sidebar m-t-10">
														<spring:bind path="pageColor">
															<div id="pageColorForm" class="form-group">
																<form:label class="col-form-label" path="pageColor">
																	<spring:message code="lbl.sub.appearance2.5.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.5.2" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="pageColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<div class="form-group">
															<label class="col-form-label" for="widgetColor">
																	<spring:message code="lbl.sub.appearance2.5.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.5.3" /></span>
															</label>
															<div class="input-group-icon">
																<input class="form-control" type="text" id="widgetColor" name="widgetColor" value="#ffffff" disabled/>
																<span class="input-group-color"><i class="input-color-result" style="background:#ffffff;"></i></span>
															</div>
														</div>
														<spring:bind path="textColor">
															<div id="textColorForm" class="form-group">
																<form:label class="col-form-label" path="textColor">
																	<spring:message code="lbl.sub.appearance2.2.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.5.4" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="textColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="footerColor">
															<div id="footerColorForm" class="form-group">
																<form:label class="col-form-label" path="footerColor">
																	<spring:message code="lbl.sub.appearance2.5.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.5.5" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="footerColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
													</li>
													<li class="text-center">
														<hr class="my-2">
														<button class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-page">
															<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.defaultColor"/></span>
														</button>
													</li>
												</ul>
											</li>
											<li id="buttonColors" class="treeview">
												<a class="lien"><spring:message code="lbl.sub.appearance2.6"/><i class="breadview-trigger cmsms-icon-angle-down"></i></a>
												<ul class="treeview-menu">
													<li class="help-text"><spring:message code="txt.help.appeerance1.6.1" /></li>
													<li class="form-sidebar m-t-10">
														<spring:bind path="buttonColor">
															<div id="buttonColorForm" class="form-group">
																<form:label class="col-form-label" path="buttonColor">
																	<spring:message code="lbl.sub.appearance2.2.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.6.2" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="buttonColor" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="primaryTop">
															<div id="primaryTopForm" class="form-group">
																<form:label class="col-form-label" path="primaryTop">
																	<spring:message code="lbl.sub.appearance2.6.1" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.6.3" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="primaryTop" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="primaryBottom">
															<div id="primaryBottomForm" class="form-group">
																<form:label class="col-form-label" path="primaryBottom">
																	<spring:message code="lbl.sub.appearance2.6.2" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.6.4" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="primaryBottom" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="segondTop">
															<div id="segondTopForm" class="form-group">
																<form:label class="col-form-label" path="segondTop">
																	<spring:message code="lbl.sub.appearance2.6.3" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.6.5" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="segondTop" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
														<spring:bind path="segondBottom">
															<div id="segondBottomForm" class="form-group">
																<form:label class="col-form-label" path="segondBottom">
																	<spring:message code="lbl.sub.appearance2.6.4" /> <small class="min"><spring:message code="lbl.requis" /></small>
																	<span class="help-text"><spring:message code="txt.help.appeerance1.6.6" /></span>
																</form:label>
																<div class="input-group-icon">
																	<form:input class="form-control form-color" type="text" path="segondBottom" />
																	<span class="input-group-color"><i class="input-color-result"></i></span>
																</div>
																<span class="error"></span>
															</div>
														</spring:bind>
													</li>
													<li class="text-center">
														<hr class="my-2">
														<button class="btn btn-more btn-simple btn-add btn-left" data-attribut="defualt-button">
															<span><i class="cmsms-icon-retweet transition-35"></i><spring:message code="btn.defaultColor"/></span>
														</button>
													</li>
												</ul>
											</li>
										</ul>
									</div>
								</div>
								<div class="col-lg-9 m-b-10">
									<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="wizard.marketplace.promote3"/></h2>
									<div class="bn-overview bn-header m-t-20" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
										<div class="window-appearance">
											<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
											<nav class="navbar navbar-appearance" data-background="segond" style="background:${appearance.segondColor};">
												<div class="container">
													<ul class="navbar-nav">
														<li class="nav-item">
															<a class="nav-link lien">
																<img height="14" src="<c:url value="/static/vectors/${langage.lang}-min.png" />" class="m-r-5">
																<c:out value="${langage.lang == 'fr' ? 'Français' : langage.lang == 'en' ? 'English' : 'العربية'}" />
															</a>
														</li>
													</ul>
													<ul class="navbar-nav ml-auto">
														<li class="nav-item partner-menu">
															<a class="nav-link" data-backcolor="primary" style="background-color:${appearance.primaryColor};">
																<i class="cmsms-icon-lock-5 m-r-10"></i><spring:message code="btn.appearance.partner" /><i class="cmsms-icon-down-dir i-8 m-l-20"></i>
															</a>
														</li>
													</ul>
												</div>
											</nav>
											<nav class="navbar menu-appearance navbar-expand-lg">
												<div class="container">
													<div class="navbar-header">
														<a class="navbar-brand" href="<c:url value="/"/>">
															<img height="34" src="<c:url value="/static/icons/brand-logo-min.png"/>" alt="<spring:message code="app.brand"/>" />
														</a>
													</div>
													<ul class="navbar-nav nav-flex-icons navbar-icons ml-auto">
														<c:import url="/WEB-INF/basics/navbar_social.jsp"/>
													</ul>
												</div>
											</nav>
											<div class="mainheader background-container" style="background-image: url('<c:url value="/static/picts/aobns/header-min.jpg" />');">
												<div class="mainheader-overlay p-position" style="opacity:.6;background-color:#2C3F50;"></div>
												<div class="container h-100" style="color:#FFFFFF;">
													<img class="mainheader-logo" src="<c:url value="${appearanceView.urlAvatar}"/>" alt="<c:out value="${currentCompany.tradename}"/>">
													<div class="mainheader-text">
														<h1 class="h-header h-header1 font-bold sh-black"><c:out value="${currentCompany.tradename}"/></h1>
														<p class="font-small">
															<i class="cmsms-icon-location-1 i-red m-r-10"></i><c:out value="${appearanceView.address}" /> 
															<span class="text-uppercase"><spring:message code="chose.wilaya${appearanceView.wilaya}"/></span>
														</p>
														<ul class="list-none list-evaluation m-t-5">
															<c:forEach var="i" begin="1" end="5" step="1">
																<li class="i-yellow"><i class="cmsms-icon-star-1"></i></li>
															</c:forEach>
															<li class="font-small font-bold i-green m-l-20"><i class="cmsms-icon-thumbs-up m-r-5"></i><c:out value="100 %"/></li>
														</ul>
														<h2 class="h-header h-header5 m-t-5"><spring:message code="chose.activity.${appearanceView.activity}"/></h2>
													</div>
													<div class="mainheader-navbar">
														<p class="nav-text font-small font-italic m-b-5">
															<spring:message code="tool.navigate.company"/> : <c:out value="${appearanceView.viewDate}" /></p>
														<ul class="navbar-nav nav-flex-icons nav-company">
															<c:set var="providers" value="call-out,star-3,mail-1" scope="page"></c:set>
															<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
																<li>
																	<a class="lien disabled">
																		<i class="cmsms-icon-${pageScope.provider} i-navigate block"></i>
																		<span class="font-mini"><spring:message code="tool.navigate.company${state.count}"/></span>
																	</a>
																</li>
															</c:forEach>
														</ul>
													</div>
												</div>
											</div>
											<nav class="navbar navbar-mainmenu navbar-expand-lg" data-backcolor="menu" style="background-color:${appearance.menuBack};">
												<div class="container">
													<ul class="navbar-nav menu-navigate">
														<li><a class="transition-color" data-color="menu" style="color:${appearance.menuColor};"><i class="cmsms-icon-home-2"></i></a></li>
														<li class="dropdown">
															<a class="active" data-color="menu-back" style="color:${appearance.menuBack};">
																<spring:message code="explorer.mainmenu2"/><i class="cmsms-icon-down-dir i-8 m-l-5"></i>
															</a>
															<ul class="dropdown-menu animated fadeInDown" role="menu" 
																data-backcolor="popup" style="background-color:${appearance.popupBack};">
																<c:forEach var="i" begin="1" end="4">
																	<li><a class="dropdown-item transition-35" data-color="popup" 
																		style="color:${appearance.popupColor};"><spring:message code="explorer.mainmenu2.${i}"/></a></li>
																</c:forEach>
															</ul>
														</li>
														<li><a class="transition-color" data-color="menu" style="color:${appearance.menuColor};"><spring:message code="explorer.mainmenu3"/></a></li>
														<li><a class="transition-color" data-color="menu" style="color:${appearance.menuColor};"><spring:message code="explorer.mainmenu5"/></a></li>
													</ul>
													<ul class="navbar-nav nav-flex-icons shared-navigate ml-auto">
														<li><a class="transition-color" data-color="shared" data-backcolor="shared" 
															style="color:${appearance.sharedColor};background-color:${appearance.sharedBack};">
															<i class="cmsms-icon-calendar-empty m-r-10"></i><spring:message code="explorer.popup.home1"/></a></li>
														<li><a class="transition-color" data-color="shared" data-backcolor="shared"
															style="color:${appearance.sharedColor};background-color:${appearance.sharedBack};">
															<i class="cmsms-icon-thumbs-up m-r-10"></i><spring:message code="explorer.popup.home2"/></a></li>
													</ul>
												</div>
											</nav>
											<div class="explorer-container" data-color="text" data-backcolor="page" 
												style="color:${appearance.textColor};background-color:${appearance.pageColor};">
												<div class="container">
													<ol class="bread-crumb bread-explorer">
														<li><a class="lien"><i class="cmsms-icon-home"></i></a></li>
														<li><a class="lien"><spring:message code="sidebar.admin.dashboard3"/></a></li>
														<li class="active"><c:out value="${currentCompany.tradename}"/></li>
													</ol>
													<div class="row">
														<div class="col-lg-3 m-t-10">
															<div class="explorer-sidebar h-header" data-backcolor="menu" style="background-color:${appearance.menuBack};">
																<div class="sidebar-header bn-explorer-primary" data-color="button" 
																	style="color:${appearance.buttonColor};"><spring:message code="explorer.mainmenu2"/>
																</div>
																<ul class="sidebar-mainmenu list-none list-block">
																	<c:forEach var="i" begin="1" end="5">
																		<c:choose>
																			<c:when test="${i == 2}">
																				<li class="active">
																					<a data-color="menu-back" data-backcolor="menu-hover" 
																						style="color:${appearance.menuBack};background-color:${appearance.menuHover};">
																						<spring:message code="explorer.mainmenu2.2"/></a>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<a class="transition-color" data-color="menu" style="color:${appearance.menuColor};">
																						<spring:message code="explorer.mainmenu2.${i}"/></a>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																</ul>
															</div>
															<div class="widget-sticky m-t-10" data-color="button" data-backcolor="segond" 
																style="color:${appearance.buttonColor};background-color:${appearance.segondColor};">
																<h2 class="h-doc" data-color="button" data-border="primary" 
																	style="color:${appearance.buttonColor};border-color:${appearance.primaryColor};">
																	<spring:message code="subheader.appearance1"/></h2>
																<div class="widget-body m-t-20">
																	<p class="h-header font-small"><spring:message code="text.appearance.sticky"/></p>
																	<div class="m-t-20">
																		<a class="btn btn-explorer-sticky btn-simple btn-block">
																			<span  data-color="menu" data-backcolor="menu"
																				style="color:${appearance.menuColor};background-color:${appearance.menuBack};">
																				<spring:message code="tooltip.appearance.sticky"/>
																			</span>
																		</a>
																	</div>
																</div>
															</div>
															<div class="explorer-column explorer-actu m-t-20">
																<div class="widget-title">
																	<h2 class="h-doc h-header4" data-color="title" data-border="after" 
																		style="color:${appearance.titleColor};border-color:${appearance.titleAfter};">
																		<spring:message code="explorer.subheader.actus"/></h2>
																</div>
																<div class="widget-header background-container"
																	style="background-image: url('<c:url value="/static/picts/aobns/sticky-min.jpg" />');">
																	<a class="inner-link"></a>
																	<h3 class="h-header h-header4 sh-black" data-color="button" style="color:${appearance.buttonColor};">
																		<spring:message code="subheader.appearance3"/></h3>
																	<p class="font-bold font-small m-t-5" data-color="segond" style="color:${appearance.segondColor};">
																		<c:out value="${appearanceView.viewDate}" /></p>
																</div>
																<div class="widget-body" style="padding-bottom:20px;">
																	<ul class="list-none m-t-5">
																		<c:forEach var="i" begin="1" end="3">
																			<li><span class="item-provider p-left"><c:out value="00${i}"/></span><p><spring:message code="subheader.appearance3.${i}"/></p></li>
																		</c:forEach>
																		<li>
																			<i class="cmsms-icon-th-list-3 m-r-10"></i>
																			<a class="lien lien-black"><spring:message code="explorer.desktop.element2.1"/></a>
																		</li>
																	</ul>
																</div>
															</div>
														</div>
														<div class="col-lg-9 m-t-10">
															<div class="explorer-column widget-column m-b-20">
																<div class="row row-mini">
																	<div class="col-md-4 col-mini">
																		<div class="widget-window">
																			<div class="widget-header">
																				<a class="inner-link" title="<spring:message code="subheader.appearance2"/>"></a>
																				<div class="inner-overlay"><i class="cmsms-icon-explorer-arrow"></i></div>
																				<div class="inner-thumbnail"><img class="img-responsive" src="<c:url value="/static/picts/aobns/appearance-min.jpg" />"></div>
																			</div>
																		</div>
																	</div>
																	<div class="col-md-8 col-mini">
																		<div class="widget-title">
																			<span class="item-provider p-right"><c:out value="001"/></span>
																			<a class="h-doc h-doc4 lien lien-explorer-title sh-black" data-color="product" data-border="after" 
																				style="color:${appearance.titleProduct};border-color:${appearance.titleAfter};">
																				<spring:message code="subheader.appearance2"/>
																			</a>
																		</div>
																		<div class="widget-category">
																			<i class="cmsms-icon-box m-r-10" data-color="segond" style="color:${appearance.segondColor};"></i>
																			<spring:message code="chose.post.type2"/>
																		</div>
																		<div class="widget-descriptif"><p><spring:message code="text.appearance.product"/></p></div>
																		<div class="widget-body">
																			<div class="widget-price h-header" data-color="segond" style="color:${appearance.segondColor};">
																				<spring:message code="chose.post.price1" />
																			</div>
																		</div>
																	</div>
																</div>
																<div class="widget-body">
																	<div class="widget-about-post">
																		<div class="row row-mini">
																			<div class="col-md-8 col-mini">
																				<div class="widget-about">
																					<ul class="navbar-nav nav-flex-icons">
																						<li class="widget-icon"><img class="img-circle" src="<c:url value="${currentCompany.iconurl}"/>"></li>
																						<li class="widget-abouter">
																							<spring:message code="wizard.screen.autor1" /> : 
																							<a class="lien lien-black lien-underline"><c:out value="${currentCompany.tradename}"/></a>
																						</li>
																					</ul>
																				</div>
																			</div>
																			<div class="col-md-4 col-mini">
																				<div class="widget-button text-right">
																					<a class="btn btn-explorer-primary btn-simple btn-fixed" data-color="button" 
																						style="color:${appearance.buttonColor};"><span><spring:message code="btn.explorer.contact3"/></span>
																					</a>
																					<a class="btn btn-explorer-segond btn-flat-favorite btn-simple" data-color="button" 
																						style="color:${appearance.buttonColor};"><span><i class="cmsms-icon-star-filled"></i></span>
																					</a>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="explorer-column widget-document m-t-10">
																<p class="ind-screen"><spring:message code="explorer.desktop.grid5" /></p>
																<div class="document-favorite p-right"><a class="transition-35"><i class="cmsms-icon-star-empty-2"></i></a></div>
																<h1 class="h-doc h-doc1" data-color="product" data-border="after" 
																	style="color:${appearance.titleProduct};border-color:${appearance.titleAfter};">
																	<spring:message code="subheader.appearance2"/>
																</h1>
																<div class="document-content">
																	<div class="row">
																		<div class="col-6">
																			<p class="font-small"><spring:message code="tool.navigate.company"/> : <c:out value="${appearanceView.viewDate}" /></p>
																		</div>
																		<div class="col-6">
																			<p class="font-small text-right">
																				<a class="lien lien-black lien-underline"><spring:message code="btn.print"/></a><i class="cmsms-icon-print m-l-5"></i>
																			</p>
																		</div>
																	</div>
																	<div class="document-background background-container" style="background-image: url('<c:url value="/static/picts/aobns/appearance-min.jpg" />');"></div>
																	<h2 class="h-header h-explorer3" data-color="primary" style="color:${appearance.primaryColor};"><spring:message code="lbl.sub.explorer5.1"/></h2>
																	<div class="table-responsive m-t-10">
																		<table class="table table-explorer">
																			<thead><tr><th style="width:30px;"></th><th style="width:34%;"></th><th style="width:calc(66% - 30px);"></th></tr></thead>
																			<tbody class="h-header">
																				<tr>
																					<td><i class="cmsms-icon-basket"></i></td>
																					<td><spring:message code="tabs.type"/></td>
																					<td><spring:message code="chose.post.type2"/></td>
																				</tr>
																				<tr>
																					<td><i class="cmsms-icon-folder-2"></i></td>
																					<td><spring:message code="tabs.category"/></td>
																					<td><span class="font-small"><c:out value="--"/></span></td>
																				</tr>
																				<tr>
																					<td><i class="cmsms-icon-dollar"></i></td>
																					<td><spring:message code="lbl.price"/></td>
																					<td class="font-big" data-color="segond" style="color:${appearance.segondColor};">
																						<spring:message code="chose.post.price1" />
																					</td>
																				</tr>
																				
																				
																			</tbody>
																		</table>
																	</div>
																	<h2 class="h-header h-explorer3 m-t-20"><spring:message code="lbl.sub.explorer5.3"/></h2>
																	<div class="fr-view fr-explorer m-t-20">
																		<p class="m-b-20"><spring:message code="text.appearance.document1"/></p>
																		<h3 data-color="editor" style="color:${appearance.titleEditor};">
																			<spring:message code="text.appearance.document2"/></h3>
																		<p class="font-small"><spring:message code="text.appearance.document3"/></p><br>
																		<p class="font-small"><spring:message code="text.appearance.document4"/></p>
																	</div>
																	<ul class="list-keysword list-none m-t-10">
																		<c:forEach var="i" begin="1" end="4">
																			<li>
																				<i class="cmsms-icon-hash-1 i-tags m-r-5" data-color="segond" style="color:${appearance.segondColor};"></i>
																				<a class="lien lien-black"><spring:message code="tooltip.appearance.keys${i}"/></a>
																			</li>
																		</c:forEach>
																	</ul>
																</div>
																<div class="document-footer" data-backcolor="treen" style="background-color:${appearance.treenColor};">
																	<div>
																		<div class="widget-prospect">
																			<a class="btn btn-document btn-simple btn-block disabled">
																				<span><i class="cmsms-icon-bag m-r-10"></i><spring:message code="overview.contact1"/></span>
																			</a>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="explorer-footer" data-backcolor="primary" style="background-color:${appearance.primaryColor};">
												<a class="btn btn-simple btn-totop" data-color="button" style="color:${appearance.buttonColor};">
													<span><i class="cmsms-icon-up-open"></i></span></a>
												<div class="explorer-up" data-color="footer" style="color:${appearance.footerColor};">
													<div class="container">
														<div class="row h-header">
															<div class="col-lg-3 m-t-10 m-b-10">
																<h3 class="h-doc h-footer" data-color="button" data-border="after" 
																	style="color:${appearance.buttonColor};border-color:${appearance.titleAfter};">
																	<spring:message code="header.explorer.footer1"/></h3>
																<div class="widget-footer">
																	<p class="font-big"><c:out value="${currentCompany.tradename}"/></p>
																	<p class="font-small m-t-5" style="line-height:14px;"><spring:message code="tooltip.appearance.tageline"/></p>
																</div>
															</div>
															<div class="col-lg-3 m-t-10 m-b-10">
																<h3 class="h-doc h-footer" data-color="button" data-border="after" 
																	style="color:${appearance.buttonColor};border-color:${appearance.titleAfter};">
																	<spring:message code="header.explorer.footer2"/></h3>
																<div class="widget-footer">
																	<ul class="list-none list-explorer-footer list-block font-small">
																		<c:forEach var="i" begin="1" end="7">
																			<c:if test="${i <= 3 || i >= 6}">
																				<li>
																					<i class="cmsms-icon-explorer-angle m-r-10"></i>
																					<a class="lien" data-color="footer" style="color:${appearance.footerColor};">
																						<spring:message code="explorer.mainmenu6.${i}"/>
																					</a>
																				</li>
																			</c:if>
																		</c:forEach>
																	</ul>
																</div>
															</div>
															<div class="col-lg-3 m-t-10 m-b-10">
																<h3 class="h-doc h-footer" data-color="button" data-border="after" 
																	style="color:${appearance.buttonColor};border-color:${appearance.titleAfter};"><spring:message code="lbl.shedule"/></h3>
																<div class="widget-footer">
																	<p class="font-small" style="line-height:14px;"><spring:message code="txt.help.explorer4.4"/></p>
																</div>
															</div>
															<div class="col-lg-3 m-t-10 m-b-10">
																<h3 class="h-doc h-footer" data-color="button" data-border="after" 
																	style="color:${appearance.buttonColor};border-color:${appearance.titleAfter};"><spring:message code="header.explorer.footer3"/></h3>
																<div class="widget-footer">
																	<p class="font-small" style="line-height:14px;"><spring:message code="txt.help.explorer4.5"/></p>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="explorer-down h-header" data-color="footer" style="color:${appearance.footerColor};">
													<div class="container">
														<p class="font-mini text-center">&copy; <span class="yearsApp">2021</span> <spring:message code="app.copyright"/></p>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="alert alert-info form-expand-down m-t-10">
								<i class="cmsms-icon-info i-alert"></i>
								<p class="p-alert"><spring:message code="txt.help.appeerance2"/></p>
							</div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-warning m-t-20">
								<i class="cmsms-icon-dollar i-alert"></i>
								<p class="p-alert">
									<spring:message code="message.premium.appearance"/> 
									<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
								</p>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<hr class="my-2">
			<div class="form-group row m-b-30">
				<div class="col-lg-3 hidden-sm-down"></div>
				<div class="col-lg-9">
					<div id="submitForm" class="form-submit">
						<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>