Coding Rules for Actions (General)
===



Content
---

* [Naming convention for the action methods](#NamingConvention)
* [Register an action with the library Lib-Actions](#RegisterAnAction)
* [Define actionkeys](#DefineActionKeys)
* [Log events in action methods](#LogEventsInActions)



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

**Important:**

All actions have there equivalent in the registerOnActionXy methods (see also next section).



Register an action with the library [Lib-Action]<a name="RegisterAnAction" />
---

* Lib-Action is a library for `easy` storing and accessing actions (EventHandler<ActionEvent>) in a [JavaFX] & [Maven] desktop application.
* For more informatios see [Lib-Action].


Example
```java
private void registerOnActionCreateNewDream() {

    ActionFacade.getDefault().register(
            ACTION__CREATE_NEW_DREAM,
            (ActionEvent ae) -> {
                this.show();
            });
}
```



Define actionkeys<a name="DefineActionKeys" />
---

Actionkeys are defined as String constants in the interface `IActionConfiguration` in the module `DBW-Core-Configuration-Api`.


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

* Every from the following methods have a logger message in DEBUG mode.
* For onActionXy() the message format is the same like the method name.
* For registerActions() is the message format: "Register actions in Xy"
* For registerOnDynamicActionXy is  the message format: "Register on dynamic action "


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
[Lib-Actions]:https://github.com/Naoghuman/lib-action
[Maven]:http://maven.apache.org/


