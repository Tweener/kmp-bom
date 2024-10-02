package com.tweener.firebase.firestore

import com.tweener.firebase.firestore.model.FirestoreModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.ChangeType
import dev.gitlive.firebase.firestore.firestore
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * @author Vivien Mahe
 * @since 09/12/2023
 */
class FirestoreService {

    suspend inline fun <reified T : FirestoreModel> getAll(collection: String): List<T> =
        try {
            Napier.d { "Fetching Firestore collection $collection..." }

            Firebase
                .firestore
                .collection(collection)
                .get()
                .documents
                .map { it.data<T>().apply { id = it.id } }
                .also { Napier.d { "Successfully fetched Firestore collection $collection!" } }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't fetch Firestore collection $collection" }
            throw throwable
        }

    inline fun <reified T : FirestoreModel> getAllAsFlow(collection: String): Flow<List<T>> =
        Firebase
            .firestore
            .collection(collection)
            .snapshots()
            .onStart { Napier.d { "Listening to changes for Firestore documents in collection $collection..." } }
            .map { it.documentChanges.onEach { document -> Napier.d { "Firestore document ${document.type} (id=${document.document.id}) in collection $collection" } } }
            .map { documentChange -> documentChange.map { it.document.data<T>().apply { id = it.document.id } } }
            .onEach { Napier.d { "New emitted values for Firestore in collection $collection!" } }
            .onCompletion { throwable -> throwable?.let { Napier.e(it) { "Couldn't fetch Firestore collection $collection" } } }

    suspend inline fun <reified T : FirestoreModel> get(collection: String, id: String): T =
        try {
            Napier.d { "Fetching Firestore document $id in collection $collection..." }

            Firebase
                .firestore
                .collection(collection)
                .document(id)
                .get()
                .data<T>()
                .also { Napier.d { "Successfully fetched Firestore document $id in collection $collection!" } }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Firestore document $id does not exist in collection $collection" }
            throw throwable
        }

    inline fun <reified T : FirestoreModel> getAsFlow(collection: String, id: String): Flow<T> =
        Firebase
            .firestore
            .collection(collection)
            .snapshots()
            .onStart { Napier.d { "Listening to new or updated Firestore documents matching id=$id in collection $collection..." } }
            .map {
                it.documentChanges
                    .onEach { document -> Napier.d { "Firestore document ${document.type} (id=${document.document.id}) in collection $collection" } }
                    .filter { document -> document.type == ChangeType.ADDED || document.type == ChangeType.MODIFIED }
                    .filter { document -> document.document.id == id }
            }
            .mapNotNull { it.firstOrNull()?.document?.data<T>() }
            .onEach { Napier.d { "New emitted value for Firestore document $id in collection $collection!" } }
            .onCompletion { throwable -> throwable?.let { Napier.e(it) { "Couldn't fetch Firestore document $id in collection $collection" } } }

    suspend inline fun <reified T : FirestoreModel> create(collection: String, id: String, data: Map<String, Any?>): T {
        try {
            Napier.d { "Creating Firestore document $id in collection $collection..." }

            Firebase
                .firestore
                .collection(collection)
                .document(id)
                .set(data)
                .also { Napier.d { "Successfully created Firestore document $id in collection $collection!" } }
                .also { return Firebase.firestore.collection(collection).document(id).get().data<T>() }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't create Firestore document $id in collection $collection" }
            throw throwable
        }
    }

    suspend inline fun add(collection: String, data: Map<String, Any?>) {
        try {
            Napier.d { "Adding Firestore document in collection $collection..." }

            Firebase
                .firestore
                .collection(collection)
                .add(data)
                .also { Napier.d { "Successfully added Firestore document in collection $collection!" } }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't add Firestore document in collection $collection" }
            throw throwable
        }
    }

    suspend inline fun update(collection: String, id: String, data: Map<String, Any?>) {
        try {
            Napier.d { "Updating Firestore document $id in collection $collection..." }

            Firebase
                .firestore
                .collection(collection)
                .document(id)
                .update(data)
                .also { Napier.d { "Successfully updated Firestore document $id in collection $collection!" } }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't update Firestore document $id in collection $collection" }
            throw throwable
        }
    }

    suspend fun delete(collection: String, id: String) {
        try {
            Napier.d { "Deleting Firestore document $id in collection $collection..." }

            Firebase
                .firestore
                .collection(collection)
                .document(id)
                .delete()
                .also { Napier.d { "Successfully deleted Firestore document $id in collection $collection!" } }
        } catch (throwable: Throwable) {
            Napier.e(throwable) { "Couldn't delete Firestore document $id in collection $collection" }
            throw throwable
        }
    }
}
