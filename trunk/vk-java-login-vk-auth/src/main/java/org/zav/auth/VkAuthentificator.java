package org.zav.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.realm.GenericPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkAuthentificator
{
   private static final Logger LOG = LoggerFactory.getLogger(VkAuthentificator.class);

   private static final String VK_APP_ID = "2045117";

   private static final String VK_APP_SECRET = "4SlidEThakNkfVk9jUx5";

   private static final String VK_APP_COOKIE_NAME = "vk_app_" + VK_APP_ID;

   private static final String USER_GROUP = "users";

   private static final String COOKIE_PARAM_EXPIRE = "expire";

   private static final String COOKIE_PARAM_MID = "mid";

   private static final String COOKIE_PARAM_SECRET = "secret";

   private static final String COOKIE_PARAM_SID = "sid";

   private static final Object COOKIE_PARAM_SIG = "sig";

   private static MessageDigest md = null;
   static
   {
      try
      {
         md = MessageDigest.getInstance("MD5");
      }
      catch (NoSuchAlgorithmException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Check whether the hash and user ID was ok.
    * 
    * @param httpRequest the httpRequest
    * @return the Principal with the verified user name
    */
   public static Principal authenticate(HttpServletRequest httpRequest)
   {
      LOG.debug("Entering the method");
      LOG.debug(" httpRequest.getRequestURI() = " + httpRequest.getRequestURI());
      Principal principal = httpRequest.getUserPrincipal();
      LOG.debug(" principal = " + principal);
      if (principal != null)
         LOG.debug(" principal.getName() = " + principal.getName());
      // the authentication
      String mid = checkHash(httpRequest);
      if (mid != null)
      {
         // the user is ok
         String username = mid;
         List<String> roles = new ArrayList<String>();
         roles.add(USER_GROUP);
         GenericPrincipal serverPrincipal = new GenericPrincipal(null, username, "N/P", roles);
         return serverPrincipal;
      }
      else
      {
         // the user was wrong or there wasn't Vk App cookie
         return null;
      }
   }

   /**
    * Returns member ID if the hash was ok, null otherwise.
    * 
    * @param httpRequest the http servlet request
    * @return member ID
    */
   public static String checkHash(HttpServletRequest httpRequest)
   {
      String cookieHeader = httpRequest.getHeader("cookie");
      LOG.debug(" cookieHeader = " + cookieHeader);

      if (cookieHeader == null)
         return null;

      // Get cookie header
      String vkAppValue = null;
      // parse several cookies
      String[] cookiesArray = cookieHeader.split("; ");
      for (String cookie : cookiesArray)
      {
         if (cookie != null && cookie.startsWith(VK_APP_COOKIE_NAME + "="))
         {
            vkAppValue = cookie;
         }
      }
      LOG.debug(" vkAppValue = " + vkAppValue);

      if (vkAppValue == null)
         return null;

      // get the cookie value
      vkAppValue = vkAppValue.substring((VK_APP_COOKIE_NAME + "=").length());

      // parse the cookie value
      Map<String, String> map = parseVkAppValue(vkAppValue);

      // check parameters within map
      if (map == null || map.size() < 4 || map.get(COOKIE_PARAM_EXPIRE) == null || map.get(COOKIE_PARAM_MID) == null
         || map.get(COOKIE_PARAM_SECRET) == null || map.get(COOKIE_PARAM_SID) == null)
         return null;

      // create parameters mix
      StringBuffer sb = new StringBuffer();
      sb.append(COOKIE_PARAM_EXPIRE + "=" + map.get(COOKIE_PARAM_EXPIRE));
      sb.append(COOKIE_PARAM_MID + "=" + map.get(COOKIE_PARAM_MID));
      sb.append(COOKIE_PARAM_SECRET + "=" + map.get(COOKIE_PARAM_SECRET));
      sb.append(COOKIE_PARAM_SID + "=" + map.get(COOKIE_PARAM_SID));
      sb.append(VK_APP_SECRET);
      String toHash = sb.toString();

      // get the client checksum
      String sig = map.get(COOKIE_PARAM_SIG);
      LOG.debug(" sig = !!!!! = " + sig);
      // get the user ID
      String mid = map.get(COOKIE_PARAM_MID);
      LOG.debug(" mid = " + mid);

      // create the control checksum
      byte[] mdbytes = md.digest(toHash.getBytes());
      // convert the byte to the hex format
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < mdbytes.length; i++)
      {
         String hex = Integer.toHexString(0xff & mdbytes[i]);
         if (hex.length() == 1)
            hexString.append('0');
         hexString.append(hex);
      }
      String digest = hexString.toString();

      LOG.debug(" digest = " + digest);
      LOG.debug(" sig.equals(digest) = " + sig.equals(digest));
      if (sig.equals(digest))
         return mid;
      else
         return null;
   }

   /**
    * Create a map contains cookie parameters.
    * 
    * @param vkAppValue the cookie value
    * @return map of the cookie parameters
    */
   private static Map<String, String> parseVkAppValue(String vkAppValue)
   {
      LOG.debug(" vkAppValue = " + vkAppValue);
      if (vkAppValue == null || vkAppValue.length() == 0)
         return null;
      String[] vkAppValuesArray = vkAppValue.split("&");
      // parse cookie parameters
      Map<String, String> map = new HashMap<String, String>();
      for (String vkAppParam : vkAppValuesArray)
      {
         LOG.debug(" vkAppParam = " + vkAppParam);
         String[] splitted = vkAppParam.split("=");
         if (splitted.length > 1)
         {
            map.put(splitted[0], splitted[1]);
         }
      }
      return map;
   }

}
