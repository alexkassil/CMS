#!/bin/bash

cd /home/alex/Desktop/Stuff/Eclipse/FinalProject/src/project
for file in *; do
	cp "$file" /home/alex/Desktop/Stuff/TeacherGUIProject/application/"$file"
done
