@set keystore-file=..\..\..\setupfiles\keystore\keystore.jks
@set keystore-pwd=password
@set cert-alias=eaf
@set input-jar=nic-applet-1.1-SNAPSHOT.jar
@set output-jar=nic-applet.jar
rem jarsigner %input-jar% %cert-alias% -keystore %keystore-file% -storepass %keystore-pwd% -signedjar %output-jar% -tsa http://timestamp.digicert.com
jarsigner %input-jar% %cert-alias% -keystore %keystore-file% -storepass %keystore-pwd% -signedjar %output-jar% -verbose -tsa http://timestamp.digicert.com -tsacert %cert-alias% -sigalg SHA256withRSA -digestalg SHA1
pause