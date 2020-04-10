package org.wisdomrider.lazylibrary.modules.sqlite

import android.database.Cursor
import org.wisdomrider.lazylibrary.Functions
import org.wisdomrider.lazylibrary.LazyModule
import org.wisdomrider.lazylibrary.modules.sqlite.SQLITECONSTANTS.AND

class SqliteModule : LazyModule() {
    lateinit var sqliteClosedHelper: SqliteClosedHelper
    fun build(db_name: String) {
        sqliteClosedHelper = SqliteClosedHelper(context, db_name)

    }

    fun <T> whereQuery(any: T, type: String, condition: HashMap<String, Any>): ArrayList<T> {
        return sqliteClosedHelper.specialWhere(any, type, condition)
    }

    fun <T> deleteQuery(t: T, type: String, condition: HashMap<String, Any>) {
        sqliteClosedHelper.specialDelete(t, type, condition)
    }

    fun <T> updateQuery(t: T, type: String, condition: HashMap<String, Any>, autoInsert: Boolean) {
        sqliteClosedHelper.specialUpdate(t, type, condition, autoInsert)
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
    condition: HashMap<String, Any>,
    type: String = AND,
    function: (a: ArrayList<T>) -> Unit
): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        function(it.whereQuery(this, type, condition))
    }
}

fun <T> T.delete(condition: HashMap<String, Any>, type: String = AND): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        it.deleteQuery(this, type, condition)
    }
}

fun <T> T.update(
    condition: HashMap<String, Any>,
    type: String = AND,
    autoInsert: Boolean = true
): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        it.updateQuery(this, type, condition, autoInsert)
    }

}




fun <T> T.removeAll(): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        it.sqliteClosedHelper.removeAll(this)
    }
}
/*

fun String.executeQuery(): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java) {
        it.sqliteClosedHelper.Query(this)
    }
}
*/

fun String.rawQuery(func: (cursor: Cursor) -> Unit): Functions<SqliteModule> {
    return Functions(SqliteModule::class.java){
        func(it.sqliteClosedHelper.runRaw(this))
    }
}
