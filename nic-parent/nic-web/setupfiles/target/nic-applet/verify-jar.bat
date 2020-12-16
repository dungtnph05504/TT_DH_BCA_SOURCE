@set keystore-file=..\..\..\setupfiles\keystore\keystore.jks
@set keystore-pwd=password
@set cert-alias=eaf
@set input-jar=nic-applet-1.1-SNAPSHOT.jar
@set output-jar=nic-applet.jar
rem jarsigner -verify -verbose -certs c:\path\to\your\file.jar
jarsigner -verify -verbose -certs %output-jar% -keystore %keystore-file% -storepass %keystore-pwd% -strict
pause