How to merge brances in the NetBeans IDE
===

This document describes the steps how to merge branches from [GitHub] in the 
[NetBeans IDE].



Content
---

* [Pre-Condition](#PreCondition)
    - [Check out branches](#CheckOutBranches)
* [Merge](#Merge)
* [Conflict](#Conflict)
* [Post-Processing](#PostProcessing)



Pre-Condition<a name="PreCondition" />
---
* If both branches are in a state to merge and the branches are checked out in
  [NetBeans IDE] then goto section [Merge](#Merge).
  

  
Check out branches<a name="CheckOutBranches" />
---
* Click Team -> Git -> Clone in [NetBeans IDE].
* Paste https://github.com/Naoghuman/Dream-Better-Worlds.git into `Repository URL`.
* Add `User` and `Password` and click `Next`.
* Select the branches which should be merged.
* Add additional `Parameter` if needed.



Merge<a name="Merge" />
---
* Check out the `destination` branch.
* Right click in `Git Repository Browser` on the `source` branch and select 
  `Merge revision`.
    - Performs a three-way merge between the current branch, your Working Tree 
      contents, and the specified branch. Displays the Merge Revision dialog box.
* If the merge is done without a ´conflict´, the goto section [Post-Processing](#PostProcessing).



Conflict<a name="Conflict" />
---
* Happens during the [Merge](#Merge) a `conflict` then a warning popup will be
  shown in [NetBeans IDE].
* Click the `TODO Name? Review` button.
* A new side `TODO Name? Merge conflicts` will be open which shows all files with a 
  `conflict`.
* Go through every file (double click) and resolve the `conflict`.



Post-Processing<a name="PostProcessing" />
---
* Check if in the `source` branches extentions (for example new moduls) are 
  available which not exists in the `destination` branch.
* If an extentions exists, then controll if there also the new `features` needed 
  to implement. If yes, implement them.
* Perform the action `Build with Dependencies`.
* Project is running?
* `Commit` and `push` the changes to [GitHub].



[//]: # (Links)
[GitHub]:https://github.com/
[NetBeans IDE]:https://netbeans.org/
