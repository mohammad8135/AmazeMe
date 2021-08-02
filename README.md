
## Built With 🛠

Amaze Me in a glance\

![alt text](./demo/showlist.png "Show List") ![alt text](./demo/showdetail.png "Show Detail") ![alt text](./demo/showdetailscrolled.png "Show Detail Scrolled") ![alt text](./demo/showlistoffline.png "Show List Offline") 

Demo\
![alt text](./demo/demo.gif "Demo") 


- Android Studio 4.2.2+
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Coroutines is Google's recommended solution for asynchronous programming on Android. 
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - An asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
- [Material Components](https://github.com/material-components/material-components-android) - Material Components for Android (MDC-Android) help developers execute Material Design. Developed by a core team of engineers and UX designers at Google.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Android Navigation Component
  - [Paging v3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Dagger Hilt](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.


- Networking
  - [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
  - [Gson](https://github.com/google/gson) - Gson is a modern JSON library for Android and Java. It makes it easy to parse JSON into Java objects.
  - [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.

- Testing
  - [JUnit](https://junit.org/junit4/) - JUnit is a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.
  - [MockK](https://mockk.io/) - mocking library for Kotlin.
  - [kotlinx-coroutines-test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/) - This package provides testing utilities for effectively testing coroutines.
  - [Arch Core](https://developer.android.com/jetpack/androidx/releases/arch-core) - Helper for other arch dependencies, including JUnit test rules that can be used with LiveData.


## Architecture
- This app inspired by Google's recommended [MVVM (Model View View-Model) - Repository](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture, repository pattern. In addition, it it has a component called domain which wrap the use cases that supported. It makes viewModels cleaner and defines data flow better.

Overview of the application architecture 
![alt text](https://github.com/mohammad8135/AmazeMe/tree/develop/demo/architecture.png "Architecture Overview")

