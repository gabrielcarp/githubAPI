package com.gabriel.test.githubuserdata.api.mapping


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("login") val login: String?,
    @SerializedName("avatar_url") val avatar_url: String?,
    @SerializedName("html_url") val html_url: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("blog") val blog: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("public_repos") val public_repos: Int?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("created_at") val created_at: String?,
    @SerializedName("updated_at") val updated_at: String?,
    @SerializedName("id") val id: Int?
)