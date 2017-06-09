package com.jakubwyc.mysecretvault.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*


data class Treasure(val text: String = "", val date: Long = Date().time) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Treasure> = object : Parcelable.Creator<Treasure> {
            override fun createFromParcel(source: Parcel): Treasure = Treasure(source)
            override fun newArray(size: Int): Array<Treasure?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(text)
        dest.writeLong(date)
    }
}
