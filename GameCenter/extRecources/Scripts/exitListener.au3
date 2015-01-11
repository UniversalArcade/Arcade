#include <MsgBoxConstants.au3>

$waitForExe = "SandboxieDcomLaunch.exe"

$sucessSandboxie = -1
$sucessStarter = -1

Func focusArcadeWindow()
	$whnd = WinActivate("Newschool Arcade")
EndFunc




ProcessWait($waitForExe)

while ProcessExists("starter.exe") And ProcessExists($waitForExe)
	Sleep(50)
WEnd

If ProcessExists($waitForExe) Then
	$successSandboxie = Run("C:/Program Files/Sandboxie/Start.exe " & "/terminate_all")
EndIf

If ProcessExists("starter.exe") Then
	$sucessStarter = ProcessClose("starter.exe")
EndIf

focusArcadeWindow()

If $sucessSandboxie == 0 Or $sucessStarter == 0 Then
	Sleep(1000)

	If $sucessSandboxie == 0 Then
		While ProcessExists($waitForExe)
			Run("C:/Program Files/Sandboxie/Start.exe " & "/terminate_all")
		WEnd
	EndIf

	If $sucessStarter == 0 Then
		While ProcessExists("starter.exe")
			ProcessClose("starter.exe")
		WEnd
	EndIf
	focusArcadeWindow()
EndIf





