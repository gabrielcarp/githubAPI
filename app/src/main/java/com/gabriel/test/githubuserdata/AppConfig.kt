package com.gabriel.test.githubuserdata

import androidx.appcompat.app.AppCompatActivity
import com.gabriel.test.githubuserdata.api.UserRepository
import com.gabriel.test.githubuserdata.api.initGithubAPI
import com.gabriel.test.githubuserdata.api.initHttpClient
import com.gabriel.test.githubuserdata.intermediary.UserConfig

object AppConfig {
    private val woeIds = listOf(
        "octocat",
        "pjhyett",
        "wycats",
        "ezmobius",
        "defunkt"
    )

    private val httpClient by lazy { initHttpClient() }
    private val githubAPI by lazy { initGithubAPI(httpClient) }

    /**
     * Initializes the Github default user use case configuration.
     *
     * @param The parent activity used to launch new activities and manage lifecycle events.
     */
    fun initGithubConfig(activity: AppCompatActivity) =
        UserConfig(
            activity = activity,
            userRepository = UserRepository(
                githubAPI = githubAPI,
                userNameValue = woeIds.random()
            )
        )
}