package com.gabriel.test.githubuserdata.ui.main
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.gabriel.test.githubuserdata.AppConfig
import com.gabriel.test.githubuserdata.R
import com.gabriel.test.githubuserdata.intermediary.UserPresenter
import com.gabriel.test.githubuserdata.intermediary.UserUIConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class GithubShowSearchActivity : AppCompatActivity() {

    private var userConfig = AppConfig.initGithubConfig(activity = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrieveUser.setOnClickListener {
            if (!searchUser.text.isNullOrBlank()) {
                userConfig.loadData(searchUser.text.toString())
            }
        }

        userConfig.presenter.onStateChanged = { state ->
            when (state) {
                is UserPresenter.State.Content -> {
                    fillUserInfo(state.uiConfig)
                    containerView.visibility = View.VISIBLE
                    loadingView.visibility = View.GONE
                    errorView.visibility = View.GONE
                }
                is UserPresenter.State.Loading -> {
                    containerView.visibility = View.GONE
                    loadingView.visibility = View.VISIBLE
                    errorView.visibility = View.GONE
                }
                is UserPresenter.State.Error -> {
                    containerView.visibility = View.GONE
                    loadingView.visibility = View.GONE
                    errorView.visibility = View.VISIBLE

                    errorRetryButton.setOnClickListener {
                        state.onRetry()
                    }
                }
            }
        }


//        searchForUser.setOnClickListener() {
//            if (!searchUser.text.isNullOrBlank()) {
//                    AppConfig.searchForName(name = searchUser.text.toString(), activity = this)
//            }
//        }
    }

    private fun fillUserInfo(userConfig: UserUIConfig) {
        sortVisibility(userConfig)
        textLocation.text = getString(R.string.location_header, userConfig.location)
        userName.text = getString(R.string.username_header, userConfig.name)
        searchUser.setText(userConfig.name)
        textLogin.text = getString(R.string.login_header, userConfig.login)
        if (!userConfig.image.isNullOrBlank()) {

            Glide
                .with(this)
                .load(userConfig.image)
                .circleCrop()
                .placeholder(android.R.drawable.ic_menu_upload)
                .into(imageAvatar)
//            imageAvatar.setImageURI(Uri.parse(userConfig.image))
        }
        textHtmlUrl.text = getString(R.string.html_header, userConfig.htmlUrl)
        textBlog.text = getString(R.string.blog_header, userConfig.blog)
        textPublicReposCount.text =
            getString(R.string.repos_count_header, userConfig.reposCount.toString())
        textFollowersCount.text =
            getString(R.string.followers_header, userConfig.followersCount.toString())
        textCreationDate.text = getString(R.string.created_date_header, userConfig.creationDate)
        textUniqueId.text = getString(R.string.id_header, userConfig.id.toString())
        textEmail.text = getString(R.string.email_header, userConfig.email)
        textBio.text = getString(R.string.bio_header, userConfig.bio)
    }

    private fun sortVisibility(userConfig: UserUIConfig) {
        textLocation.visibility =
            if (userConfig.location.isNullOrBlank()) View.GONE else View.VISIBLE
        userName.visibility = if (userConfig.name.isNullOrBlank()) View.GONE else View.VISIBLE
        textLogin.visibility = if (userConfig.login.isNullOrBlank()) View.GONE else View.VISIBLE
        imageAvatar.visibility = if (userConfig.image.isNullOrBlank()) View.GONE else View.VISIBLE
        textHtmlUrl.visibility = if (userConfig.htmlUrl.isNullOrBlank()) View.GONE else View.VISIBLE
        textBlog.visibility = if (userConfig.blog.isNullOrBlank()) View.GONE else View.VISIBLE
        textPublicReposCount.visibility =
            if (userConfig.reposCount == -1) View.GONE else View.VISIBLE
        textFollowersCount.visibility =
            if (userConfig.followersCount == -1) View.GONE else View.VISIBLE
        textCreationDate.visibility =
            if (userConfig.creationDate.isNullOrBlank()) View.GONE else View.VISIBLE
        textUniqueId.visibility = if (userConfig.id == -1) View.GONE else View.VISIBLE
        textEmail.visibility = if (userConfig.email.isNullOrBlank()) View.GONE else View.VISIBLE
        textBio.visibility = if (userConfig.bio.isNullOrBlank()) View.GONE else View.VISIBLE
    }
}

