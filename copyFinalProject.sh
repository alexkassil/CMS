#!/bin/bash

cd /home/alex/Desktop/Stuff/Eclipse/FinalProject/src
for file in *.java; do
	cp "$file" /home/alex/Desktop/Stuff/TeacherGUIProject/application/"$file"
done
