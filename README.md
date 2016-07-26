# homecontrol_frontend_java

Note that when running the front end on raspian it is necessary to identify the necessary jzmq libraries using

java -Djava.library.path=/usr/local/lib -jar Launcher.jar

For some reason even updating the LD_LIBRARY_PATH system variable does not do this...
