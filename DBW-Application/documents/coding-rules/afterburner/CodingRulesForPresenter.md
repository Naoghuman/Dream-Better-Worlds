Coding Rules for Presenter (Afterburner)
===


The class **[CLASSNAME=PACKAGENAME]Presenter** implements Initializable. The 
class is binded to the **[CLASSNAME=PACKAGENAME].fxml** file. That means all 
defined variables in the .fxml file with id should be injected in the presenter
with the annotation **@FXML**.



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
public class DreamPresenter implements Initializable {

    @FXML private Button bDelete;
    @FXML private Button bSave;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize DreamFilePresenter"); // NOI18N
        
        this.resources = resources;
        
        assert (bDelete != null)       : "fx:id=\"bDelete\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (bSave != null)         : "fx:id=\"bSave\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
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


