package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBSubjectsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Sergio on 3/17/2016.
 */

// View.OnClickListener, AdapterView.OnItemSelectedListener
public class ReportTS extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static String EMIS_code = "";
    public static String TS_code = "", code="", TS_report_enable="";
    String[] _shift = {"Morning","Afternoon","Evening"};
    String[] _level = {"Primary","Secondary","Pre-Primary"};
    //private String[] _grade = {"G1","G2","G3","G4","G5","G6","G7","G8"};
    private String[] _section = {"A","B","C","D","E","F","G"};
    //private String[] _subject_p = {"Mathematics","English","Kiswahili","French","Science","Geography","Civics","History","Vocational skills","Personality and Sports","ICT","Other"};
    public Spinner sp_shift, sp_level, sp_grade, sp_section, sp_subject;
    ArrayList<String> list_1 = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    ArrayList<String> list_shift = new ArrayList<>();
    ArrayList<String> list_level = new ArrayList<>();
    ArrayList<String> list_grade = new ArrayList<>();
//    ArrayList<String> list_grade_cod = new ArrayList<>();
//    ArrayList<String> list_section = new ArrayList<>();
    ArrayList<String> list_subject = new ArrayList<>();
//    ArrayList<String> list_subject_cod = new ArrayList<>();
    FrameLayout fl_part2; // ************ FrameLayout ***************
    //EditText _col0, _col1, _col2, _col4, _col5, _col6, _col7;
    RadioButton _col3a, _col3b;
    ListView lv_subject, lv_list;
    FloatingActionButton add_reg, save_reg, erase_reg;
    private DBSubjectsUtils conn;
    TextView txt1, txt2, txt3;
    CharSequence texto;

    @Override
    protected void onRestart() {
        super.onRestart();
        //loadListTeacher();
        //loadListTeacher(sp_shift.getSelectedItem().toString(), sp_level.getSelectedItem().toString(), sp_grade.getSelectedItem().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadListTeacher();
        //loadListTeacher(sp_shift.getSelectedItem().toString(), sp_level.getSelectedItem().toString(), sp_grade.getSelectedItem().toString());
        TS_code="";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_ts);

        // ********************** Global vars ******************

        lv_list = (ListView) findViewById(R.id.lv_list);

        sp_shift = (Spinner) findViewById(R.id.sp_shift);
        sp_level = (Spinner) findViewById(R.id.sp_level);
        sp_grade = (Spinner) findViewById(R.id.sp_grade);
        sp_section = (Spinner) findViewById(R.id.sp_section);
        sp_subject = (Spinner) findViewById(R.id.sp_subject);

        //  ************************ Objects assing *********************
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);

        //  ************************ Objects Buttoms *********************

        add_reg = (FloatingActionButton) findViewById(R.id.add_reg);
        save_reg = (FloatingActionButton) findViewById(R.id.save_reg);
        erase_reg = (FloatingActionButton) findViewById(R.id.erase_reg);

        //************* Start FrameLayout **************************
        fl_part2.setVisibility(View.VISIBLE);




        // **************** CLICK ON BUTTONS ********************
        add_reg.setOnClickListener(this);
        save_reg.setOnClickListener(this);
        erase_reg.setOnClickListener(this);
//        _col0.setOnClickListener(this);


        save_reg.setVisibility(View.GONE);
        erase_reg.setVisibility(View.GONE);
        add_reg.setVisibility(View.GONE);

        // ***************** LOCAD INFORMATION *************************
        //Toast.makeText(getContext(),"llena LV ..." + code, Toast.LENGTH_SHORT).show();
        start_array();
        LoadSpinner();
    }

    private void start_array() {
        _shift[0] = getResources().getString(R.string.str_g_morning);
        _shift[1] = getResources().getString(R.string.str_g_afternoon);
        _shift[2] = getResources().getString(R.string.str_g_evening);
        _level[0] = getResources().getString(R.string.p);
        _level[1] = getResources().getString(R.string.s);
        _level[2] = getResources().getString(R.string.pp);

        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std1)+"' WHERE level=1 and id=1");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std2)+"' WHERE level=1 and id=2");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std3)+"' WHERE level=1 and id=3");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std4)+"' WHERE level=1 and id=4");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std5)+"' WHERE level=1 and id=5");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std6)+"' WHERE level=1 and id=6");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_std7)+"' WHERE level=1 and id=7");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_yeari)+"' WHERE level=3 and id=1");
        dbSET.execSQL("UPDATE grade SET grade='"+getResources().getString(R.string.str_g_yearii)+"' WHERE level=3 and id=3");

        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_reading)+"' WHERE level=1 and id=1");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_writing)+"' WHERE level=1 and id=2");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_arithmetic)+"' WHERE level=1 and id=3");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_healt)+"' WHERE level=1 and id=4");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_games)+"' WHERE level=1 and id=5");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_religion)+"' WHERE level=1 and id=6");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_mathematics)+"' WHERE level=1 and id=7");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_english)+"' WHERE level=1 and id=8");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_science) + "' WHERE level=1 and id=9");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_history) + "' WHERE level=1 and id=10");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_geography) + "' WHERE level=1 and id=11");

        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_all) + "' WHERE level=3 and id=1");

        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_kiswahili) + "' WHERE level=1 and id=12");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_civics) + "' WHERE level=1 and id=13");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_vocational_skills) + "' WHERE level=1 and id=14");
        dbSET.execSQL("UPDATE subject SET subject='"+getResources().getString(R.string.str_g_ict)+"' WHERE level=1 and id=15");
        dbSET.execSQL("UPDATE subject SET subject='" + getResources().getString(R.string.str_g_personality) + "' WHERE level=1 and id=16");
    }

    private void LoadSpinner(){
        // ***************** Load Subject  if read DATABASE recordSet ****************************
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_shift;
//        String sql = "SELECT  shift\n" +
//                "FROM \n" +
//                "(SELECT 1 AS id, CASE WHEN m_pp = 1 THEN 'Morning' ELSE 'Select one' END AS Shift, CASE WHEN m_pp = 1 THEN 'Pre-Primary' ELSE 'Select one' END AS 'Level' FROM ms_0  UNION\n" +
//                "SELECT 1 AS id, CASE WHEN m_p = 1 THEN 'Morning' ELSE 'Select one' END AS Shift, CASE WHEN m_p = 1 THEN 'Primary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT 1 AS id, CASE WHEN m_s = 1 THEN 'Morning'  ELSE 'Select one' END AS shift, CASE WHEN m_s = 1 THEN 'Secondary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT  2 AS id, CASE WHEN a_pp = 1 THEN 'Afternoon' ELSE 'Select one' END AS Shift, CASE WHEN a_pp = 1 THEN 'Pre-Primary' ELSE 'Select one' END AS 'Level' FROM ms_0  UNION\n" +
//                "SELECT 2 AS id, CASE WHEN a_p = 1 THEN 'Afternoon' ELSE 'Select one' END AS Shift, CASE WHEN a_p = 1 THEN 'Primary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT 2 AS id, CASE WHEN a_s = 1 THEN 'Afternoon' ELSE 'Select one' END AS Shift, CASE WHEN a_s = 1 THEN 'Secondary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT  3 AS id, CASE WHEN e_pp = 1 THEN 'Evening' ELSE 'Select one' END AS Shift, CASE WHEN e_pp = 1 THEN 'Pre-Primary' ELSE 'Select one' END AS 'Level' FROM ms_0  UNION\n" +
//                "SELECT 3 AS id, CASE WHEN e_p = 1 THEN 'Evening' ELSE 'Select one' END AS Shift, CASE WHEN e_p = 1 THEN 'Primary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT 3 AS id, CASE WHEN e_s = 1 THEN 'Evening' ELSE 'Select one' END AS Shift, CASE WHEN e_s = 1 THEN 'Secondary' ELSE 'Select one' END AS 'Level' FROM ms_0)\n" +
//                "WHERE shift <>'Select one'  \n" +
//                "GROUP BY shift\n" +
//                " ORDER BY id";
        String sql = "SELECT  shift\n" +
                "FROM \n" +
                "(SELECT 1 AS id, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 1 AS id, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 1 AS id, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"'  ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS shift, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT  2 AS id, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 2 AS id, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 2 AS id, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT  3 AS id, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 3 AS id, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 3 AS id, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0)\n" +
                "WHERE shift <>'"+getResources().getString(R.string.str_g_selectone)+"'  \n" +
                "GROUP BY shift\n" +
                " ORDER BY id";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_shift = cur_data.getString(0);
                list_shift.add(col_shift);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_shift);
        sp_shift.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();

        ArrayAdapter<String> adap_section = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, _section);
        sp_section.setAdapter(adap_section);

        // ****************** if NEED ONLY read database to do DINAMIC SPINNER ***********
        this.sp_shift.setOnItemSelectedListener(this);
        this.sp_level.setOnItemSelectedListener(this);
        this.sp_grade.setOnItemSelectedListener(this);
        this.sp_section.setOnItemSelectedListener(this);

        //loadListTeacher(getIndexArray(_shift, sp_shift.getSelectedItem().toString()), getIndexArray(_level, sp_level.getSelectedItem().toString()), sp_grade.getSelectedItem().toString());
        //loadListTeacher();
    }


// ***************** Load Teacher/Staff LIST *************************
    public void loadListTeacher(String sql) {
        //Toast.makeText(getApplicationContext(), "Ahora CARGA LISTA DE TEACHER... !!! " + sql, Toast.LENGTH_SHORT).show();
        Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT t._id, t.t_s, t.surname, givenname FROM teacher  AS t \n" +
                "INNER JOIN _ta  AS ta ON ta.tc=t._id " + sql + " \n" +
                "GROUP BY t._id, t.t_s, t.surname, t.givenname", null);
        String col_id, col_g1="";
        cur_data.moveToFirst();
        //TS_code = 0;
        list_1.clear();
        list_code.clear();
        String surName="", givenName="";
        if (cur_data.moveToFirst()) {
            do {
                //col_g1 = cur_data.getString(0) + " - " + cur_data.getString(2) + ", " + cur_data.getString(3);
                //if(cur_data.isEmpty()) {col_g1 = cur_data.getString(2).toString();} else {col_g1 = cur_data.getString(2).toString() + ", "+ cur_data.getString(3);}
                if (cur_data.getString(2) != null) {surName = cur_data.getString(2);} else {surName="";}
                if (cur_data.getString(3) != null) {givenName = cur_data.getString(3) + ", " ;} else {givenName="";}
                //col_g1 = cur_data.getString(3) + ", " + cur_data.getString(2);
                col_g1 = givenName + surName;
                col_id = cur_data.getString(0);
                list_1.add(col_g1);
                list_code.add(col_id);
            } while (cur_data.moveToNext());

            ArrayAdapter adap_list = new ArrayAdapter(this, R.layout.row_menu_select, list_1);
            lv_list.setAdapter(adap_list);

            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                    //Toast.makeText(getApplicationContext(), "Selected: " + String.valueOf(posicion) + ", Code: " + list_code.get(posicion) , Toast.LENGTH_SHORT).show();
                    TS_report_enable="1"; // **** Control para desabilitar campos en ficha del profesor.
                    code = list_code.get(posicion);
                    SettingsMenuStaff.TS_code = list_code.get(posicion);
                    Intent intent1 = new Intent(ReportTS.this, SettingsMenuStaff_menu.class);
                    startActivity(intent1);
                    //loadListTeacher();
                    //loadListTeacher(sp_shift.getSelectedItem().toString(), sp_level.getSelectedItem().toString(), sp_grade.getSelectedItem().toString());
                }
            });
        } else {
            ArrayAdapter adap_list = new ArrayAdapter(this, R.layout.row_menu_select, list_1);
            lv_list.setAdapter(adap_list);}
        lv_list.refreshDrawableState();
    }



    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reg:
                //load_lv_subject_assing(SettingsMenuStaff.TS_code);

                break;

        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Save and Exit !!!");}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage("Are you sure to delete record?");}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
                //load_lv_subject_assing(SettingsMenuStaff.TS_code);
            }
        });
        dialogo1.show();
    }
    public void aceptar() {
        //deleteRecord(texto.toString());
    }
    public void cancelar() {
        //finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_shift:
                fill_sp_level(sp_shift.getSelectedItem().toString());
                break;
            case R.id.sp_level:
                fill_sp_grade(sp_level.getSelectedItem().toString());
                //fill_sp_subject(sp_level.getSelectedItem().toString(), sp_grade.getSelectedItem().toString());
                break;
            case R.id.sp_grade:
                //fill_sp_subject(sp_level.getSelectedItem().toString(), sp_grade.getSelectedItem().toString());
//                Toast.makeText(this, sp_shift.getSelectedItem().toString() + " = " + getIndexArray(_shift,sp_shift.getSelectedItem().toString()) + ", " +
//                                    sp_level.getSelectedItem().toString() + " = " + getIndexArray(_level, sp_level.getSelectedItem().toString()) + ", " +
//                                    sp_grade.getSelectedItem().toString() + " = " +  String.valueOf(sp_grade.getSelectedItemId() + 1) , Toast.LENGTH_SHORT).show();

                loadListTeacher(" AND shift=" + getIndexArray(_shift,sp_shift.getSelectedItem().toString()) +
                                " AND  level=" + getIndexArray(_level, sp_level.getSelectedItem().toString()) +
                                " AND grade=" + String.valueOf(sp_grade.getSelectedItemId() + 1)
                                );

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // *********** END Control Alerts ************************
    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(this,STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        //Cursor cur_data = dbSET.rawQuery("SELECT a1 FROM a", null);
        Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        return getemiscode;
    }

    public int getIndexArray(String[] myArray , String myString){
        int index = 0;
        for (int i=0;i<myArray.length;i++){
            if (myArray[i].equals(myString)){
                index = i+1;
            }
        }
        return index;
    }

    // *************************** FILL SPINERR SHIFT -  LEVEL  -   GRADE  -  SECTION  - SUBJECT - **************
    private void fill_sp_level(String shift) {
        list_level.clear();
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_level,
//        String sql = "SELECT id, Shift, level\n" +
//                "FROM \n" +
//                "(SELECT 13 AS id, CASE WHEN m_pp = 1 THEN 'Morning' ELSE 'Select one' END AS Shift, CASE WHEN m_pp = 1 THEN 'Pre-Primary' ELSE 'Select one' END AS 'Level' FROM ms_0  UNION\n" +
//                "SELECT 11 AS id, CASE WHEN m_p = 1 THEN 'Morning' ELSE 'Select one' END AS Shift, CASE WHEN m_p = 1 THEN 'Primary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT 12 AS id, CASE WHEN m_s = 1 THEN 'Morning'  ELSE 'Select one' END AS shift, CASE WHEN m_s = 1 THEN 'Secondary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT  23 AS id, CASE WHEN a_pp = 1 THEN 'Afternoon' ELSE 'Select one' END AS Shift, CASE WHEN a_pp = 1 THEN 'Pre-Primary' ELSE 'Select one' END AS 'Level' FROM ms_0  UNION\n" +
//                "SELECT 21 AS id, CASE WHEN a_p = 1 THEN 'Afternoon' ELSE 'Select one' END AS Shift, CASE WHEN a_p = 1 THEN 'Primary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT 22 AS id, CASE WHEN a_s = 1 THEN 'Afternoon' ELSE 'Select one' END AS Shift, CASE WHEN a_s = 1 THEN 'Secondary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT  33 AS id, CASE WHEN e_pp = 1 THEN 'Evening' ELSE 'Select one' END AS Shift, CASE WHEN e_pp = 1 THEN 'Pre-Primary' ELSE 'Select one' END AS 'Level' FROM ms_0  UNION\n" +
//                "SELECT 31 AS id, CASE WHEN e_p = 1 THEN 'Evening' ELSE 'Select one' END AS Shift, CASE WHEN e_p = 1 THEN 'Primary' ELSE 'Select one' END AS 'Level' FROM ms_0 UNION\n" +
//                "SELECT 32 AS id, CASE WHEN e_s = 1 THEN 'Evening' ELSE 'Select one' END AS Shift, CASE WHEN e_s = 1 THEN 'Secondary' ELSE 'Select one' END AS 'Level' FROM ms_0)\n" +
//                "WHERE shift <>'Select one'  AND shift='"+shift+"'\n" +
//                "GROUP BY id, shift, level\n" +
//                " ORDER BY id";
        sql = "SELECT id, Shift, level\n" +
                "FROM \n" +
                "(SELECT 13 AS id, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 11 AS id, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN m_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 12 AS id, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.str_g_morning)+"'  ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS shift, CASE WHEN m_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT  23 AS id, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 21 AS id, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 22 AS id, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.str_g_afternoon)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN a_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT  33 AS id, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_pp = 1 THEN '"+getResources().getString(R.string.pp)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0  UNION\n" +
                "SELECT 31 AS id, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_p = 1 THEN '"+getResources().getString(R.string.p)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0 UNION\n" +
                "SELECT 32 AS id, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.str_g_evening)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS Shift, CASE WHEN e_s = 1 THEN '"+getResources().getString(R.string.s)+"' ELSE '"+getResources().getString(R.string.str_g_selectone)+"' END AS 'Level' FROM ms_0)\n" +
                "WHERE shift <>'"+getResources().getString(R.string.str_g_selectone)+"'  AND shift='"+shift+"'\n" +
                "GROUP BY id, shift, level\n" +
                " ORDER BY id";

        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_level = cur_data.getString(2);
                list_level.add(col_level);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_level);
        sp_level.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();

//        fill_sp_grade(sp_level.getSelectedItem().toString());
//        fill_sp_subject(sp_level.getSelectedItem().toString());
    }

        private void fill_sp_grade(String level){
        list_grade.clear();
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_grade;
        String sql = "SELECT id, grade FROM grade WHERE level="+ getIndexArray(_level, level);
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_grade = cur_data.getString(1);
                list_grade.add(col_grade);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_grade);
        sp_grade.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();
    }

    private void fill_sp_subject(String level, String grade){
        list_subject.clear();
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String col_subject;
        String complementSQL = "";
        //Toast.makeText(getContext(),"Grado... " + grade.toString(), Toast.LENGTH_SHORT).show();
//        if (grade.equals("STD 1") || grade.equals("STD 2") ) {complementSQL=" AND id IN (1,2,3,4,5,6)";}
//        else if (grade.equals("STD 3") || grade.equals("STD 4") || grade.equals("STD 5") || grade.equals("STD 6") || grade.equals("STD 7") ) {complementSQL=" AND id IN (6,7,8,9,10,11,12,13,14,15,16)";}
//        else {complementSQL="";}
        if (grade.equals(getResources().getString(R.string.str_g_std1)) || grade.equals(getResources().getString(R.string.str_g_std2)) ) {complementSQL=" AND id IN (1,2,3,4,5,6)";}
        else if (grade.equals(getResources().getString(R.string.str_g_std3)) || grade.equals(getResources().getString(R.string.str_g_std4)) || grade.equals(getResources().getString(R.string.str_g_std5)) || grade.equals(getResources().getString(R.string.str_g_std6)) || grade.equals(getResources().getString(R.string.str_g_std7)) ) {complementSQL=" AND id IN (6,7,8,9,10,11,12,13,14,15,16)";}
        else {complementSQL="";}
        //if (grade.equals("STD 3")) {complementSQL=" AND id>1";} else {complementSQL="";}
        String sql = "SELECT id, subject FROM subject WHERE level="+ getIndexArray(_level, level ) + complementSQL;
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        if (cur_data.getCount()>0) {
            do {
                col_subject = cur_data.getString(1);
                list_subject.add(col_subject);
            } while (cur_data.moveToNext());
        }
        ArrayAdapter<String> adap_shift = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_subject);
        sp_subject.setAdapter(adap_shift);
        cur_data.close();
        dbSET.close();
        dbSET.close();
    }


    private String getGradeBDD(int level, int id){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String result;
        String sql = "SELECT grade FROM grade WHERE level="+ level+" AND id="+id;
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        result =  cur_data.getString(0);
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }

    private String getSubjectBDD(int level, int id){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String result;
        String sql = "SELECT subject FROM subject WHERE level="+ level+" AND id="+id;
        Cursor cur_data = dbSET.rawQuery(sql, null);
        if (cur_data.getCount()>0 ) {
            cur_data.moveToFirst();
            result =  cur_data.getString(0);
        } else {result =  "PP";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }

    private int findGradeBDD(int level, String grade){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        int result;
        String sql = "SELECT id FROM grade WHERE level="+ level+" AND grade='"+grade+"'";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        cur_data.moveToFirst();
        result =  cur_data.getInt(0);
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }

    private int findSubjectBDD(int level, String subject){
        Conexion cnSET = new Conexion(this, STATICS_ROOT + File.separator + "sisdb.sqlite", null, 4);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        int result;
        String sql = "SELECT id FROM subject WHERE level="+ level+" AND subject='"+subject+"'";
        Cursor cur_data = dbSET.rawQuery(sql, null);
        if (cur_data.getCount()>0 ) {
            cur_data.moveToFirst();
            result =  cur_data.getInt(0);
        } else {result = 0;}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return result;
    }


}
