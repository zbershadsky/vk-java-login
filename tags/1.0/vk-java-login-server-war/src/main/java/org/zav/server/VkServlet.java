package org.zav.server;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zav.auth.VkAuthentificatorValve;

public class VkServlet extends GenericServlet
{

   private static final Logger LOG = LoggerFactory.getLogger(VkAuthentificatorValve.class);

   @Override
   public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
   {
      LOG.debug("\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      LOG.debug("Entering the method");

      HttpServletRequest httpRequest = (HttpServletRequest)req;
      String servletPath = httpRequest.getServletPath();

      Principal principal = httpRequest.getUserPrincipal();
      LOG.debug(" principal = " + principal);
      if (principal != null)
         LOG.debug(" principal.getName() = " + principal.getName());

      LOG.debug(" hReq.getRemoteUser() = " + httpRequest.getRemoteUser());
      LOG.debug(" hReq.getUserPrincipal() = " + httpRequest.getUserPrincipal());
      if (httpRequest.getUserPrincipal() != null)
         LOG.debug(" hReq.getUserPrincipal().getName() = " + httpRequest.getUserPrincipal().getName());
      LOG.debug(" hReq.getSession(false) = " + httpRequest.getSession(false));

      printParsAndAttrs(httpRequest);
      printHeaders(httpRequest);
      printCookies(httpRequest);

      if (servletPath.startsWith("/vk/private"))
      {
         LOG.debug(" Go to the MAIN page");
         httpRequest.getRequestDispatcher("/main.jsp").include(req, res);
      }
      else
      {
         LOG.debug(" Go to the INDEX page");
         httpRequest.getRequestDispatcher("/index.jsp").include(req, res);
      }
   }

   private void printParsAndAttrs(ServletRequest req)
   {
      Enumeration<String> parameterNames = req.getParameterNames();
      while (parameterNames.hasMoreElements())
      {
         String name = (String)parameterNames.nextElement();
         LOG.info(">>> alexey: VkServlet.logParsAndAttrs P name = " + name);
         LOG.info(">>> alexey: VkServlet.logParsAndAttrs        = " + req.getParameter(name));
      }
      Enumeration<String> attributeNames = req.getAttributeNames();
      while (attributeNames.hasMoreElements())
      {
         String name = (String)attributeNames.nextElement();
         LOG.info(">>> alexey: VkServlet.logParsAndAttrs A name = " + name);
         LOG.info(">>> alexey: VkServlet.logParsAndAttrs        = " + req.getAttribute(name));
      }
   }

   private void printHeaders(HttpServletRequest hReq)
   {
      Enumeration<String> headerNames = hReq.getHeaderNames();
      if (headerNames != null)
      {
         while (headerNames.hasMoreElements())
         {
            String name = (String)headerNames.nextElement();
            LOG.info(">>> alexey: VkServlet.printHeaders H name = " + name);
            LOG.info(">>> alexey: VkServlet.printHeaders        = " + hReq.getHeader(name));
         }
      }
   }

   private void printCookies(HttpServletRequest hReq)
   {
      Cookie[] cookies = hReq.getCookies();
      if (cookies != null)
      {
         for (Cookie cookie : cookies)
         {
            LOG.info(">>> alexey: VkServlet.printCookies C name = " + cookie.getName());
            LOG.info(">>> alexey: VkServlet.printCookies        = " + cookie.getValue());
         }
      }
   }

}
