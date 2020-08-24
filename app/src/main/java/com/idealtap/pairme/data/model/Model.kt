package com.idealtap.pairme.data.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties


/**
 * A Base Model to be extended by other models to add ids.
 */
@IgnoreExtraProperties
open class Model {
    @Exclude
    var id: String? = null

    fun <T : Model> withId(id: String): T {
        this.id = id
        return this as T
    }
}