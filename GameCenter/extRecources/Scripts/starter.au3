If $CmdLine[0] > 0 Then
	Run("C:/Program Files/Sandboxie/Start.exe " & $CmdLine[2] & " " & $CmdLine[1])
	Run("exitListener.exe")
	while 1
		Sleep(1)
	WEnd
EndIf

