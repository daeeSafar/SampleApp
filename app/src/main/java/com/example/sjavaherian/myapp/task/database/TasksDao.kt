/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sjavaherian.myapp.task.database

import android.arch.persistence.room.*
import io.reactivex.Flowable

/**
 * Data Access Object for the loadTasks table.
 */
@Dao
interface TasksDao {

    /**
     * Select all loadTasks from the loadTasks table.
     *
     * @return all loadTasks.
     */
    @Query("SELECT * FROM Tasks")
    fun loadAllTasks(): Flowable<List<Task>>

    @Query("SELECT COUNT(*) FROM tasks")
    fun rowCount(): Long

    /**
     * Select a task by mId.
     *
     * @param taskId the task mId.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM tasks WHERE mId = :taskId LIMIT 1")
    fun getTaskById(taskId: String): Task

    @Query("SELECT * FROM tasks WHERE title LIKE :query OR description LIKE :query ORDER BY mId")
    fun getTaskByTitle(query: String): Flowable<List<Task>>

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    /**
     * Update a task.
     *
     * @param task task to be updated
     * @return the number of loadTasks updated. This should always be 1.
     */
    @Update
    fun updateTask(task: Task): Int

    /**
     * Update the active status of a task
     *
     * @param taskId    mId of the task
     * @param completed status to be updated
     */
    @Query("UPDATE tasks SET active = :completed WHERE mId = :taskId")
    fun updateCompleted(taskId: String, completed: Boolean)

    /**
     * Delete a task by mId.
     *
     * @return the number of loadTasks deleted. This should always be 1.
     */
    @Query("DELETE FROM Tasks WHERE mId = :taskId")
    fun deleteTaskById(taskId: String): Int

    /**
     * Delete all loadTasks.
     */
    @Query("DELETE FROM Tasks")
    fun deleteTasks()

    /**
     * Delete all active loadTasks from the table.
     *
     * @return the number of loadTasks deleted.
     */
    @Query("DELETE FROM Tasks WHERE active = 1")
    fun deleteCompletedTasks(): Int
}
