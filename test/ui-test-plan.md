# TBlade UI test plan

The `test-ui` skill reads the test cases below. Keep the expected output complete: it is compared character-for-character with the program output.

## Test Case: Add and list all task types
### Aim
Verify that todo, deadline, and event commands create the correct task subtype and that list displays each type and its date/time text.
### Inputs
```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```
### Expected output
```text
____________________________________________________________ 🐷
#####  ####   #       ###   ####   ##### 🐷
  #    #   #  #      #   #  #   #  # 🐷
  #    ####   #      #####  #   #  #### 🐷
  #    #   #  #      #   #  #   #  # 🐷
  #    ####   #####  #   #  ####   ##### 🐷
Hello! I'm TBlade. 🐷
What can I do for you? 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Got it. I've added this task: 🐷
  [T][ ] borrow book 🐷
Now you have 1 tasks in the list. 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Got it. I've added this task: 🐷
  [D][ ] return book (by: Sunday) 🐷
Now you have 2 tasks in the list. 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Got it. I've added this task: 🐷
  [E][ ] project meeting (from: Mon 2pm to: 4pm) 🐷
Now you have 3 tasks in the list. 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Here are the tasks in your list: 🐷
1.[T][ ] borrow book 🐷
2.[D][ ] return book (by: Sunday) 🐷
3.[E][ ] project meeting (from: Mon 2pm to: 4pm) 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Bye. Hope to see you again soon! 🐷
____________________________________________________________ 🐷
```

## Test Case: Mark and unmark a task
### Aim
Verify that marking a task done and reversing that status changes the list status icon.
### Inputs
```text
todo read book
mark 1
unmark 1
list
bye
```
### Expected output
```text
____________________________________________________________ 🐷
#####  ####   #       ###   ####   ##### 🐷
  #    #   #  #      #   #  #   #  # 🐷
  #    ####   #      #####  #   #  #### 🐷
  #    #   #  #      #   #  #   #  # 🐷
  #    ####   #####  #   #  ####   ##### 🐷
Hello! I'm TBlade. 🐷
What can I do for you? 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Got it. I've added this task: 🐷
  [T][ ] read book 🐷
Now you have 1 tasks in the list. 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Nice! I've marked this task as done: 🐷
  [X] read book 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
OK, I've marked this task as not done yet: 🐷
  [ ] read book 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Here are the tasks in your list: 🐷
1.[T][ ] read book 🐷
____________________________________________________________ 🐷
____________________________________________________________ 🐷
Bye. Hope to see you again soon! 🐷
____________________________________________________________ 🐷
```
