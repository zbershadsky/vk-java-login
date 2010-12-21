<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<% final Logger LOG = LoggerFactory.getLogger("error.jsp");%>
<%LOG.error("Error.");%>

<html>
<head>
<title>Error</title>
</head>
<body bgcolor="white">
<a href="<%=request.getContextPath() %>/vk">Go to the main page</a>
</body>
</html>
