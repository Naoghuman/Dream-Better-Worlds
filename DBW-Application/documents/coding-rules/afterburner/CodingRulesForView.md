Coding Rules for View (Afterburner)
===

The class **[CLASSNAME=PACKAGENAME]View** extends from FXMLView. One additional method 
will be created in the class -> `getRealPresenter()` so we musn't cast in the 
project the method getPresenter():Object.

I have wrote a [NetBeans IDE] plugin which will create this method automatically 
during the generation from the **[CLASS=PACKAGENAME]View** file.


Content
---
* [Example](#Example)
* [NetBeansIDE-AfterburnerFX-Plugin](#Plugin)



[//]: # (Content)
Example<a name="Example" />
---

```java
public class DreamBookNavigationView extends FXMLView {

    public DreamBookNavigationPresenter getRealPresenter() {
        return (DreamBookNavigationPresenter) super.getPresenter();
    }
}
```



NetBeansIDE-AfterburnerFX-Plugin<a name="Plugin" />
---
* The `NetBeansIDE-AfterburnerFX-Plugin` is a [NetBeans IDE] plugin which supports
  the file generation in **convention** with the library [afterburner.fx] in a 
  [JavaFX] project.
* More information about the plugin can be found on the [project page] in GitHub.



[//]: # (Links)
[afterburner.fx]:https://github.com/AdamBien/afterburner.fx/
[JavaFX]:http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
[NetBeans IDE]:https://netbeans.org/
[project page]:https://github.com/Naoghuman/NetBeansIDE-AfterburnerFX-Plugin


