# kmp-realm

kmp-realm a Kotlin Multiplaform library which wraps [Kotlin Realm](https://github.com/realm/realm-kotlin) with pre-configuration.

### 💾 Installation

1️⃣ Add the dependency in the sourceSet of all modules that require Realm:

```groovy
implementation(project.dependencies.platform("io.github.tweener:kmp-bom:$kmp-bom_version"))
implementation("io.github.tweener:kmp-realm")
```

_The latest version
is: [![](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fservice%2Flocal%2Frepo_groups%2Fpublic%2Fcontent%2Fio%2Fgithub%2Ftweener%2Fkmp-bom%2Fmaven-metadata.xml)](https://central.sonatype.com/artifact/io.github.tweener/kmp-bom)_

2️⃣ Add the Realm plugin dependency to the root's `build.gradle.kts`:

```groovy
id("io.realm.kotlin").version("1.13.0").apply(false)
```

3️⃣ Apply the Realm plugin to all modules that declare `RealmObject`s:

```groovy
id("io.realm.kotlin")
```

### ⚙️ Usage

1️⃣ Configure your database schema by specifying all your `RealmObject`s:

```groovy
val realmDatabse = RealmDatabase(
  schema = setOf(
    // Here, add all your classes that extend `RealmObject`
    RealmUserModel::class, // This is an example. RealmUserModel extends RealmObject
    ...
  )
)
```

2️⃣ Use the methods from `RealmDatabase` class to interact with your Realm database:

```groovy
// Get all users synchronously
realmDatabase.getAll<RealmUserModel>()

// Get all users asynchronously, using Kotlin Flows
realmDatabase.getAllAsFlow<RealmUserModel>()

// Find by property
realmDatabase.findByProperty(propertyName = "name", propertyValue = "John")

// Find by Realm UUID
realmDatabase.findByRealmUuid(uuid = "f826c548-fec7-4409-bdfb-523f29383857")

// Insert a new model or update an existing one
realmDatabase.upsert(user)

// Delete a model
realmDatabase.deleteById(id = "user1234")
```
