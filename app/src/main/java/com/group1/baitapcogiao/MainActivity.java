package com.group1.baitapcogiao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ImageButton btnAdd;
    ArrayList<SinhVien> sinhVienArrayList;
    Adapter adapter;
    DatabaseStudent databaseStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setEvent();
    }

    private void setEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_edt);
                final EditText edt_name = dialog.findViewById(R.id.edt_name);
                final EditText edt_birth = dialog.findViewById(R.id.edt_birthday);
                final EditText edt_address = dialog.findViewById(R.id.edt_address);
                final RadioButton nam = dialog.findViewById(R.id.nam);
                RadioButton nu = dialog.findViewById(R.id.nu);
                Button btnSend = dialog.findViewById(R.id.btnSend);
                dialog.show();
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int sex =0;
                        if(nam.isChecked()){
                            sex = 1;
                        }else {
                            sex = 0;
                        }
                        SinhVien sinhVien1 = new SinhVien();
                        sinhVien1.setId(edt_name.getText().toString().replace(" ","")+new Random().nextInt(35000));
                        sinhVien1.setName(edt_name.getText().toString());
                        sinhVien1.setBirthday(edt_birth.getText().toString());
                        sinhVien1.setAddress(edt_address.getText().toString());
                        sinhVien1.setSex(sex);
                        boolean re = databaseStudent.insertStudent(sinhVien1);
                        if(re){
                            sinhVienArrayList.add(sinhVien1);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }

                });
            }
        });
    }

    public void init(){
        databaseStudent= new DatabaseStudent(this,DatabaseStudent.DATABASE_NAME,null,1);
        listView = (ListView) findViewById(R.id.lvDs);
        sinhVienArrayList = new ArrayList<>();
        sinhVienArrayList.addAll(databaseStudent.getListSinhVien());
        adapter = new Adapter(this,sinhVienArrayList);
        listView.setAdapter(adapter);
        btnAdd = findViewById(R.id.btnAdd);

    }

}
