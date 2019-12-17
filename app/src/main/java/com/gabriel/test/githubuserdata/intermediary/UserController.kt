package com.gabriel.test.githubuserdata.intermediary

interface UserController {
    var onStateChanged: (State) -> Unit

    sealed class State {
        class Content(val uiConfig: UserUIConfig) : State()
        object Loading : State()
        class Error(val onRetry: () -> Unit) : State()
    }
}

class UserControllerImpl(viewModel: UserViewModel) : UserController {

    override var onStateChanged: (UserController.State) -> Unit = {}
        set(value) {
            field = value
            onStateChanged(state)
        }

    private var state: UserController.State = UserController.State.Loading
        set(value) {
            if (field != value) {
                field = value
                onStateChanged(value)
            }
        }

    init {
        viewModel.onStateChanged = { state ->
            this.state = when (state) {
                is UserViewModel.State.Content -> state.getData()
                is UserViewModel.State.Loading -> UserController.State.Loading
                is UserViewModel.State.Error -> UserController.State.Error(
                    onRetry = { state.onRetry() }
                )
            }
        }
    }
}

private fun UserViewModel.State.Content.getData(): UserController.State.Content {
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
    return UserController.State.Content(uiConfig = uiConfig)
}

//TODO reuse for search
//private fun UserInteractor.State.Content.map(): UserPresenter.State.Content {
//    val uiConfigs = userHandlers.map { handler ->
//
//        UIConfig(
//    }
//    return Presenter.State.Content(uiConfigs = uiConfigs)
//}