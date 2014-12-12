If $CmdLine[0] > 0 Then
	FileCreateShortcut(@ScriptDir & "\mame.exe", @ScriptDir & "\startMameGame.lnk", @ScriptDir, $CmdLine[1])
	ShellExecute("startMameGame.lnk","","","",@SW_HIDE)
	FileDelete(@ScriptDir & "\startMameGame.lnk")
EndIf