package com.gabriel.test.githubuserdata.intermediary

interface UserPresenter {
    var onStateChanged: (State) -> Unit

    sealed class State {
        class Content(val uiConfig: UserUIConfig) : State()
        object Loading : State()
        class Error(val onRetry: () -> Unit) : State()
    }
}

class UserPresenterImpl(interactor: UserInteractor) : UserPresenter {

    override var onStateChanged: (UserPresenter.State) -> Unit = {}
        set(value) {
            field = value
            onStateChanged(state)
        }

    private var state: UserPresenter.State = UserPresenter.State.Loading
        set(value) {
            if (field != value) {
                field = value
                onStateChanged(value)
            }
        }

    init {
        interactor.onStateChanged = { state ->
            this.state = when (state) {
                is UserInteractor.State.Content -> state.getData()
                is UserInteractor.State.Loading -> UserPresenter.State.Loading
                is UserInteractor.State.Error -> UserPresenter.State.Error(
                    onRetry = { state.onRetry() }
                )
            }
        }
    }
}

private fun UserInteractor.State.Content.getData(): UserPresenter.State.Content {
    val uiConfig = UserUIConfig(
        name = user.name,
        location = user.location,
        login = user.login,
        image = user.avatarURL,
        htmlUrl = user.htmlURL,
        blog = user.blog,
        reposCount = user.publicRepos,
        followersCount = user.followers,
        creationDate = user.createdAt,
        id = user.id,
        email = user.email,
        bio = user.bio
    )
    return UserPresenter.State.Content(uiConfig = uiConfig)
}

//TODO reuse for search
//private fun UserInteractor.State.Content.map(): UserPresenter.State.Content {
//    val uiConfigs = userHandlers.map { handler ->
//
//        UIConfig(
//    }
//    return Presenter.State.Content(uiConfigs = uiConfigs)
//}