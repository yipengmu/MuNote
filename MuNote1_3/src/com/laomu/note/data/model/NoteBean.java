/**
 * 
 */
package com.laomu.note.data.model;

import com.j256.ormlite.field.DatabaseField;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author luoyuan.myp
 *
 * 2013-6-22
 */
public class NoteBean implements Parcelable {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	public int note_index;
	@DatabaseField
	public String note_title;
	@DatabaseField
	public String note_content;
	@DatabaseField
	public String note_time;
	public LocationBean note_location;
	
	/**
	 * constructer
	 */
	public NoteBean(String title ,String content, String time,LocationBean l) {
		note_title = title;
		note_content = content;
		note_time = time;
		note_location = l;
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
		note_location = in.readParcelable(LocationBean.class.getClassLoader());
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
		out.writeParcelable(note_location,Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
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
