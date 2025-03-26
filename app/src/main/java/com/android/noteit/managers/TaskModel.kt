package com.android.noteit.managers

data class Task(var isDone : Boolean, var title: String)

class TaskModel {
    var tasks = mutableListOf<Task>()
    var doneTasks = mutableListOf<Task>()

    fun addTask(title: String){
        tasks.add(Task(false, title))
    }

    fun completeTask(index: Int) {
        if (index in tasks.indices) {
            val task = tasks[index]
            task.isDone = true
            doneTasks.add(task)
            tasks.removeAt(index)
        }
    }
}