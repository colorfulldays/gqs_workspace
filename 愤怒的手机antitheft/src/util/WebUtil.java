package util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antitheft.Const;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public class WebUtil {
	
	public static boolean visit(Context context,String url){
		boolean ret = false;  
        /*����HTTP Get����*/
        HttpGet httpRequest = new HttpGet(url); 
        try 
        { 
          /*����HTTP request*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*��״̬��Ϊ200 ok*/
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*ȡ����Ӧ�ַ���*/
            String strResult = EntityUtils.toString(httpResponse.getEntity());
            /*ɾ�������ַ�*/
            strResult = eregi_replace("(\r\n|\r|\n|\n\r)","",strResult);
            ToastUtil.show(context, strResult,Const.showWebUtil);
            return true;
          } 
          else 
          {  
        	  ToastUtil.show(context, "!=200",Const.showWebUtil);
          } 
        } 
        catch (ClientProtocolException e) 
        {    
        	e.printStackTrace(); 
        	ToastUtil.show(context, e.getMessage(),Const.showWebUtil);
        } 
        catch (IOException e) 
        { 
        	ToastUtil.show(context, e.getMessage(),Const.showWebUtil);
        	e.printStackTrace(); 
        } 
         
		return ret; 
	}
	
	public static String eregi_replace(String strFrom, String strTo, String strTarget)
    {
      String strPattern = "(?i)"+strFrom;
      Pattern p = Pattern.compile(strPattern);
      Matcher m = p.matcher(strTarget);
      if(m.find())
      {
        return strTarget.replaceAll(strFrom, strTo);
      }
      else
      {
        return strTarget;
      }
    }
}
