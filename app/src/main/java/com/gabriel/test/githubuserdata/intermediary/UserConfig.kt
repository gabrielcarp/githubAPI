package com.gabriel.test.githubuserdata.intermediary

import androidx.appcompat.app.AppCompatActivity
import com.gabriel.test.githubuserdata.api.UserRepository

/**
 * Configures the User Use Case.
 *
 * @param activity The parent activity used to launch new activities and manage lifecycle events.
 * @param userRepository Repository used to fetch github users.
 */
class UserConfig(
    activity: AppCompatActivity,
    userRepository: UserRepository
) {

    /**
     * Provides routing from the User screen to any other screens.
     */
    val router: UserRouter = UserRouterImpl(
        activity = activity
    )

    /**
     * Handles business logic to manipulate model objects and carry out tasks for
     * the user use case.
     */
    var interactor: UserInteractor = run {
        val interactor = UserInteractorImpl(
            repository = userRepository,
            router = router
        )
        activity.lifecycle.addObserver(interactor)
        interactor
    }

    fun loadData(name: String) {
        interactor.query(name)
    }

    /**
     * Converts data states from the Interactor into view states that are ready to be presented on
     * screen.
     */
    val presenter: UserPresenter = UserPresenterImpl(
        interactor = interactor
    )
}