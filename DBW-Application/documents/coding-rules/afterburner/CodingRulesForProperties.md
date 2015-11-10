Coding Rules for Properties (Afterburner)
===

The file **[CLASSNAME=PACKAGENAME].properties** will if generated automatically
injected into the initialize method in the particular class `[CLASSNAME=PACKAGENAME]Presenter`. 


I have wrote a [NetBeans IDE] plugin which will create this file automatically.



Content
---
* [Injection from the .properties file](#Injection)
* [Access to the properties](#Access)
* [Exception from the property handling](Exception)
* [NetBeansIDE-AfterburnerFX-Plugin](#Plugin)



[//]: # (Content)
Injection from the .properties file<a name="Injection" />
---

```java
public class DesktopPresenter {
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DesktopPresenter"); // NOI18N

        this.resources = resources;
    }
}
```


Access to the properties<a name="Access" />
---

Every access to a property will be defined as a private constant in the class
[PACKAGE-NAME]Presenter. The constant have the format:
* 'KEY__'[name]

Format for the key in a property file:
* 'key.'[name]'='[value]

Example for a key/value pair:
* key.file.dream=Dream

```java
public class DesktopPresenter {
    private static final String KEY__FILE__DREAM = "key.file.dream"; // NOI18N

    private void initializeToolBar() {
        LoggerFacade.INSTANCE.getLogger().info(this.getClass(), "Initialize ToolBar"); // NOI18N
        
        tbDesktop.getItems().add(this.createToolBarButton(resources.getString(KEY__FILE__DREAM), ACTION__CREATE_NEW_DREAM));
    }
}
```


Exception from the property handling<a name="Exception" />
---

The definition from global properties will be described in the file
[Coding Rules for Properties (General)].



NetBeansIDE-AfterburnerFX-Plugin<a name="Plugin" />
---
* The `NetBeansIDE-AfterburnerFX-Plugin` is a [NetBeans IDE] plugin which supports
  the file generation in **convention** with the library [afterburner.fx] in a 
  [JavaFX] project.
* More information about the plugin can be found on the [project page] in GitHub.



[//]: # (Links)
[afterburner.fx]:https://github.com/AdamBien/afterburner.fx/
[Coding Rules for Properties (General)]:https://github.com/Naoghuman/Dream-Better-Worlds/blob/master/DBW-Application/documents/coding-rules/general/CodingRulesForProperties.md
[JavaFX]:http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
[NetBeans IDE]:https://netbeans.org/
[project page]:https://github.com/Naoghuman/NetBeansIDE-AfterburnerFX-Plugin


