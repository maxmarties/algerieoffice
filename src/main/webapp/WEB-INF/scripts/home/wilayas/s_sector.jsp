<%@ include file="/WEB-INF/tags/script/script_public.jsp"%>
<script src="<c:url value="/static/webjars/js/lib/owlcarousel/owlcarousel.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/lib/chartjs/chart.min.js" />"></script>
<script src="<c:url value="/static/webjars/js/plugin/scripanalytic.min.js" />"></script>
<script type="text/javascript">
$(function(){$("#screenAnalytic").scripanalytic({url:'<c:url value="/feedback/analyse/"/>',wilaya:'<c:out value="${wilaya}"/>',sector:'<c:out value="${sector}"/>',primaryColor:'<c:out value="${currentConfig.aocolor(3)}"/>',greenColor:'<c:out value="${currentConfig.aocolor(36)}"/>',yellowColor:'<c:out value="${currentConfig.aocolor(37)}"/>',blueColor:'<c:out value="${currentConfig.aocolor(38)}"/>',redColor:'<c:out value="${currentConfig.aocolor(35)}"/>',whiteColor:'<c:out value="${currentConfig.aocolor(2)}"/>',lineColor:'<c:out value="${currentConfig.aocolor(59)}"/>'}),$("#cardScreenBlog").find(".owl-carousel").owlCarousel({autoplay:!1,dots:!0,smartSpeed:300,animateIn:"fadeFromLeft",animateOut:"fadeOutFromLeft",rtl:Algerieoffice.rtl()})});
</script>