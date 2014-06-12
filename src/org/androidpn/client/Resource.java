package org.androidpn.client;

/*
 * write by Jackie on 0514
 * */
import android.os.Parcel;
import android.os.Parcelable;

public class Resource  implements Parcelable{

	String resourceId;
	String resourceUri;
	String picUri;
	
	public Resource() {
		super();
	}

	public Resource(String resourceId) {
		super();
		this.resourceId = resourceId;
	}

	public Resource(String resourceId, String resourceUri) {
		super();
		this.resourceId = resourceId;
		this.resourceUri = resourceUri;
	}

	public Resource(String resourceId, String resourceUri, String picUri) {
		super();
		this.resourceId = resourceId;
		this.resourceUri = resourceUri;
		this.picUri = picUri;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public String getPicUri() {
		return picUri;
	}

	public void setPicUri(String picUri) {
		this.picUri = picUri;
	}

	public static final Parcelable.Creator<Resource> CREATOR =
			new Creator<Resource>() {

				public Resource createFromParcel(Parcel source) {
					// TODO Auto-generated method stub
					Resource res = new Resource();
					res.resourceId = source.readString();
					res.resourceUri = source.readString();		
					res.picUri = source.readString();
					return res;
				}

				public Resource[] newArray(int size) {
					// TODO Auto-generated method stub
					return new Resource[size];
				}
		
	};
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		parcel.writeString(resourceId);
		parcel.writeString(resourceUri);
		parcel.writeString(picUri);
	}
	
}
