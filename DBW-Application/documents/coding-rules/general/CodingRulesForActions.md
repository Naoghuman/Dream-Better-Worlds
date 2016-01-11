Coding Rules for Actions (General)
===



Content
---

* [Library Lib-Action](#LibraryLibAction)
* [Naming convention for the action methods](#NamingConvention)
* [Register an action](#RegisterAnAction)
* [Define an action key](#DefineActionKey)
* [Log events in action methods](#LogEventsInActions)



Library Lib-Action<a name="LibraryLibAction" />
---

* [Lib-Action] is a library for `easy` storing and accessing actions (EventHandler<ActionEvent>) 
  in a [JavaFX] & [Maven] desktop application.
* For more informatios see the ReadMe in the GitHub project [Lib-Action].



[//]: # (Content)
Naming convention for the action methods<a name="NamingConvention" />
---

* onActionXy()
* onActionAddXy()
* onActionClose()
* onActionCreate()
* onActionEdit()
* onActionDelete()
* onActionJobXy()
* onActionOpenFromXy()
* onActionSave()
* onActionSearchInXy()
* onActionShowXy()
* onActionRefreshXy()

Every `onActionXy()` method have there equivalent in the `registerOnActionXy()` 
method (see also next section).


Register an action<a name="RegisterAnAction" />
---

Example
```java
private void registerOnActionCreateNewDream() {

    ActionFacade.INSTANCE.register(
            ACTION__CREATE_NEW_DREAM,
            (ActionEvent ae) -> {
                this.show();
            });
}
```

With the interface `IRegisterActions` the handling to register all actions can 
be simplified:

Example
```java
public final class DreamProvider implements ..., IRegisterActions {

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in DreamProvider"); // NOI18N
        
        this.registerOnActionCreateNewDream();
        this.registerOnActionCreateNewFastDream();
        this.registerOnActionOpenDreamFromNavigation();
    }
}
```

Example
```java
public class FileProvider implements ..., IRegisterActions {

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in FileProvider"); // NOI18N
        
        this.registerActionRemoveFileFromEditor();
        this.registerActionSaveAllChangedFiles();
        
        DreamProvider.getDefault().registerActions();
        DreammapProvider.getDefault().registerActions();
        HelpProvider.getDefault().registerActions();
        ReflectionProvider.getDefault().registerActions();
        TipOfTheNightProvider.getDefault().registerActions();
    }
}
```



Define an action key<a name="DefineActionKey" />
---

Action keys are defined as `String` constants in the interface `IActionConfiguration` 
in the module `DBW-Core-Configuration-Api`.


Example
```java
public interface IActionConfiguration {
    
    public static final String ACTION__CREATE_NEW_DREAM = "ACTION__CREATE_NEW_DREAM"; // NOI18N
    public static final String ACTION__CREATE_NEW_FAST_DREAM = "ACTION__CREATE_NEW_FAST_DREAM"; // NOI18N
    public static final String ACTION__CREATE_NEW_FILE__REFLECTION = "ACTION__CREATE_NEW_FILE__REFLECTION"; // NOI18N

    public static final String ACTION__EDIT_FILE__REFLECTION = "ACTION__EDIT_FILE__REFLECTION"; // NOI18N
    ...
}
```



Log events in action methods<a name="LogEventsInActions" />
---

Every from the following methods have a logger message in `DEBUG` mode.
* For `onActionXy()` the message format is the same like the method name.
* For `registerActions()` the message format is: "Register actions in Xy"
* For `registerOnDynamicActionXy()` the message format is: "Register on dynamic action "


Example
```java
public void onActionSearchInDreams() {
    LoggerFacade.INSTANCE.debug(this.getClass(), "On action search in Dreams"); // NOI18N
}

@Override
public void registerActions() {
    LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in VotingProvider"); // NOI18N
}

private String registerOnDynamicActionDeleteComment() {
    LoggerFacade.INSTANCE.debug(this.getClass(), "Register on dynamic action delete Comment"); // NOI18N
}
```



[//]: # (Links)
[JavaFX]:http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
[Lib-Action]:https://github.com/Naoghuman/lib-action
[Maven]:http://maven.apache.org/


