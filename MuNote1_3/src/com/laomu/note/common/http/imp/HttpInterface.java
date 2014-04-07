/**
 * 
 */
package com.laomu.note.common.http.imp;

import com.laomu.note.common.http.HttpReqBean;


/**
 * @author luoyuan.myp
 *
 * 2014-4-3
 */
public interface HttpInterface {
	public abstract void req(String urlMap,HttpReqBean req);
	
}
