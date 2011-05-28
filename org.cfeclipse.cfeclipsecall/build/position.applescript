@window.properties@

tell application "Finder"
	tell disk (volumeName as string)
		open
		
		set theXOrigin to WINX
		set theYOrigin to WINY
		set theWidth to WINW
		set theHeight to WINH
		
		set theBottomRightX to (theXOrigin + theWidth)
		set theBottomRightY to (theYOrigin + theHeight)
		set dsStore to "\"" & "/Volumes/" & volumeName & "/" & ".DS_STORE\""
		--			do shell script "rm " & dsStore
		
		tell container window
			set current view to icon view
			set toolbar visible to false
			set statusbar visible to false
			set the bounds to {theXOrigin, theYOrigin, theBottomRightX, theBottomRightY}
			set statusbar visible to false
		end tell
		
		set opts to the icon view options of container window
		tell opts
			set icon size to ICON_SIZE
			set arrangement to not arranged
		end tell
		
		-- Positioning
		-- POSITION_CLAUSE
		update without registering applications
		-- Force saving of the size
		delay 1
		@icon.positions@

		set background picture of opts to file ".background:background.png"

		-- Custom icons
		-- my copyIconOfTo(artPath & "/ApplicationsIcon", "/Volumes/" & volumeName & "/Applications")
		
		-- Label colors
		-- set label index of item "Adium.app" to 6
		-- set label index of item "License.txt" to 7
		-- set label index of item "Changes.txt" to 7
		-- set label index of item "Applications" to 4
		
		update without registering applications
		-- Force saving of the size
		delay 1
		
		tell container window
			set statusbar visible to false
			set the bounds to {theXOrigin, theYOrigin, theBottomRightX - 10, theBottomRightY - 10}
		end tell
		
		update without registering applications
	end tell
	
	delay 1
	
	tell disk (volumeName as string)
		tell container window
			set statusbar visible to false
			set the bounds to {theXOrigin, theYOrigin, theBottomRightX, theBottomRightY}
		end tell
		
		update without registering applications
	end tell
	
	--give the finder some time to write the .DS_Store file
	delay 3
	
end tell

on copyIconOfTo(aFileOrFolderWithIcon, aFileOrFolder)
	tell application "Finder" to set f to POSIX file aFileOrFolderWithIcon as alias
	-- grab the file's icon
	my CopyOrPaste(f, "c")
	-- now the icon is in the clipboard
	tell application "Finder" to set c to POSIX file aFileOrFolder as alias
	my CopyOrPaste(result, "v")
end copyIconOfTo

on CopyOrPaste(i, cv)
	tell application "Finder"
		activate
		open information window of i
	end tell
	tell application "System Events" to tell process "Finder" to tell window 1
		keystroke tab -- select icon button
		keystroke (cv & "w") using command down (* (copy or paste) + close window *)
	end tell -- window 1 then process Finder then System Events
end CopyOrPaste