# Auto-generated by EclipseNSIS Script Wizard
# 31-oct-2006 8:43:47

Name Tico
# Defines
!define REGKEY "SOFTWARE\$(^Name)"

!define VERSION "e1.0"
!define TICO_HOME "C:\Tico"
!define COMPANY "Universidad de Zaragoza - CPEE Alborada"
!define URL "www.proyectotico.es"

# MUI defines
!define MUI_WELCOMEFINISHPAGE_BITMAP tico-main-install.bmp
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\win-install.ico"
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_STARTMENUPAGE_REGISTRY_ROOT HKLM
!define MUI_STARTMENUPAGE_NODISABLE
!define MUI_STARTMENUPAGE_REGISTRY_KEY Software\Tico
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME StartMenuGroup
!define MUI_STARTMENUPAGE_DEFAULT_FOLDER Tico
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\win-uninstall.ico"
!define MUI_UNFINISHPAGE_NOAUTOCLOSE
# Remember installing language
!define MUI_LANGDLL_REGISTRY_ROOT HKLM
!define MUI_LANGDLL_REGISTRY_KEY ${REGKEY}
!define MUI_LANGDLL_REGISTRY_VALUENAME InstallerLanguage

# Included files
!include Sections.nsh
!include LogicLib.nsh
!include MUI.nsh

# Reserved Files
!insertmacro MUI_RESERVEFILE_LANGDLL

# Variables
Var StartMenuGroup

# Installer pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "$(MUILicense)"
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_STARTMENU Application $StartMenuGroup
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

# Installer languages
!insertmacro MUI_LANGUAGE English
!insertmacro MUI_LANGUAGE Spanish
#!insertmacro MUI_LANGUAGE German
!insertmacro MUI_LANGUAGE French

LicenseLangString MUILicense ${LANG_ENGLISH} en\LICENSE.txt
LicenseLangString MUILicense ${LANG_SPANISH} es\LICENSE.txt
#LicenseLangString MUILicense ${LANG_GERMAN} de\LICENSE.txt
LicenseLangString MUILicense ${LANG_FRENCH} fr\LICENSE.txt

# Installer attributes
OutFile ../../dist/tico-bin-w32-${VERSION}.exe
#InstallDir $PROGRAMFILES\Tico
InstallDir ${TICO_HOME}
CRCCheck on
XPStyle on
ShowInstDetails show
VIProductVersion 1.0.0.0
#VIAddVersionKey /LANG=${LANG_ENGLISH} ProductName Tico
#VIAddVersionKey ProductVersion "${VERSION}"
#VIAddVersionKey /LANG=${LANG_ENGLISH} CompanyName "${COMPANY}"
#VIAddVersionKey /LANG=${LANG_ENGLISH} FileVersion ""
#VIAddVersionKey /LANG=${LANG_ENGLISH} FileDescription ""
#VIAddVersionKey /LANG=${LANG_ENGLISH} LegalCopyright ""
VIAddVersionKey ProductName Tico
VIAddVersionKey ProductVersion "${VERSION}"
VIAddVersionKey CompanyName "${COMPANY}"
VIAddVersionKey FileVersion ""
VIAddVersionKey FileDescription ""
VIAddVersionKey LegalCopyright "Universidad de Zaragoza - CPEE Alborada"
InstallDirRegKey HKLM "${REGKEY}" Path
ShowUninstDetails show

# Installer sections
Section -Main SEC0000
    SetOutPath $INSTDIR
    SetOverwrite on
    File ..\tico.jar
    File ..\executables\tico-editor.exe
    File ..\executables\tico-interpreter.exe
    File ..\..\README.txt
    File ..\..\CHANGELOG.txt
    ${Switch} $LANGUAGE
        ${Case} ${LANG_SPANISH}
            File es\LICENSE.txt
            ${Break}
        ${Case} ${LANG_ENGLISH}
            File en\LICENSE.txt
            ${Break}
        #${Case} ${LANG_GERMAN}
            #File de\LICENSE.txt
            #${Break}
        ${Case} ${LANG_FRENCH}
            File fr\LICENSE.txt
            ${Break}
    ${EndSwitch}
    SetOutPath $INSTDIR\libs
    File /r /x .svn ..\..\libs\*
    SetOutPath $INSTDIR\lang
    File /r /x .svn ..\..\lang\*
    SetOutPath $INSTDIR\environment
    File /r /x .svn ..\..\environment\*
    SetOutPath $INSTDIR\controller-icons
    File /r /x .svn ..\..\controller-icons\*
    SetOutPath $INSTDIR\conf
    File /r /x tico.conf /x .svn ..\..\conf\*
    ${Switch} $LANGUAGE
        ${Case} ${LANG_SPANISH}
            File es\tico.conf
            ${Break}
        ${Case} ${LANG_ENGLISH}
            File en\tico.conf
            ${Break}
        #${Case} ${LANG_GERMAN}
           # File de\tico.conf
           # ${Break}
        ${Case} ${LANG_FRENCH}
            File fr\tico.conf
            ${Break}
    ${EndSwitch}
    SetOutPath $INSTDIR\doc
    File ..\..\doc\manual.pdf
    SetOutPath $INSTDIR
    CreateShortcut "$DESKTOP\$(^EditorExe).lnk" $INSTDIR\tico-editor.exe
    CreateShortcut "$DESKTOP\$(^InterpreterExe).lnk" $INSTDIR\tico-interpreter.exe
    WriteRegStr HKLM "${REGKEY}\Components" Main 1
SectionEnd

Section -post SEC0001
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    WriteUninstaller $INSTDIR\uninstall.exe
    !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    SetOutPath $INSTDIR
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\$(^UninstallLink).lnk" $INSTDIR\uninstall.exe
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\$(^EditorExe).lnk" $INSTDIR\tico-editor.exe
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\$(^InterpreterExe).lnk" $INSTDIR\tico-interpreter.exe
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\$(^UserManual).lnk" $INSTDIR\doc\manual.pdf
    !insertmacro MUI_STARTMENU_WRITE_END
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayVersion "${VERSION}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" Publisher "${COMPANY}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\uninstall.exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\uninstall.exe
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
SectionEnd

# Macro for selecting uninstaller sections
!macro SELECT_UNSECTION SECTION_NAME UNSECTION_ID
    Push $R0
    ReadRegStr $R0 HKLM "${REGKEY}\Components" "${SECTION_NAME}"
    StrCmp $R0 1 0 next${UNSECTION_ID}
    !insertmacro SelectSection "${UNSECTION_ID}"
    GoTo done${UNSECTION_ID}
next${UNSECTION_ID}:
    !insertmacro UnselectSection "${UNSECTION_ID}"
done${UNSECTION_ID}:
    Pop $R0
!macroend

# Uninstaller sections
Section /o un.Main UNSEC0000
    Delete /REBOOTOK "$DESKTOP\$(^EditorExe).lnk"
    Delete /REBOOTOK "$DESKTOP\$(^InterpreterExe).lnk"
    RmDir /r /REBOOTOK $INSTDIR\libs
    RmDir /r /REBOOTOK $INSTDIR\current
    RmDir /r /REBOOTOK $INSTDIR\lang
    RmDir /r /REBOOTOK $INSTDIR\environment
    #RmDir /r /REBOOTOK $INSTDIR\images
    RmDir /r /REBOOTOK $INSTDIR\controller-icons
    RmDir /r /REBOOTOK $INSTDIR\conf
    RmDir /r /REBOOTOK $INSTDIR\doc
    Delete /REBOOTOK $INSTDIR\LICENSE.txt
    Delete /REBOOTOK $INSTDIR\README.txt
    Delete /REBOOTOK $INSTDIR\CHANGELOG.txt
    Delete /REBOOTOK $INSTDIR\tico-editor.exe
    Delete /REBOOTOK $INSTDIR\tico-interpreter.exe
    Delete /REBOOTOK $INSTDIR\tico.jar
    DeleteRegValue HKLM "${REGKEY}\Components" Main
SectionEnd

Section un.post UNSEC0001
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^UninstallLink).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^EditorExe).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^InterpreterExe).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^UserManual).lnk"
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    DeleteRegValue HKLM "${REGKEY}" StartMenuGroup
    DeleteRegValue HKLM "${REGKEY}" Path
    DeleteRegKey /IfEmpty HKLM "${REGKEY}\Components"
    DeleteRegKey /IfEmpty HKLM "${REGKEY}"
    RmDir /REBOOTOK $SMPROGRAMS\$StartMenuGroup
    RmDir /REBOOTOK $INSTDIR
SectionEnd

# Installer functions
Function .onInit
    InitPluginsDir
    !insertmacro MUI_LANGDLL_DISPLAY
FunctionEnd

# Uninstaller functions
Function un.onInit
    ReadRegStr $INSTDIR HKLM "${REGKEY}" Path
    ReadRegStr $StartMenuGroup HKLM "${REGKEY}" StartMenuGroup
    !insertmacro MUI_UNGETLANGUAGE
    !insertmacro SELECT_UNSECTION Main ${UNSEC0000}
FunctionEnd


# Installer Language Strings
LangString ^UninstallLink ${LANG_ENGLISH} "Uninstall $(^Name)"
LangString ^UninstallLink ${LANG_SPANISH} "Desinstalar $(^Name)"
#LangString ^UninstallLink ${LANG_GERMAN} "Deinstallieren Sie $(^Name)"
LangString ^UninstallLink ${LANG_FRENCH} "Désinstaller $(^Name)"

LangString ^EditorExe ${LANG_ENGLISH} "Tico Editor"
LangString ^EditorExe ${LANG_SPANISH} "Editor Tico"
#LangString ^EditorExe ${LANG_GERMAN} "Ticos Verleger"
LangString ^EditorExe ${LANG_FRENCH} "Editeur Tico"

LangString ^InterpreterExe ${LANG_ENGLISH} "Tico Interpreter"
LangString ^InterpreterExe ${LANG_SPANISH} "Interprete Tico"
#LangString ^InterpreterExe ${LANG_GERMAN} "Ticos Interpret"
LangString ^InterpreterExe ${LANG_FRENCH} "Interprète Tico"

LangString ^UserManual ${LANG_ENGLISH} "User manual"
LangString ^UserManual ${LANG_SPANISH} "Manual de usuario"
#LangString ^UserManual ${LANG_GERMAN} "Benutzerhandbuch"
LangString ^UserManual ${LANG_FRENCH} "Mode d'emploi"


