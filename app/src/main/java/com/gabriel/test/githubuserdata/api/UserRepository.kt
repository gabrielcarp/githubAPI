package com.gabriel.test.githubuserdata.api

import com.gabriel.test.githubuserdata.api.mapping.UserInfo
import com.gabriel.test.githubuserdata.framework.Promise
import com.gabriel.test.githubuserdata.intermediary.entities.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UserRepository(
    private val githubAPI: GithubAPI,
    userNameValue: String
) {

    var name = userNameValue
    /**
     * Returns a Promise that will at some point return the desired user.
     */
    val user: Promise<UserData?>
        get() {
            val promise = Promise<UserData?>()

            githubAPI.getUser(userName = name)
                .enqueue(object : Callback<UserInfo> {
                    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                        if (!response.isSuccessful) {
                            promise.error(IOException("Response was unsuccessful: ${response.code()}"))
                            return
                        }

                        val result = response.body()
                        if (result == null) {
                            promise.error(IOException("Response has no body"))
                            return
                        }

                        result.let { responseParsed ->
                            promise.success(responseParsed.asUser())
                        }
                    }

                    override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                        promise.error(t)
                    }
                })

            return promise
        }
}

private fun UserInfo.asUser(): UserData? {
    return UserData(
        name = name,
        location = location,
        login = login,
        bio = bio,
        email = email,
        followers = followers,
        avatarURL = avatar_url,
        htmlURL = html_url,
        blog = blog,
        company = company,
        publicRepos = public_repos,
        createdAt = created_at,
        id = id
    )
}