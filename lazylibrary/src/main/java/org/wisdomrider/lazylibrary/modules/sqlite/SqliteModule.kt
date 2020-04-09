package org.wisdomrider.lazylibrary.modules.sqlite

import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LazyModule

class SqliteModule : LazyModule() {
    lateinit var sqliteClosedHelper: SqliteClosedHelper
    fun build(db_name: String) {
        sqliteClosedHelper = SqliteClosedHelper(context, db_name)

    }

    fun <T> whereQuery(any: T, type: String, condition: HashMap<String, Any>): ArrayList<T> {
        return sqliteClosedHelper.specialWhere(any, type, condition)
    }

    fun <T> deleteQuery(t: T, type: String, condition: HashMap<String, String>): T {
        return sqliteClosedHelper.specialDelete(t, type, condition)
    }

}

fun <T> T.createTable(): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        it.sqliteClosedHelper.createTable(this)
    }
}

fun <T> T.insert(): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        it.sqliteClosedHelper.insertTable(this)
    }
}

fun <T> T.where(
    type: String,
    condition: HashMap<String, Any>,
    function: (a: ArrayList<T>) -> Unit
): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        function(it.whereQuery(this, type, condition))
    }
}

private fun <T> T.delete(
    type: String,
    condition: HashMap<String, String>,
    function: (list: T) -> Unit
): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        function(it.deleteQuery(this, type, condition))
    }
}

