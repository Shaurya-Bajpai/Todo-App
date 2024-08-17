package com.example.to_dolist.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.to_dolist.R
import com.example.to_dolist.data.Todo
import com.example.to_dolist.viewmodel.TodoViewModel

@Composable
fun FrontView( viewModel: TodoViewModel ) {

    var showDialog by remember { mutableStateOf(false) }
    var id by remember { mutableStateOf(0L) }

    Scaffold(
        topBar = { topbar(title = "Todo") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                contentColor = Color.White,
                backgroundColor = colorResource(id = R.color.topbar_color),
                onClick = {
                        id = 0L
                        showDialog = true
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {

        val todoList = viewModel.getAllTask.collectAsState(initial = listOf())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.Black)
        ) {
            items(todoList.value, key = { todo -> todo.id }){

                todo -> TodoItem( todo = todo,
                    onClickEdit = {
                        id = todo.id
                        showDialog = true
                    },
                    onClickDelete =  {
                        viewModel.deleteTask(todo = todo)
                    }
                )
            }
        }
    }

    if (showDialog) {

        if(id != 0L){
            val task = viewModel.getTaskById(id).collectAsState(initial = Todo(0L,""))
            viewModel.title = task.value.title
        }
        else{
            viewModel.title = ""
        }

        CustomAlertDialog(
            onDismissRequest = { showDialog = false },

                // TODO if you want to add title you can remove the comments then u will be able to see the title of the alert box
//            title = {
//                if(id != 0L){
//                    Text("Edit Task", color = Color.White, modifier = Modifier.padding(bottom = 24.dp))
//                }
//                else{
//                    Text("Add Task", color = Color.White, modifier = Modifier.padding(bottom = 24.dp))
//                }
//                Spacer(modifier = Modifier.height(50.dp))
//            },

            text = {
                Column {
                    Spacer(modifier = Modifier.height(50.dp))
                    OutlinedTextField(
                        value = viewModel.title,
                        onValueChange = { viewModel.titleChange(it) },
                        label = { Text("Enter your task", color = Color.White) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false

                        if(viewModel.title.isNotEmpty()){
                            if(id != 0L) {
                                // TODO update task
                                viewModel.updateTask(
                                    Todo(
                                        id = id,
                                        title = viewModel.title.trim(),
                                    )
                                )
                            }
                            else {
                                // TODO add task
                                viewModel.addTask(
                                    Todo(
                                        title = viewModel.title.trim(),
                                    )
                                )
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    if(id != 0L){
                        Text("Edit", color = Color.White)
                    }
                    else{
                        Text("Add", color = Color.White)
                    }

                }
            } ,
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Cancel", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
) {
    AlertDialog(
//        modifier = Modifier.,
        onDismissRequest = onDismissRequest,
        title = title,
        text = text,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        backgroundColor = Color.Transparent,
        contentColor = Color.White
    )
    Log.d("cus", "reached")

}

@Composable
fun TodoItem(todo: Todo, onClickEdit: ()->Unit , onClickDelete: ()->Unit ) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        elevation = 10.dp,
        backgroundColor = Color.DarkGray
    ) {

        var deleteOption by remember { mutableStateOf(false) }

        Row {
            Column(modifier = Modifier
                .padding(16.dp)
                .weight(2f)) {
                Text(text = todo.title, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colors.onPrimary)
            }
            IconButton(onClick = { onClickEdit() },
                modifier = Modifier
                    .padding(16.dp)
                    .weight(.5f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            IconButton(onClick = { deleteOption = true },
                modifier = Modifier
                    .padding(16.dp)
                    .weight(.5f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }

            if(deleteOption){
                AlertDialog(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = Color.DarkGray,
                    contentColor = Color.White,

                    onDismissRequest = {
                        deleteOption = false
                        Log.d("AlertDialog", "no")
                    },
                    title = { Text("Delete") },
                    text = { Text("Do you want to delete ?") },
                    confirmButton = {
                        FilledTonalButton(onClick = {
                            deleteOption = false

                            onClickDelete()

                            Log.d("AlertDialog", "yes")
                        }) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        FilledTonalButton(onClick = {
                            deleteOption = false
                            Log.d("AlertDialog", "no")
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }

        }
    }
}