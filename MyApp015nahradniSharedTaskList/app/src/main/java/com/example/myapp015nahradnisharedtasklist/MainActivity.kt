package com.example.myapp015nahradnisharedtasklist

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp015nahradnisharedtasklist.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tasks = mutableListOf<Task>() // Lokální seznam úkolů
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializace Firebase
        FirebaseApp.initializeApp(this)
        println("Firebase initialized successfully")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializace RecyclerView
        taskAdapter = TaskAdapter(tasks) { task ->
            updateTask(task) // Callback pro změnu úkolu
        }
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = taskAdapter

        // Simulace načtení dat
        //loadTasks()
        firestore = FirebaseFirestore.getInstance()
        // Načtení úkolů z firestore db
        loadTasksFromFirestore()

        // Nastavení logiky pro FloatingActionButton
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }


        listenToTaskUpdates()

    }

    private fun loadTasks() {
        //tasks.add(Task("1", "Buy groceries", isCompleted = false, assignedTo = "Alice"))
        //tasks.add(Task("2", "Clean the house", isCompleted = false, assignedTo = ""))
        //tasks.add(Task("3", "Prepare presentation", isCompleted = true, assignedTo = "Bob"))
        taskAdapter.notifyDataSetChanged()
    }

    private fun updateTask(task: Task) {
        // Aktualizace úkolu ve Firestore
        firestore.collection("tasks").document(task.id).set(task)
            .addOnSuccessListener {
                println("Task updated in Firestore: ${task.name}, completed: ${task.isCompleted}")
            }
            .addOnFailureListener { e ->
                println("Error updating task: ${e.message}")
            }
    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Task")

        // Vytvoření vstupního pole
        val input = EditText(this)
        input.hint = "Task name"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Tlačítka dialogu
        builder.setPositiveButton("Add") { _, _ ->
            val taskName = input.text.toString()
            if (taskName.isNotBlank()) {
                addTask(taskName)
            } else {
                Toast.makeText(this, "Task name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    /*private fun addTask(name: String) {
        val newTask = Task(
            id = (tasks.size + 1).toString(), // Generujeme jednoduché ID
            name = name,
            isCompleted = false,
            assignedTo = "" // Zatím nepřiřazené
        )
        tasks.add(newTask)
        taskAdapter.notifyItemInserted(tasks.size - 1)
    }*/

    private fun addTask(name: String) {
        val newTask = Task(
            id = firestore.collection("tasks").document().id, // Vygenerujeme ID
            name = name,
            isCompleted = false,
            assignedTo = ""
        )

        // Uložíme úkol do Firestore
        firestore.collection("tasks").document(newTask.id).set(newTask)
            .addOnSuccessListener {
            // Zde přidáme úkol do seznamu pouze tehdy, pokud tam ještě není
                if (tasks.none { it.id == newTask.id }) {
                tasks.add(newTask)
                taskAdapter.notifyItemInserted(tasks.size - 1)
                println("Task added to Firestore: $name")
            }
            }
            .addOnFailureListener { e ->
                println("Error adding task: ${e.message}")
            }
    }

    private fun loadTasksFromFirestore() {
        firestore.collection("tasks").get()
            .addOnSuccessListener { result ->
                tasks.clear()
                for (document in result) {
                    val task = document.toObject(Task::class.java)
                    tasks.add(task)
                }
                taskAdapter.notifyDataSetChanged()
                println("Tasks loaded from Firestore")
            }
            .addOnFailureListener { e ->
                println("Error loading tasks: ${e.message}")
            }
    }

    private fun listenToTaskUpdates() {
        firestore.collection("tasks").addSnapshotListener { snapshots, e ->
            if (e != null) {
                println("Listen failed: ${e.message}")
                return@addSnapshotListener
            }

            tasks.clear()
            for (document in snapshots!!) {
                val task = document.toObject(Task::class.java)
                tasks.add(task)
            }
            taskAdapter.notifyDataSetChanged()
        }
    }

}