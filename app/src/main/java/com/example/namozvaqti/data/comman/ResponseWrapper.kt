package com.example.namozvaqti.data.comman

import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T>(
    @SerializedName("code")  var code: Int,
    @SerializedName("error") var status: String? = null,
    @SerializedName("data") var data: List<T>? = null,

)

data class WrappedResponse<T>(
    @SerializedName("code") var code: Int,
    @SerializedName("error") var status: String? = null,
    @SerializedName("data") var data: T? = null,

)
