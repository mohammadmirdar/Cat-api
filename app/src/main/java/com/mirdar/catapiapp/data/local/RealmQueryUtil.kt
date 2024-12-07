package com.mirdar.catapiapp.data.local


import io.realm.kotlin.TypedRealm
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.TypedRealmObject
import java.lang.StringBuilder
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.reflect.KClass
import kotlin.text.deleteAt
import kotlin.text.lastIndex


fun <T : TypedRealmObject> TypedRealm.where(clazz: KClass<T>): RealmQuery<T> {
    return this.query(clazz)
}

fun <T : TypedRealmObject> RealmQuery<T>.equalTo(field: String, value: String?): RealmQuery<T> {
    return this.query("$field == $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.equalTo(field: String, value: Int?): RealmQuery<T> {
    return this.query("$field == $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.equalTo(field: String, value: Long?): RealmQuery<T> {
    return this.query("$field == $value")
}
fun <T : TypedRealmObject> RealmQuery<T>.isNotNull(field: String): RealmQuery<T> {
    return this.query("$field == nil")
}

fun <T : TypedRealmObject> RealmQuery<T>.equalTo(field: String, value: Boolean?): RealmQuery<T> {
    return this.query("$field == $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.notEqualTo(field: String, value: String?): RealmQuery<T> {
    return this.query("$field != $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.notEqualTo(field: String, value: Int?): RealmQuery<T> {
    return this.query("$field != $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.notEqualTo(field: String, value: Long?): RealmQuery<T> {
    return this.query("$field != $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.notEqualTo(field: String, value: Boolean?): RealmQuery<T> {
    return this.query("$field != $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.between(field: String, value: Boolean?): RealmQuery<T> {
    return this.query("$field == $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.greaterThan(field: String, value: Long?): RealmQuery<T> {
    return this.query("$field > $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.greaterThan(field: String, value: Int?): RealmQuery<T> {
    return this.query("$field > $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.lessThan(field: String, value: Long?): RealmQuery<T> {
    return this.query("$field < $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.lessThan(field: String, value: Int?): RealmQuery<T> {
    return this.query("$field < $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.or(): RealmQuery<T> {
    return this.query("OR")
}

fun <T : TypedRealmObject> RealmQuery<T>.greaterThanOrEqualTo(
    field: String,
    value: Int?
): RealmQuery<T> {
    return this.query("$field >= $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.greaterThanOrEqualTo(
    field: String,
    value: Long?
): RealmQuery<T> {
    return this.query("$field >= $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.lessThanOrEqualTo(
    field: String,
    value: Int?
): RealmQuery<T> {
    return this.query("$field <= $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.lessThanOrEqualTo(
    field: String,
    value: Long?
): RealmQuery<T> {
    return this.query("$field <= $value")
}

fun <T : TypedRealmObject> RealmQuery<T>.between(
    field: String,
    value1: Long?,
    value2: Long?
): RealmQuery<T> {
    return this.query("$field BETWEEN {$value1 , $value2} ")
}

fun <T : TypedRealmObject> RealmQuery<T>.isIn(
    field: String,
    values: Array<Any>
): RealmQuery<T> {
    val stringBuilder = StringBuilder()
    stringBuilder.append("{")
    values.forEach {
        stringBuilder.append(it.toString()).append(",")
    }
    stringBuilder.deleteAt(stringBuilder.lastIndex)
    stringBuilder.append("}")

    return this.query("$field IN $stringBuilder ")
}

fun <T : TypedRealmObject> RealmQuery<T>.findFirst(): T? {
    return this.first().find()
}

fun <T : TypedRealmObject> RealmQuery<T>.findAll(): RealmResults<T> {
    return this.find()
}

fun <T : TypedRealmObject> RealmQuery<T>.sort(
    fields: Array<String>,
    values: Array<Sort>
): RealmQuery<T> {
    fields.forEachIndexed { index, s ->
        this.sort(s, values[index])
    }
    return this
}
