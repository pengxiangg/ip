package duke;

import java.util.ArrayList;

import duke.task.Task;

/**
 * Represent a list for containing tasks.
 */
public class TaskList {
    /**
     * ArrayList that stores the tasks
     */
    private ArrayList<Task> tasks;

    /**
     * Creates a TaskList that contains tasks.
     *
     * @param tasks ArrayList that will be placed inside the TaskList.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Returns the ArrayList that is inside the TaskList.
     *
     * @return ArrayList inside the TaskList.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Marks a task as done.
     *
     * @param taskNumber Represents the task to be marked as done, in array index form.
     * @return Task that is marked as done.
     * @throws DukeException if the task number is not within the range of the TaskList.
     */
    public Task markTaskDoneInList(int taskNumber) throws DukeException {
        if (taskNumber < 0 || taskNumber >= tasks.size()) {
            throw new DukeException("Please enter a valid task number\n");
        } else {
            Task task = tasks.get(taskNumber);
            task.markDone();
            return task;
        }
    }

    /**
     * Deletes task from TaskList.
     *
     * @param taskNumber Represents the task to be deleted, in array index form.
     * @return Task that is deleted
     * @throws DukeException if the task number is not within the range of the TaskList.
     */
    public Task deleteTaskFromList(int taskNumber) throws DukeException {
        if (taskNumber < 0 || taskNumber >= tasks.size()) {
            throw new DukeException("Please enter a valid task number\n");
        } else {
            Task task = tasks.get(taskNumber);
            tasks.remove(task);
            return task;
        }
    }

    /**
     * Returns the size of the TaskList.
     *
     * @return size of TaskList.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Adds task into TaskList.
     *
     * @param task Task to be added into the TaskList.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Finds tasks using keyword.
     *
     * @param keyword Keyword that is used to find tasks.
     * @return ArrayList of tasks containing tasks that match the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> foundTasks = new ArrayList<>();

        for (Task task : tasks) {
            boolean hasKeyword = task.getDescription().contains(keyword);
            if (hasKeyword) {
                foundTasks.add(task);
            }
        }

        return foundTasks;
    }
}
