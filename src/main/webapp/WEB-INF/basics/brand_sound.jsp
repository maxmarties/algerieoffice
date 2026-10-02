<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<audio id="notificationAudio" class="hidden" controls preload="none">
	<source src="<c:url value="/static/sound/notis.mp3" />" type="audio/mpeg">
	<source src="<c:url value="/static/sound/notis.ogg" />" type="audio/ogg">
</audio>
<audio id="messageAudio" class="hidden" controls preload="none">
	<source src="<c:url value="/static/sound/message.mp3" />" type="audio/mpeg">
	<source src="<c:url value="/static/sound/message.ogg" />" type="audio/ogg">
</audio>
<audio id="talkAudio" class="hidden" controls preload="none">
	<source src="<c:url value="/static/sound/chat.mp3" />" type="audio/mpeg">
	<source src="<c:url value="/static/sound/chat.ogg" />" type="audio/ogg">
</audio>
<audio id="supportAudio" class="hidden" controls preload="none">
	<source src="<c:url value="/static/sound/support.mp3" />" type="audio/mpeg">
	<source src="<c:url value="/static/sound/support.ogg" />" type="audio/ogg">
</audio>