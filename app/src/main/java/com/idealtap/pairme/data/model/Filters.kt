package com.idealtap.pairme.data.model

import com.google.firebase.firestore.DocumentReference

data class Filters(
    val locations: List<DocumentReference>? = null,
    val ageMin: Int? = null,
    val ageMax: Int? = null
): Model()