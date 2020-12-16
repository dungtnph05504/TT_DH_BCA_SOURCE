@set keystore-file=..\..\..\setupfiles\keystore\keystore.jks
@set keystore-pwd=password
@set cert-alias=eaf
@set input-jar=spid6-unsigned.jar
@set output-jar=spid6.jar
rem jarsigner spid6-unsigned.jar %cert-alias% -keystore %keystore-file% -storepass %keystore-pwd% -signedjar spid6.jar -tsa http://timestamp.digicert.com
jarsigner %input-jar% %cert-alias% -keystore %keystore-file% -storepass %keystore-pwd% -signedjar %output-jar%  -verbose -tsa http://timestamp.digicert.com -tsacert %cert-alias% -sigalg SHA256withRSA -digestalg SHA1
pause