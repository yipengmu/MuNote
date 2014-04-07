/**
 * 
 */
package com.laomu.note.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author luoyuan.myp
 *
 * 2013-6-22
 */
public class NoteBean implements Parcelable {
	public int note_index;
	public String note_title;
	public String note_content;
	public String note_time;
	
	/**
	 * constructer
	 */
	public NoteBean(String title ,String content, String time) {
		note_title = title;
		note_content = content;
		note_time = time;
	}
	
	public NoteBean(){
		
	}

	/**
	 * @param in
	 */
	public NoteBean(Parcel in) {
		note_index = in.readInt();
		note_title = in.readString();
		note_content = in.readString();
		note_time = in.readString();
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(note_index);
		out.writeString(note_title);
		out.writeString(note_content);
		out.writeString(note_time);
	}
	
	public static final Parcelable.Creator<NoteBean> CREATOR = new Parcelable.Creator<NoteBean>() {
        public NoteBean createFromParcel(Parcel in) {
            return new NoteBean(in);
        }

        @Override 
        public NoteBean[] newArray(int size) {
            return new NoteBean[size];
        }
    };


}
