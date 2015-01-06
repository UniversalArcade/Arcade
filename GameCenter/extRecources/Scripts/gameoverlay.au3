#include <GDIPlus.au3>
Global $iGUIWidth = @DesktopWidth
Global $iGUIHeight = @DesktopHeight

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
$image = _GDIPlus_BitmapCreateFromFile("D:\Dropbox\Studi\ButtonRed.png")


$aInfo = _GDIPlus_GraphicsMeasureString($hGraphics, @HOUR & ":" & @MIN & ":" & @SEC, $hFont, $tLayout, $hFormat)
;While Sleep(20)
	_Erase() ; zum cleanen, da sonst das alte nur übermalt wird, kannst aus lust ja mal weglassen, dan siehst dus
	;_GDIPlus_GraphicsDrawStringEx($hGraphics, @HOUR & ":" & @MIN & ":" & @SEC, $hFont, $aInfo[0], $hFormat, $hBrush)
	; hier "malst" du dan alles was du haben willst

	;_GDIPlus_GraphicsFillRect($hGraphics,100,100,200,200,$hBrush)
	;_GDIPlus_GraphicsFillRect($hGraphics,300,300,200,200,$hBrush)
	 ;Local $hPen = _GDIPlus_PenCreate(0xFFABCDEF, 4) ;color format AARRGGBB (hex)


	;_GDIPlus_GraphicsDrawEllipse($hGraphics, 130, 100, 200, 200, $hPen)
	_GDIPlus_GraphicsFillRect($hGraphics,100,100,600,600,$hBrushBackground);

	_GDIPlus_GraphicsFillEllipse($hGraphics, 200, 200, 200, 200, $hBrush)
	_GDIPlus_GraphicsFillEllipse($hGraphics, 500, 500, 200, 200, $hBrush)
	 ;_GDIPlus_GraphicsDrawStringEx($hGraphics, @HOUR & ":" & @MIN & ":" & @SEC, $hFont, $aInfo[0], $hFormat, $hBrush)
	 _GDIPlus_GraphicsDrawString($hGraphics, "FIGHT", 300, 300)
	_GDIPlus_DrawImagePoints($hGraphics,$image,100,100,300,100,100,300)

	_WinAPI_UpdateLayeredWindow($hWnd, $hDC_Window, 0, $pSize, $hDC_Buffer, $pSource, 0, $pBlend, 2) ; immer ganz zum schluss, updatet das dann
;WEnd

while Sleep(20)
	WEnd

Func _OnTop()
	WinSetOnTop($hWnd, "", 1)
EndFunc ;==>_OnTop

Func _Erase()
	_GDIPlus_GraphicsClear($hGraphics, 0x00000000)
EndFunc ;==>_Erase