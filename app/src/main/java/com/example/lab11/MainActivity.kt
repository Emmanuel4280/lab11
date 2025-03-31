package com.example.lab11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() //permite el modo de pantalla completa

        setContent { // configuramos el UI con el jetpack compose
            MaterialTheme { // aplicamos el tema material
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> //usamos scaffold para la base
                    Column(modifier = Modifier.padding(innerPadding)) { //el main de la pantalla
                        Todolist() //llamamos a la funcion generadora de listas
                    }
                }
            }
        }
    }
}
//composable que maneja la logica de las listas
@Composable
fun Todolist() {
    //guardamos el texto de tareas
    val taskText = remember { mutableStateOf("") }
    //lista de tareas
    val tasks = remember { mutableStateListOf<Task>() }
// organizamos los elementos de manera vertical
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = taskText.value, // contenido que se muestra
            onValueChange = { taskText.value = it }, //actualizamos el contenido cuando cambia
            label = { Text("New Task") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    //logica del submit
                    if (taskText.value.isNotEmpty()) {
                        tasks.add(Task(taskText.value))
                        taskText.value = ""
                    }
                }
            ),
            //espacio debajo del campo de texto
            modifier = Modifier.padding(bottom = 8.dp)
        )
//logica del boton submit
        Button(onClick = {
            if (taskText.value.isNotEmpty()) {
                tasks.add(Task(taskText.value))
                taskText.value = ""
            }
        }) {
            Text("Add Task")
        }
//mostramos las tareas en la lista
        tasks.forEach { task ->
            TaskItem(task = task, onCheckChange = {
                task.isCompleted = it
            })
        }
    }
}
//el check de la tarea (a medias)
@Composable
fun TaskItem(task: Task, onCheckChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onCheckChange
        )
        Text(task.text)
    }
}

data class Task(val text: String, var isCompleted: Boolean = false)

