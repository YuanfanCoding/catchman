; 脚本由 Inno Setup 脚本向导 生成！
; 有关创建 Inno Setup 脚本文件的详细资料请查阅帮助文档！

#define MyAppName "LazadaCatch"
#define MyAppVersion "1.0"
#define MyAppPublisher "yuanfan"
#define MyAppURL "http://ylfcoding.cn"
#define MyAppExeName "Lazada数据收集助手.exe"

[Setup]
; 注: AppId的值为单独标识该应用程序。
; 不要为其他安装程序使用相同的AppId值。
; (生成新的GUID，点击 工具|在IDE中生成GUID。)
AppId={{B4EC7420-B22F-49D2-A3C7-2AA6847C688F}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DisableProgramGroupPage=yes
OutputDir=E:\打包jdk版本\64位\新建文件夹
OutputBaseFilename=Lazada数据收集助手V1.0
SetupIconFile=C:\Users\Administrator\Desktop\lazada项目\lazada.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "chinesesimp"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags:checkablealone; OnlyBelowVersion: 0,8.1

[Files]
Source: "E:\打包jdk版本\64位\Lazada数据收集助手.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "E:\打包jdk版本\64位\jre\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\Administrator\Desktop\lazada项目\lazada.ico"; DestDir: "{app}"; Flags: ignoreversion
; 注意: 不要在任何共享系统文件上使用“Flags: ignoreversion”

[Icons]
Name: "{commonprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon;IconFilename: "{app}\lazada.ico"

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

