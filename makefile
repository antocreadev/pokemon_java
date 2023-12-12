build :
	javac Main.java
	find . -type f -name '*.class' -exec mv {} ./class \;

run : 
	cd class
	java Main