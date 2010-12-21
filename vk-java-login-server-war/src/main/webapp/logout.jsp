<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<% final Logger LOG = LoggerFactory.getLogger("logout.jsp");%>
<%LOG.debug("Logout: Session invalidating and redirecting...");%>

<%request.getSession().invalidate();%>
<%response.sendRedirect(request.getContextPath() + "/vk");%>
