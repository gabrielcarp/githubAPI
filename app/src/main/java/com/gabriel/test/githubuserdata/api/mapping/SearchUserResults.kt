package com.gabriel.test.githubuserdata.api.mapping


import android.service.autofill.UserData
import com.google.gson.annotations.SerializedName

data class SearchUserResults(
    @SerializedName("incomplete_results") val incomplete_results: Boolean?,
    @SerializedName("total_count") val total_count: Long?,
    @SerializedName("items") val items: List<UserData>?
)