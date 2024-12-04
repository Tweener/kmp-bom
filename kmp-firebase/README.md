# kmp-firebase

kmp-firebase is a Kotlin Multiplatform library which wraps [GitLiveApp Firebase SDK](https://github.com/GitLiveApp/firebase-kotlin-sdk) and provides a more straightforward implementation.

## 💾 Installation

Add the dependency in the `sourceSet` of your module:

```groovy
implementation(project.dependencies.platform("io.github.tweener:kmp-bom:$kmp-bom_version")) // Mandatory
implementation("io.github.tweener:kmp-firebase")
```

_The latest version
is: [![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fservice%2Flocal%2Frepo_groups%2Fpublic%2Fcontent%2Fio%2Fgithub%2Ftweener%2Fkmp-bom%2Fmaven-metadata.xml)](https://central.sonatype.com/artifact/io.github.tweener/kmp-bom)_

---

## ⚙️ Usage

### Authentication

For authentication with Firebase, please refer to this library: [Passage](https://github.com/Tweener/passage).

![Passage logo](https://github.com/user-attachments/assets/c92f7d44-df02-4860-9ba4-fb4d4c3f3d68#gh-light-mode-only)
![Passage logo](https://github.com/user-attachments/assets/28914622-f8b9-403b-a1b6-3c736af5e98a#gh-dark-mode-only)

#### Firestore

Create an instance of `FirestoreService` to manage your Firestore collections. All documents models must extend `com.tweener.firebase.firestore.model.FirestoreModel`.

```kotlin
val firestoreService: FirestoreService()

// Retrieve all document models from a collection. Example with FirestoreUserModel which extends FirestoreModel
val users: List<FirestoreUserModel> = firestoreService.getAll<FirestoreUserModel>(collection = "my_firestore_users_collection")

// Retrieve a single document model from its id
val user: FirestoreUserModel = firestoreService.get<FirestoreUserModel>(collection = "my_firestore_users_collection", id = "user1234")

// Create a new document model in a collection by specifying its ID
firestoreService.create(
    collection = "my_firestore_users_collection",
    id = "user12345",
    data = hashMapOf(
        "username" to "John",
        "age" to 28,
    )
)

// Add a new document model in a collection and let Firebase generate a random ID
firestoreService.add(
    collection = "my_firestore_users_collection",
    data = hashMapOf(
        "username" to "John",
        "age" to 28,
    )
)

// Update an existing document model in a collection
firestoreService.update(
    collection = "my_firestore_users_collection",
    id = "user1234",
    data = hashMapOf(
        "username" to "John Doe",
        "age" to 29,
    )
)

// Delete an existing document model from a collection
firestoreService.delete(
    collection = "my_firestore_users_collection",
    id = "user1234",
)
```

### Functions

Create an instance of `FirebaseFunctionsService` to call a Cloud Function and specify its request and response types:

```kotlin
val firebaseFunctionsService: FirebaseFunctionsService()

// For a Firebase Cloud Function named "splitUserName", which splits a user's fullname into its firstname and lastname, you can call the function and get the response like this:
@kotlinx.serialization.Serializable
class FirebaseFunctionsSplitNameResponse(
    val firstname: String,
    val lastname: String,
)

val response: FirebaseFunctionsSplitNameResponse = firebaseFunctionsService.callFunction<String, FirebaseFunctionsSplitNameResponse>(functionName = "splitUserName", data = "John Doe")
assertTrue(response.firstname, "John")
assertTrue(response.lastname, "Doe")
```

### Remote Config

Create an instance of `RemoteConfigDataSource` to retrieve properties set in Firebase Remote Config.
A `RemoteConfigService(isDebug: Boolean)` is required to initialize a `RemoteConfigDataSource`.
When `isDebug` property is **false**, the library will fetch Remote Config properties **every hour**. When `isDebug` property is **true**, we force the fetch of properties **every 10 seconds**.

```kotlin
val remoteConfigService = RemoteConfigService(isDebug = BuildConfig.DEBUG)
val remoteConfigDataSource = RemoteConfigDataSource(firebaseRemoteConfigService = remoteConfigService)

// Retrieve the value of a Boolean property
val isPaywallEnabled: Boolean = remoteConfigDataSource.getBoolean(key = "isPaywallEnabled", defaultValue = true)

// Retrieve the value of a String property
val defaultUsername: String = remoteConfigDataSource.getString(key = "defaultUsername", defaultValue = "John Doe")

// Retrieve the value of a Long property
val defaultRequestTimeoutInSeconds: Long = remoteConfigDataSource.getLong(key = "defaultRequestTimeoutInSeconds", defaultValue = 30L)
```

### Crashlytics

Create an instance of `CrashlyticsService` to access Firebase Crashlytics.

```kotlin
val crashlyticsService = CrashlyticsService()
val firebaseCrashlytics: FirebaseCrashlytics = crashlyticsService.getCrashlytics()
```
