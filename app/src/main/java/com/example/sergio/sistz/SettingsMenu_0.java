package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Sergio on 3/8/2016.
 */
public class SettingsMenu_0 extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1; // ************ FrameLayout ***************
    CheckBox _col1, _col2, _col3, _col4, _col5, _col6, _col7, _col8, _col9;
    EditText et_emis, et_school_name;
    String _IU="U", regExist;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String currentDateandTime = sdf.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu_0);

        // ********************** Global vars ******************
        et_emis = (EditText) findViewById(R.id.et_emis);
        et_school_name = (EditText) findViewById(R.id.et_school_name);
        _col1 = (CheckBox) findViewById(R.id._col1);
        _col2 = (CheckBox) findViewById(R.id._col2);
        _col3 = (CheckBox) findViewById(R.id._col3);
        _col4 = (CheckBox) findViewById(R.id._col4);
        _col5 = (CheckBox) findViewById(R.id._col5);
        _col6 = (CheckBox) findViewById(R.id._col6);
        _col7 = (CheckBox) findViewById(R.id._col7);
        _col8 = (CheckBox) findViewById(R.id._col8);
        _col9 = (CheckBox) findViewById(R.id._col9);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);


        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) findViewById(R.id.btn_save);
        //ImageButton btn_exit = (ImageButton) findViewById(R.id.btn_exit);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);


        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);
//        btn_exit.setOnClickListener(this);

        // ***************** LOCAD INFORMATION *************************
        loadRecord();

    }


    // **************** Load DATA *************************
    public void loadRecord() {
        //toolsfncs.dialogAlertConfirm(this,getResources(),13);
        dialogAlert(2);
        et_emis.setText("");
        et_school_name.setText("");
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, m_pp, m_p, m_s, a_pp, a_p, a_s, e_pp, e_p, e_s, emis, flag FROM ms_0 WHERE _id=1",null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            if(cur_data.getInt(1)==1) {_col1.setChecked(true);}
            if(cur_data.getInt(2)==1) {_col2.setChecked(true);}
            if(cur_data.getInt(3)==1) {_col3.setChecked(true);}
            if(cur_data.getInt(4)==1) {_col4.setChecked(true);}
            if(cur_data.getInt(5)==1) {_col5.setChecked(true);}
            if(cur_data.getInt(6)==1) {_col6.setChecked(true);}
            if(cur_data.getInt(7)==1) {_col7.setChecked(true);}
            if(cur_data.getInt(8)==1) {_col8.setChecked(true);}
            if(cur_data.getInt(9)==1) {_col9.setChecked(true);}
            et_emis.setText(cur_data.getString(10));
            et_school_name.setText(cur_data.getString(11));
            _IU = "U";
        } else {_IU = "I";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit=";", s1;
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        //if (!et_emis.getText().toString().isEmpty()  ) {  //        if (!et_emis.getText().toString().isEmpty()) {  //
        if ((!et_emis.getText().toString().isEmpty()) && (!et_emis.getText().toString().equals("0"))) {
            if (_IU == "I" ) {reg.put("_id",1);}
            //reg.put("flag", _IU); sql = sql  + "ms_0;" + getEMIS_code() + delimit
            reg.put("flag", et_school_name.getText().toString());
            sql = sql  + "ms_0;" + et_emis.getText().toString() + delimit;
            reg.put("emis", et_emis.getText().toString());
            if (_col1.isChecked() == true) {reg.put("m_pp", 1); sql = sql + "1" + delimit;} else {reg.put("m_pp",0); sql = sql + "0" + delimit;}
            if (_col2.isChecked() == true) {reg.put("m_p", 1); sql = sql + "1" + delimit;} else {reg.put("m_p",0); sql = sql + "0" + delimit;}
            if (_col3.isChecked() == true) {reg.put("m_s", 1); sql = sql + "1" + delimit;} else {reg.put("m_s",0); sql = sql + "0" + delimit;}
            if (_col4.isChecked() == true) {reg.put("a_pp", 1); sql = sql + "1" + delimit;} else {reg.put("a_pp",0); sql = sql + "0" + delimit;}
            if (_col5.isChecked() == true) {reg.put("a_p", 1); sql = sql + "1" + delimit;} else {reg.put("a_p",0); sql = sql + "0" + delimit;}
            if (_col6.isChecked() == true) {reg.put("a_s", 1); sql = sql + "1" + delimit;} else {reg.put("a_s",0); sql = sql + "0" + delimit;}
            if (_col7.isChecked() == true) {reg.put("e_pp", 1); sql = sql + "1" + delimit;} else {reg.put("e_pp",0); sql = sql + "0" + delimit;}
            if (_col8.isChecked() == true) {reg.put("e_p", 1); sql = sql + "1" + delimit;} else {reg.put("e_p",0); sql = sql + "0" + delimit;}
            if (_col9.isChecked() == true) {reg.put("e_s", 1); sql = sql + "1" + delimit;} else {reg.put("e_s",0); sql = sql + "0" + delimit;}
            sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
//                ContentValues Bitacora = new ContentValues();
//                Bitacora.put("sis_sql","UPDATE a SET a2='" + et_school_name.getText().toString() + "'%U");
//                dbSET.insert("sisupdate", null, Bitacora);

                //String newBiracora ="sis_sql-> "+"UPDATE a SET a2=" + et_school_name.getText().toString() + "%U";
                //Toast.makeText(getApplicationContext(), newBiracora, Toast.LENGTH_LONG).show();
                //logFunctions(currentDateandTime, "SettingMenu_0", Bitacora.toString());
                //logFunctions(currentDateandTime, "SettingMenu_0", newBiracora );

                // ********************* Fill TABLE d
                //if (_IU=="I") {
                //    dbSET.insert("ms_0", null, reg); _IU="U";
                    //Toast.makeText(getApplicationContext(), "The information has been updated!!!", Toast.LENGTH_SHORT).show();
                //} else {
                    //regExist = (getEMIS_code_form_a());
                //    if (et_emis.getText().toString().equals(getEMIS_code_form_a()) || getEMIS_code_form_a() ==  "" ) {
                dbSET.update("ms_0", reg, "_id=1", null);
                String sqlupdate = "UPDATE a SET a1='"+ et_emis.getText().toString()+"', a2='"+ et_school_name.getText().toString().replace("'","''")+"'";
                dbSET.execSQL(sqlupdate);
                // Aqui empiezo a reemplazar el codigo emis de attendaces, behaviour y evaluation. Antes debe verificar que tenga datos la tabla
                dbSET.execSQL("UPDATE attendance SET emis='"+ et_emis.getText().toString()+"'");
                dbSET.execSQL("UPDATE evaluation SET emis='"+ et_emis.getText().toString()+"'");
                dbSET.execSQL("UPDATE vehaviour SET emis='"+ et_emis.getText().toString()+"'");

                        //Toast.makeText(getApplicationContext(), "The information has been updated!!!", Toast.LENGTH_SHORT).show();
                 //   } else {Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_w_alredyinfo) +et_emis.getText().toString() + " = "+ getEMIS_code_form_a(), Toast.LENGTH_SHORT).show();} // "Ya ingreso información con ese código..."
                //}
                toolsfncs.dialogAlertConfirm(this,getResources(),9);
                //finish();
            }catch (Exception e) {
                //Toast.makeText(getApplicationContext(),"Debe ingresar al menos un registro... !!! ",Toast.LENGTH_SHORT).show();
                toolsfncs.dialogAlertConfirm(this,getResources(),12);
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.str_w_a), Toast.LENGTH_SHORT).show(); // "Enter Scool EMIS Code ... !!! "

        }

        dbSET.close();
        cnSET.close();
    }




    public void clearRecord(){

    }


    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn_next:
//                fl_location++;
//                change_fl(fl_location);
//                break;
//            case R.id.btn_back:
//                fl_location--;
//                change_fl(fl_location);
//                break;
            case R.id.btn_save:
                dialogAlert(1);
                //updateRecord();
                break;
//            case R.id.btn_exit:
//                dialogAlert(2);
//                break;
        }

    }

    // *********** Control change page
    public void change_fl(Integer fl) {
        if (fl > 2) {fl_location=1;}
        if (fl < 1) {fl_location=2;}
        switch (fl_location){
            case 1:
                fl_part1.setVisibility(View.VISIBLE);
                break;
        }
    }

    // *********** Control Alerts ************************
//    public void dialogAlert(int v){
//        Toast.makeText(getApplicationContext(),String.valueOf(v) ,Toast.LENGTH_SHORT).show();
//        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
//        dialogo1.setTitle("Important");
//        if (v == 1){dialogo1.setMessage("Save and Exit !!!");}
//        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
//        dialogo1.setCancelable(false);
//        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogo1, int id) {
//                aceptar();
//            }
//        });
//        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogo1, int id) {
//                cancelar();
//            }
//        });
//        dialogo1.show();
//    }

    public void dialogAlert(int v){
        //Toast.makeText(getContext(),String.valueOf(v) ,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);

        if (v == 1) {
            dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1));
            dialogo1.setMessage(getResources().getString(R.string.str_g_change_emis_code_confirm));
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar();
                }
            });
            dialogo1.setNegativeButton(getResources().getString(R.string.str_bl_msj4), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cancelar();
                }
            });
            dialogo1.show();
        }
        if (v == 2) {
            dialogo1.setTitle(getResources().getString(R.string.str_g_warning));
            dialogo1.setMessage(getResources().getString(R.string.str_g_change_emis_code));
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar2();
                }
            });
            dialogo1.setNegativeButton(getResources().getString(R.string.str_bl_msj4), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cancelar2();
                }
            });
            dialogo1.show();
        }

    }
    public void aceptar() { updateRecord();  }
    public void cancelar() {finish(); }

    public void aceptar2() {  }
    public void cancelar2() {finish(); }
    // *********** END Control Alerts ************************

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
    }
    public String getEMIS_code_form_a(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        }catch (Exception e) {}

        return getemiscode;
    }


    public void logFunctions(String log1, String log2, String log3) {
    Conexion cnSETlogFunction = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
    SQLiteDatabase dbSETlogFunction = cnSETlogFunction.getReadableDatabase();

        dbSETlogFunction.execSQL("INSERT INTO logfunctions(startlog, locationlog, finishlog) VALUES('" + log1 + "', '" + log2 + "', '" + log3 + "')");
        dbSETlogFunction.close();
        cnSETlogFunction.close();
    }
}
