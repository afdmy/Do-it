package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.doit.Adapter.ToDoAdapter;
import com.example.doit.Model.ToDoModel;
import com.example.doit.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    //RecyclerView 简称 RV， 是作为 ListView 和 GridView 的加强版出现的，
    // 目的是在有限的屏幕之上展示大量的内容
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//把布局文件放在activity中显示
        getSupportActionBar().hide();//隐藏标题栏

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView=findViewById(R.id.tasksRecyclerview);
        //这样设置的效果是组件垂直往下
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,MainActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        //找到控件
        fab = findViewById(R.id.fab);

        //ItemTouchHelper是一个强大的帮助类。用来配合RecyclerView使用，ItemTouchHelper同一时刻只能支持两种效果：
        //swipe、drag中的一种。分别用来实现RecyclerView里面item侧滑删除(swipe)效果或者item长按拖拽移动(drag)。
        // 当然swipe和drag效果同一时刻只能支持一种。因为事件冲突不能同时支持。
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        //对List进行逆序
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        //设置监听事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}