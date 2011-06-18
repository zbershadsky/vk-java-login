package org.zav.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.authenticator.AuthenticatorBase;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.realm.GenericPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkAuthentificatorValve extends AuthenticatorBase
{

   private static final Logger LOG = LoggerFactory.getLogger(VkAuthentificatorValve.class);

   @Override
   protected boolean authenticate(Request request, Response response, LoginConfig loginConfig) throws IOException
   {
      LOG.debug("Entering the method");

      HttpServletRequest httpRequest = (HttpServletRequest)request;

      GenericPrincipal principal = (GenericPrincipal)VkAuthentificator.authenticate(httpRequest);
      LOG.debug(" principal = " + principal);
      if (principal == null)
      {
         forwardToErrorPage(request, response, loginConfig);
         return false;
      }

      register(request, response, principal, "", principal.getName(), "N/P");
      return true;
   }

   protected void forwardToErrorPage(Request request, Response response, LoginConfig config)
   {
      String errorPage = "/error.jsp"; // config.getErrorPage();
      RequestDispatcher disp = context.getServletContext().getRequestDispatcher(errorPage);
      try
      {
         disp.forward(request.getRequest(), response.getResponse());
      }
      catch (Throwable t)
      {
         LOG.warn("Unexpected error forwarding to error page", t);
      }
   }
}
