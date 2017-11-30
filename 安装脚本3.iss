; �ű��� Inno Setup �ű��� ���ɣ�
; �йش��� Inno Setup �ű��ļ�����ϸ��������İ����ĵ���

#define MyAppName "LazadaCatch"
#define MyAppVersion "1.0"
#define MyAppPublisher "yuanfan"
#define MyAppURL "http://ylfcoding.cn"
#define MyAppExeName "Lazada�����ռ�����.exe"

[Setup]
; ע: AppId��ֵΪ������ʶ��Ӧ�ó���
; ��ҪΪ������װ����ʹ����ͬ��AppIdֵ��
; (�����µ�GUID����� ����|��IDE������GUID��)
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
OutputDir=E:\���jdk�汾\64λ\�½��ļ���
OutputBaseFilename=Lazada�����ռ�����V1.0
SetupIconFile=C:\Users\Administrator\Desktop\lazada��Ŀ\lazada.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "chinesesimp"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags:checkablealone; OnlyBelowVersion: 0,8.1

[Files]
Source: "E:\���jdk�汾\64λ\Lazada�����ռ�����.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "E:\���jdk�汾\64λ\jre\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\Administrator\Desktop\lazada��Ŀ\lazada.ico"; DestDir: "{app}"; Flags: ignoreversion
; ע��: ��Ҫ���κι���ϵͳ�ļ���ʹ�á�Flags: ignoreversion��

[Icons]
Name: "{commonprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon;IconFilename: "{app}\lazada.ico"

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

