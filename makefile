build :
	javac Main.java

run : 
	cd class
	java Main

clean :
	find . -type f -name '*.class' -exec rm {} \;