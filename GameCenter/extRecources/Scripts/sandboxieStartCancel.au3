ShellExecute("C:\Program Files\Sandboxie\Start.exe","default_browser")
$waitForExe = "Start.exe"
ProcessWait($waitForExe)
ProcessClose($waitForExe)