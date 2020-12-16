@set keystore-file=..\..\..\setupfiles\keystore\keystore.jks
@set keystore-pwd=password
@set cert-alias=eaf
@set input-jar=spid6-unsigned.jar
@set output-jar=spid6.jar
rem jarsigner -verify -verbose -certs c:\path\to\your\file.jar
jarsigner -verify -verbose -certs %output-jar% -keystore %keystore-file% -storepass %keystore-pwd% -strict
pause