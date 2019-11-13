Github User Info

This repository is an example of using Github API with kotlin to retrieve and display user info.

Used Retrofit, OkHttp, and Gson as libraries to properly parse Github API

The application has basically 3 layers of responsability in accordance with the MVVM standard.

The UI which is the activity
THe middle which is the presenter and interactor
and the end which is the repository and api client.
Between these 3 layers there is another translator of data and interactions between them
so we can easily test and change and layer without a huge impact for the app.
Each of the layers has it's own data class for the User object so we can manipulate it as needed
on each step, without a strict enforcement of how the API provides it to be used by the UI.

The project should be compiled and can run as any android project.

TODO
fix activity forgetting last name
add unit testing
add flow tests
add user name search
improve UI
add storage of results
change default android icon
look into utility of min API 24