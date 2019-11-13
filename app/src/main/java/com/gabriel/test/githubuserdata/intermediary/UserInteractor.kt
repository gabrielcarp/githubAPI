package com.gabriel.test.githubuserdata.intermediary

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.gabriel.test.githubuserdata.api.UserRepository
import com.gabriel.test.githubuserdata.intermediary.entities.UserData

interface UserInteractor {
    var onStateChanged: (State) -> Unit

    sealed class State {
        class Content(val user: UserData) : State()
        object Loading : State()
        class Error(val onRetry: () -> Unit) : State()
    }
}

class UserInteractorImpl(
    private val router: UserRouter,
    private val repository: UserRepository
) : UserInteractor, LifecycleObserver {

    override var onStateChanged: (UserInteractor.State) -> Unit = {}
        set(value) {
            field = value
            onStateChanged(state)
        }

    private var state: UserInteractor.State = UserInteractor.State.Loading
        set(value) {
            if (field != value) {
                field = value
                onStateChanged(value)
            }
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        loadData()
    }

    private fun loadData() {
        state = UserInteractor.State.Loading
        repository.user
            .onSuccess { info: UserData? ->
                state = if (info == null) {
                    UserInteractor.State.Error(
                        onRetry = {
                            loadData()
                        })
                } else {
                    UserInteractor.State.Content(
                        user = info
                    )
                }
            }
            .onError {
                state = UserInteractor.State.Error(
                    onRetry = {
                        loadData()
                    }
                )
            }
    }
}