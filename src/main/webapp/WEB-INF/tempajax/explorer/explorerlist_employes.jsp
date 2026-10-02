<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${explorerList.lines.isEmpty()}"><div class="explorer-empty m-b-20"><p><spring:message code="tool.empty.desktop" /></p></div></c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="line" items="${explorerList.lines}" varStatus="state">
				<c:set var="currItem" value="${currentItem + state.count}" scope="page"></c:set>
				<div class="col-12 m-b-20">
					<div class="explorer-column">
						<div class="widget-title">
							<span class="item-provider item-mini p-right"><c:out value="${pageScope.currItem < 10 ? '00' : pageScope.currItem < 100 ? '0' : ''}${pageScope.currItem}"/></span>
							<a href="<c:url value="${line.identifyURL}" />" class="h-doc h-doc4 lien lien-explorer-title sh-black explorer-resultFind"><c:out value="${line.title}" /></a>
						</div>
						<div class="widget-body">
							<div class="table-responsive">
								<table class="table table-explorer table-desktop">
									<thead><tr><th style="width:50%;"></th><th style="width:50%;"></th></tr></thead>
									<tbody class="font-small">
										<tr>
											<td><spring:message code="tabs.contract" /> : <strong><spring:message code="chose.contract${line.contract}"/></strong></td>
											<td class="text-right"><spring:message code="lbl.contract.discover" /> : 
												<strong class="text-segond">
													<c:choose>
														<c:when test="${line.discoverType != 3}"><spring:message code="chose.discover${line.discoverType}" /></c:when>
														<c:otherwise><c:out value="${line.discoverValue}"/> <spring:message code="tool.order.devise"/></c:otherwise>
													</c:choose>
												</strong>
											</td>
										</tr>
										<tr>
											<td><spring:message code="lbl.contract.domaine" /> : <strong><spring:message code="chose.domaine${line.domaine}"/></strong></td>
											<td class="text-right"><spring:message code="tabs.expire" /> : <span class="text-red"><strong><c:out value="${line.expiredDate}"/></strong></span></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="widget-about"><c:out value="${line.description}" /></div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>
<input type="hidden" id="countDesktopResult" value="${explorerList.count}" />
<input type="hidden" id="countDesktopSize" value="${explorerList.lines.size()}" />
</compress:html>