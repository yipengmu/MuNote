/**
 * 
 */
package com.laomu.note.data.model;

import android.graphics.Bitmap;

/**
 * @author luoyuan.myp
 *
 * 2014-5-22
 */
public class ImageModel {
	public Bitmap bitmap;
	public String name;

	public String time;
	public LocationBean location;
	
	public ImageModel(Bitmap b,String name) {
		bitmap = b;
		this.name = name;
	}
	
	public ImageModel(Bitmap b,String name,String time,LocationBean location) {
		bitmap = b;
		this.name = name;
		this.time = time;
		this.location = location;
	}
}
