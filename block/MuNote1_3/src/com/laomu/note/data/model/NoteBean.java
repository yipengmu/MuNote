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
	public int id;
	@DatabaseField
	public int note_index;
	@DatabaseField
	public String note_title;
	@DatabaseField
	public String note_content;
	@DatabaseField
	public String note_time;
	@DatabaseField
	public String note_weather_temp;
	@DatabaseField
	public String note_weather_info;
	/** note_locationId 作为外键 是另一个表的主键*/
	@DatabaseField
	public int note_location_id = 0;
	
	/**
	 * constructer
	 */
	public NoteBean(String title ,String content, String time, String weather_tmp,String weather_info ,int locationId) {
		note_title = title;
		note_content = content;
		note_time = time;
		note_weather_temp = weather_tmp;
		note_weather_info = weather_info;
		note_location_id = locationId;
	}
	
	public NoteBean(){
		
	}

	/**
	 * @param in
	 */
	public NoteBean(Parcel in) {
		id = in.readInt();
		note_index = in.readInt();
		note_title = in.readString();
		note_content = in.readString();
		note_time = in.readString();
		note_weather_temp = in.readString();
		note_weather_info = in.readString();
		note_location_id = in.readInt();
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
		out.writeInt(id);
		out.writeInt(note_index);
		out.writeString(note_title);
		out.writeString(note_content);
		out.writeString(note_time);
		out.writeString(note_weather_temp);
		out.writeString(note_weather_info);
		out.writeInt(note_location_id);
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
