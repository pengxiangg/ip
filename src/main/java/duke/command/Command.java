package duke.command;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * Command class to execute commands.
 */
public abstract class Command {

    /**
     * Executes commands, depending on child classes.
     * @param tasks TaskList that is being executed on.
     * @param ui Ui to update the user.
     * @param storage Storage to update the text file.
     * @throws DukeException If there is an error.
     */
    public abstract void execute(TaskList tasks, TaskList archives,
                                 Ui ui, Storage storage)
            throws DukeException;

    /**
     * Returns executed message, depending on child classes.
     * @param tasks TaskList that is being executed on.
     * @param archives TaskList that contains archived tasks.
     * @param ui Ui to update the user.
     * @param storage Storage to update the text file.
     * @return String of executed message
     * @throws DukeException If there is an error.
     */
    public abstract String getExecuteString(TaskList tasks, TaskList archives,
                                            Ui ui, Storage storage) throws DukeException;

    /**
     * Checks if it is the exit command.
     * @return Returns true if it is the exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
