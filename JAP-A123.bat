:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: SCRIPT CST8221 - Fall 2023
:: ---------------------------------------------------------------------
:: Begin of Script (A13 - F23)
:: ---------------------------------------------------------------------

CLS

:: LOCAL VARIABLES ....................................................

SET LIBDIR=lib
SET SRCDIR=src
SET MAINDIR=src/
SET BINERR=jap-javac.err
SET JARNAME=jap.jar
SET JAROUT=jap-jar.out
SET JARERR=jap-jar.err
SET DOCDIR=doc
SET DOCERR=jap-javadoc.err
SET MAINCLASSSRC=src/*.java
SET MAINCLASSBIN_A22=A22.GameOfLife
SET MAINCLASSBIN_TuringMachine=TuringMachine
SET IMAGES=images
@echo off

ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
ECHO "@                                                                   @"
ECHO "@                   #       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@                  ##       @  A L G O N Q U I N  C O L L E G E  @  @"
ECHO "@                ##  #      @    JAVA APPLICATION PROGRAMMING    @  @"
ECHO "@             ###    ##     @          F A L L - 2 0 2 3         @  @"
ECHO "@          ###    ##        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@        ###    ##                                                  @"
ECHO "@        ##    ###                 ###                              @"
ECHO "@         ##    ###                ###                              @"
ECHO "@           ##    ##               ###   #####  ##     ##  #####    @"
ECHO "@         (     (      ((((()      ###       ## ###   ###      ##   @"
ECHO "@     ((((     ((((((((     ()     ###   ######  ###  ##   ######   @"
ECHO "@        ((                ()      ###  ##   ##   ## ##   ##   ##   @"
ECHO "@         ((((((((((( ((()         ###   ######    ###     ######   @"
ECHO "@         ((         ((           ###                               @"
ECHO "@          (((((((((((                                              @"
ECHO "@   (((                      ((        /-----------------------\    @"
ECHO "@    ((((((((((((((((((((() ))         |  COMPUTATIONAL MODEL  |    @"
ECHO "@         ((((((((((((((((()           \-----------------------/    @"
ECHO "@                                                                   @"
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"

ECHO "[LABS SCRIPT ---------------------]"

ECHO "0. Preconfiguring ................."

mkdir "%MAINDIR%\%IMAGES%"
copy "%IMAGES%\*.png" "%MAINDIR%\%IMAGES%"

cd src
ECHO "1. Compiling Server ......................"
javac Server.java  2> %BINERR%

ECHO "2. Compiling Server Window......................"
javac ServerWindow.java  2> %BINERR%

ECHO "3. Compiling Config ......................"
javac Config.java  2> %BINERR%

ECHO "4. Creating Server Jar ..................."
jar cvfe Server.jar ServerWindow Server.class ServerWindow.class ServerWindow$1.class ServerWindow$2.class ServerWindow$3.class ServerWindow$4.class ServerWindow$5.class Worked.class  Config.class images/*.png > Server-out.txt 2> Server-err.txt


ECHO "5. Compiling Client ......................"
javac Client.java 2> %BINERR%

ECHO "6. Compiling Client Window ......................" 
javac ClientWindow.java 2> %BINERR%

ECHO "7. Compiling Turing Machine ......................"
javac -Xlint -d %MAINDIR% %SRCDIR%/TuringMachine.java %SRCDIR%/TuringMachineWindow.java 2> %BINERR%

ECHO "8. Creating Client Jar ..................."
jar cvfe Client.jar ClientWindow Client.class ClientWindow.class ClientWindow$1.class ClientWindow$2.class ClientWindow$3.class   Config.class TuringMachine.class TuringMachineWindow.class TuringMachineWindow$1.class TuringMachineWindow$2.class TuringMachineWindow$3.class   images/*.png > Client-out.txt 2> Client-err.txt
cd ..


ECHO "9. Creating the batch file to run the JAR for Server..."
echo java -jar src/Server.jar > RUN_SERVER.bat

ECHO "10. Creating the batch file to run the JAR for Client..."
echo java -jar src/Client.jar > RUN_CLIENT.bat

ECHO "7. Creating the batch file to run the JAR for Turing Machine..."
echo java -jar src/TuringMachine.jar > RUN_TURINGMACHINE.bat

ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on

pause 

::ECHO "[END OF SCRIPT -------------------]"
::ECHO "                                   "
