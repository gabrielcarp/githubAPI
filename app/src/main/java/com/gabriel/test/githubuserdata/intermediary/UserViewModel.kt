package com.gabriel.test.githubuserdata.intermediary

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.gabriel.test.githubuserdata.api.UserRepository
import com.gabriel.test.githubuserdata.intermediary.entities.UserData

interface UserViewModel {
    var onStateChanged: (State) -> Unit
    fun query(name: String)

    sealed class State {
        class Content(val user: UserData) : State()
        object Loading : State()
        class Error(val onRetry: () -> Unit) : State()
    }
}

class UserViewModelImpl(
    private val router: UserRouter,
    private val repository: UserRepository
) : UserViewModel, LifecycleObserver {

    override var onStateChanged: (UserViewModel.State) -> Unit = {}
        set(value) {
            field = value
            onStateChanged(state)
        }

    private var state: UserViewModel.State = UserViewModel.State.Loading
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

    fun loadData() {
        state = UserViewModel.State.Loading
        repository.user
            .onSuccess { info: UserData? ->
                state = if (info == null) {
                    UserViewModel.State.Error(
                        onRetry = {
                            loadData()
                        })
                } else {
                    UserViewModel.State.Content(
                        user = info
                    )
                }
            }
            .onError {
                state = UserViewModel.State.Error(
                    onRetry = {
                        loadData()
                    }
                )
            }
    }

    override fun query(name: String) {
        repository.name = name
        loadData()
    }
}