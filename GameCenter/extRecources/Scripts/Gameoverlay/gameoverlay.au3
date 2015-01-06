#include <GDIPlus.au3>

Global $iGUIWidth = @DesktopWidth
Global $iGUIHeight = @DesktopHeight
$BGWidth = 1000;
$BGHeight = 800;
$TopLeftBGx = $iGUIWidth / 2 - $BGWidth / 2
$TopLeftBGy = $iGUIHeight / 2 - $BGHeight / 2

$values = StringSplit("HochHochHoch,Runter,Links,Rechts,1Unten,1Oben,2Unten,2Oben,3Unten,3Oben",',')
;$values = StringSplit(",,,,1Unten,1Oben,2Unten,2Oben,3Unten,3Oben",',')

Global $vUser32DLL = DllOpen("User32.dll")
$tSize = DllStructCreate($tagSIZE)
$pSize = DllStructGetPtr($tSize)
DllStructSetData($tSize, "X", $iGUIWidth)
DllStructSetData($tSize, "Y", $iGUIHeight)
$tSource = DllStructCreate($tagPOINT)
$pSource = DllStructGetPtr($tSource)
$tBlend = DllStructCreate($tagBLENDFUNCTION)
$pBlend = DllStructGetPtr($tBlend)
DllStructSetData($tBlend, "Alpha", 255)
DllStructSetData($tBlend, "Format", 1)

_GDIPlus_Startup()

$hDummy = GUICreate("")
$hWnd = GUICreate("", $iGUIWidth, $iGUIHeight, 0, 0, BitOR(0x80000000, 0x08000000), BitOR(0x00080000, 0x00000008), $hDummy)
GUISetState()
$hDC_Window = _WinAPI_GetDC($hWnd)
$hDC_Buffer = _WinAPI_CreateCompatibleDC($hDC_Window)
$hBitmap_Buffer = _WinAPI_CreateCompatibleBitmap($hDC_Window, $iGUIWidth, $iGUIHeight)
_WinAPI_SelectObject($hDC_Buffer, $hBitmap_Buffer)

$hGraphics = _GDIPlus_GraphicsCreateFromHDC($hDC_Buffer)
_GDIPlus_GraphicsSetSmoothingMode($hGraphics, 2)


; BEISPIEL:
$hBrush = _GDIPlus_BrushCreateSolid(0xFFFF0000)
$hBrushBackground = _GDIPlus_BrushCreateSolid(0xFF1BFA02)
$hFormat = _GDIPlus_StringFormatCreate()
$hFamily = _GDIPlus_FontFamilyCreate("Arial")
$hFont = _GDIPlus_FontCreate($hFamily, 10)
$tLayout = _GDIPlus_RectFCreate(0, 0, 0, 0)
$buttonRed = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/graphics/ButtonRed.png")
$buttonGreen = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/graphics/ButtonGreen.png")
$buttonYellow = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/graphics/ButtonYellow.png")
$buttonInactive = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/graphics/ButtonInactive.png")
$joystick = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/graphics/Joystick.png")
$joystickInactive = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/graphics/JoystickInactive.png")
$arrow = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/graphics/Arrow.png")

$aInfo = _GDIPlus_GraphicsMeasureString($hGraphics, @HOUR & ":" & @MIN & ":" & @SEC, $hFont, $tLayout, $hFormat)
;While Sleep(20)
	_Erase() ; zum cleanen, da sonst das alte nur übermalt wird, kannst aus lust ja mal weglassen, dan siehst dus
	;_GDIPlus_GraphicsDrawStringEx($hGraphics, @HOUR & ":" & @MIN & ":" & @SEC, $hFont, $aInfo[0], $hFormat, $hBrush)
	; hier "malst" du dan alles was du haben willst

	;_GDIPlus_GraphicsFillRect($hGraphics,100,100,200,200,$hBrush)
	;_GDIPlus_GraphicsFillRect($hGraphics,300,300,200,200,$hBrush)
	 ;Local $hPen = _GDIPlus_PenCreate(0xFFABCDEF, 4) ;color format AARRGGBB (hex)


	;_GDIPlus_GraphicsDrawEllipse($hGraphics, 130, 100, 200, 200, $hPen)
	_GDIPlus_GraphicsFillRect($hGraphics,$TopLeftBGx,$TopLeftBGy,$BGWidth,$BGHeight,$hBrushBackground);

	;_GDIPlus_GraphicsFillEllipse($hGraphics, 200, 200, 200, 200, $hBrush)
	;_GDIPlus_GraphicsFillEllipse($hGraphics, 500, 500, 200, 200, $hBrush)
	;_GDIPlus_GraphicsDrawStringEx($hGraphics, @HOUR & ":" & @MIN & ":" & @SEC, $hFont, $aInfo[0], $hFormat, $hBrush)
	;Top Button Red
	;_GDIPlus_DrawImagePoints($hGraphics,$buttonRed,100,100,300,100,100,300)
	;Bottom Button Red
	;_GDIPlus_DrawImagePoints($hGraphics,$buttonRed,100,100,300,100,100,300)





	#Region JOYSTICK

	$joyscalingFactor = 0.8
	$joysizeWidth = 314 * $joyscalingFactor
	$joysizeHeight = 400 * $joyscalingFactor
	$joystartX = $TopLeftBGx + 100;
	;$startY = $TopLeftBGy + 200;
	$joystartY = $iGUIHeight / 2  - $joysizeHeight / 2;

	$joyStickToDisplay = Null
	;$values[0] != ""
	If StringLen($values[1]) > 0 Or StringLen($values[2]) > 0 Or StringLen($values[3]) > 0 Or StringLen($values[4]) > 0  Then
		_GDIPlus_DrawImagePoints($hGraphics,$joystick,$joystartX,$joystartY ,$joystartX + $joysizeWidth,$joystartY,$joystartX,$joystartY + $joysizeHeight)

		$sizeWidth = 124 * $joyscalingFactor
		$sizeHeight = 110 * $joyscalingFactor

		#Region UPARROW
		If StringLen($values[1]) > 0 Then

			$startX = $joystartX + $joysizeWidth/2 - $sizeWidth / 2 - 5;
			$startY = $joyStartY - $sizeHeight + 15;

			$arrowUp = $arrow
			_GDIPlus_ImageRotateFlip($arrowUp, 1)
			_GDIPlus_DrawImagePoints($hGraphics,$arrowUp,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
			drawText($values[1], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)


		EndIf
		#EndRegion UPARROW



	Else
		_GDIPlus_DrawImagePoints($hGraphics,$joystickInactive,$joystartX,$joystartY ,$joystartX + $joysizeWidth,$joystartY,$joystartX,$joystartY + $joysizeHeight)

	EndIf



	#EndRegion JOYSTICK







	_WinAPI_UpdateLayeredWindow($hWnd, $hDC_Window, 0, $pSize, $hDC_Buffer, $pSource, 0, $pBlend, 2) ; immer ganz zum schluss, updatet das dann
;WEnd

while Sleep(20)
	WEnd

Func drawText($input, $x, $y)
	$textSize = _GDIPlus_GraphicsMeasureString($hGraphics, $input, $hFont, $tLayout, $hFormat)
	$width = DllStructGetData($textSize[0],3)
	_GDIPlus_GraphicsDrawString($hGraphics, $input, $x - $width / 2, $y)
EndFunc


Func _OnTop()
	WinSetOnTop($hWnd, "", 1)
EndFunc ;==>_OnTop

Func _Erase()
	_GDIPlus_GraphicsClear($hGraphics, 0x00000000)
EndFunc ;==>_Erase