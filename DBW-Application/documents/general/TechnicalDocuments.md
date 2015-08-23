Technical Documents
===



Content
---

* [Coding Rules](#CodingRules)
    - [General Coding Rules](#GeneralCodingRules)
        + [Coding Rules for Actions (General)](#CodingRulesForActionsGeneral)
        + [Coding Rules for CSS (General)](#CodingRulesForCSSGeneral)
        + [Coding Rules for Entities (General)](#CodingRulesForEntitiesGeneral)
        + [Coding Rules for Properties (General)](#CodingRulesForPropertiesGeneral)
    - [Coding Rules in Context from library afterburner](#CodingRulesInContextFromLibraryAfterburner)
        + [Coding Rules for Presenter (Afterburner)](#CodingRulesForPresenterAfterburner)
        + [Coding Rules for Properties (Afterburner)](#CodingRulesForPropertiesAfterburner)
        + [Coding Rules for View (Afterburner)](#CodingRulesForViewAfterburner)
* [How To ...](#HowTo)
    - [How to clone other branches then master with NetBeans](#HowToCloneOtherBranchesThenMasterWithNetBeans)
    - [How to merge branches in NetBeans](#HowToMergeBranchesInNetBeans)
    - [How to release](#HowToRelease)
* [Release Notes](#ReleaseNotes)
* [Templates](#Templates)



Coding Rules<a name="CodingRules" />
---

`Coding Rules` defined the rules how some functionality should be implemented in
`Dream-Better-Worlds`. There are 2 main areas which have their own `Coding Rules`.
* General `Coding Rules` apply for the hole application.
* Expected the `Coding Rules` in contex from the library [Afterburner].


### General Coding Rules<a name="GeneralCodingRules" />


#### Coding Rules for Actions (General)<a name="CodingRulesForActionsGeneral" />
* This file contains the `Coding Rules` for writing `Actions` in
  `Dream-Better-Worlds` in general purpose.
* For more information see [CodingRulesForActionsGeneral.md].


#### Coding Rules for CSS (General)<a name="CodingRulesForCSSGeneral" />
* This file contains the `Coding Rules` for styling with `CSS` in
  `Dream-Better-Worlds` in general purpose.
* For more information see [CodingRulesForCSSGeneral.md].


#### Coding Rules for Entities (General)<a name="CodingRulesForEntitiesGeneral" />
* This file contains the `Coding Rules` for writing `Entities` in
  `Dream-Better-Worlds` in general purpose.
* For more information see [CodingRulesForEntitiesGeneral.md].


#### Coding Rules for Properties (General)<a name="CodingRulesForPropertiesGeneral" />
* This file contains the `Coding Rules` for working with `Properties` in
  `Dream-Better-Worlds` in general purpose.
* For more information see [CodingRulesForPropertiesGeneral.md].



### Coding Rules in Context from library afterburner<a name="CodingRulesInContextFromLibraryAfterburner" />


#### Coding Rules for Presenter (Afterburner)]<a name="CodingRulesForPresenterAfterburner" />
* This file contains the `Coding Rules` for writing a `Presenter` in
  `Dream-Better-Worlds` in context from the library [Afterburner].
* For more information see [CodingRulesForPresenterAfterburner.md].


#### Coding Rules for Properties (Afterburner)]<a name="CodingRulesForPropertiesAfterburner" />
* This file contains the `Coding Rules` for working with `Properties` in
  `Dream-Better-Worlds` in context from the library [Afterburner].
* For more information see [CodingRulesForPropertiesAfterburner.md].


#### Coding Rules for View (Afterburner)]<a name="CodingRulesForViewAfterburner" />
* This file contains the `Coding Rules` for writing a `View` in
  `Dream-Better-Worlds` in context from the library [Afterburner].
* For more information see [CodingRulesForViewAfterburner.md].



How To ...<a name="HowTo" />
---


### How to clone other branches then master with NetBeans<a name="HowToCloneOtherBranchesThenMasterWithNetBeans" />
* This file describes the steps how to `clone` other branches then the `master` 
  branch with [NetBeans IDE].
* For more information see [HowToCloneOtherBranchesThenMasterWithNetBeans.md].


### How to merge branches in NetBeans<a name="HowToMergeBranchesInNetBeans" />
* This file describes the steps how to `merge` branches from [GitHub] in the 
  [NetBeans IDE].
* For more information see [HowToMergeBranchesInNetBeans.md].


### How to release<a name="HowToRelease" />
* This file contains a step-by-step instruction how to `release` a release
  in `Dream-Better-Worlds`.
* For more information see [HowToRelease.md].



Release Notes<a name="ReleaseNotes" />
---

Current pre-release is `v0.1.3` which contains following points:
* The main focus in this release is the documentation. Extended the ReadMe.md
  with serveral document from the area 'Technical Informations' and 'Technical 
  documents'.
* Internal will now .properties files be used für the names in dialogs and tabs.
* A bug will be fixed which concerns the sorting in DreamBook and History.

Detailed information about the current release can be found here:
* [Release v0.1.3 (2015-08-09 10:00)]

An overview about all existings releases can be found here:
* [Overview from all releases in Dream-Better-Worlds]



Templates<a name="Templates" />
---

* [Release_vX.Y.Z_yyyy-MM-dd_HH-mm.md] can be used as a template to create a 
  `Release` file in `GitHub` which contains the information what happen in xy 
  release.



[//]: # (Links)
[Afterburner]:http://afterburner.adam-bien.com/

[CodingRulesForActionsGeneral.md]:./../coding-rules/general/CodingRulesForActions.md
[CodingRulesForCSSGeneral.md]:./../coding-rules/general/CodingRulesForCSS.md
[CodingRulesForEntitiesGeneral.md]:./../coding-rules/general/CodingRulesForEntities.md
[CodingRulesForPropertiesGeneral.md]:./../coding-rules/general/CodingRulesForProperties.md

[CodingRulesForPresenterAfterburner.md]:./../coding-rules/afterburner/CodingRulesForPresenter.md
[CodingRulesForPropertiesAfterburner.md]:./../coding-rules/afterburner/CodingRulesForProperties.md
[CodingRulesForViewAfterburner.md]:./../coding-rules/afterburner/CodingRulesForView.md

[GitHub]:https://github.com/
[HowToCloneOtherBranchesThenMasterWithNetBeans.md]:./../howto/HowToCloneOtherBranchesThenMasterWithNetBeans.md
[HowToMergeBranchesInNetBeans.md]:./../howto/HowToMergeBranchesInNetBeans.md
[HowToRelease.md]:./../howto/HowToRelease.md
[NetBeans IDE]:https://netbeans.org/
[Overview from all releases in Dream-Better-Worlds]:https://github.com/Naoghuman/Dream-Better-Worlds/releases
[Release v0.1.3 (2015-08-09 10:00)]:https://github.com/Naoghuman/Dream-Better-Worlds/releases/tag/v0.1.3
[Release_vX.Y.Z_yyyy-MM-dd_HH-mm.md]:./../release/Release_vX.Y.Z_yyyy-MM-dd_HH-mm.md
