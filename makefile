build :
	javac Main.java

run : 
	java Main

clean :
	find . -type f -name '*.class' -exec rm {} \;