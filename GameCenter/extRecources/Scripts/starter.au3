If $CmdLine[0] > 0 Then
	Run("C:/Program Files/Sandboxie/Start.exe " & $CmdLine[2] & " " & $CmdLine[1])
	Run("exitListener.exe")
	while 1
	WEnd
Else
    MsgBox(64, "Fehler", "Es wurde kein Spiel als Parameter übergeben")
EndIf

