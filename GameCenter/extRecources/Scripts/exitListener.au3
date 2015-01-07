$waitForExe = "SandboxieDcomLaunch.exe"

ProcessWait($waitForExe)

while ProcessExists("starter.exe") And ProcessExists($waitForExe)
	Sleep(50)
WEnd

If ProcessExists($waitForExe) Then
	Run("C:/Program Files/Sandboxie/Start.exe " & "/terminate_all")
EndIf

If ProcessExists("starter.exe") Then
	ProcessClose("starter.exe")
EndIf

