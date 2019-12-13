package com.example.todorealmsimple.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Item(

    /**
     * A default public constructor is needed for Realm so default values are provided while when you code in Java
     * it is implicitly provided.
     *
     */

    @PrimaryKey
    @Required
    var itemId: String? = null,

    @Required
    var body:String ="",

    var isDone: Boolean = false,

    @Required
    var timeStamp: Date = Date()

): RealmObject()