Performance Check
===


Intention
===

In this document I list a `Overview` from manuell `Performance Checks`. Over the 
application `PerformanceApplication` in the module `DBW-Application` the single 
views can shown. Every action in the view will logged the needed time when executed.

With the application `TestdataApplication` (in the same module / package) a 
different count from entitites (100 - 100.000) can created.

So the intention is to show an overview from needed time per action and with 
different filled tables.



Content
===

* [Tip of the Night - Chooser](#TipOfTheNightChooser)
    * [onActionNext()](#ChooserOnActionNext)
    * [onActionRandom()](#ChooserOnActionRandom)
* [Tip of the Night - Editor](#TipOfTheNightEditor)



Tip of the Night - Chooser<a name="TipOfTheNightChooser" />
---

##### onActionNext()<a name="ChooserOnActionNext" />
2015-12-27 13:02:56,640  DEBUG ================================================================================     [PerformanceHelper]  
2015-12-27 13:02:56,640  DEBUG Execute action 1000 times.     [PerformanceHelper]  
2015-12-27 13:02:56,640  DEBUG   + 00:00:00.003 (177)     [PerformanceHelper]  
2015-12-27 13:02:56,640  DEBUG   + 00:00:00.004 (012)     [PerformanceHelper]  
2015-12-27 13:02:56,640  DEBUG   + 00:00:00.005 (052)     [PerformanceHelper]  
2015-12-27 13:02:56,640  DEBUG   + 00:00:00.006 (015)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.007 (022)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.008 (028)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.009 (017)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.010 (007)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.011 (013)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.012 (034)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.013 (012)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.014 (020)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.015 (032)     [PerformanceHelper]  
2015-12-27 13:02:56,641  DEBUG   + 00:00:00.016 (378)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.017 (104)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.018 (003)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.019 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.020 (004)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.021 (002)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.022 (004)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.023 (009)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.024 (009)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.025 (018)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.026 (009)     [PerformanceHelper]  
2015-12-27 13:02:56,642  DEBUG   + 00:00:00.027 (008)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.028 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.043 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.044 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.053 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.059 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.063 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.065 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.067 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.068 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   + 00:00:00.078 (001)     [PerformanceHelper]  
2015-12-27 13:02:56,643  DEBUG   --------------     [PerformanceHelper]  
2015-12-27 13:02:56,644  DEBUG     00:00:00.027 Average     [PerformanceHelper]  
2015-12-27 13:02:56,644  DEBUG ================================================================================     [PerformanceHelper]  


##### onActionRandom()<a name="ChooserOnActionRandom" />
2015-12-27 12:57:56,504  DEBUG ================================================================================     [PerformanceHelper]  
2015-12-27 12:57:56,506  DEBUG Execute action 1000 times.     [PerformanceHelper]  
2015-12-27 12:57:56,506  DEBUG   + 00:00:00.003 (207)     [PerformanceHelper]  
2015-12-27 12:57:56,506  DEBUG   + 00:00:00.004 (020)     [PerformanceHelper]  
2015-12-27 12:57:56,507  DEBUG   + 00:00:00.005 (029)     [PerformanceHelper]  
2015-12-27 12:57:56,508  DEBUG   + 00:00:00.006 (020)     [PerformanceHelper]  
2015-12-27 12:57:56,509  DEBUG   + 00:00:00.007 (031)     [PerformanceHelper]  
2015-12-27 12:57:56,510  DEBUG   + 00:00:00.008 (027)     [PerformanceHelper]  
2015-12-27 12:57:56,511  DEBUG   + 00:00:00.009 (022)     [PerformanceHelper]  
2015-12-27 12:57:56,511  DEBUG   + 00:00:00.010 (008)     [PerformanceHelper]  
2015-12-27 12:57:56,512  DEBUG   + 00:00:00.011 (008)     [PerformanceHelper]  
2015-12-27 12:57:56,513  DEBUG   + 00:00:00.012 (021)     [PerformanceHelper]  
2015-12-27 12:57:56,513  DEBUG   + 00:00:00.013 (019)     [PerformanceHelper]  
2015-12-27 12:57:56,514  DEBUG   + 00:00:00.014 (031)     [PerformanceHelper]  
2015-12-27 12:57:56,515  DEBUG   + 00:00:00.015 (030)     [PerformanceHelper]  
2015-12-27 12:57:56,515  DEBUG   + 00:00:00.016 (236)     [PerformanceHelper]  
2015-12-27 12:57:56,516  DEBUG   + 00:00:00.017 (160)     [PerformanceHelper]  
2015-12-27 12:57:56,516  DEBUG   + 00:00:00.018 (104)     [PerformanceHelper]  
2015-12-27 12:57:56,517  DEBUG   + 00:00:00.019 (014)     [PerformanceHelper]  
2015-12-27 12:57:56,518  DEBUG   + 00:00:00.020 (002)     [PerformanceHelper]  
2015-12-27 12:57:56,519  DEBUG   + 00:00:00.021 (002)     [PerformanceHelper]  
2015-12-27 12:57:56,519  DEBUG   + 00:00:00.024 (002)     [PerformanceHelper]  
2015-12-27 12:57:56,520  DEBUG   + 00:00:00.026 (001)     [PerformanceHelper]  
2015-12-27 12:57:56,521  DEBUG   + 00:00:00.027 (001)     [PerformanceHelper]  
2015-12-27 12:57:56,521  DEBUG   + 00:00:00.030 (003)     [PerformanceHelper]  
2015-12-27 12:57:56,522  DEBUG   + 00:00:00.034 (001)     [PerformanceHelper]  
2015-12-27 12:57:56,522  DEBUG   + 00:00:00.048 (001)     [PerformanceHelper]  
2015-12-27 12:57:56,523  DEBUG   --------------     [PerformanceHelper]  
2015-12-27 12:57:56,523  DEBUG     00:00:00.017 Average     [PerformanceHelper]  
2015-12-27 12:57:56,524  DEBUG ================================================================================     [PerformanceHelper]  



Tip of the Night - Chooser<a name="TipOfTheNightEditor" />
---



