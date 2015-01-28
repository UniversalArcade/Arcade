#include <GDIPlus.au3>

If $CmdLine[0] <= 0 Then
Exit
EndIf

Global $iGUIWidth = @DesktopWidth
Global $iGUIHeight = @DesktopHeight
$BGWidth = 1100
$BGHeight = 700; 800
$TopLeftBGx = $iGUIWidth / 2 - $BGWidth / 2
$TopLeftBGy = $iGUIHeight / 2 - $BGHeight / 2


$values = StringSplit($CmdLine[1],',')

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
$hBrushBackground = _GDIPlus_BrushCreateSolid(0xFFFFFF99)
$hBrushBackgroundFrame = _GDIPlus_BrushCreateSolid(0xFFFF9933)
$hFormat = _GDIPlus_StringFormatCreate()
$hFamily = _GDIPlus_FontFamilyCreate("Arial")
$hFont = _GDIPlus_FontCreate($hFamily, 18)
$tLayout = _GDIPlus_RectFCreate(0, 0, 0, 0)
$buttonWhite = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/ButtonWhite.png")
$buttonRed = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/ButtonRed.png")
$buttonGreen = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/ButtonGreen.png")
$buttonYellow = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/ButtonYellow.png")
$buttonInactive = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/ButtonInactive.png")
$joystick = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/Joystick.png")
$joystickInactive = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/JoystickInactive.png")
$arrowUp = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/Arrow.png")
$arrowDown = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/Arrow.png")
$arrowLeft = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/Arrow.png")
$arrowRight = _GDIPlus_BitmapCreateFromFile(@ScriptDir & "/img/Arrow.png")

$aInfo = _GDIPlus_GraphicsMeasureString($hGraphics, @HOUR & ":" & @MIN & ":" & @SEC, $hFont, $tLayout, $hFormat)

	_Erase()
	$thresh = 10;
	_GDIPlus_GraphicsFillRect($hGraphics,$TopLeftBGx-$thresh,$TopLeftBGy-$thresh,$BGWidth+$thresh*2,$BGHeight+$thresh*2,$hBrushBackgroundFrame);

	_GDIPlus_GraphicsFillRect($hGraphics,$TopLeftBGx,$TopLeftBGy,$BGWidth,$BGHeight,$hBrushBackground);

	#Region JOYSTICK

	$joyscalingFactor = 0.8
	$joysizeWidth = 163 * $joyscalingFactor
	$joysizeHeight = 267 * $joyscalingFactor
	$joystartX = $TopLeftBGx + 200;
	;$startY = $TopLeftBGy + 200;
	$joystartY = $iGUIHeight / 2  - $joysizeHeight / 2 + 80;

	$joyStickToDisplay = Null
	If StringLen($values[1]) > 0 Or StringLen($values[2]) > 0 Or StringLen($values[3]) > 0 Or StringLen($values[4]) > 0  Then
		_GDIPlus_DrawImagePoints($hGraphics,$joystick,$joystartX,$joystartY ,$joystartX + $joysizeWidth,$joystartY,$joystartX,$joystartY + $joysizeHeight)


		$sizeWidth = 124 * $joyscalingFactor
		$sizeHeight = 110 * $joyscalingFactor

		#Region UPARROW
		If StringLen($values[1]) > 0 Then
			$startX = $joystartX + $joysizeWidth/2 - $sizeWidth / 2 - 5;
			$startY = $joyStartY - $sizeHeight + 15;


			_GDIPlus_ImageRotateFlip($arrowUp, 1)
			_GDIPlus_DrawImagePoints($hGraphics,$arrowUp,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
			drawText($values[1], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
		EndIf
		#EndRegion UPARROW

		#Region DOWNARROW
		If StringLen($values[2]) > 0 Then
			$startX = $joystartX + $joysizeWidth/2 - $sizeWidth / 2;
			$startY = $joyStartY + $joysizeHeight - 15;


			_GDIPlus_ImageRotateFlip($arrowDown, 3)
			_GDIPlus_DrawImagePoints($hGraphics,$arrowDown,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
			drawText($values[2], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
		EndIf
		#EndRegion DOWNARROW

		#Region LEFTARROW
		If StringLen($values[3]) > 0 Then
			$startX = $joystartX - $joysizeWidth / 2 - $sizeWidth/2 + 10;
			$startY = $joyStartY + $joysizeHeight / 2 - $sizeHeight / 2 ;

			_GDIPlus_DrawImagePoints($hGraphics,$arrowLeft,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
			drawText($values[3], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
		EndIf
		#EndRegion LEFTARROW

		#Region RIGHTARROW
		If StringLen($values[4]) > 0 Then
			$startX = $joystartX + $joysizeWidth;
			$startY = $joyStartY + $joysizeHeight / 2 - $sizeHeight / 2 ;

			_GDIPlus_ImageRotateFlip($arrowRight, 2)
			_GDIPlus_DrawImagePoints($hGraphics,$arrowRight,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
			drawText($values[4], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
		EndIf
		#EndRegion RIGHTARROW

	Else
		_GDIPlus_DrawImagePoints($hGraphics,$joystickInactive,$joystartX,$joystartY ,$joystartX + $joysizeWidth,$joystartY,$joystartX,$joystartY + $joysizeHeight)

	EndIf
	#EndRegion JOYSTICK

	#REGION BUTTONS

	$buttonStartX = $joystartX + $joysizeWidth + 130;
	$buttonStartY = $joyStartY + $joysizeHeight/2 + 50 ;

	$sizeWidth = 260 * ($joyscalingFactor - 0.2)
	$sizeHeight = 250 * ($joyscalingFactor - 0.2)


	#Region BUTTON_1
	$startX = $buttonStartX
	$startY = $buttonStartY

	If StringLen($values[5]) > 0 Then
		_GDIPlus_DrawImagePoints($hGraphics,$buttonRed,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
		drawText($values[5], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
	Else
	    _GDIPlus_DrawImagePoints($hGraphics,$buttonInactive,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
	EndIf
	#EndRegion BUTTON_1

	#Region BUTTON_2
	;$startX = $buttonStartX
	$startY = $buttonStartY - $sizeHeight - 50

	If StringLen($values[6]) > 0 Then
		_GDIPlus_DrawImagePoints($hGraphics,$buttonRed,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
		drawText($values[6], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
	Else
	    _GDIPlus_DrawImagePoints($hGraphics,$buttonInactive,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
	EndIf
	#EndRegion BUTTON_2

	#Region BUTTON_3
	$startX = $buttonStartX + $sizeWidth + 40
	$startY = $buttonStartY - 40

	If StringLen($values[7]) > 0 Then
		_GDIPlus_DrawImagePoints($hGraphics,$buttonYellow,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
		drawText($values[7], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
	Else
	    _GDIPlus_DrawImagePoints($hGraphics,$buttonInactive,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
	EndIf
	#EndRegion BUTTON_3

	#Region BUTTON_4
	;$startX = $buttonStartX + $sizeWidth + 40
	$startY = $buttonStartY - $sizeHeight - 40 - 50

	If StringLen($values[8]) > 0 Then
		_GDIPlus_DrawImagePoints($hGraphics,$buttonYellow,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
		drawText($values[8], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
	Else
	    _GDIPlus_DrawImagePoints($hGraphics,$buttonInactive,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
	EndIf
	#EndRegion BUTTON_4

	#Region BUTTON_5
	$startX = $buttonStartX + $sizeWidth * 2 + 40 * 2
	$startY = $buttonStartY - 40 * 2

	If StringLen($values[9]) > 0 Then
		_GDIPlus_DrawImagePoints($hGraphics,$buttonGreen,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
		drawText($values[9], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
	Else
	    _GDIPlus_DrawImagePoints($hGraphics,$buttonInactive,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
	EndIf
	#EndRegion BUTTON_5

	#Region BUTTON_6
	;$startX = $buttonStartX + $sizeWidth + 40
	$startY = $buttonStartY - $sizeHeight - 40 * 2 - 50

	If StringLen($values[10]) > 0 Then
		_GDIPlus_DrawImagePoints($hGraphics,$buttonGreen,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
		drawText($values[10], $startX + $sizeWidth/2 ,$startY + $sizeHeight/2)
	Else
	    _GDIPlus_DrawImagePoints($hGraphics,$buttonInactive,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
	EndIf
	#EndRegion BUTTON_6

	#Region BUTTON_MENU
	$startX = $TopLeftBGx + 40
	$startY = $TopLeftBGy + 40

	_GDIPlus_DrawImagePoints($hGraphics,$buttonWhite,$startX,$startY ,$startX + $sizeWidth,$startY,$startX,$startY + $sizeHeight)
	_GDIPlus_GraphicsDrawString($hGraphics, "Kurz drücken: Overlay Ausblenden",  $startX + $sizeWidth, $startY + 35, "Arial", 18)
	_GDIPlus_GraphicsDrawString($hGraphics, "Lange drücken: Spiel beenden und zurück zur Spieleauswahl",  $startX + $sizeWidth, $startY + $sizeHeight - 70, "Arial", 18)

	#EndRegion BUTTON_MENU


	#EndRegion BUTTONS






	_WinAPI_UpdateLayeredWindow($hWnd, $hDC_Window, 0, $pSize, $hDC_Buffer, $pSource, 0, $pBlend, 2) ; immer ganz zum schluss, updatet das dann


while Sleep(20)
	WEnd

Func drawText($input, $x, $y)
	$textSize = _GDIPlus_GraphicsMeasureString($hGraphics, $input, $hFont, $tLayout, $hFormat)
	$width = DllStructGetData($textSize[0],3)
	$height = DllStructGetData($textSize[0],4)
	_GDIPlus_GraphicsDrawString($hGraphics, $input, $x - $width / 2, $y - $height / 2, "Arial", 18)
EndFunc


Func _OnTop()
	WinSetOnTop($hWnd, "", 1)
EndFunc ;==>_OnTop

Func _Erase()
	_GDIPlus_GraphicsClear($hGraphics, 0x00000000)
EndFunc ;==>_Erase