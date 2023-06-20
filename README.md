# ProgettoSoftware2023

## Istruzione per l'esecuzione del pacchetto "Client.jar" "Server.jar" dal prompt

### Client:
From root E:-> ***cd E:\ingegneria_software_progetto_finale\ProgettoSoftware2023\out\artifacts\Client_jar***

path del pacchetto jar

poi per esecuzione:

***java --module-path "E:\javaFX\javafx-sdk-19.0.2.1\lib" --add-modules javafx.controls,javafx.fxml -jar Client.jar***

### Server:

From root E:-> ***cd E:\ingegneria_software_progetto_finale\ProgettoSoftware2023\out\artifacts\Server_jar***

path del pacchetto jar

poi per esecuzione:

***java -jar Server.jar***

## Eventuali problemi

* Unsupported View configuration: classes were loaded from 'unnamed module
  * chiamata di una classe View extends Application da un thread che non è specificato per View 


* Graphics Device initialization failed for :  d3d, sw
  Error initializing QuantumRenderer: no suitable pipeline found
  java.lang.RuntimeException: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
  at com.sun.javafx.tk.quantum.QuantumRenderer.getInstance(QuantumRenderer.java:283)
  at com.sun.javafx.tk.quantum.QuantumToolkit.init(QuantumToolkit.java:253)
  at com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:268)
  at com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:291)
  at com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:163)
  at com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:659)
  at com.sun.javafx.application.LauncherImpl.launchApplication1(LauncherImpl.java:679)
  at com.sun.javafx.application.LauncherImpl.lambda$launchApplication$2(LauncherImpl.java:196)
  at java.base/java.lang.Thread.run(Thread.java:833)
  Caused by: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
  at com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.init(QuantumRenderer.java:95)
  at com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.run(QuantumRenderer.java:125)
  ... 1 more
  Exception in thread "main" java.lang.RuntimeException: No toolkit found
  at com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:280)
  at com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:291)
  at com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:163)
  at com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:659)
  at com.sun.javafx.application.LauncherImpl.launchApplication1(LauncherImpl.java:679)
  at com.sun.javafx.application.LauncherImpl.lambda$launchApplication$2(LauncherImpl.java:196)
  at java.base/java.lang.Thread.run(Thread.java:833)
  * usare la seguente istruzione per eseguire il pacchetto jar (Client.jar) includendo le dipendenze di View:
  
    * java --module-path "C:\Program Files\View\javafx-sdk-VERSION\lib" --add-modules javafx.controls,javafx.fxml -jar Client.jar
  ## Istruzione di base
  Per accedere al disco E da disco C-> "E:" 