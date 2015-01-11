#include <WinAPI.au3>
#include <WindowsConstants.au3>
#include <Constants.au3>
#include <File.au3>

If $CmdLine[0] > 0 Then

	_FileWriteToLine("C:\Users\Public\Arcade\Mame\mame.ini",1, "rompath                   C:\Users\Public\Arcade\Games\" & $CmdLine[2] & "\game", 1)

	Run("C:\Users\Public\Arcade\Mame\mame.exe " & $CmdLine[1],"C:\Users\Public\Arcade\Mame\")
	Local $hWnd = WinWait("[CLASS:MAME]", "", 10)
	WinSetState($hWnd, "", @SW_MAXIMIZE)

	Local $iStyle = _WinAPI_GetWindowLong($hWnd, $GWL_STYLE)
	$iStyle = BitOR(BitXOR($iStyle, $WS_MINIMIZEBOX, $WS_MAXIMIZEBOX, $WS_CAPTION, $WS_BORDER, $WS_SIZEBOX), $WS_POPUP)
	_WinAPI_SetWindowLong($hWnd, $GWL_STYLE, $iStyle)

EndIf