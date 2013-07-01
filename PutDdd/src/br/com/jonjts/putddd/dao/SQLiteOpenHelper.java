package br.com.jonjts.putddd.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper{

	public static final String NOME_BANCO =  "ddd";
    public static final int VERSAO =  3;
     
    private static SQLiteOpenHelper instance;
     
    private SQLiteOpenHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }
     
    public static SQLiteOpenHelper getInstance(Context context) {
        if(instance == null)
            instance = new SQLiteOpenHelper(context);
         
        return instance;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OperadoraDAO.SCRIPT_CRIACAO_TABELA_OPERADORA);
        String[] s = OperadoraDAO.ss;
        for(int i = 0; i < s.length; i++ ){
        	db.execSQL(s[i]);
        }
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(OperadoraDAO.SCRIPT_DELECAO_TABELA);
        onCreate(db);
    }
 

}
