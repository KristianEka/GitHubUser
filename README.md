# GitHubUser-Dicoding-MADE 🚀
This project is part of the **Dicoding "Menjadi Android Developer Expert" (MADE)** course. The GitHubUser app allows users to search for GitHub users, view their profiles, and manage favorite users with secure data storage.

<br>

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/KristianEka/GitHubUser/tree/master.svg?style=shield)](https://dl.circleci.com/status-badge/redirect/gh/KristianEka/GitHubUser/tree/master)

## Features 📱
- **Search GitHub Users:** Fetch GitHub users and view profile information using the GitHub API.
- **Favorites:** Save favorite users securely using **Room** with **SQLCipher** for encrypted local storage.
- **User Details:** View detailed information about each GitHub user, including repositories and followers.
- **Dark Mode:** Support for light and dark themes.
- **Secure Storage:** Data encryption with **SQLCipher** and obfuscation with **ProGuard**.
- **Certificate Pinning:** Enhance security using **OkHttp** for SSL certificate pinning.
- **Continuous Integration:** Integrated with **CircleCI** for automated builds and testing.
- **Memory Leak Detection:** Monitor app memory with **LeakCanary**.

## Tech Stack 🛠️
- **Kotlin** - Android development language.
- **Android View** - For UI components.
- **Clean Architecture** - Ensuring scalable, maintainable code.
- **DataStore** - For handling user preferences.
- **Room** - For local database storage with encryption.
- **Koin (DI)** - Dependency injection framework.
- **Coroutine Flow** - Asynchronous data stream handling.
- **LiveData** - Observing and updating UI based on data changes.
- **Glide** - For efficient image loading.
- **Circle Image View** - For displaying circular profile pictures.
- **Retrofit** - HTTP client for making API calls.
- **CircleCI** - For continuous integration and deployment.
- **LeakCanary** - To detect memory leaks.
- **SQLCipher** - For encrypting the Room database.
- **ProGuard** - Code obfuscation and optimization.
- **OkHttp** - For HTTP requests with certificate pinning.

## Setup & Installation ⚙️
1. Clone the repository:
   ```bash
   git clone https://github.com/KristianEka/GitHubUser.git
   ```
2. Install dependencies and build the project in **Android Studio**.
3. Run the app on an emulator or device.
