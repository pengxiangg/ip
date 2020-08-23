
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Date;

import java.util.List;
import java.util.Scanner;


public class Duke {
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat dateTimeConverterFormat = new SimpleDateFormat("MMM dd yyyy hh:mma");
    private static final SimpleDateFormat dateConverterFormat = new SimpleDateFormat("MMM dd yyyy");


    public static void readAndEcho(List<Task> list) {

        //Formatting of greeting
        String intro1 = "Hello! I'm Duke \n";
        String intro2 = "What can I do for you? \n";

        String greeting = addDividers(formatString(intro1) + formatString(intro2));
        System.out.println(greeting);

        //Reading in user input
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        //Stop when user inputs "bye"
        while (!input.equals("bye")) {
            String[] inputArr = input.split(" ");
            try {
                //Print list when user inputs "list"
                if (input.equals("list")) {
                    printList(list);
                }
                // Mark task as done when user inputs "done"
                else if (inputArr[0].equals("done")) {
                    if (inputArr.length < 2) {
                        throw new DukeException("Task to be done not specified :(\n");
                    }
                    String taskNumber = inputArr[1];
                    markTaskDoneInList(list, Integer.parseInt(taskNumber) - 1);
                }

                else if (inputArr[0].equals("delete")) {
                    if (inputArr.length < 2) {
                        throw new DukeException("Task to delete not specified :(\n");
                    }
                    String taskNumber = inputArr[1];
                    deleteTaskFromList(list, Integer.parseInt(taskNumber) - 1);
                }
                //Add a new task to the list
                else {
                    //Add a new to-do task
                    if (inputArr[0].equals("todo")) {
                        if (inputArr.length < 2) {
                            throw new TaskException("☹ OOPS!!! The description of a todo cannot be empty.\n");
                        }
                        String todoName = input.substring(5);
                        ToDo todo = new ToDo(todoName);
                        list.add(todo);
                        String s = formatString("Got it. I've added this task: \n") +
                                formatString(todo.toString() + '\n') +
                                formatString("Now you have " + list.size() + " tasks in the list. \n");
                        System.out.println(addDividers(s));
                    }
                    //Add a new deadline task
                    else if (inputArr[0].equals("deadline")) {
                        if (inputArr.length < 2) {
                            throw new TaskException("☹ OOPS!!! The description of a deadline cannot be empty.\n");
                        }
                        String deadlineString = input.substring(9);
                        String[] deadlineArr = deadlineString.split(" /by ");
                        if (deadlineArr.length < 2) {
                            throw new TaskException("☹ OOPS!!! The date of a deadline cannot be empty.\n");
                        }

                        Date date;
                        boolean isTime;
                        if (deadlineArr[1].split(" ").length == 1) {
                            date = dateFormat.parse(deadlineArr[1]);
                            isTime = false;
                        } else {
                            date = dateTimeFormat.parse(deadlineArr[1]);
                            isTime = true;
                        }
                        Deadline deadline = new Deadline(deadlineArr[0], date, isTime);
                        list.add(deadline);
                        String s = formatString("Got it. I've added this task: \n") +
                                formatString(deadline.toString() + '\n') +
                                formatString("Now you have " + list.size() + " tasks in the list. \n");
                        System.out.println(addDividers(s));
                    }
                    //Add a new Event task
                    else if (inputArr[0].equals("event")) {
                        if (inputArr.length < 2) {
                            throw new TaskException("☹ OOPS!!! The description of an event cannot be empty.\n");
                        }
                        String eventString = input.substring(6);
                        String[] eventArr = eventString.split(" /at ");
                        if (eventArr.length < 2) {
                            throw new TaskException("☹ OOPS!!! The date of an event cannot be empty.\n");
                        }
                        Date date;
                        boolean isTime;
                        if (eventArr[1].split(" ").length == 1) {
                            date = dateFormat.parse(eventArr[1]);
                            isTime = false;
                        } else {
                            date = dateTimeFormat.parse(eventArr[1]);
                            isTime = true;
                        }
                        Event event = new Event(eventArr[0], date, isTime);
                        list.add(event);
                        String s = formatString("Got it. I've added this task: \n") +
                                formatString(event.toString() + '\n') +
                                formatString("Now you have " + list.size() + " tasks in the list. \n");
                        System.out.println(addDividers(s));
                    }
                    //Unrecognised command
                    else {
                        throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(\n");
                    }
                }
                writeToFile(list);
            } catch (TaskException e) {
                System.out.println(addDividers(formatString(e.toString())));
            } catch (DukeException e) {
                System.out.println(addDividers(formatString(e.toString())));
            } catch (NumberFormatException e) {
                System.out.println(addDividers(formatString("Please enter out a valid number\n")));
            } catch (ParseException e) {
                System.out.println(addDividers(formatString("Please enter a date and time in the format of \n") +
                        formatString("dd/MM/2020 HHmm (e.g. 02/12/2020 1530) " +
                        "or dd/MM/2020 (e.g. 15/02/2020)\n")));
            }
            input = sc.nextLine();
        }


        //Formatting and printing of goodbye message
        String goodbye = "Bye. Hope to see you again soon! \n";
        System.out.println(addDividers(formatString(goodbye)));
    }

    private static void deleteTaskFromList(List<Task> list, int taskNumber) throws DukeException {
        if (taskNumber < 0 || taskNumber > list.size() - 1) {
            throw new DukeException("Please enter a valid task number\n");
        } else {
            Task task = list.get(taskNumber);
            list.remove(task);
            String success = formatString("Noted. I've removed this task: \n") +
                    formatString(task.toString() + "\n") +
                    formatString("Now you have " + list.size() + " tasks in the list. \n");
            System.out.println(addDividers(success));
        }
    }


    private static void markTaskDoneInList(List<Task> list, Integer taskNumber) throws DukeException {
        if (taskNumber < 0 || taskNumber > list.size() - 1) {
            throw new DukeException("Please enter a valid task number\n");
        } else {
            list.get(taskNumber).markDone();
            String success = formatString("Nice! I've marked this task as done: \n") +
                    formatString(list.get(taskNumber).toString() + "\n");
            System.out.println(addDividers(success));
        }

    }

    private static void printList(List<Task> list) {
        String printedList = "";
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String lineItem = (i + 1) + ". " + list.get(i) + "\n";
                printedList = printedList + formatString(lineItem);
            }
        } else {
            String emptyString = "List is empty \n";
            printedList = formatString(emptyString);
        }
        System.out.println(addDividers(printedList));
    }

    private static String addDividers(String s) {
        String divider = "____________________________________________________________________\n";
        String dividerFormatted = String.format("%" + (5 + divider.length()) + "s", divider);
        return dividerFormatted + s + dividerFormatted;
    }

    private static String formatString(String s) {
        return String.format("%" + (6 + s.length()) + "s", s);
    }

    public static void main(String[] args) {
        ArrayList<Task> arrayList = new ArrayList<>();

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        readFile(arrayList);
        readAndEcho(arrayList);
    }

    private static void readFile(List<Task> arrayList) {
        //From https://www.sghill.net/how-do-i-make-cross-platform-file-paths-in-java.html
        String home = System.getProperty("user.dir");
        Path path = Paths.get(home, "data", "duke.txt");
        if (!Files.isRegularFile(path)) {
            createFile();
        } else {
            File f = new File(path.toString());
            try {
                Scanner s = new Scanner(f);
                while (s.hasNext()) {
                    String string = s.nextLine();
                    String[] arr = string.split(" \\| ");

                    boolean isDone = arr[1].equals("1");
                    boolean isTime;
                    Date date;
                    switch (arr[0]) {
                    case "T":
                        arrayList.add(new ToDo(arr[2], isDone));
                        break;
                    case "D":
                        isTime = arr[4].equals("1");
                        date = (isTime) ? dateTimeConverterFormat.parse(arr[3]) : dateConverterFormat.parse(arr[3]);
                        arrayList.add(new Deadline(arr[2], date, isTime, isDone));
                        break;
                    case "E":
                        isTime = arr[4].equals("1");
                        date = (isTime) ? dateTimeConverterFormat.parse(arr[3]) : dateConverterFormat.parse(arr[3]);
                        arrayList.add(new Event(arr[2], date, isTime, isDone));
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file :(");
                createFile();
            } catch (ParseException e) {
                System.out.println("Unable to parse date :(");
            }
        }
    }

    private static void createFile() {
        String home = System.getProperty("user.dir");
        Path path = Paths.get(home, "data");
        if (!Files.isDirectory(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Failed to create directory");
            }
        }

        path = Paths.get(home, "data", "duke.txt");
        try {
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Failed to create file");
        }
    }

    private static void writeToFile(List<Task> arrayList) {
        Path path = Paths.get(System.getProperty("user.dir"), "data", "duke.txt");
        try {
            FileWriter fw = new FileWriter(path.toString());
            for (Task task : arrayList) {
                fw.write(task.toStoredTextString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Unable to write to file");
        }
    }
}
