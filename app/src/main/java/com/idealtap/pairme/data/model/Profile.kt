package com.idealtap.pairme.data.model

import java.util.*

data class Profile(var fname: String? = null,
                   var lname: String? = null,
                   var gender: Gender? = null,
                   var country: Country? = null,
                   var birthDate: Date? = null,
                   var liked: Boolean? = null,
                   var disliked: Int? = null
): Model(){

    companion object{
        const val FIRST_NAME = "fname"
        const val LAST_NAME = "lname"
        const val GENDER = "gender"
        const val COUNTRY = "country"
        const val BIRTH_DATE = "birthDate"
        const val LIKED = "liked"
        const val DISLIKED = "disliked"
    }

}