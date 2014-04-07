/**
 * 
 */
package com.laomu.note.common.http.excutor;

import java.util.Map;

import com.laomu.note.common.http.HttpReqBean;

/**
 * @author luoyuan.myp
 *
 * 2014-4-4
 */
public class HttpController{

	public HttpExcutor mHttpEx;
	private static HttpController ins;
	private HttpController(){
		
	}
	
	//singleton
	public static HttpController instance(){
		if(ins == null){
			ins = new HttpController();
		}
		return ins;
	}
	
	public Object req(String mapKey,HttpReqBean req){
		HttpExcutor ex = new HttpExcutor();
		ex.req(mapKey,req);
		return null;
	}
}
