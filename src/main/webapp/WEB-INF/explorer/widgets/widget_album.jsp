<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-catalog">
	<c:choose>
		<c:when test="${explorerPage.catalog.style}">
			<div id="catalogExplorer" class="diapo-overview">
				<div class="diapo-header background-container" style="background-image: url('${explorerPage.catalog.photosURL.get(0)}');"></div>
				<c:if test="${explorerPage.catalog.photosURL.size() > 1}">
					<div class="diapo-body m-t-10">
						<div class="owl-carousel owl-theme">
							<c:forEach var="i" begin="0" end="${explorerPage.catalog.photosURL.size() - 1}">
								<div class="item background-container" data-attribut="diapo-selected" 
									style="background-image: url('${explorerPage.catalog.photosURL.get(i)}');" 
									title="<c:out value="${explorerPage.catalog.titles.get(i)}"/>">
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</div>
		</c:when>
		<c:otherwise>
			<div class="catalog-overview">
				<c:set var="size" value="${explorerPage.catalog.photosURL.size()}" scope="page"></c:set>
				<div class="row row-mini">
					<div class="col-sm-${pageScope.size > 1 ? '6' : '12'} col-mini m-b-10">
						<a href="<c:url value="${explorerPage.catalog.photosURL.get(0)}" />" class="quick-catalog background-container" 
							style="background-image: url('${explorerPage.catalog.photosURL.get(0)}');" 
							title="<c:out value="${explorerPage.catalog.titles.get(0)}"/>">
							<span class="inner-overlay"><i class="cmsms-icon-search-6 text-white"></i></span>
						</a>
					</div>
					<c:if test="${pageScope.size > 1}">
						<div class="col-sm-6 col-mini ${pageScope.size == 2 ? 'm-b-10' : ''}">
							<c:choose>
								<c:when test="${pageScope.size == 2}">
									<a href="<c:url value="${explorerPage.catalog.photosURL.get(1)}" />" class="quick-catalog background-container" 
										style="background-image: url('${explorerPage.catalog.photosURL.get(1)}');" 
										title="<c:out value="${explorerPage.catalog.titles.get(1)}"/>">
										<span class="inner-overlay"><i class="cmsms-icon-search-6 text-white"></i></span>
									</a>
								</c:when>
								<c:otherwise>
									<div class="row row-mini">
										<c:forEach var="i" begin="1" end="4">
											<c:if test="${i < pageScope.size}">
												<div class="col-sm-${pageScope.size == 3 || pageScope.size == 4 && i == 3 ? '12' : '6'} col-mini m-b-10">
													<a href="<c:url value="${explorerPage.catalog.photosURL.get(i)}" />" 
														class="quick-catalog background-container"  
														style="background-image: url('${explorerPage.catalog.photosURL.get(i)}');" 
														title="<c:out value="${explorerPage.catalog.titles.get(i)}"/>">
														<span class="inner-overlay"><i class="cmsms-icon-search-6 text-white"></i></span>
													</a>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>
				<c:if test="${pageScope.size > 5}">
					<div class="row row-mini">
						<div class="col-sm-${pageScope.size == 6 ? '12 m-b-10' : '6'} col-mini">
							<c:choose>
								<c:when test="${pageScope.size == 6}">
									<a href="<c:url value="${explorerPage.catalog.photosURL.get(5)}" />" class="quick-catalog background-container" 
										style="background-image: url('${explorerPage.catalog.photosURL.get(5)}');" 
										title="<c:out value="${explorerPage.catalog.titles.get(5)}"/>">
										<span class="inner-overlay"><i class="cmsms-icon-search-6 text-white"></i></span>
									</a>
								</c:when>
								<c:otherwise>
									<div class="row row-mini">
										<c:forEach var="i" begin="5" end="${pageScope.size - 2}">
											<div class="col-sm-${pageScope.size == 7 || pageScope.size == 9 && i == 7 ? '12' : '6'} col-mini m-b-10">
												<a href="<c:url value="${explorerPage.catalog.photosURL.get(i)}" />" 
													class="quick-catalog background-container"  
													style="background-image: url('${explorerPage.catalog.photosURL.get(i)}');" 
													title="<c:out value="${explorerPage.catalog.titles.get(i)}"/>">
													<span class="inner-overlay"><i class="cmsms-icon-search-6 text-white"></i></span>
												</a>
											</div>
										</c:forEach>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<c:if test="${pageScope.size != 6}">
							<div class="col-sm-6 col-mini m-b-10">
								<a href="<c:url value="${explorerPage.catalog.photosURL.get(pageScope.size - 1)}" />" 
									class="quick-catalog background-container" 
									style="background-image: url('${explorerPage.catalog.photosURL.get(pageScope.size - 1)}');" 
									title="<c:out value="${explorerPage.catalog.titles.get(pageScope.size - 1)}"/>">
									<span class="inner-overlay"><i class="cmsms-icon-search-6 text-white"></i></span>
								</a>
							</div>
						</c:if>
					</div>
				</c:if>
			</div>
		</c:otherwise>
	</c:choose>
</div>