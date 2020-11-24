# BakalariGui
BakalariGui is graphical version of https://github.com/Botaniculus/bakalari-client. It's made with Java Swing. 

# Refresh
You can now use refresh token to refresh marks and timetable. 

# addressAndUsername.txt
I added check box to Login window. If the checkbox is selected, it will save the URL and username values to csv txt file. On next startup, it will automatically load these values to Text Fields
Format of addressAndUsername.txt:
URL;username

![alt text](loginWindow.png)

# For Linux users:
To make it look native (GTK) you have to add this to your ~/.profile file:

export _JAVA_OPTIONS='-Dawt.useSystemAAFontSettings=on -Dswing.aatext=true -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel -Dswing.crossplatformlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel'

There is also an another way - bash script with binary payload. Download .jar and stub.sh and type this to your terminal:

cat stub.sh [the name].jar > BakalariGui && chmod +x BakalariGui

Then you can just run BakalariGui and it will run the jar file with GTKLookAndFeel.
