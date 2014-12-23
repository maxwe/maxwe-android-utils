package org.maxwe.android.views.treelist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.maxwe.json.Json;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;

/**
 * Created by dingpengwei on 12/23/14.
 */
public class TreeData extends SQLiteOpenHelper implements TreeListConstants{
    private static final String DBNAME = "treenode";

    public TreeData(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE T_TREE(ID varchar(80) primary key NOT NULL , CITYID varchar(80) NOT NULL ,"
                        + "GRADEID varchar(8) NOT NULL ,PARENTID varchar(80) NOT NULL ,CITYNAME varchar(80) NOT NULL ,"
                        + "CITYCODE varchar(80) NOT NULL ,"
                        + "CREATETIME DATETIME DEFAULT(STRFTIME('%Y-%m-%d %H:%M:%f','NOW')) ,"
                        + "UPDATETIME DATETIME DEFAULT(STRFTIME('%Y-%m-%d %H:%M:%f','NOW')) )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean initBySql(JsonArray sqls) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            int size = sqls.getLenght();
            for(int i=0;i<size;i++){
                db.execSQL(sqls.getString(i));
            }
            db.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }

    public JsonArray getTreeNode(String pid) {
        JsonArray result = Json.createJsonArray();
        String sql = "SELECT * FROM T_TREE WHERE PARENTID = ?";
        String[] params = new String[]{pid};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try {
            db.beginTransaction();
            cursor = db.rawQuery(sql.toString(), params);
            while (cursor.moveToNext()) {
                JsonObject node = Json.createJsonObject();
                node.set(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
                node.set(KEY_PID, cursor.getString(cursor.getColumnIndex(KEY_PID)));
                node.set(KEY_GRADE_ID, cursor.getString(cursor.getColumnIndex(KEY_GRADE_ID)));
                node.set(KEY_CITY_ID, cursor.getString(cursor.getColumnIndex(KEY_CITY_ID)));
                node.set(KEY_CITY_CODE, cursor.getString(cursor.getColumnIndex(KEY_CITY_CODE)));
                node.set(KEY_CITY_NAME, cursor.getString(cursor.getColumnIndex(KEY_CITY_NAME)));
                result.push(node);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            result.clear();
        }finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }

    public int getConuter() {
        int result = -1;
        String sql = "SELECT count(*) as total FROM T_TREE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try {
            db.beginTransaction();
            cursor = db.rawQuery(sql.toString(),null);
            while (cursor.moveToNext()) {
                result = cursor.getInt(cursor.getColumnIndex("total"));
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            result = -1;
        }finally {
            db.endTransaction();
            db.close();
        }
        return result;
    }
}
