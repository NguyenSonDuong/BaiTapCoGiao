package com.group1.baitapcogiao;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<SinhVien> listSinhVien ;

    public Adapter(Context context, ArrayList<SinhVien> listSinhVien) {
        this.context = context;
        this.listSinhVien = listSinhVien;
    }

    @Override
    public int getCount() {
        return listSinhVien.size();
    }

    @Override
    public Object getItem(int position) {
        return listSinhVien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if(convertView == null){
            viewHoder = new ViewHoder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter,null);
            viewHoder.tvName = convertView.findViewById(R.id.tvName);
            viewHoder.tvBirthday = convertView.findViewById(R.id.tvBirthday);
            viewHoder.tvAddress = convertView.findViewById(R.id.tvAddress);
            viewHoder.tvSex = convertView.findViewById(R.id.tvSex);
            viewHoder.tvID = convertView.findViewById(R.id.tvID);
            viewHoder.imgEdit =convertView.findViewById(R.id.imgEdit);
            viewHoder.imgDelete =convertView.findViewById(R.id.imgDelete);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        final SinhVien sinhVien = listSinhVien.get(position);
        viewHoder.tvName.setText(sinhVien.name);
        viewHoder.tvBirthday.setText(sinhVien.birthday);
        viewHoder.tvSex.setText(sinhVien.sex+"");
        viewHoder.tvAddress.setText(sinhVien.address);
        viewHoder.tvID.setText(sinhVien.id);
        viewHoder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseStudent databaseStudent = new DatabaseStudent(context,DatabaseStudent.DATABASE_NAME,null,1);
                final Dialog dialog = new Dialog(context);
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
                        final SinhVien sinhVien1 = new SinhVien();
                        sinhVien1.setId(sinhVien.getId());
                        sinhVien1.setName(edt_name.getText().toString());
                        sinhVien1.setBirthday(edt_birth.getText().toString());
                        sinhVien1.setAddress(edt_address.getText().toString());
                        sinhVien1.setSex(sex);
                        boolean re = databaseStudent.editStudent(sinhVien1);
                        if(!re){
                            Toast.makeText(context, "Lỗi sửa dữ liệu", Toast.LENGTH_SHORT).show();
                        }else {
                            listSinhVien.set(position,sinhVien1);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

            }
        });

        viewHoder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseStudent databaseStudent = new DatabaseStudent(context,DatabaseStudent.DATABASE_NAME,null,1);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Nếu bạn xóa đồng nghĩa với việc toàn bộ dữ liệu trong database sẽ bị xóa theo bạn có đồng ý không");
                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseStudent.deleteStudent(sinhVien.getId());
                        listSinhVien.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setPositiveButton("Không ĐỒng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        return convertView;
    }

    class ViewHoder{
        TextView tvName, tvBirthday,tvAddress,tvSex, tvID;
        ImageView imgEdit, imgDelete;
    }
}
