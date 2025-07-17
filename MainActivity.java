package com.example.skillcraft_todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextTask;
    Button buttonAdd, buttonDelete, buttonUpdate;
    ListView listViewTasks;

    ArrayList<String> taskList;
    ArrayAdapter<String> adapter;

    int selectedPosition = -1; // To track which task is selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, taskList);
        listViewTasks.setAdapter(adapter);
        listViewTasks.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Add
        buttonAdd.setOnClickListener(v -> {
            String task = editTextTask.getText().toString().trim();
            if (!task.isEmpty()) {
                taskList.add(task);
                adapter.notifyDataSetChanged();
                editTextTask.setText("");
            } else {
                Toast.makeText(this, "Enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        // Update
        buttonUpdate.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                String updatedTask = editTextTask.getText().toString().trim();
                if (!updatedTask.isEmpty()) {
                    taskList.set(selectedPosition, updatedTask);
                    adapter.notifyDataSetChanged();
                    editTextTask.setText("");
                    listViewTasks.clearChoices();
                    selectedPosition = -1;
                } else {
                    Toast.makeText(this, "Enter a task to update", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Select a task to update", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete
        buttonDelete.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                taskList.remove(selectedPosition);
                adapter.notifyDataSetChanged();
                editTextTask.setText("");
                listViewTasks.clearChoices();
                selectedPosition = -1;
            } else {
                Toast.makeText(this, "Select a task to delete", Toast.LENGTH_SHORT).show();
            }
        });

        // Select a task
        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            editTextTask.setText(taskList.get(position));
        });
    }
}
