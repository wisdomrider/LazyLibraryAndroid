package org.wisdomrider.lazylibrary.modules.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
   Created By WisdomRider(Avishek Adhikari)

    Email : avishekzone@gmail.com

    Make Sure to Star Me On Github :
       https://github.com/wisdomrider/SqliteClosedHelper

     Credit Me SomeWhere In Your Project :)

     Thanks !!
*/

public class SqliteClosedHelper implements Interface {
    private Context wisdomrider;
    private String DB_NAME;
    private SQLiteDatabase database;
    private helpmesqlite Sqlite;


    public SqliteClosedHelper(Context c, String DB_NAME) {
        this.wisdomrider = c;
        this.DB_NAME = DB_NAME;
        Sqlite = new helpmesqlite(c, DB_NAME);
        database = Sqlite.getDatabase();
    }

    @Override
    public void Query(String q) {
        database.execSQL(q);
    }

    @Override
    public <T> ArrayList<Method> decompile(T t) {
        ArrayList<Method> methods = new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(t.getClass().getDeclaredFields()));
        for (Field f : fields) {
            Method method = new Method(f, t);
            if (!method.checkAnnotaions(MethodAnnotations.Exclude.class) && !f.getName().equals("$change") && !f.getName().equals("serialVersionUID"))
                methods.add(method);
        }
        return methods;
    }


    @Override
    public <T> SqliteClosedHelper createTable(T t) {
        ArrayList<Method> methods = decompile(t);
        StringBuilder var_name = new StringBuilder("CREATE TABLE if not exists `" + t.getClass().getSimpleName() + "` (");
        for (Method m : methods) var_name.append(m.getCreateTableQuery());
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - 1) + " )");
        Log.e("QUERY", var_name.toString());
        Query(var_name.toString());
        return this;
    }


    @Override
    public <T> SqliteClosedHelper insertTable(T t) {
        ArrayList<Method> methods = decompile(t);
        StringBuilder var_name = new StringBuilder("INSERT INTO `" + t.getClass().getSimpleName() + "`(");
        for (Method m : methods) {
            if (m.getValue() != null)
                var_name.append("`" + m.key() + "`,");
        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - 1) + ") VALUES (");
        for (Method m : methods) {
            if (!m.isNull())
                if (m.isString())
                    var_name.append("'" + parseString(m.getValue()) + "' ,");
                else
                    var_name.append(m.getValue() + ",");
        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - 1) + ")");
        Log.e("QUERY", var_name.toString());
        database.execSQL(String.valueOf(var_name));
        return this;
    }

    private String parseString(Object value) {
        return value.toString().replace("'", "''");
    }


    public <T> void specialUpdate(T t, @NotNull String type, @NotNull HashMap<String, Object> condition, boolean autoInsert) {
        ArrayList<Method> methods = decompile(t);
        StringBuilder primary = new StringBuilder(" WHERE ");
        StringBuilder var_name = new StringBuilder("UPDATE " + t.getClass().getSimpleName() + " SET ");
        for (Method m : methods) {
            String key = "";
            if (!m.isNull()) {
                if (m.isString())
                    key = m.key() + "='" + parseSql(m.getValue()) + "',";
                else
                    key += m.key() + "=" + m.getValue() + ",";
            }
            var_name.append(key);
        }
        Iterator it = condition.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() instanceof String) primary.append(pair.getKey())
                    .append("=")
                    .append("'" + parseSql(pair.getValue()) + "'" + type + " ");

            else primary.append(pair.getKey())
                    .append("=")
                    .append(pair.getValue().toString() + type + " ");
        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - 1));
        primary = new StringBuilder(primary.substring(0, primary.length() - (type.length() + 1)));

        Cursor cursor = database.rawQuery("SELECT * FROM " + t.getClass().getSimpleName() + primary.toString().replace(",", ""), null);
        if (cursor.getCount() == 0) {
            if (autoInsert)
                insertTable(t);
            return;
        }
        database.execSQL(var_name.toString() + primary.toString());
    }


    @Override
    public <T> SqliteClosedHelper updateTable(T t) {
        ArrayList<Method> methods = decompile(t);
        StringBuilder primary = new StringBuilder(" WHERE ");
        StringBuilder var_name = new StringBuilder("UPDATE " + t.getClass().getSimpleName() + " SET ");
        for (Method m : methods) {
            String key = "";
            if (!m.isNull()) {
                if (m.isString())
                    key = m.key() + "='" + parseSql(m.getValue()) + "',";
                else
                    key += m.key() + "=" + m.getValue() + ",";
                if (!m.isPrimary()) var_name.append(key);
                else {
                    primary.append(key);
                }
            }
        }
        Cursor cursor = database.rawQuery("SELECT * FROM " + t.getClass().getSimpleName() + primary.toString().replace(",", ""), null);
        if (cursor.getCount() == 0) {
            insertTable(t);
            return this;
        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - 1));
        primary = new StringBuilder(primary.substring(0, primary.length() - 1));
        var_name.append(primary);
        Log.d("QUERY", String.valueOf(var_name));
        database.execSQL(String.valueOf(var_name));
        return this;
    }


    @Override
    public <T> SqliteClosedHelper updateTable(T data, T where, String CASE) {
        ArrayList<Method> methods = decompile(data);
        StringBuilder primary = new StringBuilder(" WHERE ");
        StringBuilder var_name = new StringBuilder("UPDATE " + data.getClass().getSimpleName() + " SET ");
        for (Method m : methods) {
            String key = "";
            if (!m.isNull()) {
                if (m.isString())
                    key = m.key() + "='" + parseSql(m.getValue()) + "',";
                else
                    key += m.key() + "=" + m.getValue() + ",";
                var_name.append(key);
            }
        }
        for (Method m : decompile(where)) {
            String key = "";
            if (!m.isNull()) {
                if (m.isString())
                    key = m.key() + "='" + parseSql(m.getValue()) + "' " + CASE;
                else
                    key += m.key() + "=" + m.getValue() + " " + CASE;
                primary.append(key);
            }
        }

        Cursor cursor = database.rawQuery("SELECT * FROM " + data.getClass().getSimpleName() + primary.toString().replace(",", ""), null);
        if (cursor.getCount() == 0) {
            insertTable(data);
            return this;
        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - CASE.length()));
        primary = new StringBuilder(primary.substring(0, primary.length() - CASE.length()));
        var_name.append(primary);
        database.execSQL(String.valueOf(var_name));
        return this;
    }


    @Override
    public <T> ArrayList<T> whereAND(T t) {
        return where(t, SQLITECONSTANTS.AND, 4);
    }

    @Override
    public <T> SqliteClosedHelper delete(T t, String type) {
        StringBuilder var_name = new StringBuilder("DELETE FROM " + t.getClass().getSimpleName() + " WHERE ");
        ArrayList<Method> methods = decompile(t);
        for (Method m : methods) {
            if (!m.isNull()) {
                if (m.isString())
                    var_name.append(m.key()).append(" = '").append(parseSql(m.getValue())).append("' ").append(type).append(" ");
                else
                    var_name.append(m.key()).append(" = ").append(m.getValue()).append(" ").append(type).append(" ");
            }

        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - type.length()));
        database.execSQL(String.valueOf(var_name));
        return this;
    }

    @Override
    public <T> SqliteClosedHelper delete(T t) {
        String what = SQLITECONSTANTS.AND;
        StringBuilder var_name = new StringBuilder("DELETE FROM " + t.getClass().getSimpleName() + " WHERE ");
        ArrayList<Method> methods = decompile(t);
        for (Method m : methods) {
            if (!m.isNull()) {
                if (m.isString())
                    var_name.append(m.key()).append(" = '").append(parseSql(m.getValue())).append("' ").append(what).append(" ");
                else
                    var_name.append(m.key()).append(" = ").append(m.getValue()).append(" ").append(what).append(" ");
            }

        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - 4));
        database.execSQL(String.valueOf(var_name));
        return this;
    }

    @Override
    public <T> ArrayList<T> whereOR(T t) {
        return where(t, SQLITECONSTANTS.OR, 3);
    }

    @Override
    public <T> ArrayList<T> getAll(T t) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + t.getClass().getSimpleName(), null);
        return getArrayFromCursor(cursor, t);
    }

    @Override
    public <T> SqliteClosedHelper removeAll(T t) {
        Query("DELETE FROM " + t.getClass().getSimpleName());
        return this;
    }

    @Override
    public <T> SqliteClosedHelper updateAll(ArrayList<T> t) {
        for (T t1 : t) updateTable(t1);
        return this;
    }

    @Override
    public <T> SqliteClosedHelper insertAll(ArrayList<T> t) {
        for (T t1 : t) insertTable(t1);
        return this;
    }

    @Override
    public void closeDatabase() {
        database.close();
    }

    @Override
    public <T> SqliteClosedHelper renameTable(T t, String newName) {
        /* Make sure to change class name too otherwise it will crash */
        Query("ALTER TABLE " + t.getClass().getSimpleName() + " RENAME TO " + newName + ";");
        return this;
    }

    private String parseSql(Object s) {
        return ((String) s).replace("'", "''");
    }

    private String parseQuery(HashMap<String, Object> condition, String type) {
        StringBuilder var_name = new StringBuilder();
        Iterator it = condition.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() instanceof String) var_name.append(pair.getKey())
                    .append("=")
                    .append("'" + parseSql(pair.getValue()) + "'" + type + " ");

            else var_name.append(pair.getKey())
                    .append("=")
                    .append(pair.getValue().toString() + type + " ");
        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - (type.length() + 1)));
        return var_name.toString();
    }

    @NotNull
    public <T> ArrayList<T> specialWhere(T t, @NotNull String type, @NotNull HashMap<String, Object> condition) {
        Cursor c = database.rawQuery("SELECT * FROM " + t.getClass().getSimpleName() + " WHERE " + parseQuery(condition, type), null);
        return getArrayFromCursor(c, t);
    }

    @NotNull
    public Cursor runRaw(@NotNull String s) {
        return database.rawQuery(s, null);
    }


    public <T> long countTable(T t, @Nullable HashMap<String, Object> condition, @NotNull String type) {
        try {
            StringBuilder var_name;
            if (condition == null) {
                var_name = new StringBuilder("SELECT COUNT(*) from " + t.getClass().getSimpleName());
            } else {
                var_name = new StringBuilder("SELECT * FROM " + t.getClass().getSimpleName() + " WHERE ");
                var_name.append(parseQuery(condition, type));
            }
            SQLiteStatement st = database.compileStatement(var_name.toString());
            return st.simpleQueryForLong();
        } catch (Exception e) {
            return 0;
        }

    }


    private <T> ArrayList<T> where(T t, String what, int position) {
        StringBuilder var_name = new StringBuilder("SELECT * FROM " + t.getClass().getSimpleName() + " WHERE ");
        ArrayList<Method> methods = decompile(t);
        for (Method m : methods) {
            if (!m.isNull()) {
                if (m.isString())
                    var_name.append(m.key()).append(" = '").append(parseSql(m.getValue())).append("' " + what + " ");

                else
                    var_name.append(m.key()).append(" = ").append(m.getValue()).append(" " + what + " ");
            }

        }
        var_name = new StringBuilder(var_name.substring(0, var_name.length() - position));
        Cursor c = database.rawQuery(String.valueOf(var_name), null);
        return getArrayFromCursor(c, t);
    }


    private <T> ArrayList<T> getArrayFromCursor(Cursor c, T t) {
        ArrayList<T> list = new ArrayList<>();
        if (c.getCount() == 0) return list;
        while (c.moveToNext()) {
            try {
                T tt = (T) t.getClass().newInstance();
                ArrayList<Method> methods = decompile(tt);
                int i = 0;
                for (Method m : methods) {
                    if (m.getType().equals(String.class))
                        m.setValue(c.getString(i).replace("''", "'"));
                    else if (m.getType().equals(Integer.class) || m.getType().equals(int.class))
                        m.setValue(c.getInt(i));
                    else if (m.getType().equals(Double.class) || m.getType().equals(double.class))
                        m.setValue(c.getDouble(i));
                    else if (m.getType().equals(Float.class) || m.getType().equals(float.class))
                        m.setValue(c.getFloat(i));
                    else if (m.getType().equals(Long.class) || m.getType().equals(long.class))
                        m.setValue(c.getLong(i));
                    else if (m.getType().equals(boolean.class) || m.getType().equals(Boolean.class))
                        m.setValue(Boolean.parseBoolean(c.getString(i)));
                    i++;

                }
                list.add(tt);

            } catch (InstantiationException e) {
                throw new Error("EMPTY CONSTRUCTOR IS COMPULSARY !", e);
            } catch (IllegalAccessException e) {
                throw new Error(e);
            }

        }

        return list;

    }


    @NotNull
    public <T> void specialDelete(T t, @NotNull String type, @NotNull HashMap<String, Object> condition) {
        StringBuilder var_name = new StringBuilder("DELETE FROM " + t.getClass().getSimpleName() + " WHERE ");
        var_name.append(parseQuery(condition, type));
        Query(var_name.toString());
    }

}