  -injars      target/wols-0.0.1-SNAPSHOT-jar-with-dependencies.jar
  -outjars     target/wols-0.0.1-SNAPSHOT.jar
  -libraryjars "<java.home>/lib/rt.jar":"<java.home>/lib/jce.jar"
  -dontwarn

  -keepclasseswithmembers public class * {
      public static void main(java.lang.String[]);
  }
  
  -keep class org.apache.** { *; }
  -keep class org.slf4j.** { *; }
  -keep class org.bouncycastle.** { *; }
  
  # :"/home/user/opt/eclipse/plugins/org.junit_4.13.0.v20200204-1500.jar":"/home/user/opt/eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar":"/home/user/opt/libs/bouncycastle/bcprov-jdk18on-172.jar":"/home/user/opt/libs/jcalendar-1.4/lib/jcalendar-1.4.jar":"/home/user/opt/libs/jxmapviewer2-2.6/jxmapviewer2-2.6.jar":"/home/user/opt/libs/commons-logging-1.2/commons-logging-1.2.jar":"/home/user/opt/libs/org.json/json-20230227.jar"