How to release in Dream-Better-Worlds
=====================================

This document describes the steps how to release in `Dream-Better-Worlds`.  
Momentary only `pre-releases` will be released, so there is not a `jar-file` to 
execute the program.



Content
---

* [Preparation](#Preparation)
* [Release](#Release)
* [Post-Processing](#PostProcessing)



[//]: # (Content)
Preparation<a name="Preparation" />
---

* The project is in a state to release :) .
* Remove the commentary in the `pom.xml` from the child-modul `DBW-Application`.
    - `Pre-releases` mustn't comment out because they have no executable jar.
* Update `pom.xml`from the parent-modul `Dream-Better-Worlds` to the new version.
* Navigate in explorer into the folder from the updated `pom.xml` descripted in
  the previous step.
* Open the `CommandLine` with `Shift + right click`.
* In the `CommandLine` execute `mvn versions:update-child-modules`.
* `Build with Dependencies` the project so that the application can start.
* Delete following folders in the child-modul `DBW-Application`:
    - Folder `database`.
    - Folder `log`.
    - Folder `target`.
* Delete following files in the child-modul `DBW-Application`:
    - File `Preferences.properties`.
* Check the `Release_vX.Y.Z_yyyy-MM-dd_HH-mm.md` for the new version.
* Update the `ReadMe.md` (section `Release Notes`).
* Update the parameter `title` in the files `DreamBetterWorlds.properties` and 
  `TestdataApplication.properties` from the child-modul `DBW-Application`.
* Commit all changes into the branch `release_vX.Y.Z` in `GitHub`.
* Create a `zip` from the project and store it local.



Release<a name="Release" />
---

* Merge the branch `release_vX.Y.Z` into the branch `master`.
* Run the branch `master` local to see if new exception happen during the merge.
* Commit all changes into the branch `master` in `GitHub`.
* Release the new release in `GitHub`.
    - Use the prepared `Release_vX.Y.Z_yyyy-MM-dd_HH-mm.md` for the release text.



Post-Processing<a name="PostProcessing" />
---

* Merge the branch `release_vX.Y.Z` into the branch `development`.
* Run the branch `development` local to see if new exception happen during the 
  merge.
* Add the previously removed commentary into the `pom.xml` from the child-modul 
  `DBW-Application`.
* Commit all changes into the branch `development` in `GitHub`.
* Repeat the previous steps for all other existing branches.
    - Merge the branch `development` into the branch `xy`. 
