package ru.otus.otuskotlin.marketplace.sql

@SqlDsl
class SqlSelectBuilder {
    private var columns: String = "*"
    private var table: String? = null
    private var conditions: String? = null

    @SqlDsl
    fun select(vararg columns: String) {
        this.columns = columns.toList().joinToString(", ")
    }

    @SqlDsl
    fun from(table: String) {
        this.table = table
    }

    @SqlDsl
    fun where(conditions: String) {
        this.conditions = conditions
    }

    fun build(): String {
        requireNotNull(table) { "table must be set" }
        var query = "select $columns from $table"
        conditions?.let { query += " where $conditions" }
        return query
    }
}

fun query(block: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
    return SqlSelectBuilder().apply(block)
}

infix fun String.eq(value: String?) : String {
    if (value == null) {
        return "$this is $value"
    } else {
        return "$this = '$value'"
    }
}

infix fun String.eq(value: Int) : String {
    return "$this = $value"
}

infix fun String.nonEq(value: String?) : String {
    if (value == null) {
        return "$this !is $value"
    } else {
        return "$this != '$value'"
    }
}

infix fun String.nonEq(value: Int) : String {
    return "$this != $value"
}

fun or(vararg conditions: String): String {
    return conditions.toList().joinToString(" or ", prefix = "(", postfix = ")")
}

@DslMarker
annotation class SqlDsl