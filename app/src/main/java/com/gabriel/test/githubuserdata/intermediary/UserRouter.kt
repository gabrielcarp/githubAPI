package com.gabriel.test.githubuserdata.intermediary

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import com.gabriel.test.githubuserdata.ui.main.GithubShowSearchActivity
import java.lang.ref.WeakReference

//TODO update for search
interface UserRouter {
    fun onItemSelecte(woeid: String)
}

class UserRouterImpl(activity: Activity) : UserRouter {
    override fun onItemSelecte(woeid: String) {
//        val activity = activityRef.get()
//        if (activity != null) {
//            val intent = Intent(activity, GithubShowSearchActivity::class.java).apply {
//                putExtra(Constants.USER_KEY, woeid)
//            }
//            val options = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
//            activity.startActivity(intent, options)
////            activity.startActivity(intent)
//        }
    }

    val activityRef = WeakReference<Activity>(activity)
}