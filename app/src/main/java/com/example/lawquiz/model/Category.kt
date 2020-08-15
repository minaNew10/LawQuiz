package com.example.lawquiz.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
* Data class for category either main or branched
* @Param name is the name of the category it self
* @Param parent the name of parent if exists
* @Param branches the name of branches if exists
* */
@Parcelize
data class Category(val name :String,var parent : String? = null,var branches : List<Category>? = null) : Parcelable