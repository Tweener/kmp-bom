package com.tweener.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.TypedRealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

/**
 * @author Vivien Mahe
 * @since 08/02/2024
 */
class RealmDatabase(
    private val schema: Set<KClass<out TypedRealmObject>>,
) {

    val realm: Realm by lazy {
        val configuration = RealmConfiguration.create(schema = schema)
        Realm.open(configuration)
    }

    inline fun <reified T : TypedRealmObject> getAll(): RealmResults<T> =
        realm.query<T>().find()

    inline fun <reified T : TypedRealmObject> getAllAsFlow(): Flow<RealmResults<T>> =
        realm.query<T>().asFlow().map { it.list }

    inline fun <reified T : TypedRealmObject> findByRealmUuid(uuid: String): T? =
        findByProperty(propertyName = "id", propertyValue = RealmUUID.from(uuid))

    inline fun <reified T : TypedRealmObject> findByProperty(propertyName: String, propertyValue: Any): T? =
        realm.query<T>("$propertyName == $0", propertyValue).first().find()

    inline fun <reified T : TypedRealmObject> findByProperties(properties: List<RealmQueryConstraint>): List<T> {
        if (properties.isEmpty()) return emptyList()

        val firstProperty = properties[0]

        if (properties.size == 1) {
            return realm.query<T>(firstProperty.query, firstProperty.value).find()
        }

        return realm
            .query<T>(firstProperty.query, firstProperty.value)
            .apply { for (i in 1..<properties.size) query(properties[i].query, properties[i].value) }
            .find()
    }

    inline fun <reified T : RealmObject> upsert(instance: T) =
        realm.writeBlocking {
            copyToRealm(instance, updatePolicy = UpdatePolicy.ALL)
        }

    inline fun <reified T : RealmObject> deleteByRealmUuid(uuid: String) {
        realm.writeBlocking {
            findByRealmUuid<T>(uuid = uuid)?.let { instance ->
                // To make sure to use the latest version of the frozen object, we need to use "findLatest"
                // See doc: https://www.mongodb.com/docs/realm/sdk/kotlin/realm-database/frozen-arch/#access-a-live-version-of-frozen-object
                findLatest(instance)?.also {
                    delete(it)
                }
            }
        }
    }

    inline fun <reified T : RealmObject> deleteByProperty(propertyName: String, propertyValue: Any) {
        realm.writeBlocking {
            findByProperty<T>(propertyName = propertyName, propertyValue = propertyValue)?.let { instance ->
                // To make sure to use the latest version of the frozen object, we need to use "findLatest"
                // See doc: https://www.mongodb.com/docs/realm/sdk/kotlin/realm-database/frozen-arch/#access-a-live-version-of-frozen-object
                findLatest(instance)?.also {
                    delete(it)
                }
            }
        }
    }

    inline fun <reified T : RealmObject> delete(instance: T) {
        realm.writeBlocking {
            findLatest(instance)?.also {
                delete(it)
            }
        }
    }

    inline fun <reified T : RealmObject> deleteAll() {
        realm.writeBlocking {
            val all = query<T>().find()
            delete(all)
        }
    }

    fun clear() {
        realm.writeBlocking {
            deleteAll()
        }
    }
}
