package com.catalin.library

import com.google.gson.annotations.SerializedName

class Message(@SerializedName("image")val image:String,
              @SerializedName( "filter")val filter: String,
              @SerializedName("title")val title: String)